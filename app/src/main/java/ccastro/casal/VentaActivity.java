package ccastro.casal;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);



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
}