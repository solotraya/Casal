package ccastro.casal;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import ccastro.casal.RecyclerView.HeaderAdapterMenu;
import ccastro.casal.RecyclerView.HeaderMenu;
import ccastro.casal.SQLite.ContracteBD;
import ccastro.casal.SQLite.DBInterface;
import ccastro.casal.Utils.Statics;
import ccastro.casal.Utils.Utilitats;

public class MenuActivity extends AppCompatActivity {

    TextView textViewFechaMenu;
    private android.support.v7.widget.Toolbar mToolbar;
    private Integer diaInicio=null, mesInicio,añoInicio;
    private String fechaMenu,semanaAño,semanaActual;
    private ArrayList<HeaderMenu> myDataset;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    DBInterface db;
    private HeaderAdapterMenu headerAdapterMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar_cliente);
        textViewFechaMenu = (TextView) findViewById(R.id.fechaVenta);
        fechaMenu = Utilitats.obtenerFechaActual();
        obtenerAñoMesDiaInicio(fechaMenu);
        obtenerNumeroSemanaAño(true);
        textViewFechaMenu.setText("Semana: "+obtenerNumeroSemanaAño(false)+"  Fecha: "+Utilitats.getFechaFormatSpain(fechaMenu));
        findViewById(R.id.buttonFechaAnterior).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String fecha;
                do {
                    obtenerAñoMesDiaInicio(fechaMenu);
                    if (diaInicio!=1){
                        diaInicio = diaInicio - 1;
                    } else if ( mesInicio==2 || mesInicio==4 || mesInicio==6 || mesInicio==8 ||  mesInicio==9 || mesInicio==11){
                        diaInicio=31; mesInicio--;
                    } else if ( mesInicio==5 || mesInicio==7 || mesInicio==10 || mesInicio==12){
                        diaInicio=30; mesInicio--;
                    } else if (mesInicio==3){
                        if (añoInicio % 4 == 0 && añoInicio % 100 != 0 || añoInicio % 400 == 0) {
                            diaInicio=29; mesInicio--;
                        } else diaInicio=28; mesInicio--;

                    } else if (mesInicio==1){
                        diaInicio = 31;
                        mesInicio = 12;
                        añoInicio = añoInicio -1;
                    }
                    fechaMenu = añoInicio + " "+ mesInicio + " " + diaInicio;
                    fecha = añoInicio + "-"+ mesInicio + "-" + diaInicio;
                    // TODO: PARA VENTAS SOLO INHABILITAMOS EL SABADO, DOMINGO PERMITIDO.
                } while (!Utilitats.diaHabil(fecha, Calendar.SATURDAY) && !Utilitats.diaHabil(fecha,Calendar.SUNDAY));
                textViewFechaMenu.setText("Semana: "+obtenerNumeroSemanaAño(false)+"  Fecha: "+Utilitats.getFechaFormatSpain(fechaMenu));
                actualizarRecyclerView();
                headerAdapterMenu.actualitzaRecycler(myDataset);
            }
        });
        findViewById(R.id.buttonFechaPosterior).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String fecha;
                do {
                    obtenerAñoMesDiaInicio(fechaMenu);
                    if (mesInicio==4 || mesInicio==6 || mesInicio==9 || mesInicio==11){
                        if (diaInicio!=30){ diaInicio++;
                        } else { diaInicio = 1; mesInicio++; }
                    } else if (mesInicio==1 || mesInicio==3 || mesInicio==5 || mesInicio==7 || mesInicio==8 || mesInicio==10 || mesInicio==12) {
                        if (diaInicio!=31){ diaInicio++; }
                        else {
                            diaInicio = 1;
                            if (mesInicio!=12){ mesInicio++; }
                            else { mesInicio=1; añoInicio++; }
                        }
                    } else if (mesInicio==2){
                        if (añoInicio % 4 == 0 && añoInicio % 100 != 0 || añoInicio % 400 == 0) {
                            if (diaInicio!=29){  diaInicio++; }
                            else { diaInicio =1; mesInicio++; }
                        } else {
                            if (diaInicio!=28){ diaInicio++; }
                            else { diaInicio =1; mesInicio++; }
                        }
                    }
                    fechaMenu = añoInicio + " "+ mesInicio + " " + diaInicio;
                    fecha = añoInicio + "-"+ mesInicio + "-" + diaInicio;
                } while (!Utilitats.diaHabil(fecha,Calendar.SATURDAY) && !Utilitats.diaHabil(fecha,Calendar.SUNDAY));
                textViewFechaMenu.setText("Semana: "+obtenerNumeroSemanaAño(false)+"  Fecha: "+Utilitats.getFechaFormatSpain(fechaMenu));
                actualizarRecyclerView();
                headerAdapterMenu.actualitzaRecycler(myDataset);

            }
        });
        textViewFechaMenu.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Calendar cal= Calendar.getInstance();
                int añoActual = cal.get(Calendar.YEAR);
                Log.d("AÑO INICIO-ACTUAL",Integer.toString(añoInicio)+" "+Integer.toString(añoActual));

                if (Integer.parseInt(semanaAño)>=Integer.parseInt(semanaActual) || añoInicio > añoActual){
                    mToolbar.setVisibility(View.VISIBLE);
                } else mToolbar.setVisibility(View.GONE);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        actualizarRecyclerView();
    }
    public String obtenerNumeroSemanaAño(Boolean esSemanaActual){
        Calendar calendar = Calendar.getInstance();
        calendar.set(añoInicio, mesInicio - 1, diaInicio, 0, 0);
        int numberWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        semanaAño = Integer.toString(numberWeekOfYear);
        if (esSemanaActual){
            semanaActual = semanaAño;
        }


        return semanaAño;
    }
    public void obtenerAñoMesDiaInicio(String fechaInicio){
        String [] fecha = fechaInicio.split(" ");
        añoInicio = Integer.parseInt(fecha[0]);
        mesInicio = Integer.parseInt(fecha[1]);
        diaInicio = Integer.parseInt(fecha[2]);
        Log.d("FECHA INICIO: ",fechaInicio);
    }
    public void actualizarRecyclerView(){
        myDataset = new ArrayList<>();
        headerAdapterMenu= new HeaderAdapterMenu(myDataset);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_consulta);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(headerAdapterMenu);

        db = new DBInterface(this);
        db.obre();
        Cursor cursor = db.RetornaMenuSemanaAño(semanaAño);
        myDataset = mouCursor(cursor);
        db.tanca();
    }

    public ArrayList mouCursor(Cursor cursor) {
        if (cursor.moveToFirst()) {
            int contador = 0;
            do {

                String gluten = cursor.getString(cursor.getColumnIndex("glutenPrimero"));
                String crustaceos = cursor.getString(cursor.getColumnIndex("crustaceosPrimero"));
                String huevos = cursor.getString(cursor.getColumnIndex("huevosPrimero"));
                String cacahuetes = cursor.getString(cursor.getColumnIndex("cacahuetesPrimero"));
                String lacteos = cursor.getString(cursor.getColumnIndex("lacteosPrimero"));
                String cascaras = cursor.getString(cursor.getColumnIndex("cascarasPrimero"));
                String apio =  cursor.getString(cursor.getColumnIndex("apioPrimero"));
                String sulfitos = cursor.getString(cursor.getColumnIndex("sulfitosPrimero"));
                String moluscos = cursor.getString(cursor.getColumnIndex("moluscosPrimero"));
                if (gluten.equals("0")) Statics.esconderGluten1.add(contador,true); else Statics.esconderGluten1.add(contador,false);
                if (crustaceos.equals("0")) Statics.esconderCrustaceo1.add(contador,true); else Statics.esconderCrustaceo1.add(contador,false);
                if (huevos.equals("0")) Statics.esconderHuevos1.add(contador,true); else Statics.esconderHuevos1.add(contador,false);
                if (cacahuetes.equals("0")) Statics.esconderCacahuetes1.add(contador,true); else Statics.esconderCacahuetes1.add(contador,false);
                if (lacteos.equals("0")) Statics.esconderLacteos1.add(contador,true); else Statics.esconderLacteos1.add(contador,false);
                if (cascaras.equals("0")) Statics.esconderCascaras1.add(contador,true); else Statics.esconderCascaras1.add(contador,false);
                if (apio.equals("0")) Statics.esconderApio1.add(contador,true); else Statics.esconderApio1.add(contador,false);
                if (sulfitos.equals("0")) Statics.esconderSulfitos1.add(contador,true); else Statics.esconderSulfitos1.add(contador,false);
                if (moluscos.equals("0")) Statics.esconderMoluscos1.add(contador,true); else Statics.esconderMoluscos1.add(contador,false);

                String gluten2 = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.GLUTEN));
                String crustaceos2 = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.CRUSTACEOS));
                String huevos2 = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.HUEVOS));
                String cacahuetes2 = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.CACAHUETES));
                String lacteos2 = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.LACTEOS));
                String cascaras2 = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.FRUTOS_DE_CASCARA));
                String apio2 =  cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.APIO));
                String sulfitos2 = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.DIOXIDO_AZUFRE_SULFITOS));
                String moluscos2 = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.MOLUSCOS));
                if (gluten2.equals("0")) Statics.esconderGluten2.add(contador,true); else Statics.esconderGluten2.add(contador,false);
                if (crustaceos2.equals("0")) Statics.esconderCrustaceo2.add(contador,true); else Statics.esconderCrustaceo2.add(contador,false);
                if (huevos2.equals("0")) Statics.esconderHuevos2.add(contador,true); else Statics.esconderHuevos2.add(contador,false);
                if (cacahuetes2.equals("0")) Statics.esconderCacahuetes2.add(contador,true); else Statics.esconderCacahuetes2.add(contador,false);
                if (lacteos2.equals("0")) Statics.esconderLacteos2.add(contador,true); else Statics.esconderLacteos2.add(contador,false);
                if (cascaras2.equals("0")) Statics.esconderCascaras2.add(contador,true); else Statics.esconderCascaras2.add(contador,false);
                if (apio2.equals("0")) Statics.esconderApio2.add(contador,true); else Statics.esconderApio2.add(contador,false);
                if (sulfitos2.equals("0")) Statics.esconderSulfitos2.add(contador,true); else Statics.esconderSulfitos2.add(contador,false);
                if (moluscos2.equals("0")) Statics.esconderMoluscos2.add(contador,true); else Statics.esconderMoluscos2.add(contador,false);
                myDataset.add(new HeaderMenu(
                        cursor.getString(cursor.getColumnIndex(ContracteBD.MenuPlato.ID_MENU)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.MenuPlato.DIA_MENU)),
                        cursor.getString(cursor.getColumnIndex("primerPlato")),
                        cursor.getString(cursor.getColumnIndex("segundoPlato")),

                        gluten, crustaceos, huevos, cacahuetes, lacteos, cascaras,apio, sulfitos, moluscos,
                        gluten2, crustaceos2, huevos2, cacahuetes2, lacteos2, cascaras2,apio2, sulfitos2, moluscos2

                        ));
                contador++;
            } while (cursor.moveToNext());
        }
        return myDataset;
    }
}
