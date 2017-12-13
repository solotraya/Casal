package ccastro.casal;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import ccastro.casal.SQLite.DBInterface;
import ccastro.casal.Utils.Cursors;

public class InsertarClienteActivity extends AppCompatActivity {
    LinearLayout layoutClient,layoutClientSinDeterminar;
    Button insertarClientes,clienteSinDeterminar;
    DBInterface db;
    Spinner spinnerNumClientes, spinnerTipoClientes,spinnerTipoPago,spinnerMesaFavorita,spinnerTipoComida;
    String arraySpinnerNumClientes [] = {"1","2","3","4","5"};
    String arraySpinnerTipoClientes [] = {"Comedor","Llevar","Ayuntamiento"};
    /**
     *  SEGUIR POR AQUI
     */
    String arraySpinneTipoPago[] = {"1","2","3","4","5"};
    String arraySpinnerMesaFavorita [] = {"1","2","3","4","5"};
    String arraySpinnertipoComida [] = {"1","2","3","4","5"};
    Integer quantitatAfegir;
    ArrayAdapter<String> adapterNumClientes;
    private android.support.v7.widget.Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBInterface(this);
        setContentView(R.layout.activity_insertar_cliente);
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar_inserir_cliente);
        layoutClient = (LinearLayout) findViewById(R.id.layoutClient);
        layoutClientSinDeterminar = (LinearLayout) findViewById(R.id.layoutClientSinDeterminar);
        spinnerNumClientes = (Spinner) findViewById(R.id.spinnerNumClientes);
        adapterNumClientes = new ArrayAdapter<String> (this,
                android.R.layout.simple_list_item_1, android.R.id.text1, arraySpinnerNumClientes);
        spinnerNumClientes.setAdapter(adapterNumClientes);
        mToolbar.findViewById(R.id.buttonAñadirCliente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        clienteSinDeterminar = (Button) mToolbar.findViewById(R.id.buttonAñadirClienteSinDeterminar);
        clienteSinDeterminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layoutClient.getVisibility()==View.VISIBLE){
                    layoutClient.setVisibility(View.GONE);
                    layoutClientSinDeterminar.setVisibility(View.VISIBLE);
                    mToolbar.findViewById(R.id.buttonAñadirCliente).setVisibility(View.GONE);
                    clienteSinDeterminar.setText("NUEVO CLIENTE NORMAL");

                } else {
                    layoutClient.setVisibility(View.VISIBLE);
                    layoutClientSinDeterminar.setVisibility(View.GONE);
                    mToolbar.findViewById(R.id.buttonAñadirCliente).setVisibility(View.VISIBLE);
                    clienteSinDeterminar.setText("NUEVO CLIENTE ESTANDAR");
                }

            }
        });
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
