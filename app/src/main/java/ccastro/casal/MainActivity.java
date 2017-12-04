package ccastro.casal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ccastro.casal.SQLite.DBInterface;

import static ccastro.casal.LoginActivity.NOM_USUARI;

public class MainActivity extends AppCompatActivity {
    DBInterface db;
    Button  buttonClients, buttonVenta, buttonComedor, buttonBarra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this,NOM_USUARI, Toast.LENGTH_SHORT).show();

        db = new DBInterface(this);

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
        buttonComedor = (Button) findViewById(R.id.buttonComedor);
        buttonComedor.setOnClickListener( new View.OnClickListener(){
                                            @Override
                                            public void onClick(View view) {
                                                startActivity(new Intent(MainActivity.this, MesaActivity.class));
                                            }
                                        }
        );
        buttonBarra = (Button) findViewById(R.id.buttonBarra);
        buttonBarra.setOnClickListener( new View.OnClickListener(){
                                              @Override
                                              public void onClick(View view) {
                                                  startActivity(new Intent(MainActivity.this, PedidoActivity.class));
                                              }
                                          }
        );
    }
}
