package ccastro.casal;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ccastro.casal.RecyclerView.HeaderAdapterMesa;
import ccastro.casal.RecyclerView.HeaderMesa;
import ccastro.casal.SQLite.ContracteBD;
import ccastro.casal.SQLite.DBInterface;

public class MesaActivity extends AppCompatActivity {
    DBInterface db;
    TextView nomMesa;
    EditText dia,pagado,cliente,mesa;
    Button  reservar;
    private Spinner spinnerMesa, spinnerClientes;
    LinearLayout reserva;
    String idMesa;
    View v;
    Long resultatInserirClient;
    private HeaderAdapterMesa headerAdapterMesa;
    private ArrayList<HeaderMesa> myDataset;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private android.widget.SimpleCursorAdapter adapterMesa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesa);
        db = new DBInterface(this);

        Toolbar editToolbar = (Toolbar) findViewById(R.id.filter_toolbarMesa);
        editToolbar.inflateMenu(R.menu.toolbar_menu_mesa);
        spinnerClientes = (Spinner) findViewById(R.id.spinnerClientes);
        spinnerMesa = (Spinner)findViewById(R.id.spinnerMesa);

        iniciarSpinnerClientes();
        iniciarSpinnerMesa();
        nomMesa = (TextView) findViewById(R.id.nomMesa);
        dia = (EditText) findViewById(R.id.editTextDia);
        pagado = (EditText) findViewById(R.id.editTextPagado);
        cliente = (EditText) findViewById(R.id.editTextIDCliente);
        mesa = (EditText) findViewById(R.id.editTextIDMesa);
        reservar = (Button) findViewById(R.id.buttonReservar) ;
        reserva = (LinearLayout) findViewById(R.id.LayoutReserva);
        dia.getText().toString();
        reservar.setOnClickListener( new View.OnClickListener(){
                                              @Override
                                              public void onClick(View view) {
                                                  String diaReservado = dia.getText().toString();
                                                  String pagadoReserva = pagado.getText().toString();
                                                  Integer id_cliente = Integer.parseInt(cliente.getText().toString());
                                                  Integer id_mesa = Integer.parseInt(mesa.getText().toString());
                                                  db.obre();
                                                //  db.InserirReserva_Cliente(diaReservado,"0",pagadoReserva,id_cliente,id_mesa);
                                                  resultatInserirClient = db.InserirReserva_Cliente("2017 11 18","0",pagadoReserva,id_cliente,id_mesa);
                                                  Log.d("Result INSERIR CLIENT: ",Long.toString(resultatInserirClient));
                                                  db.tanca();
                                                  // SI EL CLIENTE TIENE YA MESA RESERVADA: CREAMOS FACTURA
                                                  if (resultatInserirClient!=-1){
                                                      reserva.setVisibility(View.GONE);
                                                      actualizarRecyclerView();
                                                      crearFacturaReservaMesa();
                                                  } else Toast.makeText(view.getContext(), "El cliente ya tiene mesa reservada!", Toast.LENGTH_SHORT).show();
                                              }
                                          }
        );
       /* añadirReserva.setOnClickListener( new View.OnClickListener(){
                                         @Override
                                         public void onClick(View view) {
                                             reserva.setVisibility(View.VISIBLE);
                                         }
                                     }
        ); */
        myDataset = new ArrayList<>();
        headerAdapterMesa= new HeaderAdapterMesa(myDataset);
        db = new DBInterface(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_consulta);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(headerAdapterMesa);
        actualizarRecyclerView();

    }
    public void iniciarSpinnerClientes(){

    }

    public void iniciarSpinnerMesa(){
        db.obre();
        Cursor cursor = getCursorSpinnerMesa(db.RetornaTodasLasMesas());
        adapterMesa = new android.widget.SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                cursor,
                new String[]{"nombre_mesa"}, //Columna del cursor que volem agafar
                new int[]{android.R.id.text1}, 0);
        adapterMesa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Afegeix l'adapter al Spinner de treballadors
        spinnerMesa.setAdapter(adapterMesa);
        db.tanca();
    }
    private Cursor getCursorSpinnerMesa(Cursor cursor) {
        MatrixCursor extras = new MatrixCursor(new String[]{"_id", "nombre_mesa"});
        //extras.addRow(new String[]{"0", "Tots"});
        Cursor[] cursors = {extras, cursor};
        return new MergeCursor(cursors);
    }


    public  void crearFacturaReservaMesa(){
        db.obre();
        Cursor cursorVentaFactura = db.EncontrarId_VentaFacturaSinPagar(cliente.getText().toString());
        Integer idVentaFactura = cursorIDVentaFactura(cursorVentaFactura);
        String idVenta = Integer.toString(idVentaFactura);
        Log.d("IDVENTA: ", Integer.toString(idVentaFactura));
        if (idVentaFactura==-1){ // Si no tienen una factura pendiente por pagar
            Date ahora = new Date();
            SimpleDateFormat formateador = new SimpleDateFormat("hh:mm");
            String hora = formateador.format(ahora);
            //       *** CAMBIAR POR FEHCA Y HORA ACTUAL ***
            db.InserirVenta(Integer.parseInt(cliente.getText().toString()),Integer.parseInt(LoginActivity.ID_TREBALLADOR),"2017 11 18","0",hora);
            cursorVentaFactura = db.EncontrarId_VentaFacturaSinPagar(cliente.getText().toString());
            idVentaFactura = cursorIDVentaFactura(cursorVentaFactura);
            idVenta = Integer.toString(idVentaFactura);
        }
        // CREAMOS FACTURA: AÑADIMOS MENU AL RESERVAR MESA
        db.InserirFactura(1,idVentaFactura,1);
        db.tanca();


    }
    public  Integer cursorIDVentaFactura(Cursor cursor){
        Integer idVenta=-1;
        if(cursor.moveToFirst()){
            do {
                idVenta=Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContracteBD.Venta._ID)));
            } while(cursor.moveToNext());
        }
        return idVenta;
    }

    public void actualizarRecyclerView(){
        myDataset.clear();
        db.obre();
        Cursor cursor = db.RetornaMesasReservadasDataActual();
        myDataset = CursorBD(cursor);
        db.tanca();
    }

    public  ArrayList CursorBD(Cursor cursor){
        if(cursor.moveToFirst()){
            do {
                myDataset.add(new HeaderMesa(
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Mesa._ID)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Mesa.NOMBRE_MESA))
                ));
            } while(cursor.moveToNext());
        }
        return myDataset;
    }
}
