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
    Button buttonExemples, buttonClients, buttonVenta;
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
        buttonVenta = (Button) findViewById(R.id.buttonVenta);
        buttonVenta.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, VentaActivity.class));
            }
                                        }
        );
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
                 //id_producte,id_venta/quantitatProducte
        db.InserirFactura(2,1,1);db.InserirFactura(3,1,2);db.InserirFactura(5,1,4);
        db.InserirFactura(1,2,2);db.InserirFactura(4,2,1);db.InserirFactura(6,2,3);
                //id_client,dataVenta,Cobrada,TotalVenta
        db.InserirVenta(1,"2017 11 15","0","10:15");  // SIN ID_FACTURA!!
        db.InserirVenta(2,"2017 11 15","1","11:00");
        db.tanca();
    }

}
