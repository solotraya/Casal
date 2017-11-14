package ccastro.casal;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import ccastro.casal.RecyclerView.HeaderAdapterVenta;
import ccastro.casal.RecyclerView.HeaderVenta;
import ccastro.casal.SQLite.ContracteBD;
import ccastro.casal.SQLite.DBInterface;

public class VentaActivity extends AppCompatActivity  implements View.OnClickListener,View.OnLongClickListener {
    DBInterface db;
    ListView listVentas;
    //ArrayList<String> ventas = null;
    private HeaderAdapterVenta headerAdapterVenta;
    private ArrayList<HeaderVenta> myDataset;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);
        /**
         * Created by Maria Remedios Ortega
         * Instanciació del Recycler i de l'arrayList
         */

        myDataset = new ArrayList<>();
        headerAdapterVenta= new HeaderAdapterVenta(myDataset);
        db = new DBInterface(this);
        db.obre();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_consulta);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(headerAdapterVenta);
        Cursor cursor = db.RetornaTotesLesVentes();
        myDataset = mouCursor(cursor);



        db.tanca();

        //ventas= new ArrayList();
      //  ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,android.R.layout.simple_list_item_1, android.R.id.text1, ventas);
        // Assign adapter to ListView
        //listVentas.setAdapter(adapter);

    //    retornaVentas();

    }

    public ArrayList mouCursor(Cursor cursor) {
        if (cursor.moveToFirst()) {
            do {
                myDataset.add(new HeaderVenta(
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Venta._ID)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Client.NOM_CLIENT)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Venta.DATA_VENTA)),
                        ventaPagada(cursor.getString(cursor.getColumnIndex(ContracteBD.Venta.VENTA_COBRADA)))));
            } while (cursor.moveToNext());
        }
        return myDataset;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
/*
    public void retornaVentas(){
        db.obre();
        Cursor cursor= db.RetornaTotesLesVentes();
        if (cursor.moveToFirst()) {
            do {
                ventas.add(cursor.getString(cursor.getColumnIndex(Venta._ID))+" "+cursor.getString(cursor.getColumnIndex(Venta.DATA_VENTA))
                        +" "+ventaPagada(cursor.getString(cursor.getColumnIndex(Venta.VENTA_COBRADA)))+" "+cursor.getString(cursor.getColumnIndex(ContracteBD.Client.NOM_CLIENT)));
            } while (cursor.moveToNext());
        }
        db.tanca();
    } */

    public String ventaPagada(String ventaPagada){
        if (ventaPagada.equalsIgnoreCase("0")) return "Falta Pagar";
        else return "Pagado";
    }



}