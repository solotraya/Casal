package ccastro.casal.SQLite;

import android.provider.BaseColumns;

/**
 * Created by Carlos on 13/11/2017.
 *
 * Una classe Contract és un contenidor per constants que defineixen noms d'URI (identificadors
 * uniformes de recursos), taules i columnes. La classe Contract et permet utilitzar les mateixes
 * constants en totes les altres classes del mateix paquet. Això et permet canviar el nom d'una
 * columna en un lloc i que aquest canvi es propagui a tot el codi.
 */

public class ContracteBD {
    /**
     * Per prevenir que algú accidentalment instancii aquest contracte,
     * el constructor s'ha fet private
     */
    private ContracteBD() {}

    public static final class Client implements BaseColumns {
        public static final String NOM_TAULA = "Client";
        public static final String NOM_CLIENT = "nomClient";
        public static final String COGNOMS_CLIENT = "cognomsClient";
        public static final String TIPUS_CLIENT = "tipusClient"; // 0 Comedor 1 Llevar 2 Ayuntamiento

    }

    public static final class Producte implements BaseColumns {
        public static final String NOM_TAULA = "Producte";
        public static final String NOM_PRODUCTE = "nomProducte"; // Cocacola
        public static final String PREU_PRODUCTE = "preuProducte";
        public static final String TIPUS_PRODUCTE = "tipusProducte";  // Bebida, Comida, Bocadillo.
    }

    public static final class Factura implements BaseColumns {
        public static final String NOM_TAULA = "Factura";
        public static final String ID_PRODUCTE = "id_producte";
        public static final String ID_VENTA = "id_venta";
        public static final String QUANTITAT_PRODUCTE = "quantitat_producte";

    }

    public static final class Venta implements BaseColumns {
        public static final String NOM_TAULA = "Venta";
        public static final String ID_CLIENT = "id_client";
        //public static final String ID_FACTURA = "id_factura";
        public static final String DATA_VENTA= "dataVenta";  // DATA ACTUAL
        public static final String VENTA_COBRADA = "ventaCobrada";  // 0 NO 1 SI
        public static final String HORA_VENTA = "horaVenta";
    }
}
