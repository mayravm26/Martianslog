package edu.upc.dsa.martianslog;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
    private ApiService apiService;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);
        recycle = findViewById(R.id.recycle);
        //dinero = findViewById(R.id.titulo);
        //sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycle.setLayoutManager(llm);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        getStoreProducts();
    }
    private void getStoreProducts() {
        Call<List<Product>> call = apiService.getStoreProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
                    Log.d("API", "Error code: " + response.code());
                    Toast.makeText(TiendaActivity.this, "Failed to fetch products", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Product> products = response.body();
                for (Product product : products) {
                    Log.d("API", "Product: " + product.getName());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("API", "Error: " + t.getMessage());
                Toast.makeText(TiendaActivity.this, "Error fetching products", Toast.LENGTH_SHORT).show();
            }
        });
    }
}






