package ccastro.casal;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import ccastro.casal.SQLite.DBInterface;
import ccastro.casal.Utils.Cursors;

public class InsertarClienteActivity extends AppCompatActivity {
    LinearLayout layoutClient,layoutClientSinDeterminar;
    Button insertarClientes,clienteSinDeterminar;
    DBInterface db;
    Spinner spinnerNumClientes, spinnerTipoClientes,spinnerTipoPago,spinnerMesaFavorita,spinnerTipoComida;
    String arraySpinnerNumClientes [] = {"1","2","3","4","5"};
    String arraySpinnerTipoClientes [] = {"Comedor","Llevar","Ayuntamiento"};
    String arraySpinnerTipoPago[] = {"5,50€","3€","2€","1,5€"};  // Poner lo que sea
    String arraySpinnerMesaFavorita [] = {"Llevar","1","2","3","4","5","6","7","8","9","10","11","12"};
    String arraySpinnerTipoComida [] = {"Normal","Diabetes","Estringente"};
    String tipusClient="0",tipoPago="0",tipoComida="0";
    Integer quantitatAfegir, mesaFavorita=0;
    ArrayAdapter<String> adapterNumClientes, adapterTipoClientes, adapterTipoPago,adapterMesaFavorita,adapterTipoComida;
    EditText nomClient, cognomsClient, observaciones;
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
        adapterNumClientes = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, android.R.id.text1, arraySpinnerNumClientes);
        spinnerNumClientes.setAdapter(adapterNumClientes);
        spinnerTipoClientes = (Spinner) findViewById(R.id.spinnerTipoCliente);
        adapterTipoClientes = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, android.R.id.text1, arraySpinnerTipoClientes);
        spinnerTipoClientes.setAdapter(adapterTipoClientes);
        spinnerTipoPago = (Spinner) findViewById(R.id.spinnerTipoPago);
        adapterTipoPago = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, android.R.id.text1, arraySpinnerTipoPago);
        spinnerTipoPago.setAdapter(adapterTipoPago);
        spinnerMesaFavorita = (Spinner) findViewById(R.id.spinnerMesaFavorita);
        adapterMesaFavorita = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, android.R.id.text1, arraySpinnerMesaFavorita);
        spinnerMesaFavorita.setAdapter(adapterMesaFavorita);
        spinnerTipoComida = (Spinner) findViewById(R.id.spinnerTipoComida);
        adapterTipoComida = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, android.R.id.text1, arraySpinnerTipoComida);
        spinnerTipoComida.setAdapter(adapterTipoComida);
        nomClient = (EditText) findViewById(R.id.editTextNomClient);
        cognomsClient = (EditText) findViewById(R.id.editTextCognomsClients);
        observaciones = (EditText) findViewById(R.id.editTextObservaciones);
        mToolbar.findViewById(R.id.buttonAñadirCliente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nomClient.getText().toString().length()>0 && cognomsClient.getText().toString().length()>0){
                    db.obre();
                    long posicio = db.InserirClient(nomClient.getText().toString(),cognomsClient.getText().toString(),tipusClient,mesaFavorita,tipoPago,tipoComida,observaciones.getText().toString());
                    db.tanca();
                    if (posicio!=-1) {
                        Toast.makeText(InsertarClienteActivity.this, "Cliente "+nomClient.getText().toString()+" añadido!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else Toast.makeText(InsertarClienteActivity.this, "El cliente "+nomClient.getText().toString()+" ya existe", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InsertarClienteActivity.this, "Introduce nombre y apellidos!", Toast.LENGTH_SHORT).show();
                }
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
                quantitatAfegir = position+1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        spinnerMesaFavorita.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mesaFavorita = position;
                Log.d("MESA FAVORITA",Integer.toString(mesaFavorita));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        spinnerTipoClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                tipusClient = Integer.toString(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        spinnerTipoComida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                tipoComida = Integer.toString(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        spinnerTipoPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                tipoPago = Integer.toString(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
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
