package ccastro.casal.SQLite;

import ccastro.casal.SQLite.ContracteBD.Client;
import ccastro.casal.SQLite.ContracteBD.Factura;
import ccastro.casal.SQLite.ContracteBD.Mesa;
import ccastro.casal.SQLite.ContracteBD.Producte;
import ccastro.casal.SQLite.ContracteBD.Reserva_Cliente;
import ccastro.casal.SQLite.ContracteBD.Treballador;
import ccastro.casal.SQLite.ContracteBD.Venta;
import ccastro.casal.SQLite.ContracteBD.MenuPlato;
import ccastro.casal.SQLite.ContracteBD.PrimerPlato;
import ccastro.casal.SQLite.ContracteBD.SegundoPlato;
import ccastro.casal.SQLite.ContracteBD.Menu;


/**
 * @author Carlos Alberto Castro Cañabate
 *
 * Classe que ajudarà  a retornar un seguit de consultes de la base de dades
 */

public class ConsultesSQL {
    String RetornaTodasLasMesas ="Select m."+ Mesa._ID+", m."+ Mesa.NOMBRE_MESA+
            " FROM "+ Mesa.NOM_TAULA+" m";


    String RetornaClientsReservadosDataActual ="Select c."+Client.MESA_FAVORITA+", c."+Client.COGNOMS_CLIENT+", m."+ Mesa.NOMBRE_MESA+
            " FROM "+ Reserva_Cliente.NOM_TAULA+" r"+
            " LEFT JOIN  " + Client.NOM_TAULA + " c ON c." + ContracteBD.Client._ID + " = r." + Reserva_Cliente.ID_CLIENTE+
            " WHERE r."+ Reserva_Cliente.DIA_RESERVADO+" LIKE strftime('%Y %m %d','now')";

    String RetornaTotsElsProductes ="Select p."+ ContracteBD.Producte._ID+", p."+ Producte.NOM_PRODUCTE+", p."
            +  Producte.TIPUS_PRODUCTE+", p."+  Producte.PREU_PRODUCTE+
            " FROM "+ Producte.NOM_TAULA+" p";

    String RetornaTotsElsClients ="Select c."+ ContracteBD.Client._ID+", c."+ Client.NOM_CLIENT+", c."+  Client.COGNOMS_CLIENT
            +", c."+  Client.TIPUS_CLIENT+", c."+Client.TIPO_PAGO+", c."+Client.TIPO_COMIDA+", c."+Client.OBSERVACIONS_CLIENT+
            " FROM "+ Client.NOM_TAULA+" c"+
            " WHERE c."+ Client.NOM_CLIENT+" NOT LIKE '~Cliente Barra' "+
            " ORDER BY c."+ Client.NOM_CLIENT;

    public String RetornaTipusClients(Integer tipusClient){
        return  "Select c."+ ContracteBD.Client._ID+", c."+ Client.NOM_CLIENT+", c."+  Client.COGNOMS_CLIENT
                +", c."+  Client.TIPUS_CLIENT+", c."+Client.TIPO_PAGO+", c."+Client.TIPO_COMIDA+", c."+Client.OBSERVACIONS_CLIENT+
                " FROM "+ Client.NOM_TAULA+" c"+
                " WHERE c."+ Client.NOM_CLIENT+" NOT LIKE '~Cliente Barra' AND c."+Client.TIPUS_CLIENT+" LIKE '"+tipusClient+"' "+
                " ORDER BY c."+ Client.NOM_CLIENT;
    }

    public String RetornaVentes(String fecha){
        return " Select v."+ Venta._ID+", v."+ Venta.DATA_VENTA+
                ", v." + Venta.VENTA_COBRADA+", v." + Venta.HORA_VENTA+", c."+ Client.NOM_CLIENT+", c."+  Client.COGNOMS_CLIENT+
                ", t."+  Treballador.NOM_TREBALLADOR+", t."+ Treballador.COGNOMS_TREBALLADOR+
                " FROM "+ Venta.NOM_TAULA+" v"+
                " LEFT JOIN  " + Treballador.NOM_TAULA + " t ON t." + Treballador._ID + " = v." + Venta.ID_TREBALLADOR+
                " LEFT JOIN  " + Client.NOM_TAULA + " c ON c." + Client._ID + " = v." + Venta.ID_CLIENT+
                " WHERE v."+ Venta.DATA_VENTA+" LIKE '"+fecha+"'";
    }

