package edu.upc.dsa.martianslog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import edu.upc.dsa.martianslog.service.ApiService;
import edu.upc.dsa.martianslog.service.LoginUsuari;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity
{
    TextView usertext_login;
    TextView passtext_log;
    ApiService apiService;
    public static final String API_URL="http://10.0.2.2:8080/dsaApp/";
    private static final String TAG= "POKEDEX";

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            //Declaración del retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiService = retrofit.create(ApiService.class);

        }


        public void Onclickbtn(View view) //Boton funcion consulta
        {
            //Toast.makeText(this,"InicioSesion",Toast.LENGTH_LONG).show();
            usertext_login= (TextView) findViewById(R.id.usertext_login);
            passtext_log=(TextView) findViewById(R.id.passtex_log);

            LoginUsuari user =new LoginUsuari(usertext_login.getText().toString(),passtext_log.getText().toString());
            Log.d(TAG,"LoginUsername Login user " + user.getUsername()+" and password: " + user.getPassword());
            Call<LoginUsuari> call= apiService.loginUser(user);
            call.enqueue(new Callback<LoginUsuari>() {
                @Override
                public void onResponse(Call<LoginUsuari> call, Response<LoginUsuari> response)
                {
                    if(response.isSuccessful())
                    {
                        LoginUsuari loginUsuari = response.body();
                        Toast.makeText(LoginActivity.this,"Welcome:" + loginUsuari.getUsername(),Toast.LENGTH_LONG).show();
                        Log.d(TAG,"lOGINuSER sUCCESSFUL LOGINUSER" + loginUsuari.getUsername());
                        saveSharedPreference(loginUsuari);
                        Intent intent = new Intent(LoginActivity.this,PerfilMainActivity.class);
                        String usernamelogin = usertext_login.getText().toString();
                        intent.putExtra("username", usernamelogin);
                        startActivity(intent);
                        finish();



                        /*LoginUsuari loginUsuari = response.body();
                        Toast.makeText(LoginActivity.this,"Welcome:" + loginUsuari.getUsername(),Toast.LENGTH_LONG).show();
                        Log.d(TAG,"lOGINuSER sUCCESSFUL LOGINUSER" + loginUsuari.getUsername());
                        saveSharedPreference(loginUsuari);
                        //Intent intent = new Intent(LoginActivity.this,PerfilMainActivity.class);
                        //startActivity(intent);
                        Intent intent = new Intent(LoginActivity.this, PerfilMainActivity.class);
                        String username = usertext_login.getText().toString();
                        intent.putExtra("username", username);
                        startActivity(intent);
                        finish();

                         */

                    } else {
                        Log.d(TAG, "LoginUser Error loguin User:" + response.code());
                        Toast.makeText(LoginActivity.this, "Usuario no registrado", Toast.LENGTH_LONG).show();
                        //return;
                    }
                }

                @Override
                public void onFailure(Call<LoginUsuari> call, Throwable t)
                {
                    Toast.makeText(LoginActivity.this, "HELLOOOO!", Toast.LENGTH_LONG);
                    Toast.makeText(LoginActivity.this, "Error en la conecion del servico", Toast.LENGTH_LONG).show();
                    Log.d(TAG,"lOGUINuSER Error loguin user response from service retrofit:"+ t.getMessage());
                }
            });


        }

        public void saveSharedPreference(LoginUsuari loginUsuari)
        {
            SharedPreferences sharedPreferences= getSharedPreferences("credenciales", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user",loginUsuari.getUsername());
            editor.putString("password",loginUsuari.getPassword());
            Log.d(TAG,"LoguinUser: save user: "+ loginUsuari.getUsername());
            Log.d(TAG,"LoginUser: save password: "+ loginUsuari.getPassword());
            editor.commit();
        }
}
