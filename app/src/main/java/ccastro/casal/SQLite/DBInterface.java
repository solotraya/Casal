package ccastro.casal.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ccastro.casal.SQLite.ContracteBD.Client;
import ccastro.casal.SQLite.ContracteBD.Producte;
import ccastro.casal.SQLite.ContracteBD.Venta;

/**
 * @author Carlos Alberto Castro Cañabate
 *
 * Classe per crear la base de dades amb ajuda de la classe AjudaBD
 * i retornar consultes de dades emmagatzemada a la base de dades.
 *
 */
public class DBInterface {
    public static final String TAG = "DBInterface";
    ConsultesSQL consulta=new ConsultesSQL();


    private final Context context;
    private AjudaBD ajuda;
    private SQLiteDatabase bd;

    /**
     * Mètode constructor de la classe DBInterface
     * @param con contexte
     */
    public DBInterface(Context con) {
        this.context = con;
        ajuda = new AjudaBD(context);
    }

    /**
     * Mètode per obrir la bd
     * @return this
     */
    public DBInterface obre() {
        bd = ajuda.getWritableDatabase();
        return this;
    }

    /**
     * Mètode per tancar la base de dades
     */
    public void tanca() {
        ajuda.close();
    }

    /**
     * Mètode per esborrar les taules de la base de dades
     */
    public void Esborra() {
        bd.execSQL("drop table if exists " + Venta.NOM_TAULA + " ;");
        bd.execSQL("drop table if exists " + Client.NOM_TAULA + " ;");
        bd.execSQL("drop table if exists " + Producte.NOM_TAULA + " ;");
        ajuda.onCreate(bd);
    }

    /**
     * Mètode per retornar tots els clients
     * @return cursor amb els clients a retornar
     */
    public Cursor RetornaTotsElsProductes() {
        return bd.rawQuery(consulta.RetornaTotsElsProductes,null);
    }

    /**
     * Mètode per retornar tots els serveis sense filtrat.
     * @return cursor amb els serveis a retornar
     */
    public Cursor RetornaTotsElsClients() {
        return bd.rawQuery(consulta.RetornaTotsElsClients, null);
    }


    /**
     * Mètode per inserir Client
     * @param nom del Client
     * @param cognoms del Client
     * @param tipusClient del Client
     * @return posicio a taula client
     */
    public long InserirClient(String nom, String cognoms, String tipusClient) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(Client.NOM_CLIENT, nom);
        initialValues.put(Client.COGNOMS_CLIENT, cognoms);
        initialValues.put(Client.TIPUS_CLIENT, tipusClient);
        return bd.insert(Client.NOM_TAULA, null, initialValues);
    }
    public long InserirProducte(String nom, String preu, String tipusProducte) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(Producte.NOM_PRODUCTE, nom);
        initialValues.put(Producte.PREU_PRODUCTE, preu);
        initialValues.put(Producte.TIPUS_PRODUCTE, tipusProducte);
        return bd.insert(Producte.NOM_TAULA, null, initialValues);
    }
    public long InserirVenta(String data) {

        ContentValues initialValues = new ContentValues();
        return bd.insert(Venta.NOM_TAULA, null, initialValues);
    }
}
