package edu.upc.dsa.martianslog;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import edu.upc.dsa.martianslog.models.Pregunta;
import edu.upc.dsa.martianslog.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultaActivity extends AppCompatActivity {

    EditText editTextDate, editTextMessage, editTextTitle, editTextSender;
    Button buttonEnviar;
    SharedPreferences sharedPreferences;
    ApiService apiService;

    // URL base de tu API
    public static final String API_URL = "http://10.0.2.2:8080/dsaApp/";  // Ajusta con tu URL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        // Inicializar vistas
        editTextDate = findViewById(R.id.etDate);
        editTextMessage = findViewById(R.id.etMessage);
        editTextTitle = findViewById(R.id.etTitle);
        editTextSender = findViewById(R.id.etSender);
        buttonEnviar = findViewById(R.id.btnSubmit);

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);

        // Configuración de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        // Configurar OnClickListener para el botón de enviar
        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validar campos de entrada
                if (TextUtils.isEmpty(editTextDate.getText().toString()) ||
                        TextUtils.isEmpty(editTextMessage.getText().toString()) ||
                        TextUtils.isEmpty(editTextTitle.getText().toString()) ||
                        TextUtils.isEmpty(editTextSender.getText().toString())) {
                    String message = "Todos los campos son obligatorios";
                    Toast.makeText(ConsultaActivity.this, message, Toast.LENGTH_LONG).show();
                } else {
                    // Crear objeto Pregunta con los datos de entrada
                    Pregunta pregunta = new Pregunta();
                    pregunta.setDate(editTextDate.getText().toString());
                    pregunta.setMessage(editTextMessage.getText().toString());
                    pregunta.setTitle(editTextTitle.getText().toString());
                    pregunta.setSender(editTextSender.getText().toString());

                    // Llamar al método para añadir la pregunta
                    addQuestion(pregunta);
                }
            }
        });
    }

    // Método para añadir la pregunta utilizando Retrofit
    private void addQuestion(Pregunta pregunta) {
        Call<Void> questionResponseCall = apiService.addQuestion(pregunta);
        questionResponseCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Éxito: mostrar mensaje y finalizar actividad
                    String message = "Consulta enviada correctamente";
                    Toast.makeText(ConsultaActivity.this, message, Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    // Error al procesar la solicitud
                    String message = "Ocurrió un error al enviar la consulta";
                    Toast.makeText(ConsultaActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Error de red o fallo en la llamada
                Log.e("onFailure", "Error: " + t.getMessage());
                String message = "Error de red: " + t.getLocalizedMessage();
                Toast.makeText(ConsultaActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
