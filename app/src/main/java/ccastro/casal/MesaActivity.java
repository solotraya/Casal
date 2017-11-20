package ccastro.casal;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ccastro.casal.RecyclerView.HeaderAdapterMesa;
import ccastro.casal.RecyclerView.HeaderMesa;
import ccastro.casal.SQLite.ContracteBD;
import ccastro.casal.SQLite.DBInterface;

public class MesaActivity extends AppCompatActivity {
    DBInterface db;

    private Spinner spinnerMesa;
    Button buttonnDataInicial;
    Button buttonAceptarReserva;
    private String fechaInicio;
    private String idCliente,nombreCliente;
    private Integer idMesa;
    String taulaPerDefecteClient;
    ArrayList<String> clients = null;
    ArrayAdapter<String> adapterClientes;
    Long resultatInserirClient;
    ListView listViewClientes;
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
        fechaInicio=obtenerFechaReserva(); // por defecto le metemos la fecha actual (DE HOY)
        android.support.v7.widget.Toolbar mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar_mesa);


        buttonAceptarReserva = (Button) mToolbar.findViewById(R.id.buttonAñadirCliente) ;
        buttonnDataInicial = (Button) mToolbar.findViewById(R.id.buttonDataIniciCliente) ;
        buttonnDataInicial.setOnClickListener( new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Calendar mcurrentDate = Calendar.getInstance();
                    int mYear = mcurrentDate.get(Calendar.YEAR);
                    int mMonth = mcurrentDate.get(Calendar.MONTH);
                    int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog mDatePicker;
                    mDatePicker = new DatePickerDialog(MesaActivity.this, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                            selectedmonth = selectedmonth + 1;
                            fechaInicio="" + selectedyear + " " + selectedmonth + " " + selectedday;
                            actualizarRecyclerView();
                            headerAdapterMesa.actualitzaRecycler(myDataset);
                            // METODO DE LA BD PARA CARGAR MESA SEGUN FECHA
                            //carregarDataTreballador();

                        }
                    }, mYear, mMonth, mDay);
                    mDatePicker.setTitle("Selecciona Data");
                    mDatePicker.show();
                }
            }
        );
        listViewClientes = (ListView)findViewById(R.id.listViewClientes);
        clients= new ArrayList();
        adapterClientes = new ArrayAdapter<String> (this,
                android.R.layout.simple_list_item_1, android.R.id.text1, clients);
        // Assign adapter to ListView
        listViewClientes.setAdapter(adapterClientes);


        listViewClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nombre = (String) listViewClientes.getItemAtPosition(position);
                String [] cogerIDCliente = nombre.split(" ");
                idCliente = cogerIDCliente[0];
                // TODO CONSULTA PARA CONSEGUIR LA MESA POR DEFECTO DEL CLIENTE
                obtenirTaulaDefecteClient();

                // TODO AHORA ESTARIA GENIA QUE DESPUES DE TENER EL ID CLIENTE
                nombreCliente = cogerIDCliente[1];

                Toast.makeText(view.getContext(), cogerIDCliente[0], Toast.LENGTH_SHORT).show();
                listViewClientes.setVisibility(View.GONE);
                // TODO Y SELECCIONAR MESA FAVORITA DE ESE CLIENTE EN EL SPINNER DE MESA
                spinnerMesa.setSelection(Integer.parseInt(taulaPerDefecteClient)-1);
                adapterMesa.notifyDataSetChanged();
            }
        });
        retornaClients();

           // TODO ESTO ES PARA EL BOTON DE AÑADIR RESERVA
        buttonAceptarReserva.setOnClickListener( new View.OnClickListener(){
              @Override
              public void onClick(View view) {
                  // TODO SI NO SE AÑADE FECHA LE PONEMOS FECHA ACTUAL

                  if (idCliente!=null){
                      db.obre();
                      //  db.InserirReserva_Cliente(diaReservado,"0",pagadoReserva,id_cliente,id_mesa);
                      resultatInserirClient = db.InserirReserva_Cliente(fechaInicio,"0","0",Integer.parseInt(idCliente),idMesa);
                      Log.d("Result INSERIR CLIENT: ",Long.toString(resultatInserirClient));
                      db.tanca();
                      // SI EL CLIENTE TIENE YA MESA RESERVADA: CREAMOS FACTURA
                      if (resultatInserirClient!=-1){
                          actualizarRecyclerView();
                          crearFacturaReservaMesa();
                      } else Toast.makeText(view.getContext(), "El cliente ya tiene mesa reservada!", Toast.LENGTH_SHORT).show();
                      headerAdapterMesa.actualitzaRecycler(myDataset);
                  } else Toast.makeText(MesaActivity.this, "Introduce cliente!", Toast.LENGTH_SHORT).show();
              }
            }
        );

    }

    public void obtenirTaulaDefecteClient (){
        if (idCliente!=null){
            db.obre();
            Cursor cursorTaulaDefecte = db.RetornaTaulaDefecteClient(idCliente);

            if (cursorTaulaDefecte.moveToFirst()) {
                do {
                    taulaPerDefecteClient = cursorTaulaDefecte.getString(cursorTaulaDefecte.getColumnIndex(ContracteBD.Client.MESA_FAVORITA));
                    Log.d("MESA POR DEFECTO: ", taulaPerDefecteClient);
                } while (cursorTaulaDefecte.moveToNext());
            }
        }
        db.tanca();



    }

    public String obtenerFechaReserva (){
        Calendar ahoraCal = Calendar.getInstance();
        // PARECE QUE EL MES EMPIEZA DESDE 0, HAY QUE SUMAR UNO.
        ahoraCal.getTime();
        return ahoraCal.get(Calendar.YEAR)+" "+(ahoraCal.get(Calendar.MONTH)+1)+
                " "+ahoraCal.get(Calendar.DATE);
    }

    public void retornaClients(){
        db.obre();
        Cursor cursor= db.RetornaTotsElsClients();
        if (cursor.moveToFirst()) {
            do {
                clients.add(cursor.getString(cursor.getColumnIndex(ContracteBD.Client._ID))+" "+cursor.getString(cursor.getColumnIndex(ContracteBD.Client.NOM_CLIENT))
                        +" "+cursor.getString(cursor.getColumnIndex(ContracteBD.Client.COGNOMS_CLIENT)));
            } while (cursor.moveToNext());
        }
        db.tanca();
        //
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

        spinnerMesa.setOnItemSelectedListener(new myOnItemSelectedListener());
    }
    private Cursor getCursorSpinnerMesa(Cursor cursor) {
        MatrixCursor extras = new MatrixCursor(new String[]{"_id", "nombre_mesa"});
        //extras.addRow(new String[]{"0", "Tots"});
        Cursor[] cursors = {extras, cursor};
        return new MergeCursor(cursors);

    }

    public  void crearFacturaReservaMesa(){
        db.obre();
        Cursor cursorVentaFactura = db.EncontrarId_VentaFacturaSinPagar(idCliente);
        Integer idVentaFactura = cursorIDVentaFactura(cursorVentaFactura);
        String idVenta = Integer.toString(idVentaFactura);
        Log.d("IDVENTA: ", Integer.toString(idVentaFactura));
        if (idVentaFactura==-1){ // Si no tienen una factura pendiente por pagar
            Date ahora = new Date();
            SimpleDateFormat formateador = new SimpleDateFormat("hh:mm");
            String hora = formateador.format(ahora);
            //       *** CAMBIAR POR FEHCA Y HORA ACTUAL ***
            db.InserirVenta(Integer.parseInt(idCliente),Integer.parseInt(LoginActivity.ID_TREBALLADOR),obtenerFechaReserva(),"0",hora);
            cursorVentaFactura = db.EncontrarId_VentaFacturaSinPagar(idCliente);
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
        Cursor cursor = db.RetornaMesasReservadasData(fechaInicio);
        myDataset = CursorBD(cursor);
        db.tanca();
    }

    public  ArrayList CursorBD(Cursor cursor){
        if(cursor.moveToFirst()){
            do {
                myDataset.add(new HeaderMesa(
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Mesa._ID)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Mesa.NOMBRE_MESA)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Reserva_Cliente.DIA_RESERVADO))
                ));
            } while(cursor.moveToNext());
        }
        return myDataset;
    }


    class myOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        /**
         * @author Carlos Alberto Castro Cañabate
         *
         * Mètode per fer una acció una vegada seleccionat un treballador a l'spinner de treballadors.
         * @param adapterView adaptador
         * @param view spinner
         * @param position posició a l'spinner
         * @param id correspón a la columna _id de treballadors
         */
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            Cursor cursor = null;

           ((TextView) view).setTextColor(Color.WHITE);  // COLOR DEL TEXTO SELECCIONADO DEL TOOLBAR

            idMesa = new BigDecimal(id).intValueExact();