    public String  RetornaMesasReservadasData (String data) {
        return "Select distinct m." + Mesa._ID + ", m." + Mesa.NOMBRE_MESA + ", r." + Reserva_Cliente.DIA_RESERVADO +
                ", (SELECT COUNT (*) FROM " + Reserva_Cliente.NOM_TAULA + " r" + " WHERE r." + Reserva_Cliente.DIA_RESERVADO + " LIKE '"+data+"' AND r."+Reserva_Cliente.ASISTENCIA+" LIKE '0') as columnaTotal"+
                ", (SELECT COUNT (*) FROM " + Reserva_Cliente.NOM_TAULA + " r" + " WHERE r." + Reserva_Cliente.DIA_RESERVADO+ " LIKE '"+data+"' AND r."+Reserva_Cliente.ID_MESA+" LIKE 1 AND r."+Reserva_Cliente.ASISTENCIA+" LIKE '0') as columnaLlevar"+
                ", (SELECT distinct m." + Mesa._ID + " FROM " + Mesa.NOM_TAULA + " r" + " WHERE r." + Reserva_Cliente.DIA_RESERVADO+ " LIKE '"+data+"' AND r."+Reserva_Cliente.ASISTENCIA+" LIKE '0') as columnaMesas"+
                " FROM " + Mesa.NOM_TAULA + " m" +
                " LEFT JOIN  " + Reserva_Cliente.NOM_TAULA + " r ON m." + Mesa._ID + " = r." + Reserva_Cliente.ID_MESA +
                " WHERE r." + Reserva_Cliente.DIA_RESERVADO + " LIKE '"+data+"'  AND r."+Reserva_Cliente.ASISTENCIA+" LIKE '0'";
                // " WHERE r."+ Reserva_Cliente.DIA_RESERVADO+" LIKE strftime('%Y %m %d','now')";
    }


    public String RetornaFacturaId_Venta(String id_Venta){
        return " Select p."+ Producte.NOM_PRODUCTE+", p."+ Producte.PREU_PRODUCTE+", p."+ Producte.TIPUS_PRODUCTE+ ", f."+ Factura._ID+
                ", f."+ Factura.QUANTITAT_PRODUCTE+", v."+ Venta.DATA_VENTA+ ", v."+ Venta.VENTA_COBRADA+", v." + Venta.HORA_VENTA+", v." + Venta.ID_CLIENT+
                " FROM "+ Factura.NOM_TAULA+" f"+
                " LEFT JOIN  " + Venta.NOM_TAULA + " v ON f." + Factura.ID_VENTA + " = v." + Venta._ID+
                " LEFT JOIN  " + Producte.NOM_TAULA + " p ON f." + Factura.ID_PRODUCTE + " = p." + Producte._ID+
                " WHERE f."+Factura.ID_VENTA+ " = "+id_Venta;
    }

    public String RetornaFacturaIdCliente(String idCliente){

        return " Select p."+ Producte.NOM_PRODUCTE+", p."+ Producte.PREU_PRODUCTE+", p."+ Producte.TIPUS_PRODUCTE+
                ", f."+Factura._ID+", f."+ Factura.QUANTITAT_PRODUCTE+", v."+ Venta.DATA_VENTA+ ", v."+ Venta.VENTA_COBRADA+", v." + Venta.HORA_VENTA+
                " FROM "+ Factura.NOM_TAULA+" f"+
                " LEFT JOIN  " + Venta.NOM_TAULA + " v ON f." + Factura.ID_VENTA + " = v." + Venta._ID+
                " LEFT JOIN  " + Producte.NOM_TAULA + " p ON f." + Factura.ID_PRODUCTE + " = p." + Producte._ID+
                " WHERE f."+Factura.ID_VENTA+ " = " +
                "(" +
                    " Select v."+Venta._ID+
                    " FROM "+Venta.NOM_TAULA+" v"+
                    //" LEFT JOIN "+ Reserva_Cliente.NOM_TAULA+" r ON r."+Reserva_Cliente.ID_CLIENTE+" LIKE v."+Venta.ID_CLIENT+
                    " WHERE v."+Venta.ID_CLIENT+" LIKE "+idCliente+" AND (v."+Venta.VENTA_COBRADA+" LIKE '0' OR v."+Venta.VENTA_COBRADA+" LIKE '3')"+
                ")";
    }

