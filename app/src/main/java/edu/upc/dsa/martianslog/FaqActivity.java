package edu.upc.dsa.martianslog;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.upc.dsa.martianslog.models.FAQ;
import edu.upc.dsa.martianslog.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FaqActivity extends AppCompatActivity {
    ApiService apiService;
    private RecyclerView recyclerViewFAQ;
    private AdapterFAQ adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static final String API_URL = "http://147.83.7.204:80/dsaApp/";
    private static final String TAG = "POKEDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        // Initialize RecyclerView
        recyclerViewFAQ = findViewById(R.id.recycler_view);
        recyclerViewFAQ.setLayoutManager(new LinearLayoutManager(this));

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(FaqActivity.this);
        recyclerViewFAQ.setLayoutManager(layoutManager);

        // Set the adapter
        adapter = new AdapterFAQ();
        recyclerViewFAQ.setAdapter(adapter);

        //Declaración del retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);


        //Funcio per cridar FAQs
        Call<List<FAQ>> call = apiService.getFAQs();
        call.enqueue(new Callback<List<FAQ>>() {
            @Override
            public void onResponse(Call<List<FAQ>> call, Response<List<FAQ>> response) {
                if (response.isSuccessful()) {
                    adapter.setData(response.body());

                } else {
                    Log.e("Error", "Failed");
                }
            }

            @Override
            public void onFailure(Call<List<FAQ>> call, Throwable t) {
                Log.e("Error", "Network error: " + t.getMessage());
            }
        });

    }

    private void fetchFAQs(){
        Call<List<FAQ>> call = apiService.getFAQs();
        call.enqueue(new Callback<List<FAQ>>() {
            @Override
            public void onResponse(Call<List<FAQ>> call, Response<List<FAQ>> response) {
                if (response.isSuccessful()) {
                    adapter.setData(response.body());

                } else {
                    Log.e("Error", "Failed");
                }
            }

            @Override
            public void onFailure(Call<List<FAQ>> call, Throwable t) {
                Log.e("Error", "Network error: " + t.getMessage());
            }
        });
    }


}