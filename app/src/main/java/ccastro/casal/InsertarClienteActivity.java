package ccastro.casal;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import ccastro.casal.SQLite.DBInterface;
import ccastro.casal.Utils.Cursors;

public class InsertarClienteActivity extends AppCompatActivity {
    Button insertarClientes;
    DBInterface db;
    Spinner spinnerNumClientes;
    String arraySpinner [] = {"1","2","3","4","5"};
    Integer quantitatAfegir;
    ArrayAdapter<String> adapterNumClientes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBInterface(this);
        setContentView(R.layout.activity_insertar_cliente);
        spinnerNumClientes = (Spinner) findViewById(R.id.spinnerNumClientes);
        adapterNumClientes = new ArrayAdapter<String> (this,
                android.R.layout.simple_list_item_1, android.R.id.text1, arraySpinner);
        spinnerNumClientes.setAdapter(adapterNumClientes);
        spinnerNumClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0: quantitatAfegir = 1; break;
                    case 1: quantitatAfegir = 2; break;
                    case 2: quantitatAfegir = 3; break;
                    case 3: quantitatAfegir = 4; break;
                    case 4: quantitatAfegir = 5; break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        insertarClientes = (Button) findViewById(R.id.buttonAfegirClientes);
        insertarClientes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Integer quantitat;
                db.obre();
                Cursor cursor = db.obtenirNumeroDeClients("#Cliente Comedor");
                quantitat = Cursors.cursorQuantitat(cursor);
                for (int i=quantitat+1; i<=quantitat+quantitatAfegir; i++){
                    long idCliente = db.InserirClient("#Cliente Comedor",""+Integer.toString(i),"0",12,"0","0","Cliente sin identificar");
                }
                db.tanca();
                finish();
            }
        });
    }
}
