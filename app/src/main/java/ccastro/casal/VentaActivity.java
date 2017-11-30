package ccastro.casal;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.MatrixCursor;
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
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import ccastro.casal.RecyclerView.HeaderAdapterVenta;
import ccastro.casal.RecyclerView.HeaderVenta;
import ccastro.casal.SQLite.ContracteBD;
import ccastro.casal.SQLite.DBInterface;
import ccastro.casal.Utils.Utilitats;

public class VentaActivity extends AppCompatActivity   {
    DBInterface db;
    private HeaderAdapterVenta headerAdapterVenta;
    private ArrayList<HeaderVenta> myDataset;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Spinner spinnerEstatVenta;
    private android.widget.SimpleCursorAdapter adapter;
    private String fechaVenta;
    private TextView textViewFechaVenta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);
        textViewFechaVenta = (TextView) findViewById(R.id.fechaVenta);
        fechaVenta = Utilitats.obtenerFechaActual();
        textViewFechaVenta.setText(Utilitats.getFechaFormatSpain(fechaVenta));
    }

    public ArrayList mouCursor(Cursor cursor) {
        if (cursor.moveToFirst()) {
            do {
                myDataset.add(new HeaderVenta(
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Venta._ID)),
                        (cursor.getString(cursor.getColumnIndex(ContracteBD.Client.NOM_CLIENT))+" "+cursor.getString(cursor.getColumnIndex(ContracteBD.Client.COGNOMS_CLIENT))),
                        (cursor.getString(cursor.getColumnIndex(ContracteBD.Treballador.NOM_TREBALLADOR))+" "+cursor.getString(cursor.getColumnIndex(ContracteBD.Treballador.COGNOMS_TREBALLADOR))),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Venta.DATA_VENTA)),
                        ventaPagada(cursor.getString(cursor.getColumnIndex(ContracteBD.Venta.VENTA_COBRADA))),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Venta.HORA_VENTA))));
            } while (cursor.moveToNext());
        }
        return myDataset;
    }

    public String ventaPagada(String ventaPagada){
        Log.d("VENTA PAGADA: ",ventaPagada);
        if (ventaPagada.equalsIgnoreCase("0")) return "Falta Pagar";
        else if (ventaPagada.equalsIgnoreCase("1"))return "Pagado";
        else if (ventaPagada.equalsIgnoreCase("2"))return "Anulado";
        return "";
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**
         * Instanciació del Recycler i de l'arrayList
         */
        actualizarRecyclerView();
    }
    public void actualizarRecyclerView(){
        myDataset = new ArrayList<>();
        headerAdapterVenta= new HeaderAdapterVenta(myDataset);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_consulta);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(headerAdapterVenta);
        db = new DBInterface(this);
        db.obre();
        Cursor cursor = db.RetornaVentes(fechaVenta);
        myDataset = mouCursor(cursor);
        db.tanca();

    }
    public MatrixCursor getFakeCursor() {
        String[] columns = new String[]{"_id", "estatVenta"};
        MatrixCursor matrixCursor = new MatrixCursor(columns);
        startManagingCursor(matrixCursor);
        matrixCursor.addRow(new Object[]{0, "Todo"});
        matrixCursor.addRow(new Object[]{1, "Pagado"});
        matrixCursor.addRow(new Object[]{2, "Falta Pagar"});
        matrixCursor.addRow(new Object[]{3, "Anulado"});
        return matrixCursor;
    }
    public void iniciarSpinnerEstatVenta(){
        Cursor cursorTest = getFakeCursor();
        android.widget.SimpleCursorAdapter adapter = new android.widget.SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                cursorTest,
                new String[]{"estatVenta"}, //Columna del cursor que volem agafar
                new int[]{android.R.id.text1}, 0);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstatVenta.setAdapter(adapter);
        spinnerEstatVenta.setOnItemSelectedListener(new myOnItemSelectedListener());
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
            ((TextView) view).setTextColor(Color.WHITE);  // COLOR DEL TEXTO SELECCIONADO DEL TOOLBAR
            Cursor cursor = null;
            String estat="3";
            Log.d("POSICION: ",Integer.toString(position));
            if (position == 0) { // mostrar TOT, tant PAGAT COM NO PAGAT
                db.obre();
                myDataset = new ArrayList<HeaderVenta>();
                cursor = db.RetornaVentes(fechaVenta);
                myDataset = mouCursor(cursor);
                headerAdapterVenta.actualitzaRecycler(myDataset);
                db.tanca();
            } else if (position == 1) { // MOSTRAR VENTADAS PAGADAS
                estat = "1";
            } else if (position == 2) {  // MOSTRAR VENTAS QUE FALTA PAGAR
                estat = "0";
            } else if (position ==3) {  // MOSTRAR VENTAS ANULADAS
                estat = "2";
            }
            if (estat.equals("0") || estat.equals("1") || estat.equals("2")){
                db.obre();
                myDataset = new ArrayList<HeaderVenta>();
                cursor = db.RetornaVentesDataEstatVenta(estat,fechaVenta);
                myDataset = mouCursor(cursor);
                headerAdapterVenta.actualitzaRecycler(myDataset);
                db.tanca();
            }
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
        inflater.inflate(R.menu.toolbar_menu, menu);

        MenuItem item = menu.findItem(R.id.spinnerEstatVenta);
        spinnerEstatVenta = (Spinner) item.getActionView();
        iniciarSpinnerEstatVenta();
        menu.findItem(R.id.buttonDataVenta).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(VentaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth = selectedmonth + 1;
                        fechaVenta = "" + selectedyear + " " + selectedmonth + " " + selectedday;

                        textViewFechaVenta.setText(Utilitats.getFechaFormatSpain(fechaVenta));
                        actualizarRecyclerView();
                        headerAdapterVenta.actualitzaRecycler(myDataset);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Selecciona Fecha");
                mDatePicker.show();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}