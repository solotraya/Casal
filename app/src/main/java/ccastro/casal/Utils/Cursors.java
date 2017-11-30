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
}
