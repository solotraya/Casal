package ccastro.casal.SQLite;

import android.database.Cursor;

import ccastro.casal.SQLite.ContracteBD.Client;
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


    /**
     *
     * Metode per retonar el query que recollirà tots els treballadors junt amb la data i hora del
     * servei al qual estàn lligats.
     *
     * Mitjançant un HashMap definim quines columnes es vol agafar entre les dues taules, i amb
     * el mètode setTables, amb quines taules volem fer un Join
     *
     * @return SQLiteQueryBuilder creat
     */ /*
    public SQLiteQueryBuilder RetornaQuery(){

        HashMap<String, String> gProjectionMap= new HashMap<>();
        gProjectionMap.put(Reserves._ID,Reserves.NOM_TAULA+"."+Reserves._ID);
        gProjectionMap.put(Reserves.LOCALITZADOR,Reserves.LOCALITZADOR);
        gProjectionMap.put(Reserves.NOM_TITULAR,Reserves.NOM_TITULAR);
        gProjectionMap.put(Reserves.COGNOM2_TITULAR,Reserves.COGNOM2_TITULAR);
        gProjectionMap.put(Reserves.COGNOM1_TITULAR, Reserves.COGNOM1_TITULAR);
        gProjectionMap.put(Reserves.EMAIL_TITULAR, Reserves.EMAIL_TITULAR);
        gProjectionMap.put(Reserves.CHECK_IN, Reserves.CHECK_IN);
        gProjectionMap.put(Reserves.QR_CODE,Reserves.QR_CODE);
        gProjectionMap.put(Reserves.DNI_TITULAR, Reserves.DNI_TITULAR);
        gProjectionMap.put(Serveis.DATA_SERVEI,Serveis.DATA_SERVEI);
        gProjectionMap.put(Serveis.DESCRIPCIO,Serveis.DESCRIPCIO);
        gProjectionMap.put(Serveis.HORA_INICI,Serveis.HORA_INICI);
        gProjectionMap.put(Serveis.HORA_FI,Serveis.HORA_FI);

        SQLiteQueryBuilder QBuilder = new SQLiteQueryBuilder();
        QBuilder.setProjectionMap(gProjectionMap);
        QBuilder.setTables(Reserves.NOM_TAULA + " LEFT JOIN " + Serveis.NOM_TAULA + " ON " + Reserves.ID_SERVEI + " = " + Serveis.NOM_TAULA + "." + Serveis._ID);

        return QBuilder;
    }*/

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


