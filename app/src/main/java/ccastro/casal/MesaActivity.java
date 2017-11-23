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
import android.text.Editable;
import android.text.TextWatcher;
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
import ccastro.casal.Utils.Utilitats;

public class MesaActivity extends AppCompatActivity {
    DBInterface db;

    private final String MENU_NORMAL="0";
    private final String MENU_MITAD="1";
    private final String MENU_CAURTO="2";
    private String tipoPago;
    private Spinner spinnerMesa;
    Button buttonnDataInicial, buttonAceptarReserva, buttonEliminar;
    private String fechaInicio="", fechaFinal="";
    private String idCliente,nombreCliente;
    private Integer idMesa;
    int contadorFechas=0;
    String taulaPerDefecteClient;
    ArrayList<String> clients = null;
    ArrayAdapter<String> adapterClientes;
    Long resultatInserirClient;
    ListView listViewClientes;
    TextView textViewFechaInicio,textViewTotalClientes;
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
        fechaInicio = Utilitats.obtenerFechaActual(); // por defecto le metemos la fecha actual (DE HOY)
        android.support.v7.widget.Toolbar mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar_mesa);

        textViewFechaInicio = (TextView) findViewById(R.id.fechaInicio) ;
        textViewTotalClientes = (TextView) findViewById(R.id.totalClientes) ;
        textViewFechaInicio.setText(Utilitats.getFechaFormatSpain(fechaInicio));
        buttonEliminar = (Button) mToolbar.findViewById(R.id.buttonEliminarClienteMesa) ;
        buttonAceptarReserva = (Button) mToolbar.findViewById(R.id.buttonAñadirCliente) ;
        buttonnDataInicial = (Button) mToolbar.findViewById(R.id.buttonDataIniciCliente) ;
        buttonnDataInicial.setOnClickListener( new View.OnClickListener(){
                public void onClick(View view) {
                    Toast.makeText(MesaActivity.this, "Selecciona fecha Inicio", Toast.LENGTH_SHORT).show();

                    Calendar mcurrentDate = Calendar.getInstance();
                    int mYear = mcurrentDate.get(Calendar.YEAR);
                    int mMonth = mcurrentDate.get(Calendar.MONTH);
                    int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog mDatePicker;
                    mDatePicker = new DatePickerDialog(MesaActivity.this, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                            selectedmonth = selectedmonth + 1;
                                fechaInicio="" + selectedyear + " " + selectedmonth + " " + selectedday;
                                String dataFormatSpain= Utilitats.getFechaFormatSpain(fechaInicio);
                                textViewFechaInicio.setText(dataFormatSpain);
                                actualizarRecyclerView();
                                headerAdapterMesa.actualitzaRecycler(myDataset);
                            // METODO DE LA BD PARA CARGAR MESA SEGUN FECHA
                            //carregarDataTreballador();
                        }
                    }, mYear, mMonth, mDay);
                    mDatePicker.setTitle("Selecciona Fecha");
                    mDatePicker.show();
                }
            }
        );
        textViewFechaInicio.addTextChangedListener ( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Toast.makeText(MesaActivity.this, "Selecciona fecha Final", Toast.LENGTH_SHORT).show();
                Calendar currentDate = Calendar.getInstance();
                int year = currentDate.get(Calendar.YEAR);
                int month = currentDate.get(Calendar.MONTH);
                int day = currentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker;
                datePicker = new DatePickerDialog(MesaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int year, int month, int day) {
                        month = month + 1;
                        String fecha = "" + year + " " + month + " " + day;
                        fechaFinal = fecha;
                    }
                }, year, month, day);
                datePicker.setTitle("Selecciona Fecha");
                datePicker.show();
            }
            @Override
            public void afterTextChanged(Editable editable) {}

        });
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
                String [] cogerTipoPago = nombre.split(":");
                tipoPago = cogerTipoPago[1];
                // TODO CONSULTA PARA CONSEGUIR LA MESA POR DEFECTO DEL CLIENTE
                obtenirTaulaDefecteClient();

                // TODO AHORA ESTARIA GENIA QUE DESPUES DE TENER EL ID CLIENTE
                nombreCliente = cogerIDCliente[1];

                Toast.makeText(view.getContext(), cogerIDCliente[0], Toast.LENGTH_SHORT).show();
                listViewClientes.setVisibility(View.GONE);
                // TODO Y SELECCIONAR MESA FAVORITA DE ESE CLIENTE EN EL SPINNER DE MESA
                spinnerMesa.setSelection(Integer.parseInt(taulaPerDefecteClient));
                adapterMesa.notifyDataSetChanged();
            }
        });

        // TODO ESTO ES PARA EL BOTON DE AÑADIR RESERVA
        buttonAceptarReserva.setOnClickListener( new View.OnClickListener(){
             @Override
             public void onClick(View view) {
                 // TODO SI NO SE AÑADE FECHA LE PONEMOS FECHA ACTUAL


                 if (idCliente!=null ){
                     Integer fechaActualInt = Integer.parseInt(Utilitats.obtenerFechaActual().replaceAll("\\s",""));
                     Integer fechaFinalInt = Integer.parseInt(fechaFinal.replaceAll("\\s",""));
                     Integer fechaInicialInt = Integer.parseInt(fechaInicio.replaceAll("\\s",""));
                     if (fechaInicialInt >= fechaActualInt){
                         if (fechaFinalInt >= fechaInicialInt ){
                             db.obre();
                             //  db.InserirReserva_Cliente(diaReservado,"0",pagadoReserva,id_cliente,id_mesa);
                             resultatInserirClient = db.InserirReserva_Cliente(fechaInicio,"0","0",Integer.parseInt(idCliente),idMesa);
                             Log.d("Result INSERIR CLIENT: ",Long.toString(resultatInserirClient));
                             db.tanca();
                             // SI EL CLIENTE TIENE YA MESA RESERVADA: CREAMOS FACTURA
                             if (resultatInserirClient!=-1){
                                 actualizarRecyclerView();
                                 crearFacturaReservaMesa();
                                 Toast.makeText(MesaActivity.this, "Reserva realizada!", Toast.LENGTH_SHORT).show();
                             } else Toast.makeText(view.getContext(), "El cliente ya tiene mesa reservada!", Toast.LENGTH_SHORT).show();
                             headerAdapterMesa.actualitzaRecycler(myDataset);
                         } else Toast.makeText(MesaActivity.this, "Fecha Final mínima: "+Utilitats.getFechaFormatSpain(fechaInicio), Toast.LENGTH_SHORT).show();
                     } else Toast.makeText(MesaActivity.this, "Fecha Inicio mínima: "+Utilitats.getFechaFormatSpain(Utilitats.obtenerFechaActual()), Toast.LENGTH_SHORT).show();
                 } else Toast.makeText(MesaActivity.this, "Introduce cliente!", Toast.LENGTH_SHORT).show();
             }
         }
        );

        buttonEliminar.setOnClickListener( new View.OnClickListener(){
               @Override
               public void onClick(View view) {
                   // TODO SI NO SE AÑADE FECHA LE PONEMOS FECHA ACTUAL
                   Toast.makeText(MesaActivity.this, fechaInicio, Toast.LENGTH_SHORT).show();
                   Toast.makeText(MesaActivity.this, fechaFinal, Toast.LENGTH_SHORT).show();

               }
           }
        );
         // TODO  Retorna tots els clients, l'utilitzarem per a la llista que usa el SEARCH VIEW de la RESERVA
        //  retornaClients(); SE PUEDE QUITAR
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


    /* SE PUEDE QUITAR
    public void retornaClients(){
        db.obre();
        Cursor cursor= db.RetornaTotsElsClients();
        if (cursor.moveToFirst()) {
            do {
                clients.add(cursor.getString(cursor.getColumnIndex(ContracteBD.Client._ID))+" "+cursor.getString(cursor.getColumnIndex(ContracteBD.Client.NOM_CLIENT))
                        +" "+cursor.getString(cursor.getColumnIndex(ContracteBD.Client.COGNOMS_CLIENT))+" :"+cursor.getString(cursor.getColumnIndex(ContracteBD.Client.TIPO_PAGO)));
            } while (cursor.moveToNext());
        }
        db.tanca();
        //
    }  */


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
            db.InserirVenta(Integer.parseInt(idCliente),Integer.parseInt(LoginActivity.ID_TREBALLADOR),Utilitats.obtenerFechaActual(),"0",hora);
            cursorVentaFactura = db.EncontrarId_VentaFacturaSinPagar(idCliente);
            idVentaFactura = cursorIDVentaFactura(cursorVentaFactura);
            idVenta = Integer.toString(idVentaFactura);
        }
        // CREAMOS FACTURA: AÑADIMOS MENU AL RESERVAR MESA
        if (tipoPago.equalsIgnoreCase("0")) db.InserirFactura(1,idVentaFactura,1);
        else if (tipoPago.equalsIgnoreCase("1")) db.InserirFactura(2,idVentaFactura,1);
        else if (tipoPago.equalsIgnoreCase("2")) db.InserirFactura(3,idVentaFactura,1);
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
        // TODO Consulta principal que retorna les dates que es veuen al recycler de mesa
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