    public String RetornaVentesDataEstatVenta(String estatVenta,String fecha){
        return  " Select v."+ Venta._ID+", v."+ Venta.DATA_VENTA+
                ", v." + Venta.VENTA_COBRADA+", v." + Venta.HORA_VENTA+", c."+ Client.NOM_CLIENT+", c."+  Client.COGNOMS_CLIENT+
                ", t."+  Treballador.NOM_TREBALLADOR+", t."+  Treballador.COGNOMS_TREBALLADOR+
                " FROM "+ Venta.NOM_TAULA+" v"+
                " LEFT JOIN  " + Treballador.NOM_TAULA + " t ON t." + Treballador._ID + " = v." + Venta.ID_TREBALLADOR+
                " LEFT JOIN  " + Client.NOM_TAULA + " c ON c." + Client._ID + " = v." + Venta.ID_CLIENT+
                " WHERE v."+ Venta.DATA_VENTA+" LIKE '"+fecha+"' AND v."+ Venta.VENTA_COBRADA+" LIKE "+estatVenta;
    }

    public String RetornaClientsReservadosMesa(String idMesa, String data) {
        return "Select c." + Client._ID + ", c." + Client.NOM_CLIENT + ", c." + Client.COGNOMS_CLIENT + ", c." + Client.TIPUS_CLIENT + ", r." + Reserva_Cliente.PAGADO +
                ", r." + Reserva_Cliente.ASISTENCIA + ", c." + Client.TIPO_PAGO + ", c." + Client.TIPO_COMIDA + ", c." + Client.OBSERVACIONS_CLIENT +
                " FROM " + Reserva_Cliente.NOM_TAULA + " r" +
                " LEFT JOIN  " + Client.NOM_TAULA + " c ON c." + ContracteBD.Client._ID + " = r." + Reserva_Cliente.ID_CLIENTE +
                " WHERE r." + Reserva_Cliente.DIA_RESERVADO + " LIKE '" + data + "' AND r." + Reserva_Cliente.ID_MESA + " LIKE " + idMesa+
                " ORDER BY c."+Client.NOM_CLIENT+" ASC";

        //      " WHERE r."+ Reserva_Cliente.DIA_RESERVADO+" LIKE strftime('%Y %m %d','now') AND r."+Reserva_Cliente.ID_MESA+" LIKE "+idMesa;
    }

    public String RetornaMenuSemanaAño(String semanaAño){

        return "Select mp."+MenuPlato._ID+" ,mp."+MenuPlato.ID_MENU+", mp."+MenuPlato.DIA_MENU+
                ", (SELECT pp."+PrimerPlato.NOMBRE_PLATO+" FROM " + PrimerPlato.NOM_TAULA + " pp WHERE pp." + PrimerPlato._ID + " LIKE mp."+MenuPlato.PRIMER_PLATO+") as 'primerPlato'"+
                ", (SELECT sp."+SegundoPlato.NOMBRE_PLATO+" FROM " + SegundoPlato.NOM_TAULA + " sp WHERE sp." + SegundoPlato._ID + " LIKE mp."+MenuPlato.SEGUNDO_PLATO+") as 'segundoPlato'"+
                ", (SELECT pp."+PrimerPlato.GLUTEN+" FROM " + PrimerPlato.NOM_TAULA +") as 'glutenPrimero'"+
                ", (SELECT pp."+PrimerPlato.CRUSTACEOS+" FROM " + PrimerPlato.NOM_TAULA +") as 'crustaceosPrimero'"+
                ", (SELECT pp."+PrimerPlato.HUEVOS+" FROM " + PrimerPlato.NOM_TAULA +") as 'huevosPrimero'"+
                ", (SELECT pp."+PrimerPlato.PESCADO+" FROM " + PrimerPlato.NOM_TAULA +") as 'pescadoPrimero'"+
                ", (SELECT pp."+PrimerPlato.CACAHUETES+" FROM " + PrimerPlato.NOM_TAULA +") as 'cacahuetesPrimero'"+
                ", (SELECT pp."+PrimerPlato.LACTEOS+" FROM " + PrimerPlato.NOM_TAULA +") as 'lacteosPrimero'"+
                ", (SELECT pp."+PrimerPlato.FRUTOS_DE_CASCARA+" FROM " + PrimerPlato.NOM_TAULA +") as 'cascarasPrimero'"+
                ", (SELECT pp."+PrimerPlato.APIO+" FROM " + PrimerPlato.NOM_TAULA +") as 'apioPrimero'"+
                ", (SELECT pp."+PrimerPlato.DIOXIDO_AZUFRE_SULFITOS+" FROM " + PrimerPlato.NOM_TAULA +") as 'sulfitosPrimero'"+
                ", (SELECT pp."+PrimerPlato.MOLUSCOS+" FROM " + PrimerPlato.NOM_TAULA +") as 'moluscosPrimero'"+
                ", sp."+SegundoPlato.GLUTEN+",sp."+SegundoPlato.CRUSTACEOS+", sp."+SegundoPlato.HUEVOS+", sp."+SegundoPlato.PESCADO+
                ", sp."+SegundoPlato.CACAHUETES+", sp."+SegundoPlato.LACTEOS+", sp."+SegundoPlato.FRUTOS_DE_CASCARA+", sp."+SegundoPlato.APIO+
                ", sp."+SegundoPlato.DIOXIDO_AZUFRE_SULFITOS+", sp."+SegundoPlato.MOLUSCOS+
                " FROM "+ MenuPlato.NOM_TAULA+" mp"+
                " LEFT JOIN  " + Menu.NOM_TAULA + " m ON mp." + MenuPlato.ID_MENU + " = m." + Menu._ID+
                " LEFT JOIN  " + PrimerPlato.NOM_TAULA + " pp ON pp." + PrimerPlato._ID + " = mp."+MenuPlato.PRIMER_PLATO+
                " LEFT JOIN  " + SegundoPlato.NOM_TAULA + " sp ON sp." + SegundoPlato._ID + " = mp."+MenuPlato.SEGUNDO_PLATO+
                " WHERE m."+ Menu.SEMANA_MENU+" LIKE '"+semanaAño+"'"+
                " ORDER BY mp."+MenuPlato.DIA_MENU+" ASC";  // Ordenamos de lunes a viernes
    }

