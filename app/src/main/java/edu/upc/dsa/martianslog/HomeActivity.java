package edu.upc.dsa.martianslog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.upc.dsa.martianslog.service.ApiService;

public class HomeActivity extends AppCompatActivity {
    Button tienda, perfil, report, faqs, play;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tienda = findViewById(R.id.tienda_btn);
        perfil = findViewById(R.id.perfil_btn);
        report = findViewById(R.id.report_btn);
        faqs = findViewById(R.id.faqs_btn);
        //Codi per recollir el username del login i imprimirlo
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

    }

    public void goPerfil(View view)
    {
        Intent intent = new Intent(this,PerfilMainActivity.class);
        String username1 = username.toString();
        intent.putExtra("username", username1);
        startActivity(intent);

    }

    public void goReport(View view)
    {
        Intent intent = new Intent(this,ReportActivity.class);
        startActivity(intent);
    }
    public void goTienda(View view)
    {
        Intent intent = new Intent(this,TiendaActivity.class);
        startActivity(intent);
    }
    public void goPregunta(View view) {
        Intent intent = new Intent(this, PreguntaActivity.class);
        startActivity(intent);
    }
    public void goFaqs(View view) {
        Intent intent = new Intent(this, FaqActivity.class);
        startActivity(intent);
    }
}
