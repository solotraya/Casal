package ccastro.casal.RecyclerView;

/**
 * Created by Carlos on 20/12/2017.
 */

public class HeaderPlato {
    String idPlato, nombrePlato, gluten, crustaceos, huevos, cacahuetes, lacteos, cascaras, apio, azufre_sulfitos, moluscos;

    public HeaderPlato(String idPlato, String nombrePlato, String gluten, String crustaceos, String huevos, String cacahuetes, String lacteos, String cascaras, String apio, String azufre_sulfitos, String moluscos) {
        this.idPlato = idPlato;
        this.nombrePlato = nombrePlato;
        this.gluten = gluten;
        this.crustaceos = crustaceos;
        this.huevos = huevos;
        this.cacahuetes = cacahuetes;
        this.lacteos = lacteos;
        this.cascaras = cascaras;
        this.apio = apio;
        this.azufre_sulfitos = azufre_sulfitos;
        this.moluscos = moluscos;
    }

    public String getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(String idPlato) {
        this.idPlato = idPlato;
    }

    public String getNombrePlato() {
        return nombrePlato;
    }

    public void setNombrePlato(String nombrePlato) {
        this.nombrePlato = nombrePlato;
    }

    public String getGluten() {
        return gluten;
    }

    public void setGluten(String gluten) {
        this.gluten = gluten;
    }

    public String getCrustaceos() {
        return crustaceos;
    }

    public void setCrustaceos(String crustaceos) {
        this.crustaceos = crustaceos;
    }

    public String getHuevos() {
        return huevos;
    }

    public void setHuevos(String huevos) {
        this.huevos = huevos;
    }

    public String getCacahuetes() {
        return cacahuetes;
    }

    public void setCacahuetes(String cacahuetes) {
        this.cacahuetes = cacahuetes;
    }

    public String getLacteos() {
        return lacteos;
    }

    public void setLacteos(String lacteos) {
        this.lacteos = lacteos;
    }

    public String getCascaras() {
        return cascaras;
    }

    public void setCascaras(String cascaras) {
        this.cascaras = cascaras;
    }

    public String getApio() {
        return apio;
    }

    public void setApio(String apio) {
        this.apio = apio;
    }

    public String getAzufre_sulfitos() {
        return azufre_sulfitos;
    }

    public void setAzufre_sulfitos(String azufre_sulfitos) {
        this.azufre_sulfitos = azufre_sulfitos;
    }

    public String getMoluscos() {
        return moluscos;
    }

    public void setMoluscos(String moluscos) {
        this.moluscos = moluscos;
    }
}
