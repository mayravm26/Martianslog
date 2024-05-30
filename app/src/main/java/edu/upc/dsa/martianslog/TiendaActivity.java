package edu.upc.dsa.martianslog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.upc.dsa.martianslog.models.Product;
import edu.upc.dsa.martianslog.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TiendaActivity extends AppCompatActivity {
    RecyclerView recycle;
    TextView dinero;
    SharedPreferences sharedPreferences;
    List<Product> productList;
    public static final String API_URL="http://10.0.2.2:8080/dsaApp/";
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);

        // Initialize RecyclerView
        recycle = findViewById(R.id.recycle);
        recycle.setLayoutManager(new LinearLayoutManager(this));

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
    }

    private void fetchProducts() {
        Call<List<Product>> call = apiService.getStoreProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    productList = response.body();
                    RecycleAdapter adapter = new RecycleAdapter(TiendaActivity.this, productList);
                    recycle.setAdapter(adapter);
                    adapter.setOnClickListener(view -> {
                        int position = recycle.getChildAdapterPosition(view);
                        Product selectedProduct = productList.get(position);
                        Toast.makeText(getApplicationContext(), selectedProduct.getName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TiendaActivity.this, Product.class);
                        intent.putExtra("Id", selectedProduct.getIdProduct());
                        intent.putExtra("Nombre", selectedProduct.getName());
                        intent.putExtra("Precio", selectedProduct.getPrice());
                        intent.putExtra("Url", selectedProduct.getUrl());
                        startActivity(intent);
                    });
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

    class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> implements View.OnClickListener {
        private List<Product> list;
        private Context context;
        private View.OnClickListener listener;

        public RecycleAdapter(Context context, List<Product> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
            view.setOnClickListener(this);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Product product = list.get(position);
            holder.id.setText("Id: " + product.getIdProduct());
            holder.name.setText("Nombre: " + product.getName());
            holder.price.setText("Precio: " + product.getPrice());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setOnClickListener(View.OnClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onClick(view);
            }
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView id, name, price;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                id = itemView.findViewById(R.id.id);
                name = itemView.findViewById(R.id.name);
                price = itemView.findViewById(R.id.price);
            }
        }
    }
}
