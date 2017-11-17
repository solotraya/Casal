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

    public static final class Mesa implements BaseColumns {
        public static final String NOM_TAULA = "mesa";
        public static final String NOMBRE_MESA = "nombre_mesa";
    }

    public static final class Reserva_Cliente implements BaseColumns {
        public static final String NOM_TAULA = "reserva_cliente";
        public static final String DIA_RESERVADO = "dia_reservado";  // HABRA QUE PONER 2 datePicker y HACER RESERVA POR CADA DIA RESERVADO
        public static final String ASISTENCIA = "asistencia";     // Si el cliente no viene se anota
        public static final String ID_CLIENTE = "id_cliente";
        public static final String ID_MESA = "id_mesa";  // ESTA ES LA IMPORTANTE, si el cliente tiene mesa por defecto se auto-asigna, pero se puede cambiar
        // UNIQUE (id_cliente, dia_reservado)
    }

    public static final class Client implements BaseColumns {
        public static final String NOM_TAULA = "client";
        public static final String NOM_CLIENT = "nom_client";
        public static final String COGNOMS_CLIENT = "cognoms_client";
        public static final String TIPUS_CLIENT = "tipus_client"; // 0 Comedor 1 Llevar 2 Ayuntamiento
        public static final String MESA_FAVORITA = "id_mesa";  // OPCIONAL para guardar la mesa preferida,
        // para que en la Activity venga auto-rellenado con esa mesa (que igualmente se puede cambiar)
    }

    public static final class Treballador implements BaseColumns {
        public static final String NOM_TAULA = "Treballador";
        public static final String NOM_TREBALLADOR = "nomTreballador";
        public static final String COGNOMS_TREBALLADOR = "cognomsTreballador";
        public static final String USER_NAME = "userName";
        public static final String PASSWORD = "password";

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
        public static final String ID_TREBALLADOR = "id_treballador";
        public static final String DATA_VENTA= "dataVenta";  // DATA ACTUAL
        public static final String VENTA_COBRADA = "ventaCobrada";  // 0 NO 1 SI
        public static final String HORA_VENTA = "horaVenta";
    }
}
