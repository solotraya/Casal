package ccastro.casal.RecyclerView;

/**
 * Created by Carlos on 16/11/2017.
 */

public class HeaderMesa {
    public String idMesa;
    public String nombreMesa;

    public HeaderMesa(String idMesa, String nombreMesa) {
        this.idMesa = idMesa;
        this.nombreMesa = nombreMesa;
    }

    public String getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(String idMesa) {
        this.idMesa = idMesa;
    }

    public String getNombreMesa() {
        return nombreMesa;
    }

    public void setNombreMesa(String nombreMesa) {
        this.nombreMesa = nombreMesa;
    }
}
