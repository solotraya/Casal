package ccastro.casal.SQLite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ccastro.casal.SQLite.ContracteBD.Client;
import ccastro.casal.SQLite.ContracteBD.Factura;
import ccastro.casal.SQLite.ContracteBD.Producte;
import ccastro.casal.SQLite.ContracteBD.Venta;

import static android.content.ContentValues.TAG;


/*****************************************************************************************
 *********************************  CREACIÓ DE BASE DE DADES *****************************
 *****************************************************************************************
 ****************************************************************************************/


/**
 * La classe SQLiteOpenHelper, que és una classe que serveix
 * d’ajuda per gestionar la creació de bases de dades i gestió de versions.
 * AjudaBD  hereta d’aquesta d’SQLiteOpenHelper s'’ocupa d’obrir la base de dades si aquesta existeix
 * o crear-la en cas contrari, i actualitzar-la si és necessari.
 */
public class AjudaBD extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "casalcivic.db";
    private static final int DATABASE_VERSION = 3;

    public AjudaBD(Context con)
    {
        super(con, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Crea les taules a la basse de dades, l'ordre és important, per tal de no crear
     * conflictes amb les claus foranes: primer Treballador, després Serveis i per últim Reserves
     * @param db
     */
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BD_CREATE_PRODUCTE);
        db.execSQL(BD_CREATE_CLIENT);
        db.execSQL(BD_CREATE_VENTA);
        db.execSQL(BD_CREATE_FACTURA);
    }

    public static final String BD_CREATE_PRODUCTE = "CREATE TABLE IF NOT EXISTS " + Producte.NOM_TAULA + "("
            + Producte._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Producte.NOM_PRODUCTE + " TEXT NOT NULL, "
            + Producte.PREU_PRODUCTE + " TEXT NOT NULL, "
            + Producte.TIPUS_PRODUCTE + " TEXT NOT NULL);";

    public static final String BD_CREATE_CLIENT = "CREATE TABLE IF NOT EXISTS " + Client.NOM_TAULA + "("
            + Client._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Client.NOM_CLIENT + " TEXT NOT NULL, "
            + Client.COGNOMS_CLIENT + " TEXT NOT NULL, "
            + Client.TIPUS_CLIENT + " TEXT NOT NULL);";

    public static final String BD_CREATE_FACTURA = "CREATE TABLE IF NOT EXISTS " + Factura.NOM_TAULA + "("
            + Factura._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Factura.ID_PRODUCTE + " INTEGER NOT NULL, "
            + Factura.ID_VENTA + " INTEGER NOT NULL, "
            + "FOREIGN KEY("+ Factura.ID_PRODUCTE+") REFERENCES " + Client.NOM_TAULA +"(" + Client._ID +"),"
            + "FOREIGN KEY("+ Factura.ID_VENTA+") REFERENCES " + Venta.NOM_TAULA +"(" + Venta._ID +"));";

    public static final String BD_CREATE_VENTA = "CREATE TABLE IF NOT EXISTS " + Venta.NOM_TAULA + "("
            + Venta._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Venta.ID_CLIENT + " INTEGER NOT NULL, "
         //   + Venta.ID_FACTURA + " INTEGER NOT NULL, "
            + Venta.DATA_VENTA +  " TEXT NOT NULL, "
            + Venta.VENTA_COBRADA + " TEXT NOT NULL, "
            + Venta.TOTAL_VENTA +  " TEXT, "  // dejo que pueda ser null, para luego hacer el calculo.
            + "FOREIGN KEY("+ Venta.ID_CLIENT+") REFERENCES " + Client.NOM_TAULA +"(" + Client._ID +"));";


    /**
     * Elimina les taules i les torna a crear.
     * L'ordre és important, primer Venta
     * per tal de no crear un conflicte amb les claus foranes.
     */
    public void onUpgrade(SQLiteDatabase db, int VersioAntiga, int VersioNova) {
        Log.w(TAG, "Actualitzant Base de dades versió " + VersioAntiga + " a " + VersioNova + ". Destruirà totes les dades");
        db.execSQL("Drop table if exists " + Factura.NOM_TAULA);
        db.execSQL("Drop table if exists " + Venta.NOM_TAULA);
        db.execSQL("Drop table if exists " + Client.NOM_TAULA);
        db.execSQL("Drop table if exists " + Producte.NOM_TAULA);
        onCreate(db);
    }

}