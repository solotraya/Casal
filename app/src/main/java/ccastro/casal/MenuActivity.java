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
import ccastro.casal.Utils.Utilitats;

public class MenuActivity extends AppCompatActivity {
    TextView textViewFechaMenu;
    private android.support.v7.widget.Toolbar mToolbar;
    private Integer diaInicio=null, mesInicio,añoInicio;
    private String fechaMenu,semanaAño;
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
        textViewFechaMenu.setText("Semana: "+obtenerNumeroSemanaAño()+"  Fecha: "+Utilitats.getFechaFormatSpain(fechaMenu));
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
                textViewFechaMenu.setText("Semana: "+obtenerNumeroSemanaAño()+"  Fecha: "+Utilitats.getFechaFormatSpain(fechaMenu));
                actualizarRecyclerView();
                headerAdapterMenu.actualitzaRecycler(myDataset);
            }
        });
        findViewById(R.id.buttonFechaPosterior).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String fecha;
                if (!fechaMenu.equalsIgnoreCase(Utilitats.obtenerFechaActual())){  // Solo permito ir un dia adelante si el dia es anterior a hoy
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
                    textViewFechaMenu.setText("Semana: "+obtenerNumeroSemanaAño()+"  Fecha: "+Utilitats.getFechaFormatSpain(fechaMenu));
                    actualizarRecyclerView();
                    headerAdapterMenu.actualitzaRecycler(myDataset);
                }
            }
        });
        textViewFechaMenu.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (textViewFechaMenu.getText().toString().equalsIgnoreCase(Utilitats.getFechaFormatSpain(Utilitats.obtenerFechaActual()))){
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
    public String obtenerNumeroSemanaAño(){
        Calendar calendar = Calendar.getInstance();

        calendar.set(añoInicio, mesInicio - 1, diaInicio, 0, 0);
        int numberWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        semanaAño = Integer.toString(numberWeekOfYear);
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
            do {
                myDataset.add(new HeaderMenu(
                        cursor.getString(cursor.getColumnIndex(ContracteBD.MenuPlato.ID_MENU)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.MenuPlato.DIA_MENU)),
                        cursor.getString(cursor.getColumnIndex("primerPlato")),
                        cursor.getString(cursor.getColumnIndex("segundoPlato")),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Comida.GLUTEN)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Comida.CRUSTACEOS)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Comida.HUEVOS)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Comida.CACAHUETES)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Comida.LACTEOS)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Comida.FRUTOS_DE_CASCARA)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Comida.APIO)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Comida.DIOXIDO_AZUFRE_SULFITOS)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Comida.MOLUSCOS))
                        ));
            } while (cursor.moveToNext());
        }
        return myDataset;
    }
}
