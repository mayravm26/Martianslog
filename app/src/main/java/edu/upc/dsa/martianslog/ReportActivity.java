package edu.upc.dsa.martianslog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.upc.dsa.martianslog.models.Report;
import edu.upc.dsa.martianslog.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportActivity extends AppCompatActivity {

    public static final String API_URL="http://10.0.2.2:8080/dsaApp/";
    //public static final String API_URL="http://147.83.7.204:8080/dsaApp/";
    ApiService apiService;
    TextView informertxt;
    TextView datetxt;
    TextView messagetxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        informertxt = (TextView) findViewById(R.id.reportInformer_txt);
        datetxt = (TextView) findViewById(R.id.reportDate_txt);
        messagetxt = (TextView) findViewById(R.id.reportMessage_txt);

        //Declaración del retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }
    public void sendReport(View view){
        Report r = new Report(this.datetxt.getText().toString(), this.informertxt.getText().toString(), this.messagetxt.getText().toString());
        Call<Report> call = apiService.addReport(r);
        call.enqueue(new Callback<Report>(){

            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {
                if(response.isSuccessful()) {
                    Report report = response.body();
                    Toast.makeText(ReportActivity.this, "Incidència reportada, gràcies pel feedback: "+ report.getInformer(), Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(ReportActivity.this, "No s'ha pogut reportar el problema", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Report> call, Throwable t) {
                Toast.makeText(ReportActivity.this, "Error en la conecion del servico", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void goPerfil(){
        Intent intent = new Intent(ReportActivity.this, PerfilMainActivity.class);
        startActivity(intent);
    }
}