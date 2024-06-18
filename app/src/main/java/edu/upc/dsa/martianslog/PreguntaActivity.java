package edu.upc.dsa.martianslog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.upc.dsa.martianslog.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreguntaActivity extends AppCompatActivity {
    EditText ValorFecha;
    EditText ValorMensaje;
    EditText ValorTitulo;
    EditText ValorSender;
    Button entregaButton, bt_back;
    SharedPreferences sharedPreferences;

    ApiService apiService;
    public static final String API_URL = "http://147.83.7.204:80/dsaApp/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);

        // Inicialización de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        // Inicialización de vistas
        ValorFecha = findViewById(R.id.ValorFecha);
        ValorMensaje = findViewById(R.id.ValorMensaje);
        ValorTitulo = findViewById(R.id.ValorTitulo);
        ValorSender = findViewById(R.id.ValorSender);
        entregaButton = findViewById(R.id.buttonEnviar);
        bt_back = findViewById(R.id.buttonVolver);

        entregaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarConsulta();
            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresar a la actividad anterior o manejar la acción de regresar
                finish();
            }
        });
    }

    public void realizarConsulta() {
        String fecha = ValorFecha.getText().toString();
        String titulo = ValorTitulo.getText().toString();
        String mensaje = ValorMensaje.getText().toString();
        String sender = ValorSender.getText().toString();

        edu.upc.dsa.martianslog.models.Pregunta pregunta = new edu.upc.dsa.martianslog.models.Pregunta();
        pregunta.setDate(fecha);
        pregunta.setTitle(titulo);
        pregunta.setMessage(mensaje);
        pregunta.setSender(sender);

        Call<Void> call = apiService.enviarPregunta(pregunta);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PreguntaActivity.this, "Pregunta enviada exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PreguntaActivity.this, "Error al enviar la pregunta", Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(PreguntaActivity.this, "Fallo en la conexión", Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Fallo en la conexión: " + t.getMessage());
            }
        });
    }
}
