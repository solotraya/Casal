package ccastro.casal.RecyclerView;

/**
 * Created by Carlos on 18/12/2017.
 */

public class HeaderMenu {

    String idMenuPlato,idMenu, diaMenu, primerPlato, segundoPlato, gluten, crustaceos, huevos, cacahuetes, lacteos, cascaras, apio, azufre_sulfitos, moluscos;
    String gluten2, crustaceos2, huevos2, cacahuetes2, lacteos2, cascaras2, apio2, azufre_sulfitos2, moluscos2;

    public HeaderMenu(String idMenuPlato,String idMenu, String diaMenu, String primerPlato, String segundoPlato,
                      String gluten, String crustaceos, String huevos, String cacahuetes, String lacteos, String cascaras, String apio, String azufre_sulfitos, String moluscos,
                      String gluten2, String crustaceos2, String huevos2, String cacahuetes2, String lacteos2, String cascaras2, String apio2, String azufre_sulfitos2, String moluscos2) {
        this.idMenuPlato = idMenuPlato;
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
        this.gluten2 = gluten2;
        this.crustaceos2 = crustaceos2;
        this.huevos2 = huevos2;
        this.cacahuetes2 = cacahuetes2;
        this.lacteos2 = lacteos2;
        this.cascaras2 = cascaras2;
        this.apio2 = apio2;
        this.azufre_sulfitos2 = azufre_sulfitos2;
        this.moluscos2 = moluscos2;
    }

    public String getIdMenuPlato() {
        return idMenuPlato;
    }

    public void setIdMenuPlato(String idMenuPlato) {
        this.idMenuPlato = idMenuPlato;
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

    public String getGluten2() {
        return gluten2;
    }

    public void setGluten2(String gluten2) {
        this.gluten2 = gluten2;
    }

    public String getCrustaceos2() {
        return crustaceos2;
    }

    public void setCrustaceos2(String crustaceos2) {
        this.crustaceos2 = crustaceos2;
    }

    public String getHuevos2() {
        return huevos2;
    }

    public void setHuevos2(String huevos2) {
        this.huevos2 = huevos2;
    }

    public String getCacahuetes2() {
        return cacahuetes2;
    }

    public void setCacahuetes2(String cacahuetes2) {
        this.cacahuetes2 = cacahuetes2;
    }

    public String getLacteos2() {
        return lacteos2;
    }

    public void setLacteos2(String lacteos2) {
        this.lacteos2 = lacteos2;
    }

    public String getCascaras2() {
        return cascaras2;
    }

    public void setCascaras2(String cascaras2) {
        this.cascaras2 = cascaras2;
    }

    public String getApio2() {
        return apio2;
    }

    public void setApio2(String apio2) {
        this.apio2 = apio2;
    }

    public String getAzufre_sulfitos2() {
        return azufre_sulfitos2;
    }

    public void setAzufre_sulfitos2(String azufre_sulfitos2) {
        this.azufre_sulfitos2 = azufre_sulfitos2;
    }

    public String getMoluscos2() {
        return moluscos2;
    }

    public void setMoluscos2(String moluscos2) {
        this.moluscos2 = moluscos2;
    }
}
