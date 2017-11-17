package ccastro.casal.SQLite;

import android.database.Cursor;

import ccastro.casal.SQLite.ContracteBD.Client;
import ccastro.casal.SQLite.ContracteBD.Factura;
import ccastro.casal.SQLite.ContracteBD.Producte;
import ccastro.casal.SQLite.ContracteBD.Venta;
import ccastro.casal.SQLite.ContracteBD.Reserva_Cliente;
import ccastro.casal.SQLite.ContracteBD.Treballador;
import ccastro.casal.SQLite.ContracteBD.Mesa;
/**
 * @author Carlos Alberto Castro Cañabate
 *
 * Classe que ajudarà  a retornar un seguit de consultes de la base de dades
 */

public class ConsultesSQL {
    String RetornaMesasReservadasDataActual ="Select distinct m."+ Mesa._ID+", m."+ Mesa.NOMBRE_MESA+
            " FROM "+ Mesa.NOM_TAULA+" m"+
            " LEFT JOIN  " + Reserva_Cliente.NOM_TAULA + " r ON m." + Mesa._ID + " = r." + Reserva_Cliente.ID_MESA+
            " WHERE r."+ Reserva_Cliente.DIA_RESERVADO+" LIKE strftime('%Y %m %d','now')";

    String RetornaClientsReservadosDataActual ="Select c."+Client.MESA_FAVORITA+", c."+Client.COGNOMS_CLIENT+", m."+ Mesa.NOMBRE_MESA+
            " FROM "+ Treballador.NOM_TAULA+" r"+
            " LEFT JOIN  " + Client.NOM_TAULA + " c ON c." + ContracteBD.Client._ID + " = r." + Reserva_Cliente.ID_CLIENTE+
            " WHERE r."+ Reserva_Cliente.DIA_RESERVADO+" LIKE strftime('%Y %m %d','now')";


    String RetornaTotsElsClients ="Select c."+ ContracteBD.Client._ID+", c."+ Client.NOM_CLIENT+", c."+  Client.COGNOMS_CLIENT+", c."+  Client.TIPUS_CLIENT+
            " FROM "+ Client.NOM_TAULA+" c";

    String RetornaTotsElsProductes ="Select p."+ ContracteBD.Producte._ID+", p."+ Producte.NOM_PRODUCTE+", p."
            +  Producte.TIPUS_PRODUCTE+", p."+  Producte.PREU_PRODUCTE+
            " FROM "+ Producte.NOM_TAULA+" p";

    String RetornaVentesDataActual ="Select v."+ Venta._ID+", v."+ Venta.DATA_VENTA+
            ", v." + Venta.VENTA_COBRADA+", v." + Venta.HORA_VENTA+", c."+ Client.NOM_CLIENT+", c."+  Client.COGNOMS_CLIENT+
            ", t."+  Treballador.NOM_TREBALLADOR+", t."+ Treballador.COGNOMS_TREBALLADOR+
            " FROM "+ Venta.NOM_TAULA+" v"+
            " LEFT JOIN  " + Treballador.NOM_TAULA + " t ON t." + Treballador._ID + " = v." + Venta.ID_TREBALLADOR+
            " LEFT JOIN  " + Client.NOM_TAULA + " c ON c." + Client._ID + " = v." + Venta.ID_CLIENT+
            " WHERE v."+ Venta.DATA_VENTA+" LIKE strftime('%Y %m %d','now')";

    public String RetornaFacturaId_Venta(String id_Venta){
        return " Select p."+ Producte.NOM_PRODUCTE+", p."+ Producte.PREU_PRODUCTE+", p."+ Producte.TIPUS_PRODUCTE+
                ", f."+ Factura.QUANTITAT_PRODUCTE+", v."+ Venta.DATA_VENTA+ ", v."+ Venta.VENTA_COBRADA+", v." + Venta.HORA_VENTA+
                " FROM "+ Factura.NOM_TAULA+" f"+
                " LEFT JOIN  " + Venta.NOM_TAULA + " v ON f." + Factura.ID_VENTA + " = v." + Venta._ID+
                " LEFT JOIN  " + Producte.NOM_TAULA + " p ON f." + Factura.ID_PRODUCTE + " = p." + Producte._ID+
                " WHERE f."+Factura.ID_VENTA+ " = "+id_Venta;
    }
    public String RetornaVentesDataActualEstatVenta(String estatVenta){
        return  " Select v."+ Venta._ID+", v."+ Venta.DATA_VENTA+
                ", v." + Venta.VENTA_COBRADA+", v." + Venta.HORA_VENTA+", c."+ Client.NOM_CLIENT+", c."+  Client.COGNOMS_CLIENT+
                ", t."+  Treballador.NOM_TREBALLADOR+", t."+  Treballador.COGNOMS_TREBALLADOR+
                " FROM "+ Venta.NOM_TAULA+" v"+
                " LEFT JOIN  " + Treballador.NOM_TAULA + " t ON t." + Treballador._ID + " = v." + Venta.ID_TREBALLADOR+
                " LEFT JOIN  " + Client.NOM_TAULA + " c ON c." + Client._ID + " = v." + Venta.ID_CLIENT+
                " WHERE v."+ Venta.DATA_VENTA+" LIKE strftime('%Y %m %d','now') AND v."+ Venta.VENTA_COBRADA+" LIKE "+estatVenta;
    }
    public String verificarLogin (String userName, String password){
        return  " Select t."+ Treballador._ID+",t."+ Treballador.NOM_TREBALLADOR+",t."+ Treballador.COGNOMS_TREBALLADOR+
                " FROM "+ Treballador.NOM_TAULA+" t"+
                " WHERE t."+ Treballador.USER_NAME+" LIKE '"+userName+"' AND t."+ Treballador.PASSWORD+" LIKE '"+password+"'";
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


