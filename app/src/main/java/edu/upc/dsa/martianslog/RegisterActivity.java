package edu.upc.dsa.martianslog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Retrofit;
import edu.upc.dsa.martianslog.models.Usuari;
import edu.upc.dsa.martianslog.service.ApiService;
import edu.upc.dsa.martianslog.service.RegisterUsuari;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity
{
    private Retrofit retrofit;
    TextView usernameRegister;
    TextView nameRegister;
    TextView surnameRegister;
    TextView passwordRegister;

    ApiService apiService;
    public static final String API_URL="http://10.0.2.2:8080/dsaApp/";
    private static final String TAG2="POKEDEX1";

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Conexion
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        usernameRegister=findViewById(R.id.usernameRegisterText);
        nameRegister=findViewById(R.id.nameRegisterText);
        surnameRegister=findViewById(R.id.surnameRegisterText);
        passwordRegister=findViewById(R.id.passwordRegisterText);

    }

    public void registeronClicBtn(View view)
    {
        String user= usernameRegister.getText().toString();
        String name= nameRegister.getText().toString();
        String surname=surnameRegister.getText().toString();
        String pass=passwordRegister.getText().toString();

        RegisterUsuari userRegister= new RegisterUsuari(name,surname,user,pass);
        Call<Usuari> call =apiService.addUser(userRegister);
        call.enqueue(new Callback<Usuari>() {
            @Override
            public void onResponse(Call<Usuari> call, Response<Usuari> response)
            {
                if (!response.isSuccessful())
                {
                    Log.d(TAG2,"AddUser, Error addUser"+response.code());
                    Toast.makeText(RegisterActivity.this,"User name already register", Toast.LENGTH_LONG).show();
                    return;
                }
                Usuari usuari=response.body();
                Toast.makeText(RegisterActivity.this,"Welcome"+usuari.getUsername(), Toast.LENGTH_LONG).show();
                Log.d(TAG2,"AddUser, Successful adduser"+usuari.getUsername());
                SharedPreferences sharedPreferences =getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user",userRegister.getUsername());
                editor.putString("password",userRegister.getPassword());
                Log.d(TAG2,"aDDuSER SAVER USER"+ userRegister.getSurname());
                Log.d(TAG2,"aDDUSER Saver password"+userRegister.getPassword());
                editor.commit();

                Intent intent= new Intent(RegisterActivity.this,PerfilMainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<Usuari> call, Throwable t)
            {
                Toast.makeText(RegisterActivity.this, "Error in getting response from service", Toast.LENGTH_LONG).show();
                Log.d(TAG2,"AddUser Error addUser in getting response from service using retrofit: "+t.getMessage());


            }
        });
    }
}
