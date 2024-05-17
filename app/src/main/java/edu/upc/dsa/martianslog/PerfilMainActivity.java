package edu.upc.dsa.martianslog;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class PerfilMainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Button buttonOpenListaActivity = findViewById(R.id.lista);
        buttonOpenListaActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilMainActivity.this, ListaActivity.class);
                startActivity(intent);
            }});
    }
}


