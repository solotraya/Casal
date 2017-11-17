package ccastro.casal.RecyclerView;

/**
 * Created by Carlos on 17/11/2017.
 */

public class HeaderReserva {
    public String idClient;
    public String nomClient;
    public String tipusClient;
    public String pagado;
    public String assistencia;

    public HeaderReserva(String idClient, String nomClient, String tipusClient, String pagado, String assistencia) {
        this.idClient = idClient;
        this.nomClient = nomClient;
        this.tipusClient = tipusClient;
        this.pagado = pagado;
        this.assistencia = assistencia;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getTipusClient() {
        return tipusClient;
    }

    public void setTipusClient(String tipusClient) {
        this.tipusClient = tipusClient;
    }

    public String getPagado() {
        return this.pagado;
    }

    public void setPagado(String pagado) {
        this.pagado = pagado;
    }

    public String getAssistencia() {
        return this.assistencia;
    }

    public void setAssistencia(String assistencia) {
        this.assistencia = assistencia;
    }
}
