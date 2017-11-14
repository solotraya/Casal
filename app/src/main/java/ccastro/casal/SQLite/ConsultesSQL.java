package ccastro.casal.SQLite;

import android.database.Cursor;

import ccastro.casal.SQLite.ContracteBD.Client;
import ccastro.casal.SQLite.ContracteBD.Factura;
import ccastro.casal.SQLite.ContracteBD.Producte;


/**
 * @author Carlos Alberto Castro Cañabate
 *
 * Classe que ajudarà  a retornar un seguit de consultes de la base de dades
 */

public class ConsultesSQL {
    String RetornaTotsElsClients ="Select c."+ ContracteBD.Client._ID+", c."+ Client.NOM_CLIENT+", c."+  Client.COGNOMS_CLIENT+", c."+  Client.TIPUS_CLIENT+
            " FROM "+ ContracteBD.Client.NOM_TAULA+" c";

    String RetornaTotsElsProductes ="Select p."+ ContracteBD.Producte._ID+", p."+ Producte.NOM_PRODUCTE+", p."
            +  Producte.TIPUS_PRODUCTE+", p."+  Producte.PREU_PRODUCTE+
            " FROM "+ ContracteBD.Producte.NOM_TAULA+" p";

    String RetornaTotesLesVentes ="Select v."+ ContracteBD.Venta._ID+", v."+ ContracteBD.Venta.DATA_VENTA+", v."
            + ContracteBD.Venta.VENTA_COBRADA+", c."+ Client.NOM_CLIENT+
            " FROM "+ ContracteBD.Venta.NOM_TAULA+" v"+
            " LEFT JOIN  " + ContracteBD.Client.NOM_TAULA + " c ON c." + ContracteBD.Client._ID + " = v." + ContracteBD.Venta.ID_CLIENT;

    public String RetornaFacturaId_Venta(String id_Venta){
        return "Select p."+ Producte.NOM_PRODUCTE+", p."+ Producte.PREU_PRODUCTE+", p."+ Producte.TIPUS_PRODUCTE+
                " FROM "+ ContracteBD.Factura.NOM_TAULA+" f"+
                //" LEFT JOIN  " + Venta.NOM_TAULA + " v ON f." + Factura.ID_VENTA + " = v." + Venta._ID+","+
                " LEFT JOIN  " + Producte.NOM_TAULA + " p ON f." + Factura.ID_PRODUCTE + " = p." + Producte._ID+
                " WHERE f."+Factura.ID_VENTA+ " = "+id_Venta;
    }

    /**
     *
     * Metode per retonar el query que recollirà tots els treballadors junt amb la data i hora del
     * servei al qual estàn lligats.
     *
     * Mitjançant un HashMap definim quines columnes es vol agafar entre les dues taules, i amb
     * el mètode setTables, amb quines taules volem fer un Join
     *
     * @return SQLiteQueryBuilder creat
     */



    /**
     *
     * Created by Maria Remedios Ortega
     * Metode per moure el cursor a la primera posició
     * @param cursor a moure
     * @return cursor a retornar
     */
    public Cursor mouCursor(Cursor cursor){
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }
}


