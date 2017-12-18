package ccastro.casal.SQLite;

import ccastro.casal.SQLite.ContracteBD.Client;
import ccastro.casal.SQLite.ContracteBD.Factura;
import ccastro.casal.SQLite.ContracteBD.Mesa;
import ccastro.casal.SQLite.ContracteBD.Producte;
import ccastro.casal.SQLite.ContracteBD.Reserva_Cliente;
import ccastro.casal.SQLite.ContracteBD.Treballador;
import ccastro.casal.SQLite.ContracteBD.Venta;
import ccastro.casal.SQLite.ContracteBD.Comida;
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

    String RetornaTotsElsClients ="Select c."+ ContracteBD.Client._ID+", c."+ Client.NOM_CLIENT+", c."+  Client.COGNOMS_CLIENT
            +", c."+  Client.TIPUS_CLIENT+", c."+Client.TIPO_PAGO+", c."+Client.TIPO_COMIDA+", c."+Client.OBSERVACIONS_CLIENT+
            " FROM "+ Client.NOM_TAULA+" c"+
            " WHERE c."+ Client.NOM_CLIENT+" NOT LIKE '~Cliente Barra' "+
            " ORDER BY c."+ Client.NOM_CLIENT;

    String RetornaTotsElsProductes ="Select p."+ ContracteBD.Producte._ID+", p."+ Producte.NOM_PRODUCTE+", p."
            +  Producte.TIPUS_PRODUCTE+", p."+  Producte.PREU_PRODUCTE+
            " FROM "+ Producte.NOM_TAULA+" p";

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

    public String RetornaClientsReservadosMesa(String idMesa, String data){
        return "Select c."+Client._ID+", c."+Client.NOM_CLIENT+", c."+Client.COGNOMS_CLIENT+", c."+Client.TIPUS_CLIENT+", r."+Reserva_Cliente.PAGADO+
                ", r."+Reserva_Cliente.ASISTENCIA+", c."+Client.TIPO_PAGO+", c."+Client.TIPO_COMIDA+", c."+Client.OBSERVACIONS_CLIENT+
                " FROM "+ Reserva_Cliente.NOM_TAULA+" r"+
                " LEFT JOIN  " + Client.NOM_TAULA + " c ON c." + ContracteBD.Client._ID + " = r." + Reserva_Cliente.ID_CLIENTE+
                " WHERE r."+ Reserva_Cliente.DIA_RESERVADO+" LIKE '"+data+"' AND r."+Reserva_Cliente.ID_MESA+" LIKE "+idMesa;
        //      " WHERE r."+ Reserva_Cliente.DIA_RESERVADO+" LIKE strftime('%Y %m %d','now') AND r."+Reserva_Cliente.ID_MESA+" LIKE "+idMesa;

    } /*
    public String RetornaMenuSemanaAño(String semanaAño){

        return "Select c."+Comida._ID+", c."+Comida.NOMBRE_COMIDA+", c."+Comida.GLUTEN+", c."+Comida.CRUSTACEOS+", c."+Comida.HUEVOS+
                ", c."+Comida.CACAHUETES+", c."+Comida.LACTEOS+", c."+Comida.FRUTOS_DE_CASCARA+", c."+Comida.APIO+
                ", c."+Comida.DIOXIDO_AZUFRE_SULFITOS+", c."+Comida.MOLUSCOS+", m."+Menu._ID+", m."+Menu.FECHA_MENU+
                ", m."+Menu.LUNES_PRIMERO+", m."+Menu.LUNES_SEGUNDO+", m."+Menu.MARTES_PRIMERO+", m."+Menu.MARTES_SEGUNDO+
                ", m."+Menu.MIERCOLES_PRIMERO+", m."+Menu.MIERCOLES_SEGUNDO+", m."+Menu.JUEVES_PRIMERO+", m."+Menu.JUEVES_SEGUNDO+
                ", m."+Menu.VIERNES_PRIMERO+", m."+Menu.VIERNES_SEGUNDO+
                " FROM "+ Menu.NOM_TAULA+" m"+", "+Comida.NOM_TAULA+" c"+
              //  " LEFT JOIN  " + Comida.NOM_TAULA + " c ON c." + Comida._ID + " = r." + Reserva_Cliente.ID_CLIENTE+
                " WHERE m."+ Menu.FECHA_MENU+" LIKE '"+semanaAño+"'";
    } */
    /*
        SELECT 'Menu'._id,
		(SELECT 'Comida'.nombre_comida FROM Menu, Comida WHERE 'Menu'.lunesPrimero='Comida'._id AND 'Menu'.fecha_menu = '50') as lunesPrimero,
		(SELECT 'Comida'.nombre_comida FROM Menu, Comida WHERE 'Menu'.martesPrimero='Comida'._id AND 'Menu'.fecha_menu = '50') as martesPrimero,
        (SELECT 'Comida'.nombre_comida FROM Menu, Comida WHERE 'Menu'.miercolesPrimero='Comida'._id AND 'Menu'.fecha_menu = '50') as miercolesPrimero,
		(SELECT 'Comida'.nombre_comida FROM Menu, Comida WHERE 'Menu'.juevesPrimero='Comida'._id AND 'Menu'.fecha_menu = '50') as juevesPrimero,
		(SELECT 'Comida'.nombre_comida FROM Menu, Comida WHERE 'Menu'.viernesPrimero='Comida'._id AND 'Menu'.fecha_menu = '50') as viernesPrimero
FROM 'Menu'
WHERE 'Menu'.fecha_menu = '50';
         */
    public String RetornaMenuSemanaAño(String semanaAño){

        return "Select m."+Menu._ID+
                ", (SELECT c."+Comida.NOMBRE_COMIDA+" FROM " + Comida.NOM_TAULA + " c,"+Menu.NOM_TAULA+" m" + " WHERE m." + Menu.LUNES_PRIMERO + " LIKE c."+Comida._ID+" AND m."+Menu.FECHA_MENU+" LIKE '"+semanaAño+"') as lunesPrimero"+
                ", (SELECT c."+Comida.NOMBRE_COMIDA+" FROM " + Comida.NOM_TAULA + " c,"+Menu.NOM_TAULA+" m" + " WHERE m." + Menu.LUNES_SEGUNDO+ " LIKE c."+Comida._ID+" AND m."+Menu.FECHA_MENU+" LIKE '"+semanaAño+"') as lunesSegundo"+
                ", (SELECT c."+Comida.NOMBRE_COMIDA+" FROM " + Comida.NOM_TAULA + " c,"+Menu.NOM_TAULA+" m" + " WHERE m." + Menu.MARTES_PRIMERO + " LIKE c."+Comida._ID+" AND m."+Menu.FECHA_MENU+" LIKE '"+semanaAño+"') as martesPrimero"+
                ", (SELECT c."+Comida.NOMBRE_COMIDA+" FROM " + Comida.NOM_TAULA + " c,"+Menu.NOM_TAULA+" m" + " WHERE m." + Menu.MARTES_SEGUNDO+ " LIKE c."+Comida._ID+" AND m."+Menu.FECHA_MENU+" LIKE '"+semanaAño+"') as martesSegundo"+
                ", (SELECT c."+Comida.NOMBRE_COMIDA+" FROM " + Comida.NOM_TAULA + " c,"+Menu.NOM_TAULA+" m" + " WHERE m." + Menu.MIERCOLES_PRIMERO + " LIKE c."+Comida._ID+" AND m."+Menu.FECHA_MENU+" LIKE '"+semanaAño+"') as miercolesPrimero"+
                ", (SELECT c."+Comida.NOMBRE_COMIDA+" FROM " + Comida.NOM_TAULA + " c,"+Menu.NOM_TAULA+" m" + " WHERE m." + Menu.MIERCOLES_SEGUNDO + " LIKE c."+Comida._ID+" AND m."+Menu.FECHA_MENU+" LIKE '"+semanaAño+"') as miercolesSegundo"+
                ", (SELECT c."+Comida.NOMBRE_COMIDA+" FROM " + Comida.NOM_TAULA + " c,"+Menu.NOM_TAULA+" m" + " WHERE m." + Menu.JUEVES_PRIMERO + " LIKE c."+Comida._ID+" AND m."+Menu.FECHA_MENU+" LIKE '"+semanaAño+"') as juevesPrimero"+
                ", (SELECT c."+Comida.NOMBRE_COMIDA+" FROM " + Comida.NOM_TAULA + " c,"+Menu.NOM_TAULA+" m" + " WHERE m." + Menu.JUEVES_SEGUNDO + " LIKE c."+Comida._ID+" AND m."+Menu.FECHA_MENU+" LIKE '"+semanaAño+"') as juevesSegundo"+
                ", (SELECT c."+Comida.NOMBRE_COMIDA+" FROM " + Comida.NOM_TAULA + " c,"+Menu.NOM_TAULA+" m" + " WHERE m." + Menu.VIERNES_PRIMERO + " LIKE c."+Comida._ID+" AND m."+Menu.FECHA_MENU+" LIKE '"+semanaAño+"') as viernesPrimero"+
                ", (SELECT c."+Comida.NOMBRE_COMIDA+" FROM " + Comida.NOM_TAULA + " c,"+Menu.NOM_TAULA+" m" + " WHERE m." + Menu.VIERNES_SEGUNDO + " LIKE c."+Comida._ID+" AND m."+Menu.FECHA_MENU+" LIKE '"+semanaAño+"') as viernesSegundo"+
                " FROM "+ Menu.NOM_TAULA+" m"+
                " WHERE m."+ Menu.FECHA_MENU+" LIKE '"+semanaAño+"'";
    }
    public String RetornaTaulaDefecteClient(String idCliente){
        return "Select c."+Client.MESA_FAVORITA+
                " FROM "+ Client.NOM_TAULA+" c"+
                " WHERE c."+ Client._ID+" LIKE "+idCliente;
    }
    public String RetornaProductes(String tipusProducte){
        return "Select p."+Producte._ID+", p."+Producte.NOM_PRODUCTE+", p."+Producte.PREU_PRODUCTE+
                " FROM "+ Producte.NOM_TAULA+" p"+
                " WHERE p."+ Producte.TIPUS_PRODUCTE+" LIKE '"+tipusProducte+"'";
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
}


