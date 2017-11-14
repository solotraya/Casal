package ccastro.casal;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import ccastro.casal.SQLite.ContracteBD;
import ccastro.casal.SQLite.DBInterface;

public class FacturaActivity extends AppCompatActivity {
    DBInterface db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);
        db = new DBInterface(this);
        if (getIntent().hasExtra("ID_VENTA")){  // pasado desde HeaderAdapterVenta
            String idVenta = getIntent().getExtras().getString("ID_VENTA");
            Toast.makeText(this, idVenta, Toast.LENGTH_LONG).show();
            RetornarFactura(idVenta);
        }
    }
    public void RetornarFactura(String idVenta){
        db.obre();
        Cursor cursor=db.RetornaFacturaId_Venta(idVenta);
        CursorBD(cursor);
        db.tanca();
    }
    public void CursorBD(Cursor cursor){
        float preuProducteQuantitat=0;
       float preuTotal=0;
        ArrayList <String> factura = new ArrayList<>();
        if(cursor.moveToFirst()){
            factura.clear();
            do {
                factura.add("Producte: "+cursor.getString(cursor.getColumnIndex(ContracteBD.Producte.NOM_PRODUCTE))+
                " Preu: "+cursor.getString(cursor.getColumnIndex(ContracteBD.Producte.PREU_PRODUCTE))+
                " Tipus: "+cursor.getString(cursor.getColumnIndex(ContracteBD.Producte.TIPUS_PRODUCTE))+
                " Quantitat: "+cursor.getString(cursor.getColumnIndex(ContracteBD.Factura.QUANTITAT_PRODUCTE)));
               // Toast.makeText(this, cursor.getString(cursor.getColumnIndex(ContracteBD.Factura.QUANTITAT_PRODUCTE)), Toast.LENGTH_SHORT).show();
                preuProducteQuantitat = Float.parseFloat(cursor.getString(cursor.getColumnIndex(ContracteBD.Producte.PREU_PRODUCTE)))
                        * Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContracteBD.Factura.QUANTITAT_PRODUCTE)));
                preuTotal = preuTotal + preuProducteQuantitat;
            } while(cursor.moveToNext());


        }
        Iterator it = factura.iterator();
        while (it.hasNext()){
            String producto =(String) it.next();
            Log.d("PRODUCTOS: ",producto);
        }
        DecimalFormat df = new DecimalFormat("0.00");
        df.setMaximumFractionDigits(2);
        Log.d("PREU TOTAL FACTURA: ",df.format(preuTotal));

    }
}
