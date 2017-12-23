package ccastro.casal;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ccastro.casal.SQLite.ContracteBD;
import ccastro.casal.SQLite.DBInterface;
import ccastro.casal.Utils.Cursors;
import ccastro.casal.Utils.Missatges;

public class ClientActivity extends AppCompatActivity {
    DBInterface db;

    ListView listView ;
    List<String> clientes = null;
    List<String> clientesSoloNombres = null;
    ArrayAdapter<String> adapterClientes;
    String arraySpinnerTipoClientes [] = {"Todos","Comedor","Llevar","Ayuntamiento"};
    ArrayAdapter<String> adapterTipoClientes;
    Spinner spinnerTipoClientes;
    String id_cliente,nombreCliente, tipoPago, tipusClient="0";
    Integer idVentaFactura;
    boolean seleccionaCliente = false;
    private android.support.v7.widget.Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar_cliente);
        db = new DBInterface(this);
        listView = (ListView) findViewById(R.id.listView);
        clientes= new ArrayList();
        clientesSoloNombres = new ArrayList();
        adapterClientes = new ArrayAdapter(getBaseContext(),R.layout.format_list_view,clientesSoloNombres);
        listView.setAdapter(adapterClientes);
        mToolbar.findViewById(R.id.buttonAñadir).setOnClickListener( new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ClientActivity.this, InsertarClienteActivity.class));
                }
            }
        );
        mToolbar.findViewById(R.id.buttonModificar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id_cliente!= null){
                    Intent intent = new Intent (ClientActivity.this,InsertarClienteActivity.class);
                    intent.putExtra("ID_CLIENTE",id_cliente);
                    startActivity(intent);
                } else Missatges.AlertMissatge("ERROR", "Selecciona un cliente!", R.drawable.error2, ClientActivity.this);
            }
        });

        mToolbar.findViewById(R.id.buttonEliminar).setOnClickListener( new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if (id_cliente!= null){

                        db.obre();
                        Cursor cursorVentaFactura = db.EncontrarId_VentaFacturaSinPagar(id_cliente);
                        idVentaFactura = Cursors.cursorIDVentaFactura(cursorVentaFactura);
                        Log.d("IDVENTA: ", Integer.toString(idVentaFactura));
                        if (idVentaFactura==-1){
                            long resultat = db.EliminarClient(id_cliente);
                            db.tanca();
                            if (resultat==1){
                                Missatges.AlertMissatge("CLIENTE ELIMINADO", nombreCliente+" ha sido eliminado correctamente", R.drawable.papelera, ClientActivity.this);
                            }
                        } else {
                            Missatges.AlertMissatge("ERROR AL ELIMINAR", nombreCliente+" tiene facturas sin pagar!", R.drawable.error2, ClientActivity.this);
                        }

                        retornaClients();
                        adapterClientes.notifyDataSetChanged();
                    } else Missatges.AlertMissatge("ERROR", "Selecciona un cliente!", R.drawable.error2, ClientActivity.this);
                }
            }
        );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO ESCONDEMOS EL TECLADO DEL MOVIL:
                /*
                view = ClientActivity.this.getCurrentFocus();
                view.clearFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                } */
                view.setSelected(true);
                if (seleccionaCliente){
                    recogerCliente(position);
                    Log.d("NOMBRE CLIENTE: ",nombreCliente);

                    // TODO: BUSCAMOS QUE EL CLIENTE TENGA ALGUNA FACTURA ABIERTA SIN PAGAR
                    db.obre();
                    Cursor cursorVentaFactura = db.EncontrarId_VentaFacturaSinPagar(id_cliente);
                    idVentaFactura = Cursors.cursorIDVentaFactura(cursorVentaFactura);

                    Log.d("IDVENTA: ", Integer.toString(idVentaFactura));
                    db.tanca();

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("ID_CLIENTE",id_cliente);
                    returnIntent.putExtra("NOMBRE_CLIENTE",nombreCliente);
                    returnIntent.putExtra("TIPO_PAGO",tipoPago);
                    returnIntent.putExtra("ID_VENTA",Integer.toString(idVentaFactura));
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                } else {
                    // TODO AQUI HAREMOS QUE SE PUEDA ENTRAR AL CLIENTE PARA MODIFICARLO O ELIMINARLO.
                    recogerCliente(position);
                }
            }
        });
        getIntents();
    }

    public void recogerCliente(int position){
        String nombre =(String) listView.getItemAtPosition(position);
        for (String client: clientes){
            if (client.contains(nombre)){
                nombre = client;
                break;
            }
        }
        String [] cogerIDCliente = nombre.split(" ");
        id_cliente = cogerIDCliente[0];
        String [] cogerTipoPago = nombre.split(":");
        nombreCliente = cogerTipoPago[0].split(" ",2)[1];
        tipoPago = cogerTipoPago[1];
    }
    public void getIntents(){
        if (getIntent().hasExtra("SELECCIONA_CLIENTE")){  // pasado desde HeaderAdapterVenta
            seleccionaCliente = getIntent().getExtras().getBoolean("SELECCIONA_CLIENTE");
            mToolbar.setVisibility(View.GONE);
        }
    }
    public void retornaClients(){
        db.obre();
        clientes.clear();
        clientesSoloNombres.clear();
        Cursor cursor=null;
        if (tipusClient.equals("0")) {
            cursor= db.RetornaTotsElsClients();
        } else {
            cursor= db.RetornaTipusClients(Integer.parseInt(tipusClient)-1);
        }
        if (cursor.moveToFirst()) {
            do {
                    clientes.add(cursor.getString(cursor.getColumnIndex(ContracteBD.Client._ID))+" "+cursor.getString(cursor.getColumnIndex(ContracteBD.Client.NOM_CLIENT))
                            +" "+cursor.getString(cursor.getColumnIndex(ContracteBD.Client.COGNOMS_CLIENT))+" :"+cursor.getString(cursor.getColumnIndex(ContracteBD.Client.TIPO_PAGO)));
                    clientesSoloNombres.add(cursor.getString(cursor.getColumnIndex(ContracteBD.Client.NOM_CLIENT))
                            +" "+cursor.getString(cursor.getColumnIndex(ContracteBD.Client.COGNOMS_CLIENT)));
            } while (cursor.moveToNext());

        }
        db.tanca();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu_client, menu);

        MenuItem item = menu.findItem(R.id.searchViewClientes);
        MenuItem itemSpinner = menu.findItem(R.id.spinnerMesa);
        spinnerTipoClientes = (Spinner) itemSpinner.getActionView();
        adapterTipoClientes = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, android.R.id.text1, arraySpinnerTipoClientes);
        spinnerTipoClientes.setAdapter(adapterTipoClientes);
        final SearchView searchView = (SearchView)item.getActionView();
        searchView.setQueryHint("Nombre Cliente...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // adapter.getFilter().filter(newText);
                // TODO: MOSTRAMOS TEXTO FILTRADO DE EL ADAPTER DE CLIENTES
                // TODO: BUSCAR COMO DAR FORMATO AL LISTVIEW DE CLIENTES PARA QUE SEA MAS CHULO
                adapterClientes.getFilter().filter(newText);
                return true;
            }

        });
        spinnerTipoClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                tipusClient = Integer.toString(position);
                retornaClients();
                adapterClientes.notifyDataSetChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED,returnIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        retornaClients();
        adapterClientes.notifyDataSetChanged();
    }
}