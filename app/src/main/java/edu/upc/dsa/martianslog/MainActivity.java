package edu.upc.dsa.martianslog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.upc.dsa.martianslog.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    Button ir_login_tv;
    Button ir_register_tv;
    ApiService apiService;
    public static final String API_URL="http://10.0.2.2:8080/dsaApp/";
    private static final String TAG= "POKEDEX";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ir_login_tv =findViewById(R.id.ir_login_tv);
        ir_register_tv = findViewById(R.id.ir_register_tv);

    }

    public void ir_login_tv(View view)
    {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);

    }

    public void ir_register_tv(View view)
    {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }



}