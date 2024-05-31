package edu.upc.dsa.martianslog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.upc.dsa.martianslog.models.Product;
import edu.upc.dsa.martianslog.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TiendaActivity extends AppCompatActivity {

    TextView dinero;
    SharedPreferences sharedPreferences;
    List<Product> productList;
    public static final String API_URL = "http://10.0.2.2:8080/dsaApp/";
    ApiService apiService;
    Button buttonCompra;
    private double totalAmount = 0;
    private List<Product> carrito = new ArrayList<>();


    RecyclerView recycle;
    private MyAdapterTienda adapter;
    private RecyclerView.LayoutManager layoutManager;
    //private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);

        // Initialize RecyclerView
        recycle = findViewById(R.id.recycle);
        recycle.setLayoutManager(new LinearLayoutManager(this));

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(TiendaActivity.this);
        recycle.setLayoutManager(layoutManager);

        // Set the adapter
        adapter = new MyAdapterTienda();
        recycle.setAdapter(adapter);

        // Initialize SharedPreferences and set coins text
        dinero = findViewById(R.id.dinero);
        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        dinero.setText("Coins: " + sharedPreferences.getInt("coins", 0));

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        // Fetch products from API
        fetchProducts();

        // Initialize the purchase button and set an OnClickListener
        buttonCompra = findViewById(R.id.button_compra);
        buttonCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                efectuarCompra();
            }
        });
    }

    private void fetchProducts() {
        Call<List<Product>> call = apiService.getStoreProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    adapter.setData(response.body());
                    /*productList = response.body();
                    RecycleAdapter adapter = new RecycleAdapter(TiendaActivity.this, productList);
                    recycle.setAdapter(adapter);

                     */
                } else {
                    Log.e("Error", "Failed to fetch products");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("Error", "Network error: " + t.getMessage());
            }
        });
    }

    private void efectuarCompra() {
        String username = sharedPreferences.getString("username", "");
        for (Product product : carrito) {
            Call<List<Product>> call = apiService.buyProduct(username, product.getIdProduct());
            call.enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if (response.isSuccessful()) {
                        List<Product> updatedInventory = response.body();
                        Toast.makeText(TiendaActivity.this, "Compra efectuada", Toast.LENGTH_SHORT).show();
                        // Update the user's coins and clear the cart
                        totalAmount = 0;
                        carrito.clear();
                        updateDineroTextView();
                    } else {
                        Log.e("Error", "Failed to buy product");
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    Log.e("Error", "Network error: " + t.getMessage());
                }
            });
        }
    }

    private void updateDineroTextView() {
        dinero.setText("Coins: " + sharedPreferences.getInt("coins", 0));
    }

    public void addToCart(Product product) {
        totalAmount += product.getPrice();
        carrito.add(product);
        updateDineroTextView();
    }

    class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {
        private List<Product> list;
        private Context context;

        public RecycleAdapter(Context context, List<Product> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Product product = list.get(position);
            holder.id.setText("Id: " + product.getIdProduct());
            holder.name.setText("Nombre: " + product.getName());
            holder.price.setText("Precio: " + product.getPrice());

            holder.addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context instanceof TiendaActivity) {
                        ((TiendaActivity) context).addToCart(product);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView id, name, price;
            Button addButton;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                id = itemView.findViewById(R.id.id);
                name = itemView.findViewById(R.id.name);
                price = itemView.findViewById(R.id.price);
                addButton = itemView.findViewById(R.id.button_add_cart);
            }
        }
    }
}