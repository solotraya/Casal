package ccastro.casal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SeleccionPlatoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_plato);
        findViewById(R.id.buttonPrimeros).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeleccionPlatoActivity.this, PlatoActivity.class);
                intent.putExtra("PRIMER_PLATO",true);
                startActivity(intent);
            }
        });
        findViewById(R.id.buttonSegundos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeleccionPlatoActivity.this, PlatoActivity.class);
                intent.putExtra("PRIMER_PLATO",false);
                startActivity(intent);
            }
        });
    }
}