    public String RetornaTaulaDefecteClient(String idCliente){
        return "Select c."+Client.MESA_FAVORITA+
                " FROM "+ Client.NOM_TAULA+" c"+
                " WHERE c."+ Client._ID+" LIKE "+idCliente;
    }
    public String RetornaProductes(String tipusProducte){
        return "Select p."+Producte._ID+", p."+Producte.NOM_PRODUCTE+", p."+Producte.PREU_PRODUCTE+
                " FROM "+ Producte.NOM_TAULA+" p"+
                " WHERE p."+ Producte.TIPUS_PRODUCTE+" LIKE '"+tipusProducte+"'"+
                " ORDER BY p."+Producte.NOM_PRODUCTE+" ASC";
    }
    public String RetornaPrimerosPlatos(){
        return  "Select pp."+PrimerPlato._ID+", pp."+PrimerPlato.NOMBRE_PLATO+
                ", pp."+PrimerPlato.GLUTEN+",pp."+PrimerPlato.CRUSTACEOS+", pp."+PrimerPlato.HUEVOS+", pp."+PrimerPlato.PESCADO+
                ", pp."+PrimerPlato.CACAHUETES+", pp."+PrimerPlato.LACTEOS+", pp."+PrimerPlato.FRUTOS_DE_CASCARA+", pp."+PrimerPlato.APIO+
                ", pp."+PrimerPlato.DIOXIDO_AZUFRE_SULFITOS+", pp."+PrimerPlato.MOLUSCOS+
                " FROM "+ PrimerPlato.NOM_TAULA+" pp"+
                " ORDER BY pp."+PrimerPlato.NOMBRE_PLATO+" ASC";
    }
    public String RetornaSegundosPlatos(){
        return  "Select sp."+SegundoPlato._ID+", sp."+SegundoPlato.NOMBRE_PLATO+
                ", sp."+SegundoPlato.GLUTEN+",sp."+SegundoPlato.CRUSTACEOS+", sp."+SegundoPlato.HUEVOS+", sp."+SegundoPlato.PESCADO+
                ", sp."+SegundoPlato.CACAHUETES+", sp."+SegundoPlato.LACTEOS+", sp."+SegundoPlato.FRUTOS_DE_CASCARA+", sp."+SegundoPlato.APIO+
                ", sp."+SegundoPlato.DIOXIDO_AZUFRE_SULFITOS+", sp."+SegundoPlato.MOLUSCOS+
                " FROM "+ SegundoPlato.NOM_TAULA+" sp"+
                " ORDER BY sp."+SegundoPlato.NOMBRE_PLATO+" ASC";
    }
    public String verificarLogin (String userName, String password){
        return  " Select t."+ Treballador._ID+",t."+ Treballador.NOM_TREBALLADOR+",t."+ Treballador.COGNOMS_TREBALLADOR+
                " FROM "+ Treballador.NOM_TAULA+" t"+
                " WHERE t."+ Treballador.USER_NAME+" LIKE '"+userName+"' AND t."+ Treballador.PASSWORD+" LIKE '"+password+"'";
    }
    public String AñadirProductoAFactura(String id_cliente, String idProducto){
        String consulta = " INSERT INTO "+Factura.NOM_TAULA+"  ("+Factura.QUANTITAT_PRODUCTE+","+Factura.ID_PRODUCTE+","+Factura.ID_VENTA+")"+
                " VALUES (1,1,(Select "+Venta._ID+
                " FROM "+Venta.NOM_TAULA+
                " WHERE "+Venta.ID_CLIENT+" LIKE "+id_cliente+
                "))";
        //INSERT INTO Factura (quantitat_producte,id_producte,id_venta) VALUES (1,1,(Select v._id FROM Venta v LEFT JOIN reserva_cliente r ON r.id_cliente LIKE v.id_client WHERE v.ventaCobrada LIKE '0' AND v.id_client LIKE 1))
        return consulta;
    }
    public String EncontrarId_VentaFacturaSinPagar(String id_cliente){
        return " Select v."+Venta._ID+
                " FROM "+Venta.NOM_TAULA+" v"+
                " WHERE v."+Venta.ID_CLIENT+" LIKE "+id_cliente+" AND ( v."+Venta.VENTA_COBRADA+" LIKE '0' OR v."+Venta.VENTA_COBRADA+" LIKE '3')";
    }
    public String EncontrarId_VentaFacturaSinPagarProducto(String id_producte){
        return " Select p."+Producte._ID+
                " FROM "+Factura.NOM_TAULA+" f"+
                " LEFT JOIN " + Venta.NOM_TAULA + " v ON v."+Venta._ID+" = f."+Factura.ID_VENTA+
                " LEFT JOIN " + Producte.NOM_TAULA+" p ON p."+Producte._ID+" = f."+Factura.ID_PRODUCTE+
                " WHERE p."+Producte._ID+" LIKE "+id_producte+" AND ( v."+Venta.VENTA_COBRADA+" LIKE '0' OR v."+Venta.VENTA_COBRADA+" LIKE '3')";
    }
    public String ObtenirQuantitatProductesFactura(String idVenta){
        return  " SELECT SUM (f."+Factura.QUANTITAT_PRODUCTE+") as Quantitat"+
                " FROM "+Factura.NOM_TAULA+" f"+
                " WHERE f."+Factura.ID_VENTA+" = "+idVenta;
    }
    public String ObtenirQuantitatReservesSenseIDVenta(String id_cliente){
        return  " SELECT COUNT (r."+Reserva_Cliente._ID+") as Quantitat"+
                " FROM "+Reserva_Cliente.NOM_TAULA+" r"+
                " WHERE r."+Reserva_Cliente.ID_CLIENTE+" LIKE "+id_cliente+
                " AND (r."+Reserva_Cliente.ASISTENCIA+" LIKE '0' AND r."+Reserva_Cliente.PAGADO+" LIKE '0')";
    }
    public String obtenirDadesClientPerId(String id_cliente){
        return "Select c."+Client.NOM_CLIENT+",c."+Client.COGNOMS_CLIENT+",c."+Client.TIPUS_CLIENT+",c."+Client.TIPO_PAGO+",c."+Client.TIPO_COMIDA+",c."+Client.MESA_FAVORITA+",c."+Client.OBSERVACIONS_CLIENT+
                " FROM "+ Client.NOM_TAULA+" c"+
                " WHERE c."+ Client._ID+" LIKE "+id_cliente;
    }
    public String obtenirDadesProductetPerId(String id_producte){
        return "Select p."+Producte.NOM_PRODUCTE+",p."+Producte.PREU_PRODUCTE+
                " FROM "+ Producte.NOM_TAULA+" p"+
                " WHERE p."+ Producte._ID+" LIKE "+id_producte;
    }
    public String obtenirDadesPrimerPlatoPerId(String id_plato){
        return "Select pp."+PrimerPlato.NOMBRE_PLATO+
                ", pp."+PrimerPlato.GLUTEN+",pp."+PrimerPlato.CRUSTACEOS+", pp."+PrimerPlato.HUEVOS+", pp."+PrimerPlato.PESCADO+
                ", pp."+PrimerPlato.CACAHUETES+", pp."+PrimerPlato.LACTEOS+", pp."+PrimerPlato.FRUTOS_DE_CASCARA+", pp."+PrimerPlato.APIO+
                ", pp."+PrimerPlato.DIOXIDO_AZUFRE_SULFITOS+", pp."+PrimerPlato.MOLUSCOS+
                " FROM "+ PrimerPlato.NOM_TAULA+" pp"+
                " WHERE pp."+ PrimerPlato._ID+" LIKE "+id_plato;
    }
    public String obtenirDadesSegundoPlatoPerId(String id_plato){
        return "Select sp."+SegundoPlato.NOMBRE_PLATO+
                ", sp."+SegundoPlato.GLUTEN+",sp."+SegundoPlato.CRUSTACEOS+", sp."+SegundoPlato.HUEVOS+", sp."+SegundoPlato.PESCADO+
                ", sp."+SegundoPlato.CACAHUETES+", sp."+SegundoPlato.LACTEOS+", sp."+SegundoPlato.FRUTOS_DE_CASCARA+", sp."+SegundoPlato.APIO+
                ", sp."+SegundoPlato.DIOXIDO_AZUFRE_SULFITOS+", sp."+SegundoPlato.MOLUSCOS+
                " FROM "+ SegundoPlato.NOM_TAULA+" sp"+
                " WHERE sp."+ SegundoPlato._ID+" LIKE "+id_plato;
    }
    public String obtenirMenuSetmana(String semana){
        return  " SELECT mp."+MenuPlato.DIA_MENU+", mp."+MenuPlato.PRIMER_PLATO+", mp."+MenuPlato.SEGUNDO_PLATO+
                ",(SELECT pp."+PrimerPlato.NOMBRE_PLATO+" FROM " + PrimerPlato.NOM_TAULA + " pp WHERE pp." + PrimerPlato._ID + " LIKE mp."+MenuPlato.PRIMER_PLATO+") as 'primerPlato'"+
                ",(SELECT sp."+SegundoPlato.NOMBRE_PLATO+" FROM " + SegundoPlato.NOM_TAULA + " sp WHERE sp." + SegundoPlato._ID + " LIKE mp."+MenuPlato.SEGUNDO_PLATO+") as 'segundoPlato'"+
                " FROM "+ MenuPlato.NOM_TAULA+" mp"+
                " WHERE mp."+MenuPlato.ID_MENU +" = (SELECT "+Menu._ID+" FROM " + Menu.NOM_TAULA +" WHERE "+Menu.SEMANA_MENU+" = '"+semana+"')";
    }
    public String obtenirNumeroDeClients(String cadenaClient){
        return " SELECT COUNT (c."+Client._ID+") as Quantitat"+
                " FROM "+Client.NOM_TAULA+" c"+
                " WHERE c."+Client.NOM_CLIENT+" LIKE '"+cadenaClient+"'";
    }
    public String obtenirQuantitatFacturesVenta(String idVenta){
        return " SELECT COUNT (f."+Factura._ID+") as Quantitat"+
                " FROM "+Factura.NOM_TAULA+" f"+
                " WHERE f."+Factura.ID_VENTA+" LIKE '"+idVenta+"'";
    }
    public String obtenirCuantitatClienteBarraSinPagar(){
        return " SELECT COUNT (v."+Venta._ID+") as Quantitat"+
                " FROM "+Venta.NOM_TAULA+" v"+
                " LEFT JOIN "+Client.NOM_TAULA+" c ON c." + Client._ID + " = v."+Venta.ID_CLIENT+""+
                " WHERE c."+Client.NOM_CLIENT+" LIKE '~Cliente Barra'"+
                " AND v."+Venta.VENTA_COBRADA+" LIKE '0'";
    }
    public String ActualitzarMenuPlato (Integer idMenu, Integer idPrimerPlato, Integer idSegundoPlato, String dia){

        return  " UPDATE "+ MenuPlato.NOM_TAULA+" mp"+
                " SET (mp."+MenuPlato.PRIMER_PLATO+",mp."+MenuPlato.SEGUNDO_PLATO+")"+
                " = ("+idPrimerPlato+","+idSegundoPlato+")"+
                " WHERE mp."+MenuPlato.ID_MENU+" = "+idMenu+" AND mp."+MenuPlato.DIA_MENU+" LIKE '"+dia+"'";

    }
}


