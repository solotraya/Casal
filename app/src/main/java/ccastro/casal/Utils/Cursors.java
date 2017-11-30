package ccastro.casal.Utils;

import android.database.Cursor;

import ccastro.casal.SQLite.ContracteBD;

/**
 * Created by Carlos on 30/11/2017.
 */

public class Cursors {
    public  static Integer cursorIDVentaFactura(Cursor cursor){
        Integer idVenta=-1;
        if(cursor.moveToFirst()){
            do {
                idVenta=Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContracteBD.Venta._ID)));
            } while(cursor.moveToNext());
        }
        return idVenta;
    }

    public  static Integer cursorQuantitatProducteFactura(Cursor cursor){
        Integer quantitatProdcutes=-1;
        if(cursor.moveToFirst()){
            do {
                quantitatProdcutes=Integer.parseInt(cursor.getString(cursor.getColumnIndex("QuantitatProductes")));
            } while(cursor.moveToNext());
        }
        return quantitatProdcutes;
    }
}
