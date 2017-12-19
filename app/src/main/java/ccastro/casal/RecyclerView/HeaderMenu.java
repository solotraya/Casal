package ccastro.casal.RecyclerView;

/**
 * Created by Carlos on 18/12/2017.
 */

public class HeaderMenu {

    String idMenu, diaMenu, primerPlato, segundoPlato, gluten, crustaceos, huevos, cacahuetes, lacteos, cascaras, apio, azufre_sulfitos, moluscos;

    public HeaderMenu(String idMenu, String diaMenu, String primerPlato, String segundoPlato, String gluten, String crustaceos, String huevos, String cacahuetes, String lacteos, String cascaras, String apio, String azufre_sulfitos, String moluscos) {
        this.idMenu = idMenu;
        this.diaMenu = diaMenu;
        this.primerPlato = primerPlato;
        this.segundoPlato = segundoPlato;
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

    public String getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(String idMenu) {
        this.idMenu = idMenu;
    }

    public String getDiaMenu() {
        switch (Integer.parseInt(diaMenu)){
            case 1: return "Lunes";
            case 2: return "Martes";
            case 3: return "Miercoles";
            case 4: return "Jueves";
            case 5: return "Viernes";
        }
        return diaMenu;
    }

    public void setDiaMenu(String diaMenu) {
        this.diaMenu = diaMenu;
    }

    public String getPrimerPlato() {
        return primerPlato;
    }

    public void setPrimerPlato(String primerPlato) {
        this.primerPlato = primerPlato;
    }

    public String getSegundoPlato() {
        return segundoPlato;
    }

    public void setSegundoPlato(String segundoPlato) {
        this.segundoPlato = segundoPlato;
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
