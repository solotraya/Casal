package ccastro.casal.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ccastro.casal.SQLite.ContracteBD.Client;
import ccastro.casal.SQLite.ContracteBD.PrimerPlato;
import ccastro.casal.SQLite.ContracteBD.SegundoPlato;
import ccastro.casal.SQLite.ContracteBD.Factura;
import ccastro.casal.SQLite.ContracteBD.Menu;
import ccastro.casal.SQLite.ContracteBD.MenuPlato;
import ccastro.casal.SQLite.ContracteBD.Mesa;
import ccastro.casal.SQLite.ContracteBD.Producte;
import ccastro.casal.SQLite.ContracteBD.Reserva_Cliente;
import ccastro.casal.SQLite.ContracteBD.Treballador;
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
        bd.execSQL("drop table if exists " + Treballador.NOM_TAULA + " ;");
        bd.execSQL("drop table if exists " + Producte.NOM_TAULA + " ;");
        bd.execSQL("Drop table if exists " + Reserva_Cliente.NOM_TAULA);
        bd.execSQL("drop table if exists " + Factura.NOM_TAULA + " ;");
        bd.execSQL("drop table if exists " + Mesa.NOM_TAULA + " ;");
        bd.execSQL("drop table if exists " + MenuPlato.NOM_TAULA + " ;");
        bd.execSQL("drop table if exists " + Menu.NOM_TAULA + " ;");
        bd.execSQL("drop table if exists " + PrimerPlato.NOM_TAULA + " ;");
        bd.execSQL("drop table if exists " + SegundoPlato.NOM_TAULA + " ;");
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

    public Cursor RetornaVentes(String data){
        return bd.rawQuery(consulta.RetornaVentes(data),null);
    }

    public Cursor RetornaVentesDataEstatVenta(String estatVenta, String fecha){
        return bd.rawQuery(consulta.RetornaVentesDataEstatVenta(estatVenta,fecha),null);
    }

    public Cursor RetornaFacturaIdCliente(String idCliente){
        return bd.rawQuery(consulta.RetornaFacturaIdCliente(idCliente),null);
    }

    public Cursor RetornaFacturaId_Venta(String idVenta){
        return bd.rawQuery(consulta.RetornaFacturaId_Venta(idVenta),null);
    }

    public Cursor RetornaMesasReservadasData(String data){
        return bd.rawQuery(consulta.RetornaMesasReservadasData(data),null);
    }

    public Cursor RetornaClientsReservadosMesa(String idMesa, String data){
        return bd.rawQuery(consulta.RetornaClientsReservadosMesa(idMesa,data),null);
    }
    public Cursor  RetornaTaulaDefecteClient(String idCliente){
        return bd.rawQuery(consulta.RetornaTaulaDefecteClient(idCliente),null);
    }

    public Cursor RetornaTodasLasMesas() {
        return bd.rawQuery(consulta.RetornaTodasLasMesas,null);
    }

    public Cursor RetornaProductes(String tipusProducte){
        return bd.rawQuery(consulta.RetornaProductes(tipusProducte),null);
    }

    public Cursor RetornaPrimerosPlatos (){
        return bd.rawQuery(consulta.RetornaPrimerosPlatos(),null);
    }

    public Cursor RetornaSegundosPlatos (){
        return bd.rawQuery(consulta.RetornaSegundosPlatos(),null);
    }
    public Cursor RetornaMenuSemanaAño(String semanaAño){
        return bd.rawQuery(consulta.RetornaMenuSemanaAño(semanaAño),null);
    }

    public Cursor verificarLogin(String userName, String password){
        return bd.rawQuery(consulta.verificarLogin(userName,password),null);
    }
    public Cursor AñadirProductoAFactura(String id_cliente, String idProducto){
         bd.rawQuery(consulta.AñadirProductoAFactura(id_cliente,idProducto),null);
         return null;
    }
    public Cursor EncontrarId_VentaFacturaSinPagar(String id_cliente){
        return bd.rawQuery(consulta.EncontrarId_VentaFacturaSinPagar(id_cliente),null);
    }
    public Cursor EncontrarId_VentaFacturaSinPagarProducto(String id_producte){
        return bd.rawQuery(consulta.EncontrarId_VentaFacturaSinPagarProducto(id_producte),null);
    }
    public Cursor ObtenirQuantitatProductesFactura(String idVenta){
        return bd.rawQuery(consulta.ObtenirQuantitatProductesFactura(idVenta),null);
    }
    public Cursor ObtenirQuantitatReservesSenseIDVenta(String id_cliente){
        return bd.rawQuery(consulta.ObtenirQuantitatReservesSenseIDVenta(id_cliente),null);

    }
    public Cursor obtenirNumeroDeClients(String cadenaClient) {
        return bd.rawQuery(consulta.obtenirNumeroDeClients(cadenaClient), null);
    }
    public Cursor obtenirQuantitatFacturesVenta(String idVenta){
        return bd.rawQuery(consulta.obtenirQuantitatFacturesVenta(idVenta),null);
    }

    public Cursor obtenirCuantitatClienteBarraSinPagar(){
        return bd.rawQuery(consulta.obtenirCuantitatClienteBarraSinPagar(),null);
    }
    public Cursor obtenirDadesClientPerId(String id_cliente){
        return bd.rawQuery(consulta.obtenirDadesClientPerId(id_cliente),null);
    }

    public Cursor obtenirDadesProductetPerId(String id_producte){
        return bd.rawQuery(consulta.obtenirDadesProductetPerId(id_producte),null);
    }

    public Cursor obtenirDadesPrimerPlatoPerId(String id_plato){
        return bd.rawQuery(consulta.obtenirDadesPrimerPlatoPerId(id_plato),null);
    }

    public Cursor obtenirDadesSegundoPlatoPerId(String id_plato){
        return bd.rawQuery(consulta.obtenirDadesSegundoPlatoPerId(id_plato),null);
    }

    public Cursor obtenirMenuSetmana(String semana){
        return bd.rawQuery(consulta.obtenirMenuSetmana(semana),null);
    }


    public void EliminarTotsElsClientsDeBarra(){
        String where = Client.NOM_CLIENT + " = '~Cliente Barra'";
        String[] selection = {};
        bd.delete(Client.NOM_TAULA,where,selection);
    }
    public void ActalitzaEstatVenta(String _id,String estatVenta) {
        Integer idVenta = Integer.parseInt(_id);
        ContentValues valores = new ContentValues();
        valores.put(Venta.VENTA_COBRADA, estatVenta);
        String where = Venta._ID + " = ? ";
        String[] selection = {""+idVenta};
        bd.update(Venta.NOM_TAULA, valores, where, selection);
    }

    public void ActalitzarPagoReservaDiaActual(String _id) {
        Integer idCliente = Integer.parseInt(_id);
        ContentValues valores = new ContentValues();
        valores.put(Reserva_Cliente.PAGADO, "1");
        String where = Reserva_Cliente.ID_CLIENTE + " = ? AND "+Reserva_Cliente.DIA_RESERVADO+" LIKE strftime('%Y %m %d','now')";
        String[] selection = {""+idCliente};
        bd.update(Reserva_Cliente.NOM_TAULA, valores, where, selection);
        Log.d("proba", "Actualitzat");
    }
    public void ActalitzarPagoReservaFecha(String _id,String fecha) {
        Integer idCliente = Integer.parseInt(_id);
        ContentValues valores = new ContentValues();
        valores.put(Reserva_Cliente.PAGADO, "1");
        String where = Reserva_Cliente.ID_CLIENTE + " = ? AND "+Reserva_Cliente.DIA_RESERVADO+" LIKE '"+fecha+"'";
        String[] selection = {""+idCliente};
        bd.update(Reserva_Cliente.NOM_TAULA, valores, where, selection);
        Log.d("proba pagdo: ", fecha);
    }
    public void ActalitzarPagoReservaFecha(String _id) {
        Integer idCliente = Integer.parseInt(_id);
        ContentValues valores = new ContentValues();
        valores.put(Reserva_Cliente.PAGADO, "1");
        String where = Reserva_Cliente.ID_CLIENTE + " = ? ";
        String[] selection = {""+idCliente};
        bd.update(Reserva_Cliente.NOM_TAULA, valores, where, selection);

    }
    public void ActualitzarAsistenciaReserva(String _id, String data) {
        Integer idCliente = Integer.parseInt(_id);
        ContentValues valores = new ContentValues();
        valores.put(Reserva_Cliente.ASISTENCIA, "1");
        String where = Reserva_Cliente.ID_CLIENTE + " = ? AND "+Reserva_Cliente.DIA_RESERVADO+" LIKE '"+data+"'";
        String[] selection = {""+idCliente};
        bd.update(Reserva_Cliente.NOM_TAULA, valores, where, selection);
        Log.d("proba", "Actualitzat");
    }
    public void ActualitzarFechaHoraFactura (Integer idVenta, String data, String hora){

        ContentValues valores = new ContentValues();
        valores.put(Venta.DATA_VENTA, data);
        valores.put(Venta.HORA_VENTA, hora);
        String where = Venta._ID+ " = ?";
        String[] selection = {""+idVenta};
        bd.update(Venta.NOM_TAULA, valores, where, selection);
        Log.d("proba", "Actualitzat");
    }
    public long ActualitzarClient (Integer idClient, String nom, String cognoms, String tipusClient,Integer mesaFavorita,
                                   String tipoPago, String tipoComida, String observacions){

        ContentValues valores = new ContentValues();
        valores.put(Client.NOM_CLIENT, nom);
        valores.put(Client.COGNOMS_CLIENT, cognoms);
        valores.put(Client.TIPUS_CLIENT, tipusClient);
        valores.put(Client.MESA_FAVORITA, mesaFavorita);
        valores.put(Client.TIPO_PAGO, tipoPago);
        valores.put(Client.TIPO_COMIDA, tipoComida);
        valores.put(Client.OBSERVACIONS_CLIENT, observacions);
        String where = Client._ID+ " = ?";
        String[] selection = {""+idClient};
        return bd.update(Client.NOM_TAULA, valores, where, selection);
    }
    public long ActualitzarProducte (Integer idProducto, String nomProducte, String preuProducte){
        ContentValues valores = new ContentValues();
        valores.put(Producte.NOM_PRODUCTE, nomProducte);
        valores.put(Producte.PREU_PRODUCTE, preuProducte);
        String where = Producte._ID+ " = ?";
        String[] selection = {""+idProducto};
        return bd.update(Producte.NOM_TAULA, valores, where, selection);
    }
    public long ActualitzarFactura (String idFactura, String quantitat){
        Integer idFact = Integer.parseInt(idFactura);
        Integer quanti = Integer.parseInt(quantitat);
        ContentValues valores = new ContentValues();
        valores.put(Factura.QUANTITAT_PRODUCTE,quanti);
        String where = Factura._ID+ " = ?";
        String[] selection = {""+idFact};
        return bd.update(Factura.NOM_TAULA, valores, where, selection);
    }
    public long ActualizarPrimerPlato (String idPlato, String nombrePlato,String gluten,String crustaceos, String huevos,String pescado, String cacahuetes,
                                        String lacteos, String cascaras,String apio, String sulfitos, String moluscos) {
        Integer idPlat = Integer.parseInt(idPlato);
        ContentValues valores = new ContentValues();
        valores.put(PrimerPlato.NOMBRE_PLATO,nombrePlato);
        valores.put(PrimerPlato.GLUTEN,gluten);
        valores.put(PrimerPlato.CRUSTACEOS,crustaceos);
        valores.put(PrimerPlato.HUEVOS,huevos);
        valores.put(PrimerPlato.PESCADO,pescado);
        valores.put(PrimerPlato.LACTEOS,lacteos);
        valores.put(PrimerPlato.FRUTOS_DE_CASCARA,cascaras);
        valores.put(PrimerPlato.APIO,apio);
        valores.put(PrimerPlato.DIOXIDO_AZUFRE_SULFITOS,sulfitos);
        valores.put(PrimerPlato.MOLUSCOS,moluscos);

        String where = PrimerPlato._ID+ " = ?";
        String[] selection = {""+idPlat};
        return bd.update(PrimerPlato.NOM_TAULA, valores, where, selection);
    }
    public long ActualizarSegundoPlato (String idPlato, String nombrePlato,String gluten,String crustaceos, String huevos,String pescado, String cacahuetes,
                                        String lacteos, String cascaras,String apio, String sulfitos, String moluscos) {
        Integer idPlat = Integer.parseInt(idPlato);
        ContentValues valores = new ContentValues();
        valores.put(SegundoPlato.NOMBRE_PLATO,nombrePlato);
        valores.put(SegundoPlato.GLUTEN,gluten);
        valores.put(SegundoPlato.CRUSTACEOS,crustaceos);
        valores.put(SegundoPlato.HUEVOS,huevos);
        valores.put(SegundoPlato.PESCADO,pescado);
        valores.put(SegundoPlato.LACTEOS,lacteos);
        valores.put(SegundoPlato.FRUTOS_DE_CASCARA,cascaras);
        valores.put(SegundoPlato.APIO,apio);
        valores.put(SegundoPlato.DIOXIDO_AZUFRE_SULFITOS,sulfitos);
        valores.put(SegundoPlato.MOLUSCOS,moluscos);

        String where = SegundoPlato._ID+ " = ?";
        String[] selection = {""+idPlat};
        return bd.update(SegundoPlato.NOM_TAULA, valores, where, selection);
    }


    /**
     * Mètode per inserir Client
     * @param nom del Client
     * @param cognoms del Client
     * @param tipusClient del Client
     * @return posicio a taula client
     */
    public long InserirClient(String nom, String cognoms, String tipusClient,Integer mesaFavorita,
                              String tipoPago, String tipoComida, String observacions) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(Client.NOM_CLIENT, nom);
        initialValues.put(Client.COGNOMS_CLIENT, cognoms);
        initialValues.put(Client.TIPUS_CLIENT, tipusClient);
        initialValues.put(Client.MESA_FAVORITA, mesaFavorita);
        initialValues.put(Client.TIPO_PAGO, tipoPago);
        initialValues.put(Client.TIPO_COMIDA, tipoComida);
        initialValues.put(Client.OBSERVACIONS_CLIENT, observacions);
        return bd.insert(Client.NOM_TAULA, null, initialValues);
    }

    public long InserirTreballador(String nom, String cognoms, String userName, String password) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(Treballador.NOM_TREBALLADOR, nom);
        initialValues.put(Treballador.COGNOMS_TREBALLADOR, cognoms);
        initialValues.put(Treballador.USER_NAME, userName);
        initialValues.put(Treballador.PASSWORD, password);
        return bd.insert(Treballador.NOM_TAULA, null, initialValues);
    }

    public long InserirProducte(String nom, String preu, String tipusProducte) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(Producte.NOM_PRODUCTE, nom);
        initialValues.put(Producte.PREU_PRODUCTE, preu);
        initialValues.put(Producte.TIPUS_PRODUCTE, tipusProducte);
        return bd.insert(Producte.NOM_TAULA, null, initialValues);
    }
    public long InserirVenta(Integer idClient,Integer idTreballador, String dataVenta, String ventaCobrada, String horaVenta) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(Venta.ID_CLIENT, idClient);
        initialValues.put(Venta.ID_TREBALLADOR, idTreballador);
        initialValues.put(Venta.DATA_VENTA, dataVenta);
        initialValues.put(Venta.VENTA_COBRADA, ventaCobrada);
        initialValues.put(Venta.HORA_VENTA, horaVenta);
        return bd.insert(Venta.NOM_TAULA, null, initialValues);
    }
    public long InserirFactura(Integer idProducte, Integer idVenta, Integer quantitatProducte) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(Factura.ID_PRODUCTE, idProducte);
        initialValues.put(Factura.ID_VENTA, idVenta);
        initialValues.put(Factura.QUANTITAT_PRODUCTE, quantitatProducte);
        return bd.insert(Factura.NOM_TAULA, null, initialValues);
    }

    public long InserirMesa(String nombreMesa) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(Mesa.NOMBRE_MESA, nombreMesa);
        return bd.insert(Mesa.NOM_TAULA, null, initialValues);
    }
    public long InserirPrimerPlato(String nombrePlato, String gluten, String crustaceos, String huevos, String pescado, String cacahuetes,
                              String lacteos, String cascaras, String apio, String dioxidos, String moluscos) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(PrimerPlato.NOMBRE_PLATO, nombrePlato);
        initialValues.put(PrimerPlato.GLUTEN, gluten);
        initialValues.put(PrimerPlato.CRUSTACEOS, crustaceos);
        initialValues.put(PrimerPlato.HUEVOS, huevos);
        initialValues.put(PrimerPlato.PESCADO, pescado);
        initialValues.put(PrimerPlato.CACAHUETES, cacahuetes);
        initialValues.put(PrimerPlato.LACTEOS, lacteos);
        initialValues.put(PrimerPlato.FRUTOS_DE_CASCARA, cascaras);
        initialValues.put(PrimerPlato.APIO, apio);
        initialValues.put(PrimerPlato.DIOXIDO_AZUFRE_SULFITOS, dioxidos);
        initialValues.put(PrimerPlato.MOLUSCOS, moluscos);
        return bd.insert(PrimerPlato.NOM_TAULA, null, initialValues);
    }
    public long InserirSegundoPlato(String nombrePlato, String gluten, String crustaceos, String huevos, String pescado, String cacahuetes,
                                   String lacteos, String cascaras, String apio, String dioxidos, String moluscos) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(SegundoPlato.NOMBRE_PLATO, nombrePlato);
        initialValues.put(SegundoPlato.GLUTEN, gluten);
        initialValues.put(SegundoPlato.CRUSTACEOS, crustaceos);
        initialValues.put(SegundoPlato.HUEVOS, huevos);
        initialValues.put(SegundoPlato.PESCADO, pescado);
        initialValues.put(SegundoPlato.CACAHUETES, cacahuetes);
        initialValues.put(SegundoPlato.LACTEOS, lacteos);
        initialValues.put(SegundoPlato.FRUTOS_DE_CASCARA, cascaras);
        initialValues.put(SegundoPlato.APIO, apio);
        initialValues.put(SegundoPlato.DIOXIDO_AZUFRE_SULFITOS, dioxidos);
        initialValues.put(SegundoPlato.MOLUSCOS, moluscos);
        return bd.insert(SegundoPlato.NOM_TAULA, null, initialValues);
    }

    public long InserirMenu(String semanaMenu){
        ContentValues initialValues = new ContentValues();
        initialValues.put(Menu.SEMANA_MENU, semanaMenu);
        return bd.insert(Menu.NOM_TAULA, null, initialValues);
    }
    public long InserirMenuPlato(Integer idMenu,  Integer primerPlato, Integer segundoPlato,String diaMenu){
        ContentValues initialValues = new ContentValues();
        initialValues.put(MenuPlato.ID_MENU, idMenu);
        initialValues.put(MenuPlato.PRIMER_PLATO, primerPlato);
        initialValues.put(MenuPlato.SEGUNDO_PLATO, segundoPlato);
        initialValues.put(MenuPlato.DIA_MENU, diaMenu);
        return bd.insert(MenuPlato.NOM_TAULA, null, initialValues);
    }

    public long InserirReserva_Cliente (String dia_reservado, String asistencia,String pagado, Integer idCliente, Integer idMesa) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(Reserva_Cliente.DIA_RESERVADO,dia_reservado);
        initialValues.put(Reserva_Cliente.ASISTENCIA,asistencia);
        initialValues.put(Reserva_Cliente.PAGADO,pagado);
        initialValues.put(Reserva_Cliente.ID_CLIENTE,idCliente);
        initialValues.put(Reserva_Cliente.ID_MESA,idMesa);
        return bd.insert(Reserva_Cliente.NOM_TAULA, null, initialValues);
    }
    public long EliminarClient(String idClient){
        String where = Client._ID+ " = ?";
        String[] selection = {""+idClient};
        return bd.delete(Client.NOM_TAULA,where,selection);
    }
    public long EliminarProducte(String idProducte){
        String where = Producte._ID+ " = ?";
        String[] selection = {""+idProducte};
        return bd.delete(Producte.NOM_TAULA,where,selection);
    }
    public long EliminarFactura(String idFactura){
        String where = Factura._ID+ " = ?";
        String[] selection = {""+idFactura};
        return bd.delete(Factura.NOM_TAULA,where,selection);
    }
    public long EliminarVenta(String idVenta){
        String where = Venta._ID+ " = ?";
        String[] selection = {""+idVenta};
        return bd.delete(Venta.NOM_TAULA,where,selection);
    }
    public long EliminarPrimerPlato(String idPlato){
        String where = PrimerPlato._ID+ " = ?";
        String[] selection = {""+idPlato};
        return bd.delete(PrimerPlato.NOM_TAULA,where,selection);
    }
    public long EliminarSegundoPlato(String idPlato){
        String where = SegundoPlato._ID+ " = ?";
        String[] selection = {""+idPlato};
        return bd.delete(SegundoPlato.NOM_TAULA,where,selection);
    }
}
