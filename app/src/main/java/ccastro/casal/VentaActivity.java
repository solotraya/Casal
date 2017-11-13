package ccastro.casal;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ccastro.casal.SQLite.ContracteBD.Venta;
import ccastro.casal.SQLite.DBInterface;

public class VentaActivity extends AppCompatActivity {
    DBInterface db;
    ListView listVentas;
    ArrayList<String> ventas = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);
        db = new DBInterface(this);
        listVentas= (ListView) findViewById(R.id.listVentas);
        ventas= new ArrayList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,
                android.R.layout.simple_list_item_1, android.R.id.text1, ventas);
        // Assign adapter to ListView
        listVentas.setAdapter(adapter);

        retornaVentas();



    }

    public void retornaVentas(){
        db.obre();
        Cursor cursor= db.RetornaTotesLesVentes();
        if (cursor.moveToFirst()) {
            do {
                ventas.add(cursor.getString(cursor.getColumnIndex(Venta._ID))+" "+cursor.getString(cursor.getColumnIndex(Venta.DATA_VENTA))+" "+cursor.getString(cursor.getColumnIndex(Venta.VENTA_COBRADA)));
            } while (cursor.moveToNext());
        }
        db.tanca();
    }
    public String totalVenta(){
        String total = null;

        return total;
    }
}