//            Toast.makeText(view.getContext(),"ID: "+ Long.toString(id), Toast.LENGTH_SHORT).show();
            // CUANDO SE SELECCIONE CLIENTE, PONER AUTOMATICAMENTE SPINNER CON LA MESA_POR_DEFECTO DEL CLIENTE.
            // BUSCAR COMO INTRODUCIR UN SEARCHVIEW DENTRO DE EL TOOLBAR PARA QUE SEA UN WIDGET MAS
        }

        /**
         * Mètode per realitzar una acció quan no hi ha rés seleccionat. Al nostre cas sempre hi ha selecció.
         * @param adapterView adaptador
         */
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu_mesa_widgets, menu);
        MenuItem item = menu.findItem(R.id.searchViewClientes);
        final SearchView searchView = (SearchView)item.getActionView();


        MenuItem itemSpinnerMesa = menu.findItem(R.id.spinnerMesa);
        spinnerMesa = (Spinner)itemSpinnerMesa.getActionView();

        iniciarSpinnerMesa();

        myDataset = new ArrayList<>();
        headerAdapterMesa= new HeaderAdapterMesa(myDataset);
        db = new DBInterface(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_consulta);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(headerAdapterMesa);
        actualizarRecyclerView();
        searchView.setQueryHint("Nombre Cliente...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // adapter.getFilter().filter(newText);
                listViewClientes.setVisibility(View.VISIBLE);
                // TODO: MOSTRAMOS TEXTO FILTRADO DE EL ADAPTER DE CLIENTES
                // TODO: BUSCAR COMO DAR FORMATO AL LISTVIEW DE CLIENTES PARA QUE SEA MAS CHULO
                adapterClientes.getFilter().filter(newText);

                return true;
            }

        });
        return super.onCreateOptionsMenu(menu);
    }


}
