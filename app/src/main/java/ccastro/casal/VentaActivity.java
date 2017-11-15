package ccastro.casal;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;

import ccastro.casal.RecyclerView.HeaderAdapterVenta;
import ccastro.casal.RecyclerView.HeaderVenta;
import ccastro.casal.SQLite.ContracteBD;
import ccastro.casal.SQLite.DBInterface;

public class VentaActivity extends AppCompatActivity   {
    DBInterface db;
    private HeaderAdapterVenta headerAdapterVenta;
    private ArrayList<HeaderVenta> myDataset;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Spinner spinnerEstatVenta;
    private android.widget.SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);
        Toolbar editToolbar = (Toolbar) findViewById(R.id.filter_toolbar);
        editToolbar.inflateMenu(R.menu.toolbar_menu);
        spinnerEstatVenta = (Spinner) findViewById(R.id.spinnerEstatVenta);
        iniciarSpinnerEstatVenta();
    }

    public ArrayList mouCursor(Cursor cursor) {
        if (cursor.moveToFirst()) {
            do {
                myDataset.add(new HeaderVenta(
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Venta._ID)),
                        (cursor.getString(cursor.getColumnIndex(ContracteBD.Client.NOM_CLIENT))+" "+cursor.getString(cursor.getColumnIndex(ContracteBD.Client.COGNOMS_CLIENT))),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Venta.DATA_VENTA)),
                        ventaPagada(cursor.getString(cursor.getColumnIndex(ContracteBD.Venta.VENTA_COBRADA))),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Venta.HORA_VENTA))));
            } while (cursor.moveToNext());
        }
        return myDataset;
    }

    public String ventaPagada(String ventaPagada){
        if (ventaPagada.equalsIgnoreCase("0")) return "Falta Pagar";
        else return "Pagado";
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**
         * Instanciació del Recycler i de l'arrayList
         */

        myDataset = new ArrayList<>();
        headerAdapterVenta= new HeaderAdapterVenta(myDataset);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_consulta);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(headerAdapterVenta);
        db = new DBInterface(this);
        db.obre();
        Cursor cursor = db.RetornaVentesDataActual();
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
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
            Cursor cursor = null;
            String estat="2";
            Log.d("POSICION: ",Integer.toString(position));
            if (position == 0) { // mostrar TOT, tant PAGAT COM NO PAGAT
                db.obre();
                myDataset = new ArrayList<HeaderVenta>();
                cursor = db.RetornaVentesDataActual();
                myDataset = mouCursor(cursor);
                headerAdapterVenta.actualitzaRecycler(myDataset);
                db.tanca();
            } else if (position == 1) { // MOSTRAR VENTADAS PAGADAS
                estat = "1";
            } else if (position == 2) {  // MOSTRAR VENTAS QUE FALTA PAGAR
                estat = "0";
            }
            if (estat.equals("0") || estat.equals("1")){
                db.obre();
                myDataset = new ArrayList<HeaderVenta>();
                cursor = db.RetornaVentesDataActualEstatVenta(estat);
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
}