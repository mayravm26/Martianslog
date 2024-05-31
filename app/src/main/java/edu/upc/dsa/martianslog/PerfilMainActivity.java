package edu.upc.dsa.martianslog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

import edu.upc.dsa.martianslog.models.ProfileUser;
import edu.upc.dsa.martianslog.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerfilMainActivity extends AppCompatActivity
{
    public static final String API_URL="http://10.0.2.2:8080/dsaApp/";
    ApiService apiService;

    //Codi per recollir el username del login i imprimirlo
    ProfileUser user = new ProfileUser();
    String username_login;
    TextView nameTxt;
    TextView surnameTxt;
    TextView coinsTxt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        nameTxt = findViewById(R.id.nametxtview);
        coinsTxt = findViewById(R.id.coinsTextView);
        surnameTxt = findViewById(R.id.surnameTextView);
        nameTxt.setText("Name");
        surnameTxt.setText("Surname");
        coinsTxt.setText("coins");



        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);


        //Codi del boto per anar a la botiga
        Button buttonOpenListaActivity = findViewById(R.id.tienda);
        buttonOpenListaActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilMainActivity.this, TiendaActivity.class);
                startActivity(intent);
            }});


        //Codi per recollir el username del login i imprimirlo
        Intent intent = getIntent();
        username_login = intent.getStringExtra("username");
        //Codi per imprimir el username rebut del loginActivity
        TextView usernametxt = findViewById(R.id.usernameTextView);
        usernametxt.setText(username_login);

        //codi per cridar a la funció getUser
        Call<ProfileUser> call = apiService.getUser(username_login);
        call.enqueue(new Callback<ProfileUser>(){

            @Override
            public void onResponse(Call<ProfileUser> call, Response<ProfileUser> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    String name = user.getName();
                    String surname = user.getSurname();
                    double coins = user.getCoins();
                    PerfilMainActivity.this.nameTxt.setText(name);
                    PerfilMainActivity.this.surnameTxt.setText(surname);
                    PerfilMainActivity.this.coinsTxt.setText(""+coins);
                }
                else Log.e("Error", "Failed to fetch user");
            }

            @Override
            public void onFailure(Call<ProfileUser> call, Throwable t) {
                Log.e("Error", "Network error: " + t.getMessage());
            }

        });

        //Cridem a la funcio fetchUser per omplir dades del perfil
        //fetchUser();

    }

    public void showDataOnClick(View view){
        Call<ProfileUser> call = apiService.getUser(username_login);
        call.enqueue(new Callback<ProfileUser>(){

            @Override
            public void onResponse(Call<ProfileUser> call, Response<ProfileUser> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    String name = user.getName();
                    String surname = user.getSurname();
                    double coins = user.getCoins();
                    //TextView nametxt = findViewById(R.id.nameTextView);
                    //PerfilMainActivity.this.nameTxt.setText(name);
                    PerfilMainActivity.this.nameTxt.setText("Name: "+ name);
                    //TextView surnametxt = findViewById(R.id.surnameTextView);
                    PerfilMainActivity.this.surnameTxt.setText(surname);
                    //TextView coinstxt = findViewById(R.id.coinsTextView);
                    PerfilMainActivity.this.coinsTxt.setText((int) coins);
                }
                else Log.e("Error", "Failed to fetch user");
            }

            @Override
            public void onFailure(Call<ProfileUser> call, Throwable t) {
                Log.e("Error", "Network error: " + t.getMessage());
            }

        });
    }

    //Codi per cridar a la API i que retorni un usuari
   /*private void fetchUser(){

        Call<ProfileUser> call = apiService.getUser(username_login);
        call.enqueue(new Callback<ProfileUser>(){

            @Override
            public void onResponse(Call<ProfileUser> call, Response<ProfileUser> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    String name = user.getName();
                    String surname = user.getSurname();
                    int coins = user.getCoins();
                    TextView nametxt = findViewById(R.id.nameTextView);
                    nametxt.setText(name);
                    TextView surnametxt = findViewById(R.id.surnameTextView);
                    surnametxt.setText(surname);
                    TextView coinstxt = findViewById(R.id.coinsTextView);
                    coinstxt.setText(coins);
                }
                else Log.e("Error", "Failed to fetch user");
            }

            @Override
            public void onFailure(Call<ProfileUser> call, Throwable t) {
                Log.e("Error", "Network error: " + t.getMessage());
            }

        });

    }

    */


}


