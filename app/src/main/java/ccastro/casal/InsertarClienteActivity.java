package ccastro.casal;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import ccastro.casal.SQLite.ContracteBD;
import ccastro.casal.SQLite.DBInterface;
import ccastro.casal.Utils.Cursors;
import ccastro.casal.Utils.Missatges;

public class InsertarClienteActivity extends AppCompatActivity {
    LinearLayout layoutClient,layoutClientSinDeterminar;
    TextView tituloperacion;
    Button insertarClientes,clienteSinDeterminar;
    DBInterface db;
    Spinner spinnerNumClientes, spinnerTipoClientes,spinnerTipoPago,spinnerMesaFavorita,spinnerTipoComida;
    String arraySpinnerNumClientes [] = {"1","2","3","4","5"};
    String arraySpinnerTipoClientes [] = {"Comedor","Llevar","Ayuntamiento","Especiales"};
    String arraySpinnerTipoPago[] = {"5,70€","4,28€","2,85€","1,43€","0€"};  // Poner lo que sea
    String arraySpinnerMesaFavorita [] = {"Llevar","1","2","3","4","5","6","7","8","9","10","11","12"};
    String arraySpinnerTipoComida [] = {"Normal","Diabetes","Estringente"};
    String tipusClient="0",tipoPago="0",tipoComida="0",id_cliente;
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
        tituloperacion = (TextView) findViewById(R.id.textViewOperacion);
        tituloperacion.setPaintFlags(tituloperacion.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
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
        adapterTipoComida = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, arraySpinnerTipoComida);
        spinnerTipoComida.setAdapter(adapterTipoComida);
        nomClient = (EditText) findViewById(R.id.editTextNomClient);
        cognomsClient = (EditText) findViewById(R.id.editTextCognomsClients);
        observaciones = (EditText) findViewById(R.id.editTextObservaciones);
        mToolbar.findViewById(R.id.buttonModificarCliente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nomClient.getText().toString().length()>0 && cognomsClient.getText().toString().length()>0){
                    db.obre();
                    long resultat = db.ActualitzarClient(Integer.parseInt(id_cliente),nomClient.getText().toString(),cognomsClient.getText().toString(),tipusClient,mesaFavorita,tipoPago,tipoComida,observaciones.getText().toString());
                    db.tanca();
                    Log.d("ACTUALIZADO",Long.toString(resultat));
                    if (resultat!=0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setMessage("Cliente "+nomClient.getText().toString()+" modificado satisfactoriamente!")
                                .setTitle("CLIENTE MODIFICADO")
                                .setIcon(R.drawable.acierto)
                                .setPositiveButton("ACERPTAR",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                finish();
                                            }
                                        }
                                );
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else Missatges.AlertMissatge("ERROR AL MODIFICAR", "El cliente "+nomClient.getText().toString()+" ya existe", R.drawable.error2, InsertarClienteActivity.this);
                }

            }
        });
        mToolbar.findViewById(R.id.buttonAñadirCliente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nomClient.getText().toString().length()>0 && cognomsClient.getText().toString().length()>0){
                    db.obre();
                    long posicio = db.InserirClient(nomClient.getText().toString(),cognomsClient.getText().toString(),tipusClient,mesaFavorita,tipoPago,tipoComida,observaciones.getText().toString());
                    db.tanca();
                    if (posicio!=-1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setMessage("Cliente "+nomClient.getText().toString()+" añadido satisfactoriamente!")
                                .setTitle("CLIENTE AÑADIDO")
                                .setIcon(R.drawable.acierto)
                                .setPositiveButton("ACERPTAR",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                               finish();
                                            }
                                        }
                                );
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else Missatges.AlertMissatge("ERROR AL AÑADIR", "El cliente "+nomClient.getText().toString()+" ya existe", R.drawable.error2, InsertarClienteActivity.this);
                } else {
                    Missatges.AlertMissatge("ERROR AL AÑADIR", "Introduce nombre y apellidos!", R.drawable.error2, InsertarClienteActivity.this);
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
            if (view!=null) ((TextView) view).setTextColor(Color.WHITE);
                quantitatAfegir = position+1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        spinnerMesaFavorita.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mesaFavorita = position;
                if (view!=null) ((TextView) view).setTextColor(Color.WHITE);
                Log.d("MESA FAVORITA",Integer.toString(mesaFavorita));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        spinnerTipoClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (view!=null)((TextView) view).setTextColor(Color.WHITE);
                tipusClient = Integer.toString(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        spinnerTipoComida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (view!=null)((TextView) view).setTextColor(Color.WHITE);
                tipoComida = Integer.toString(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        spinnerTipoPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (view!=null) ((TextView) view).setTextColor(Color.WHITE);
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
                Cursor cursor = db.obtenirNumeroDeClients("~Cliente Comedor");
                quantitat = Cursors.cursorQuantitat(cursor);
                for (int i=quantitat+1; i<=quantitat+quantitatAfegir; i++){
                    long idCliente = db.InserirClient("~Cliente Comedor",""+Integer.toString(i),"3",12,"0","0","Cliente sin identificar");
                }
                db.tanca();
                finish();
            }
        });
        getIntents();
    }
    public void getIntents(){
        if (getIntent().hasExtra("ID_CLIENTE")){  // pasado desde ClientActivity
            mToolbar.findViewById(R.id.buttonAñadirCliente).setVisibility(View.GONE);
            mToolbar.findViewById(R.id.buttonAñadirClienteSinDeterminar).setVisibility(View.GONE);
            mToolbar.findViewById(R.id.buttonModificarCliente).setVisibility(View.VISIBLE);
            id_cliente = getIntent().getExtras().getString("ID_CLIENTE");
            db.obre();
            Cursor cursor = db.obtenirDadesClientPerId(id_cliente);
            mouCursor(cursor);
            db.tanca();
            tituloperacion.setText("MODIFICAR CLIENTE");
        } else{
            mToolbar.findViewById(R.id.buttonModificarCliente).setVisibility(View.GONE);
        }
    }
    public void mouCursor(Cursor cursor){
        if(cursor.moveToFirst()) {
            do {
                nomClient.setText(cursor.getString(cursor.getColumnIndex(ContracteBD.Client.NOM_CLIENT)));
                cognomsClient.setText(cursor.getString(cursor.getColumnIndex(ContracteBD.Client.COGNOMS_CLIENT)));
                String tipoPago = cursor.getString(cursor.getColumnIndex(ContracteBD.Client.TIPO_PAGO));
                spinnerTipoPago.setSelection(Integer.parseInt(tipoPago));
                adapterTipoPago.notifyDataSetChanged();
                String tipoComida = cursor.getString(cursor.getColumnIndex(ContracteBD.Client.TIPO_COMIDA));
                spinnerTipoComida.setSelection(Integer.parseInt(tipoComida));
                adapterTipoComida.notifyDataSetChanged();

                String tipoCliente = cursor.getString(cursor.getColumnIndex(ContracteBD.Client.TIPUS_CLIENT));
                spinnerTipoClientes.setSelection(Integer.parseInt(tipoCliente));
                adapterTipoClientes.notifyDataSetChanged();

                String mesaFavorita = cursor.getString(cursor.getColumnIndex(ContracteBD.Client.MESA_FAVORITA));
                spinnerMesaFavorita.setSelection(Integer.parseInt(mesaFavorita));
                adapterMesaFavorita.notifyDataSetChanged();
                observaciones.setText(cursor.getString(cursor.getColumnIndex(ContracteBD.Client.OBSERVACIONS_CLIENT)));
            } while(cursor.moveToNext());
        }
    }

}
