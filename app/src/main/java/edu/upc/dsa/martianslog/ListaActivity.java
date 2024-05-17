package edu.upc.dsa.martianslog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.analytics.ecommerce.Product;

import java.util.List;

import edu.upc.dsa.martianslog.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaActivity extends AppCompatActivity {
    private ApiService apiService;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        Button getProductsButton = findViewById(R.id.productos);
        getProductsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStoreProducts();

            }
        });
    }
    private void getStoreProducts() {
        Call<List<Product>> call = apiService.getStoreProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
                    Log.d("API", "Error code: " + response.code());
                    Toast.makeText(ListaActivity.this, "Failed to fetch products", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Product> products = response.body();
                for (Product product : products) {
                    //Log.d("API", "Product: " + product.getId()); revisarlo no se porque no va
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("API", "Error: " + t.getMessage());
                Toast.makeText(ListaActivity.this, "Error fetching products", Toast.LENGTH_SHORT).show();
            }
        });
    }
}






