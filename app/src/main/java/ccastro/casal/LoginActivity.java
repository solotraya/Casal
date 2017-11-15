package ccastro.casal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ccastro.casal.SQLite.ContracteBD;
import ccastro.casal.SQLite.DBInterface;

public class LoginActivity extends AppCompatActivity {
    DBInterface db;
    Button buttonEntrar, buttonExemples;
    EditText textUserName, textPassword;
    public static String ID_TREBALLADOR, NOM_USUARI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DBInterface(this);
        db.obre();
        // TRABAJADOR admin PARA CUANDO ELIMINE INTRODUCCION DE EJEMPLOS
        db.InserirTreballador("Administrador"," ","admin","xxx");
        db.tanca();
        buttonEntrar = (Button) findViewById(R.id.buttonEntrar);
        buttonExemples = (Button) findViewById(R.id.buttonExemples);
        textPassword = (EditText) findViewById(R.id.textPassword);
        textUserName = (EditText) findViewById(R.id.textUserName);
        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = textUserName.getText().toString();
                String password = textPassword.getText().toString();
                db = new DBInterface(getApplicationContext());
                db.obre();
                Cursor cursor = db.verificarLogin(userName,password);
                if ((cursor != null) && (cursor.getCount() > 0)){
                    mouCursor(cursor);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(view.getContext(),"Login Incorrecto!", Toast.LENGTH_SHORT).show();
                }
                db.tanca();

            }
        });
        buttonExemples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "S'han esborrat les dades de la base de dades i s'han tornat a crear.", Toast.LENGTH_SHORT).show();
                CrearExemplesBD();
            }
        });
    }

    public void mouCursor(Cursor cursor) {
        if (cursor.moveToFirst()) {
            do {
                ID_TREBALLADOR = cursor.getString(cursor.getColumnIndex(ContracteBD.Treballador._ID));
                NOM_USUARI = cursor.getString(cursor.getColumnIndex(ContracteBD.Treballador.NOM_TREBALLADOR))+" "+
                cursor.getString(cursor.getColumnIndex(ContracteBD.Treballador.COGNOMS_TREBALLADOR));
            } while (cursor.moveToNext());
        }
    }
    public void CrearExemplesBD(){
        db.obre();
        db.Esborra();

        // nom - cognoms - tipusClient
        db.InserirClient("Manuela","Torres Cobijo","Comedor");
        db.InserirClient("Manel","Garcia","Comedor");
        db.InserirClient("Remedios","Luque","Ayuntamiento");
        db.InserirClient("Juan","Gomez Fuentes","Llevar");

        // nom, cognoms, username, password
        db.InserirTreballador("Diego","Castro Hurtado","diego","1986");
        db.InserirTreballador("Maria","Cañabate Méndez","maria","1986");

        // nomProducte, preu, tipus
        db.InserirProducte("Café Solo","1.10","Café");
        db.InserirProducte("Café con leche","1.20","Café");
        db.InserirProducte("Carajillo","1.50","Café");
        db.InserirProducte("Cocacola","1.30","Bebidas");
        db.InserirProducte("Agua","0.90","Bebidas");
        db.InserirProducte("Bocadillo grande","2.50","Bocadillos");
        db.InserirProducte("Bocadillo pequeño","2","Bocadillos");
                 //id_producte,id_venta/quantitatProducte
        db.InserirFactura(2,1,1);db.InserirFactura(3,1,2);db.InserirFactura(5,1,4);
        db.InserirFactura(1,2,2);db.InserirFactura(4,2,1);db.InserirFactura(6,2,3);
             //id_client,id_treballador,dataVenta,Cobrada,TotalVenta
        db.InserirVenta(1,1,"2017 11 15","0","10:15");  // SIN ID_FACTURA!!
        db.InserirVenta(2,2,"2017 11 15","1","11:00");
        db.tanca();
    }

}
