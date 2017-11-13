package ccastro.casal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ccastro.casal.SQLite.DBInterface;

//PUSH TEST .- Toni

public class MainActivity extends AppCompatActivity {
    DBInterface db;
    Button buttonExemples, buttonClients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBInterface(this);
        buttonExemples = (Button) findViewById(R.id.buttonExemples);
        buttonExemples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "S'han esborrat les dades de la base de dades i s'han tornat a crear.", Toast.LENGTH_SHORT).show();
                CrearExemplesBD();
            }
        });
        buttonClients = (Button) findViewById(R.id.buttonClients);
        buttonClients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ClientActivity.class));
            }
        });
    }
    public void CrearExemplesBD(){
        db.obre();
        db.Esborra();

        // nom - cognoms - tipusClient
        db.InserirClient("Manuela","Torres Cobijo","Comedor");
        db.InserirClient("Manel","Garcia","Comedor");
        db.InserirClient("Remedios","Luque","Ayuntamiento");
        db.InserirClient("Juan","Gomez Fuentes","Llevar");

        // nomProducte, preu, tipus
        db.InserirProducte("Café Solo","1.10","Cafe");
        db.InserirProducte("Café con leche","1.20","Cafe");
        db.InserirProducte("Carajillo","1.50","Cafe");
        db.InserirProducte("Cocacola","1.30","Bebidas");
        db.InserirProducte("Agua","0.90","Bebidas");
        db.InserirProducte("Bocadillo grande","2.50","Bocadillos");
        db.InserirProducte("Bocadillo pequeño","2","Bocadillos");

        //dataActual
        // db.InserirVenta("12/11/2017");
        db.tanca();
    }

}
