package ccastro.casal;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import ccastro.casal.RecyclerView.HeaderAdapterMenu;
import ccastro.casal.RecyclerView.HeaderMenu;
import ccastro.casal.SQLite.ContracteBD;
import ccastro.casal.SQLite.DBInterface;
import ccastro.casal.Utils.Missatges;
import ccastro.casal.Utils.Statics;
import ccastro.casal.Utils.Utilitats;
import harmony.java.awt.Color;

public class MenuActivity extends AppCompatActivity {
    Context context;
    PdfPTable table;
    PdfPCell cell;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL = 123;
    public static final String NADA = Environment.getExternalStorageDirectory().getAbsolutePath() + "//pdf/nada.png";
    public static final String PESCADO = Environment.getExternalStorageDirectory().getAbsolutePath() + "//pdf/pescado.png";
    public static final String APIO = Environment.getExternalStorageDirectory().getAbsolutePath() + "//pdf/apio.png";
    public static final String GLUTEN = Environment.getExternalStorageDirectory().getAbsolutePath() + "//pdf/gluten.png";
    public static final String CRUSTACEOS = Environment.getExternalStorageDirectory().getAbsolutePath() + "//pdf/crustaceos.png";
    public static final String HUEVOS = Environment.getExternalStorageDirectory().getAbsolutePath() + "//pdf/huevos.png";
    public static final String CACAHUETES = Environment.getExternalStorageDirectory().getAbsolutePath() + "//pdf/cacahuetes.png";
    public static final String LACTEOS = Environment.getExternalStorageDirectory().getAbsolutePath() + "//pdf/lacteos.png";
    public static final String CASCARAS = Environment.getExternalStorageDirectory().getAbsolutePath() + "//pdf/cascaras.png";
    public static final String SULFITOS = Environment.getExternalStorageDirectory().getAbsolutePath() + "//pdf/sulfitos.png";
    public static final String MOLUSCOS = Environment.getExternalStorageDirectory().getAbsolutePath() + "//pdf/moluscos.png";

    public Integer[] arrayIngredientsLunes = new Integer[11];
    public Integer[] arrayIngredients2Lunes = new Integer[11];
    public int quantitatAlergensLunes = 0,quantitatAlergens2Lunes = 0;

    public Integer[] arrayIngredientsMartes = new Integer[11];
    public Integer[] arrayIngredients2Martes = new Integer[11];
    public int quantitatAlergensMartes = 0,quantitatAlergens2Martes = 0;

    public Integer[] arrayIngredientsMiercoles = new Integer[11];
    public Integer[] arrayIngredients2Miercoles = new Integer[11];
    public int quantitatAlergensMiercoles = 0,quantitatAlergens2Miercoles = 0;

    public Integer[] arrayIngredientsJueves = new Integer[11];
    public Integer[] arrayIngredients2Jueves = new Integer[11];
    public int quantitatAlergensJueves = 0,quantitatAlergens2Jueves = 0;

    public Integer[] arrayIngredientsViernes = new Integer[11];
    public Integer[] arrayIngredients2Viernes = new Integer[11];
    public int quantitatAlergensViernes = 0,quantitatAlergens2Viernes = 0;

    public Integer GLUTEN_INT=1,APIO_INT=2,PESCADO_INT=3,CRUSTACEOS_INT=4,HUEVOS_INT=5,CACAHUETES_INT=6,LACTEOS_INT=7,CASCARAS_INT=8, SULFITOS_INT=9, MOLUSCOS_INT=10;

    public static View viewAnterior;
    public static String idMenuPlato, idMenu;
    public String primero,segundo,primeroLunes, segundoLunes, primeroMartes, segundoMartes,primeroMiercoles, segundoMiercoles,primeroJueves, segundoJueves,primeroViernes, segundoViernes, diaMenu;
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
        context = MenuActivity.this;
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_menu_imprimir);
        textViewFechaMenu = (TextView) findViewById(R.id.fechaVenta);
        fechaMenu = Utilitats.obtenerFechaActual();
        obtenerAñoMesDiaInicio(fechaMenu);
        obtenerNumeroSemanaAño(true);
        textViewFechaMenu.setText("Semana: "+obtenerNumeroSemanaAño(false)+" Fecha: "+Utilitats.getFechaFormatSpain(fechaMenu));
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
                } while (Utilitats.principioSemana(fecha, Calendar.MONDAY));
                textViewFechaMenu.setText("Semana: "+obtenerNumeroSemanaAño(false)+" - "+Utilitats.getFechaFormatSpain(fechaMenu));
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
                } while (Utilitats.principioSemana(fecha, Calendar.MONDAY));
                textViewFechaMenu.setText("Semana: "+obtenerNumeroSemanaAño(false)+" - "+Utilitats.getFechaFormatSpain(fechaMenu));
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
        mToolbar.findViewById(R.id.buttonImprimir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = checkPermission();
                if (result) {
                    crearPDF();
                }
            }
        });
        mToolbar.findViewById(R.id.buttonAñadir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,InsertarMenuSemanalActivity.class);
                intent.putExtra("SEMANA",semanaAño);
                startActivity(intent);
            }
        });
        mToolbar.findViewById(R.id.buttonModificar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,InsertarMenuSemanalActivity.class);
                intent.putExtra("SEMANA",semanaAño);
                intent.putExtra("MODIFICAR",true);
                intent.putExtra("ID_MENU",idMenu);
                startActivity(intent);
            }
        });
        mToolbar.findViewById(R.id.buttonEliminar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Seguro que quieres eliminar el menu?")
                        .setTitle("Atención!!")
                        .setIcon(R.drawable.error2)
                        .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();

                            }
                        })
                        .setPositiveButton("ELIMINAR",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        db.obre();
                                        long resultat= db.EliminarMenu(idMenu);
                                        db.tanca();
                                        if (resultat==1){
                                            Missatges.AlertMissatge("MENU "+semanaAño+" ELIMINADO", "El menu ha sido eliminado correctamente.", R.drawable.papelera, MenuActivity.this);
                                        }
                                        actualizarRecyclerView();
                                        headerAdapterMenu.actualitzaRecycler(myDataset);
                                    }
                                }
                        );
                AlertDialog alert = builder.create();
                alert.show();
            }
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
        idMenu=null;
        idMenuPlato=null;
        if (cursor.moveToFirst()) {
            mToolbar.findViewById(R.id.buttonModificar).setVisibility(View.VISIBLE);
            mToolbar.findViewById(R.id.buttonEliminar).setVisibility(View.VISIBLE);
            mToolbar.findViewById(R.id.buttonAñadir).setVisibility(View.GONE);
            int contador = 0;
            for (int i=0;i<arrayIngredientsLunes.length;i++){
                arrayIngredientsLunes[i]=0;  arrayIngredients2Lunes[i]=0;
            }
            for (int i=0;i<arrayIngredients2Martes.length;i++){
                arrayIngredientsMartes[i]=0;  arrayIngredients2Martes[i]=0;
            }
            for (int i=0;i<arrayIngredients2Miercoles.length;i++){
                arrayIngredientsMiercoles[i]=0;  arrayIngredients2Miercoles[i]=0;
            }
            for (int i=0;i<arrayIngredients2Jueves.length;i++){
                arrayIngredientsJueves[i]=0;  arrayIngredients2Jueves[i]=0;
            }
            for (int i=0;i<arrayIngredients2Viernes.length;i++){
                arrayIngredientsViernes[i]=0;  arrayIngredients2Viernes[i]=0;
            }

            quantitatAlergensLunes=0; quantitatAlergens2Lunes=0;
            quantitatAlergensMartes=0; quantitatAlergens2Martes=0;
            quantitatAlergensMiercoles=0; quantitatAlergens2Miercoles=0;
            quantitatAlergensJueves=0; quantitatAlergens2Jueves=0;
            quantitatAlergensViernes=0; quantitatAlergens2Viernes=0;

            do {
                diaMenu = cursor.getString(cursor.getColumnIndex(ContracteBD.MenuPlato.DIA_MENU));
                idMenu = cursor.getString(cursor.getColumnIndex(ContracteBD.MenuPlato.ID_MENU));
                primero = cursor.getString(cursor.getColumnIndex("primerPlato"));
                segundo = cursor.getString(cursor.getColumnIndex("segundoPlato"));
                String gluten = cursor.getString(cursor.getColumnIndex("glutenPrimero"));
                String crustaceos = cursor.getString(cursor.getColumnIndex("crustaceosPrimero"));
                String huevos = cursor.getString(cursor.getColumnIndex("huevosPrimero"));
                String pescado = cursor.getString(cursor.getColumnIndex("pescadoPrimero"));
                String cacahuetes = cursor.getString(cursor.getColumnIndex("cacahuetesPrimero"));
                String lacteos = cursor.getString(cursor.getColumnIndex("lacteosPrimero"));
                String cascaras = cursor.getString(cursor.getColumnIndex("cascarasPrimero"));
                String apio =  cursor.getString(cursor.getColumnIndex("apioPrimero"));
                String sulfitos = cursor.getString(cursor.getColumnIndex("sulfitosPrimero"));
                String moluscos = cursor.getString(cursor.getColumnIndex("moluscosPrimero"));


                if (diaMenu.equals("1")){ primeroLunes= primero; segundoLunes= segundo;}
                if (diaMenu.equals("2")){ primeroMartes=primero; segundoMartes=segundo;}
                if (diaMenu.equals("3")){ primeroMiercoles=primero; segundoMiercoles=segundo;}
                if (diaMenu.equals("4")){ primeroJueves=primero; segundoJueves=segundo;}
                if (diaMenu.equals("5")){ primeroViernes=primero; segundoViernes=segundo; }

                if (gluten==null) gluten="0";  if (crustaceos==null) crustaceos="0"; if (huevos==null) huevos="0"; if (pescado==null) pescado="0"; if (cacahuetes==null) cacahuetes="0";
                if (lacteos==null) lacteos="0"; if (cascaras==null) cascaras="0"; if (apio==null) apio="0"; if (sulfitos==null) sulfitos="0"; if (moluscos==null) moluscos="0";

                if (gluten.equals("0")) {
                    Statics.esconderGluten1.add(contador,true);
                    if (diaMenu.equals("1"))  arrayIngredientsLunes[GLUTEN_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredientsMartes[GLUTEN_INT]=0;
                    else if (diaMenu.equals("3"))  arrayIngredientsMiercoles[GLUTEN_INT]=0;
                    else if (diaMenu.equals("4"))  arrayIngredientsJueves[GLUTEN_INT]=0;
                    else if (diaMenu.equals("5"))  arrayIngredientsViernes[GLUTEN_INT]=0;
                } else {
                    Statics.esconderGluten1.add(contador,false);
                    if (diaMenu.equals("1")){
                        arrayIngredientsLunes[GLUTEN_INT]=1;
                        quantitatAlergensLunes++;
                    } else if (diaMenu.equals("2")){
                        arrayIngredientsMartes[GLUTEN_INT]=1; quantitatAlergensMartes++;
                    } else if (diaMenu.equals("3")){
                        arrayIngredientsMiercoles[GLUTEN_INT]=1; quantitatAlergensMiercoles++;
                    } else if (diaMenu.equals("4")){
                        arrayIngredientsJueves[GLUTEN_INT]=1; quantitatAlergensJueves++;
                    } else if (diaMenu.equals("5")){
                        arrayIngredientsViernes[GLUTEN_INT]=1; quantitatAlergensViernes++;
                    }

                }
                if (crustaceos.equals("0")) {
                    Statics.esconderCrustaceo1.add(contador,true);
                    if (diaMenu.equals("1")) arrayIngredientsLunes[CRUSTACEOS_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredientsMartes[CRUSTACEOS_INT]=0;
                    else if (diaMenu.equals("3"))  arrayIngredientsMiercoles[CRUSTACEOS_INT]=0;
                    else if (diaMenu.equals("4"))  arrayIngredientsJueves[CRUSTACEOS_INT]=0;
                    else if (diaMenu.equals("5"))  arrayIngredientsViernes[CRUSTACEOS_INT]=0;
                } else{
                    Statics.esconderCrustaceo1.add(contador,false);
                    if (diaMenu.equals("1")){
                        quantitatAlergensLunes++;
                        arrayIngredientsLunes[CRUSTACEOS_INT]=1;
                    } else if (diaMenu.equals("2")){
                        arrayIngredientsMartes[CRUSTACEOS_INT]=1; quantitatAlergensMartes++;
                    } else if (diaMenu.equals("3")){
                        arrayIngredientsMiercoles[CRUSTACEOS_INT]=1; quantitatAlergensMiercoles++;
                    } else if (diaMenu.equals("4")){
                        arrayIngredientsJueves[CRUSTACEOS_INT]=1; quantitatAlergensJueves++;
                    } else if (diaMenu.equals("5")){
                        arrayIngredientsViernes[CRUSTACEOS_INT]=1; quantitatAlergensViernes++;
                    }
                }
                if (huevos.equals("0")){
                    Statics.esconderHuevos1.add(contador,true);
                    if (diaMenu.equals("1"))  arrayIngredientsLunes[HUEVOS_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredientsMartes[HUEVOS_INT]=0;
                    else if (diaMenu.equals("3"))  arrayIngredientsMiercoles[HUEVOS_INT]=0;
                    else if (diaMenu.equals("4"))  arrayIngredientsJueves[HUEVOS_INT]=0;
                    else if (diaMenu.equals("5"))  arrayIngredientsViernes[HUEVOS_INT]=0;
                } else {
                    Statics.esconderHuevos1.add(contador,false);
                    if (diaMenu.equals("1")){
                        arrayIngredientsLunes[HUEVOS_INT]=1;
                        quantitatAlergensLunes++;
                    } else if (diaMenu.equals("2")){
                        arrayIngredientsMartes[HUEVOS_INT]=1; quantitatAlergensMartes++;
                    } else if (diaMenu.equals("3")){
                        arrayIngredientsMiercoles[HUEVOS_INT]=1; quantitatAlergensMiercoles++;
                    } else if (diaMenu.equals("4")){
                        arrayIngredientsJueves[HUEVOS_INT]=1; quantitatAlergensJueves++;
                    } else if (diaMenu.equals("5")){
                        arrayIngredientsViernes[HUEVOS_INT]=1; quantitatAlergensViernes++;
                    }

                }
                if (pescado.equals("0")){
                    Statics.esconderPescado1.add(contador,true);
                    if (diaMenu.equals("1")) arrayIngredientsLunes[PESCADO_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredientsMartes[PESCADO_INT]=0;
                    else if (diaMenu.equals("3"))  arrayIngredientsMiercoles[PESCADO_INT]=0;
                    else if (diaMenu.equals("4"))  arrayIngredientsJueves[PESCADO_INT]=0;
                    else if (diaMenu.equals("5"))  arrayIngredientsViernes[PESCADO_INT]=0;
                } else {
                    Statics.esconderPescado1.add(contador,false);
                    if (diaMenu.equals("1")){
                        quantitatAlergensLunes++;
                        arrayIngredientsLunes[PESCADO_INT]=1;
                    } else if (diaMenu.equals("2")){
                        arrayIngredientsMartes[PESCADO_INT]=1; quantitatAlergensMartes++;
                    } else if (diaMenu.equals("3")){
                        arrayIngredientsMiercoles[PESCADO_INT]=1; quantitatAlergensMiercoles++;
                    } else if (diaMenu.equals("4")){
                        arrayIngredientsJueves[PESCADO_INT]=1; quantitatAlergensJueves++;
                    } else if (diaMenu.equals("5")){
                        arrayIngredientsViernes[PESCADO_INT]=1; quantitatAlergensViernes++;
                    }

                }
                if (cacahuetes.equals("0")){
                    Statics.esconderCacahuetes1.add(contador,true);
                    if (diaMenu.equals("1")) arrayIngredientsLunes[CACAHUETES_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredientsMartes[CACAHUETES_INT]=0;
                    else if (diaMenu.equals("3"))  arrayIngredientsMiercoles[CACAHUETES_INT]=0;
                    else if (diaMenu.equals("4"))  arrayIngredientsJueves[CACAHUETES_INT]=0;
                    else if (diaMenu.equals("5"))  arrayIngredientsViernes[CACAHUETES_INT]=0;
                } else {
                    Statics.esconderCacahuetes1.add(contador,false);
                    if (diaMenu.equals("1")){
                        quantitatAlergensLunes++;
                        arrayIngredientsLunes[CACAHUETES_INT]=1;
                    } else if (diaMenu.equals("2")){
                        arrayIngredientsMartes[CACAHUETES_INT]=1; quantitatAlergensMartes++;
                    } else if (diaMenu.equals("3")){
                        arrayIngredientsMiercoles[CACAHUETES_INT]=1; quantitatAlergensMiercoles++;
                    } else if (diaMenu.equals("4")){
                        arrayIngredientsJueves[CACAHUETES_INT]=1; quantitatAlergensJueves++;
                    } else if (diaMenu.equals("5")){
                        arrayIngredientsViernes[CACAHUETES_INT]=1; quantitatAlergensViernes++;
                    }
                }
                if (lacteos.equals("0")) {
                    Statics.esconderLacteos1.add(contador,true);
                    if (diaMenu.equals("1")) arrayIngredientsLunes[LACTEOS_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredientsMartes[LACTEOS_INT]=0;
                    else if (diaMenu.equals("3"))  arrayIngredientsMiercoles[LACTEOS_INT]=0;
                    else if (diaMenu.equals("4"))  arrayIngredientsJueves[LACTEOS_INT]=0;
                    else if (diaMenu.equals("5"))  arrayIngredientsViernes[LACTEOS_INT]=0;
                } else {
                    Statics.esconderLacteos1.add(contador,false);
                    if (diaMenu.equals("1")){
                        quantitatAlergensLunes++;
                        arrayIngredientsLunes[LACTEOS_INT]=1;
                    } else if (diaMenu.equals("2")){
                        arrayIngredientsMartes[LACTEOS_INT]=1; quantitatAlergensMartes++;
                    } else if (diaMenu.equals("3")){
                        arrayIngredientsMiercoles[LACTEOS_INT]=1; quantitatAlergensMiercoles++;
                    } else if (diaMenu.equals("4")){
                        arrayIngredientsJueves[LACTEOS_INT]=1; quantitatAlergensJueves++;
                    } else if (diaMenu.equals("5")){
                        arrayIngredientsViernes[LACTEOS_INT]=1; quantitatAlergensViernes++;
                    }
                }
                if (cascaras.equals("0")) {
                    Statics.esconderCascaras1.add(contador,true);
                    if (diaMenu.equals("1")) arrayIngredientsLunes[CASCARAS_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredientsMartes[CASCARAS_INT]=0;
                    else if (diaMenu.equals("3"))  arrayIngredientsMiercoles[CASCARAS_INT]=0;
                    else if (diaMenu.equals("4"))  arrayIngredientsJueves[CASCARAS_INT]=0;
                    else if (diaMenu.equals("5"))  arrayIngredientsViernes[CASCARAS_INT]=0;
                } else {
                    Statics.esconderCascaras1.add(contador,false);
                    if (diaMenu.equals("1")){
                        quantitatAlergensLunes++;
                        arrayIngredientsLunes[CASCARAS_INT]=1;

                    } else if (diaMenu.equals("2")){
                        arrayIngredientsMartes[CASCARAS_INT]=1; quantitatAlergensMartes++;
                    } else if (diaMenu.equals("3")){
                        arrayIngredientsMiercoles[CASCARAS_INT]=1; quantitatAlergensMiercoles++;
                    } else if (diaMenu.equals("4")){
                        arrayIngredientsJueves[CASCARAS_INT]=1; quantitatAlergensJueves++;
                    } else if (diaMenu.equals("5")){
                        arrayIngredientsViernes[CASCARAS_INT]=1; quantitatAlergensViernes++;
                    }
                }
                if (apio.equals("0")) {
                    Statics.esconderApio1.add(contador,true);
                    if (diaMenu.equals("1")) arrayIngredientsLunes[APIO_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredientsMartes[APIO_INT]=0;
                    else if (diaMenu.equals("3"))  arrayIngredientsMiercoles[APIO_INT]=0;
                    else if (diaMenu.equals("4"))  arrayIngredientsJueves[APIO_INT]=0;
                    else if (diaMenu.equals("5"))  arrayIngredientsViernes[APIO_INT]=0;
                } else {
                    Statics.esconderApio1.add(contador,false);
                    if (diaMenu.equals("1")){
                        quantitatAlergensLunes++;
                        arrayIngredientsLunes[APIO_INT]=1;
                    } else if (diaMenu.equals("2")){
                        arrayIngredientsMartes[APIO_INT]=1; quantitatAlergensMartes++;
                    } else if (diaMenu.equals("3")){
                        arrayIngredientsMiercoles[APIO_INT]=1; quantitatAlergensMiercoles++;
                    } else if (diaMenu.equals("4")){
                        arrayIngredientsJueves[APIO_INT]=1; quantitatAlergensJueves++;
                    } else if (diaMenu.equals("5")){
                        arrayIngredientsViernes[APIO_INT]=1; quantitatAlergensViernes++;
                    }
                }
                if (sulfitos.equals("0")) {
                    Statics.esconderSulfitos1.add(contador,true);
                    if (diaMenu.equals("1")) arrayIngredientsLunes[SULFITOS_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredientsMartes[SULFITOS_INT]=0;
                    else if (diaMenu.equals("3"))  arrayIngredientsMiercoles[SULFITOS_INT]=0;
                    else if (diaMenu.equals("4"))  arrayIngredientsJueves[SULFITOS_INT]=0;
                    else if (diaMenu.equals("5"))  arrayIngredientsViernes[SULFITOS_INT]=0;
                } else {
                    Statics.esconderSulfitos1.add(contador,false);
                    if (diaMenu.equals("1")){
                        quantitatAlergensLunes++;
                        arrayIngredientsLunes[SULFITOS_INT]=1;
                    } else if (diaMenu.equals("2")){
                        arrayIngredientsMartes[SULFITOS_INT]=1; quantitatAlergensMartes++;
                    } else if (diaMenu.equals("3")){
                        arrayIngredientsMiercoles[SULFITOS_INT]=1; quantitatAlergensMiercoles++;
                    } else if (diaMenu.equals("4")){
                        arrayIngredientsJueves[SULFITOS_INT]=1; quantitatAlergensJueves++;
                    } else if (diaMenu.equals("5")){
                        arrayIngredientsViernes[SULFITOS_INT]=1; quantitatAlergensViernes++;
                    }
                }
                if (moluscos.equals("0")) {
                    Statics.esconderMoluscos1.add(contador,true);
                    if (diaMenu.equals("1"))  arrayIngredientsLunes[MOLUSCOS_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredientsMartes[MOLUSCOS_INT]=0;
                    else if (diaMenu.equals("3"))  arrayIngredientsMiercoles[MOLUSCOS_INT]=0;
                    else if (diaMenu.equals("4"))  arrayIngredientsJueves[MOLUSCOS_INT]=0;
                    else if (diaMenu.equals("5"))  arrayIngredientsViernes[MOLUSCOS_INT]=0;
                } else{
                    Statics.esconderMoluscos1.add(contador,false);
                    if (diaMenu.equals("1")){
                        quantitatAlergensLunes++;
                        arrayIngredientsLunes[MOLUSCOS_INT]=1;
                    } else if (diaMenu.equals("2")){
                        arrayIngredientsMartes[MOLUSCOS_INT]=1; quantitatAlergensMartes++;
                    } else if (diaMenu.equals("3")){
                        arrayIngredientsMiercoles[MOLUSCOS_INT]=1; quantitatAlergensMiercoles++;
                    } else if (diaMenu.equals("4")){
                        arrayIngredientsJueves[MOLUSCOS_INT]=1; quantitatAlergensJueves++;
                    } else if (diaMenu.equals("5")){
                        arrayIngredientsViernes[MOLUSCOS_INT]=1; quantitatAlergensViernes++;
                    }
                }



                String gluten2 = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.GLUTEN));
                String crustaceos2 = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.CRUSTACEOS));
                String huevos2 = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.HUEVOS));
                String pescado2 = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.PESCADO));
                String cacahuetes2 = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.CACAHUETES));
                String lacteos2 = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.LACTEOS));
                String cascaras2 = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.FRUTOS_DE_CASCARA));
                String apio2 =  cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.APIO));
                String sulfitos2 = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.DIOXIDO_AZUFRE_SULFITOS));
                String moluscos2 = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.MOLUSCOS));

                if (gluten2==null) gluten2="0"; if (crustaceos2==null) crustaceos2="0"; if (huevos2==null) huevos2="0"; if (pescado2==null) pescado2="0"; if (cacahuetes2==null) cacahuetes2="0";
                if (lacteos2==null) lacteos2="0"; if (cascaras2==null) cascaras2="0"; if (apio2==null) apio2="0"; if (sulfitos2==null) sulfitos2="0"; if (moluscos2==null) moluscos2="0";


                if (gluten2.equals("0")) {
                    Statics.esconderGluten2.add(contador,true);
                    if (diaMenu.equals("1")) arrayIngredients2Lunes[GLUTEN_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredients2Martes[GLUTEN_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredients2Miercoles[GLUTEN_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredients2Jueves[GLUTEN_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredients2Viernes[GLUTEN_INT]=0;
                } else {
                    Statics.esconderGluten2.add(contador,false);
                    if (diaMenu.equals("1")){
                        arrayIngredients2Lunes[GLUTEN_INT]=1;
                        quantitatAlergens2Lunes++;
                    } else if (diaMenu.equals("2")){
                        arrayIngredients2Martes[GLUTEN_INT]=1; quantitatAlergens2Martes++;
                    } else if (diaMenu.equals("3")){
                        arrayIngredients2Miercoles[GLUTEN_INT]=1; quantitatAlergens2Miercoles++;
                    } else if (diaMenu.equals("4")){
                        arrayIngredients2Jueves[GLUTEN_INT]=1; quantitatAlergens2Jueves++;
                    } else if (diaMenu.equals("5")){
                        arrayIngredients2Viernes[GLUTEN_INT]=1; quantitatAlergens2Viernes++;
                    }
                }
                if (crustaceos2.equals("0")) {
                    Statics.esconderCrustaceo2.add(contador,true);
                    if (diaMenu.equals("1")) arrayIngredients2Lunes[CRUSTACEOS_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredients2Martes[CRUSTACEOS_INT]=0;
                    else if (diaMenu.equals("3"))  arrayIngredients2Miercoles[CRUSTACEOS_INT]=0;
                    else if (diaMenu.equals("4"))  arrayIngredients2Jueves[CRUSTACEOS_INT]=0;
                    else if (diaMenu.equals("5"))  arrayIngredients2Viernes[CRUSTACEOS_INT]=0;
                } else{
                    Statics.esconderCrustaceo2.add(contador,false);
                    if (diaMenu.equals("1")){
                        quantitatAlergens2Lunes++;
                        arrayIngredients2Lunes[CRUSTACEOS_INT]=1;
                    } else if (diaMenu.equals("2")){
                        arrayIngredients2Martes[CRUSTACEOS_INT]=1; quantitatAlergens2Martes++;
                    } else if (diaMenu.equals("3")){
                        arrayIngredients2Miercoles[CRUSTACEOS_INT]=1; quantitatAlergens2Miercoles++;
                    } else if (diaMenu.equals("4")){
                        arrayIngredients2Jueves[CRUSTACEOS_INT]=1; quantitatAlergens2Jueves++;
                    } else if (diaMenu.equals("5")){
                        arrayIngredients2Viernes[CRUSTACEOS_INT]=1; quantitatAlergens2Viernes++;
                    }
                }
                if (huevos2.equals("0")){
                    Statics.esconderHuevos2.add(contador,true);
                    if (diaMenu.equals("1")) arrayIngredients2Lunes[HUEVOS_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredients2Martes[HUEVOS_INT]=0;
                    else if (diaMenu.equals("3"))  arrayIngredients2Miercoles[HUEVOS_INT]=0;
                    else if (diaMenu.equals("4"))  arrayIngredients2Jueves[HUEVOS_INT]=0;
                    else if (diaMenu.equals("5"))  arrayIngredients2Viernes[HUEVOS_INT]=0;
                } else {
                    Statics.esconderHuevos2.add(contador,false);
                    if (diaMenu.equals("1")){
                        quantitatAlergens2Lunes++;
                        arrayIngredients2Lunes[HUEVOS_INT]=1;
                    } else if (diaMenu.equals("2")){
                        arrayIngredients2Martes[HUEVOS_INT]=1; quantitatAlergens2Martes++;
                    } else if (diaMenu.equals("3")){
                        arrayIngredients2Miercoles[HUEVOS_INT]=1; quantitatAlergens2Miercoles++;
                    } else if (diaMenu.equals("4")){
                        arrayIngredients2Jueves[HUEVOS_INT]=1; quantitatAlergens2Jueves++;
                    } else if (diaMenu.equals("5")){
                        arrayIngredients2Viernes[HUEVOS_INT]=1; quantitatAlergens2Viernes++;
                    }
                }
                if (pescado2.equals("0")){
                    Statics.esconderPescado2.add(contador,true);
                    if (diaMenu.equals("1"))  arrayIngredients2Lunes[PESCADO_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredients2Martes[PESCADO_INT]=0;
                    else if (diaMenu.equals("3"))  arrayIngredients2Miercoles[PESCADO_INT]=0;
                    else if (diaMenu.equals("4"))  arrayIngredients2Jueves[PESCADO_INT]=0;
                    else if (diaMenu.equals("5"))  arrayIngredients2Viernes[PESCADO_INT]=0;
                } else {
                    Statics.esconderPescado2.add(contador,false);
                    if (diaMenu.equals("1")){
                        quantitatAlergens2Lunes++;
                        arrayIngredients2Lunes[PESCADO_INT]=1;
                    } else if (diaMenu.equals("2")){
                        arrayIngredients2Martes[PESCADO_INT]=1; quantitatAlergens2Martes++;
                    } else if (diaMenu.equals("3")){
                        arrayIngredients2Miercoles[PESCADO_INT]=1; quantitatAlergens2Miercoles++;
                    } else if (diaMenu.equals("4")){
                        arrayIngredients2Jueves[PESCADO_INT]=1; quantitatAlergens2Jueves++;
                    } else if (diaMenu.equals("5")){
                        arrayIngredients2Viernes[PESCADO_INT]=1; quantitatAlergens2Viernes++;
                    }
                }
                if (cacahuetes2.equals("0")){
                    Statics.esconderCacahuetes2.add(contador,true);
                    if (diaMenu.equals("1")) arrayIngredients2Lunes[CACAHUETES_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredients2Martes[CACAHUETES_INT]=0;
                    else if (diaMenu.equals("3"))  arrayIngredients2Miercoles[CACAHUETES_INT]=0;
                    else if (diaMenu.equals("4"))  arrayIngredients2Jueves[CACAHUETES_INT]=0;
                    else if (diaMenu.equals("5"))  arrayIngredients2Viernes[CACAHUETES_INT]=0;
                } else {
                    Statics.esconderCacahuetes2.add(contador,false);
                    if (diaMenu.equals("1")){
                        quantitatAlergens2Lunes++;
                        arrayIngredients2Lunes[CACAHUETES_INT]=1;
                    } else if (diaMenu.equals("2")){
                        arrayIngredients2Martes[CACAHUETES_INT]=1; quantitatAlergens2Martes++;
                    } else if (diaMenu.equals("3")){
                        arrayIngredients2Miercoles[CACAHUETES_INT]=1; quantitatAlergens2Miercoles++;
                    } else if (diaMenu.equals("4")){
                        arrayIngredients2Jueves[CACAHUETES_INT]=1; quantitatAlergens2Jueves++;
                    } else if (diaMenu.equals("5")){
                        arrayIngredients2Viernes[CACAHUETES_INT]=1; quantitatAlergens2Viernes++;
                    }
                }
                if (lacteos2.equals("0")) {
                    Statics.esconderLacteos2.add(contador,true);
                    if (diaMenu.equals("1")) arrayIngredients2Lunes[LACTEOS_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredients2Martes[LACTEOS_INT]=0;
                    else if (diaMenu.equals("3"))  arrayIngredients2Miercoles[LACTEOS_INT]=0;
                    else if (diaMenu.equals("4"))  arrayIngredients2Jueves[LACTEOS_INT]=0;
                    else if (diaMenu.equals("5"))  arrayIngredients2Viernes[LACTEOS_INT]=0;
                } else {
                    Statics.esconderLacteos2.add(contador,false);
                    if (diaMenu.equals("1")){
                        quantitatAlergens2Lunes++;
                        arrayIngredients2Lunes[LACTEOS_INT]=1;
                    } else if (diaMenu.equals("2")){
                        arrayIngredients2Martes[LACTEOS_INT]=1;quantitatAlergens2Martes++;
                    } else if (diaMenu.equals("3")){
                        arrayIngredients2Miercoles[LACTEOS_INT]=1;quantitatAlergens2Miercoles++;
                    } else if (diaMenu.equals("4")){
                        arrayIngredients2Jueves[LACTEOS_INT]=1;quantitatAlergens2Jueves++;
                    } else if (diaMenu.equals("5")){
                        arrayIngredients2Viernes[LACTEOS_INT]=1;quantitatAlergens2Viernes++;
                    }
                }
                if (cascaras2.equals("0")) {
                    Statics.esconderCascaras2.add(contador,true);
                    if (diaMenu.equals("1")) arrayIngredients2Lunes[CASCARAS_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredients2Martes[CASCARAS_INT]=0;
                    else if (diaMenu.equals("3"))  arrayIngredients2Miercoles[CASCARAS_INT]=0;
                    else if (diaMenu.equals("4"))  arrayIngredients2Jueves[CASCARAS_INT]=0;
                    else if (diaMenu.equals("5"))  arrayIngredients2Viernes[CASCARAS_INT]=0;
                } else {
                    Statics.esconderCascaras2.add(contador,false);
                    if (diaMenu.equals("1")){
                        quantitatAlergens2Lunes++;
                        arrayIngredients2Lunes[CASCARAS_INT]=1;
                    } else if (diaMenu.equals("2")){
                        arrayIngredients2Martes[CASCARAS_INT]=1; quantitatAlergens2Martes++;
                    } else if (diaMenu.equals("3")){
                        arrayIngredients2Miercoles[CASCARAS_INT]=1; quantitatAlergens2Miercoles++;
                    } else if (diaMenu.equals("4")){
                        arrayIngredients2Jueves[CASCARAS_INT]=1; quantitatAlergens2Jueves++;
                    } else if (diaMenu.equals("5")){
                        arrayIngredients2Viernes[CASCARAS_INT]=1; quantitatAlergens2Viernes++;
                    }
                }
                if (apio2.equals("0")) {
                    Statics.esconderApio2.add(contador,true);
                    if (diaMenu.equals("1")) arrayIngredients2Lunes[APIO_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredients2Martes[APIO_INT]=0;
                    else if (diaMenu.equals("3"))  arrayIngredients2Miercoles[APIO_INT]=0;
                    else if (diaMenu.equals("4"))  arrayIngredients2Jueves[APIO_INT]=0;
                    else if (diaMenu.equals("5"))  arrayIngredients2Viernes[APIO_INT]=0;
                } else {
                    Statics.esconderApio2.add(contador,false);
                    if (diaMenu.equals("1")){
                        quantitatAlergens2Lunes++;
                        arrayIngredients2Lunes[APIO_INT]=1;
                    } else if (diaMenu.equals("2")){
                        arrayIngredients2Martes[APIO_INT]=1; quantitatAlergens2Martes++;
                    } else if (diaMenu.equals("3")){
                        arrayIngredients2Miercoles[APIO_INT]=1; quantitatAlergens2Miercoles++;
                    } else if (diaMenu.equals("4")){
                        arrayIngredients2Jueves[APIO_INT]=1; quantitatAlergens2Jueves++;
                    } else if (diaMenu.equals("5")){
                        arrayIngredients2Viernes[APIO_INT]=1; quantitatAlergens2Viernes++;
                    }
                }
                if (sulfitos2.equals("0")) {
                    Statics.esconderSulfitos2.add(contador,true);
                    if (diaMenu.equals("1"))  arrayIngredients2Lunes[SULFITOS_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredients2Martes[APIO_INT]=0;
                    else if (diaMenu.equals("3"))  arrayIngredients2Miercoles[APIO_INT]=0;
                    else if (diaMenu.equals("4"))  arrayIngredients2Jueves[APIO_INT]=0;
                    else if (diaMenu.equals("5"))  arrayIngredients2Viernes[APIO_INT]=0;
                } else {
                    Statics.esconderSulfitos2.add(contador,false);
                    if (diaMenu.equals("1")){
                        quantitatAlergens2Lunes++;
                        arrayIngredients2Lunes[SULFITOS_INT]=1;
                    } else if (diaMenu.equals("2")){
                        arrayIngredients2Martes[SULFITOS_INT]=1; quantitatAlergens2Martes++;
                    } else if (diaMenu.equals("3")){
                        arrayIngredients2Miercoles[SULFITOS_INT]=1; quantitatAlergens2Miercoles++;
                    } else if (diaMenu.equals("4")){
                        arrayIngredients2Jueves[SULFITOS_INT]=1; quantitatAlergens2Jueves++;
                    } else if (diaMenu.equals("5")){
                        arrayIngredients2Viernes[SULFITOS_INT]=1; quantitatAlergens2Viernes++;
                    }
                }
                if (moluscos2.equals("0")) {
                    Statics.esconderMoluscos2.add(contador,true);
                    if (diaMenu.equals("1")) arrayIngredients2Lunes[MOLUSCOS_INT]=0;
                    else if (diaMenu.equals("2"))  arrayIngredients2Martes[APIO_INT]=0;
                    else if (diaMenu.equals("3"))  arrayIngredients2Miercoles[APIO_INT]=0;
                    else if (diaMenu.equals("4"))  arrayIngredients2Jueves[APIO_INT]=0;
                    else if (diaMenu.equals("5"))  arrayIngredients2Viernes[APIO_INT]=0;
                } else{
                    Statics.esconderMoluscos2.add(contador,false);
                    if (diaMenu.equals("1")){
                        quantitatAlergens2Lunes++;
                        arrayIngredients2Lunes[MOLUSCOS_INT]=1;
                    } else if (diaMenu.equals("2")){
                        arrayIngredients2Martes[MOLUSCOS_INT]=1; quantitatAlergens2Martes++;
                    } else if (diaMenu.equals("3")){
                        arrayIngredients2Miercoles[MOLUSCOS_INT]=1; quantitatAlergens2Miercoles++;
                    } else if (diaMenu.equals("4")){
                        arrayIngredients2Jueves[MOLUSCOS_INT]=1; quantitatAlergens2Jueves++;
                    } else if (diaMenu.equals("5")){
                        arrayIngredients2Viernes[MOLUSCOS_INT]=1; quantitatAlergens2Viernes++;
                    }
                }
                myDataset.add(new HeaderMenu(
                        cursor.getString(cursor.getColumnIndex(ContracteBD.MenuPlato._ID)),
                        idMenu,
                        diaMenu,
                        primero, segundo,
                        gluten, crustaceos, huevos,pescado, cacahuetes, lacteos, cascaras,apio, sulfitos, moluscos,
                        gluten2, crustaceos2, huevos2,pescado2, cacahuetes2, lacteos2, cascaras2,apio2, sulfitos2, moluscos2

                ));
                contador++;
            } while (cursor.moveToNext());

        } else {  // SI NO HAY MENU CREADO:
            mToolbar.findViewById(R.id.buttonEliminar).setVisibility(View.GONE);
            mToolbar.findViewById(R.id.buttonModificar).setVisibility(View.GONE);
            mToolbar.findViewById(R.id.buttonAñadir).setVisibility(View.VISIBLE);
            int contador = 1;
            Statics.esconderMoluscos1.clear(); Statics.esconderSulfitos1.clear(); Statics.esconderApio1.clear();
            Statics.esconderCacahuetes1.clear();Statics.esconderCascaras1.clear(); Statics.esconderCrustaceo1.clear();
            Statics.esconderLacteos1.clear(); Statics.esconderHuevos1.clear(); Statics.esconderGluten1.clear();
            Statics.esconderPescado1.clear();                                  Statics.esconderPescado2.clear();
            Statics.esconderMoluscos2.clear(); Statics.esconderSulfitos2.clear(); Statics.esconderApio2.clear();
            Statics.esconderCacahuetes2.clear();Statics.esconderCascaras2.clear(); Statics.esconderCrustaceo2.clear();
            Statics.esconderLacteos2.clear(); Statics.esconderHuevos2.clear(); Statics.esconderGluten2.clear();
            do {
                myDataset.add(new HeaderMenu(
                        null,
                        null,
                        Integer.toString(contador),
                        null,
                        null,

                        null, null, null,null, null, null, null,null, null, null,
                        null, null,null,null,null, null, null,null, null, null

                ));
                contador++;
            } while (contador<6);
        }
        return myDataset;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        menu.findItem(R.id.spinnerEstatVenta).setVisible(false);
        menu.findItem(R.id.buttonDataVenta).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(MenuActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth = selectedmonth + 1;
                        fechaMenu = "" + selectedyear + " " + selectedmonth + " " + selectedday;
                        obtenerAñoMesDiaInicio(fechaMenu);
                        textViewFechaMenu.setText("Semana: "+obtenerNumeroSemanaAño(false)+"  Fecha: "+Utilitats.getFechaFormatSpain(fechaMenu));
                        actualizarRecyclerView();
                        headerAdapterMenu.actualitzaRecycler(myDataset);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Selecciona Fecha");
                mDatePicker.show();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onResume() {
        super.onResume();
        actualizarRecyclerView();
        headerAdapterMenu.actualitzaRecycler(myDataset);
    }
    public static PdfPCell createImageCell(String path) throws DocumentException, IOException {
        Image img = Image.getInstance(getByteArrayFromImage(path));
        img.scaleAbsolute(20, 20);
        PdfPCell cell = new PdfPCell(img);
        return cell;
    }
    public static PdfPCell createTextCell(String text) throws DocumentException, IOException {
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(text);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
    public void createPdf() {
        Document doc = new Document();


        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "//pdf";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            Log.d("PDFCreator", "PDF Path: " + path);
            File  file = new File(dir, "menu.pdf");

            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            Log.d("CANTIDAD",Integer.toString(quantitatAlergensLunes));


            table = new PdfPTable(14);
            //  table.setWidthPercentage(100);
            // table.setWidths(new int[]{1, 2});
            //table.addCell(createTextCell("Primero Lunes: "+primero));
            cell = new PdfPCell( new Paragraph("Menú Setmanal: "+diaInicio+"/"+mesInicio+"/"+añoInicio,FontFactory.getFont(FontFactory.TIMES_BOLD,18,Font.BOLD, Color.BLACK)));
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(14);
            cell.setMinimumHeight(50);

            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
            // Linea en blanco en tabla


            for (int i=1; i<=5; i++){

                if (i == 1){
                    cell = new PdfPCell( new Paragraph("Dilluns: ",FontFactory.getFont(FontFactory.TIMES_BOLD,18,Font.UNDERLINE, harmony.java.awt.Color.BLACK)));
                    cell.setColspan(14); cell.setBorder(Rectangle.NO_BORDER);
                    cell.setMinimumHeight(30);table.addCell(cell);
                    primerPlat(1,arrayIngredientsLunes,quantitatAlergensLunes);
                    espacioTabla();
                    segonPlat(1,arrayIngredients2Lunes,quantitatAlergens2Lunes);
                } else if (i ==2){
                    espacioTabla();
                    cell = new PdfPCell( new Paragraph("Dimarts: ",FontFactory.getFont(FontFactory.TIMES_BOLD,18,Font.UNDERLINE, harmony.java.awt.Color.BLACK)));
                    cell.setColspan(14); cell.setBorder(Rectangle.NO_BORDER);
                    cell.setMinimumHeight(30);table.addCell(cell);
                    primerPlat(2,arrayIngredientsMartes,quantitatAlergensMartes);
                    espacioTabla();
                    segonPlat(2,arrayIngredients2Martes,quantitatAlergens2Martes);
                } else if (i ==3){
                    espacioTabla();
                    cell = new PdfPCell( new Paragraph("Dimecres: ",FontFactory.getFont(FontFactory.TIMES_BOLD,18,Font.UNDERLINE, harmony.java.awt.Color.BLACK)));
                    cell.setColspan(14); cell.setBorder(Rectangle.NO_BORDER);
                    cell.setMinimumHeight(30);table.addCell(cell);
                    primerPlat(3,arrayIngredientsMiercoles,quantitatAlergensMiercoles);
                    espacioTabla();
                    segonPlat(3,arrayIngredients2Miercoles,quantitatAlergens2Miercoles);
                } else if (i ==4){
                    espacioTabla();
                    cell = new PdfPCell( new Paragraph("Dijous: ",FontFactory.getFont(FontFactory.TIMES_BOLD,18,Font.UNDERLINE, harmony.java.awt.Color.BLACK)));
                    cell.setColspan(14); cell.setBorder(Rectangle.NO_BORDER);
                    cell.setMinimumHeight(30);table.addCell(cell);
                    primerPlat(4,arrayIngredientsJueves,quantitatAlergensJueves);
                    espacioTabla();
                    segonPlat(4,arrayIngredients2Jueves,quantitatAlergens2Jueves);
                } else if (i ==5){
                    espacioTabla();
                    cell = new PdfPCell( new Paragraph("Divendres: ",FontFactory.getFont(FontFactory.TIMES_BOLD,18,Font.UNDERLINE, harmony.java.awt.Color.BLACK)));
                    cell.setColspan(14); cell.setBorder(Rectangle.NO_BORDER);
                    cell.setMinimumHeight(30);table.addCell(cell);
                    primerPlat(5,arrayIngredientsViernes,quantitatAlergensViernes);
                    espacioTabla();
                    segonPlat(5,arrayIngredients2Viernes,quantitatAlergens2Viernes);
                }
            }
            doc.add(table);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally
        {
            doc.close();
            if (!doc.isOpen()){
                Missatges.AlertMissatge("MENU CREADO", "El menú esta listo para imprimir!", R.drawable.acierto, MenuActivity.this);
            }
        }
    }
    public void espacioTabla(){
        cell = new PdfPCell(new Paragraph(""));cell.setColspan(14); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
        cell = new PdfPCell(new Paragraph(""));cell.setColspan(14); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
        cell = new PdfPCell(new Paragraph(""));cell.setColspan(14); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
    }
    public void segonPlat (Integer dia, Integer[] arrayIngredients2, Integer quantitatAlergens2){
        PdfPCell cell = null;
        switch (dia){
            case 1:
                cell = new PdfPCell( new Paragraph(segundoLunes,FontFactory.getFont(FontFactory.TIMES_BOLD,16,Font.ITALIC, Color.DARK_GRAY)));
                cell.setColspan(8); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
                break;
            case 2:
                cell = new PdfPCell( new Paragraph(segundoMartes,FontFactory.getFont(FontFactory.TIMES_BOLD,16,Font.ITALIC, Color.DARK_GRAY)));
                cell.setColspan(8); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
                break;
            case 3:
                cell = new PdfPCell( new Paragraph(segundoMiercoles,FontFactory.getFont(FontFactory.TIMES_BOLD,16,Font.ITALIC, Color.DARK_GRAY)));
                cell.setColspan(8); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
                break;
            case 4:
                cell = new PdfPCell( new Paragraph(segundoJueves,FontFactory.getFont(FontFactory.TIMES_BOLD,16,Font.ITALIC, Color.DARK_GRAY)));
                cell.setColspan(8); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
                break;
            case 5: cell = new PdfPCell( new Paragraph(segundoViernes,FontFactory.getFont(FontFactory.TIMES_BOLD,16,Font.ITALIC, Color.DARK_GRAY)));
                cell.setColspan(8); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
                break;
        }

        try {
            for (int i=0; i<arrayIngredients2.length;i++){
                Log.d("NUM",Integer.toString(arrayIngredients2[i]));
                if (arrayIngredients2[i]==0) {}
                else{

                    Log.d("NUM ALERGEN",Integer.toString(arrayIngredients2[i]));
                    switch (i){
                        //GLUTEN_INT=1,APIO_INT=2,PESCADO_INT=3,CRUSTACEOS_INT=4,HUEVOS_INT=5,CACAHUETES_INT=6,LACTEOS_INT=7,CASCARAS_INT=8, SULFITOS_INT=9, MOLUSCOS_INT=10;
                        case 1:
                            cell =createImageCell(GLUTEN);cell.setBorder(Rectangle.NO_BORDER);table.addCell(cell); break;
                        case 2:
                            cell =createImageCell(APIO);cell.setBorder(Rectangle.NO_BORDER);table.addCell(cell); break;
                        case 3:
                            cell =createImageCell(PESCADO);cell.setBorder(Rectangle.NO_BORDER);table.addCell(cell); break;
                        case 4:
                            cell =createImageCell(CRUSTACEOS);cell.setBorder(Rectangle.NO_BORDER);table.addCell(cell); break;
                        case 5:
                            cell =createImageCell(HUEVOS);cell.setBorder(Rectangle.NO_BORDER);table.addCell(cell); break;
                        case 6:
                            cell =createImageCell(CACAHUETES);cell.setBorder(Rectangle.NO_BORDER);table.addCell(cell); break;
                        case 7:
                            cell =createImageCell(LACTEOS);cell.setBorder(Rectangle.NO_BORDER);table.addCell(cell); break;
                        case 8:
                            cell =createImageCell(CASCARAS);cell.setBorder(Rectangle.NO_BORDER);table.addCell(cell); break;
                        case 9:
                            cell =createImageCell(SULFITOS);cell.setBorder(Rectangle.NO_BORDER);table.addCell(cell); break;
                        case 10:
                            cell =createImageCell(MOLUSCOS);cell.setBorder(Rectangle.NO_BORDER);table.addCell(cell); break;
                        default: break;
                    }
                }
            }

            switch (quantitatAlergens2){
                case 0:  cell =createImageCell(NADA); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
                case 1:  cell =createImageCell(NADA); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
                case 2:  cell =createImageCell(NADA); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
                case 3:  cell =createImageCell(NADA); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
                case 4:  cell =createImageCell(NADA); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
                case 5: cell =createImageCell(NADA); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell); break;
                default: break;
            }

        } catch (IOException e) {
        Log.e("PDFCreator", "ioException:" + e);
        }  catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        }
    }
    public void primerPlat(Integer dia, Integer[] arrayIngredients, Integer quantitatAlergens){
        PdfPCell cell = null;
        switch (dia){
            case 1:
                cell = new PdfPCell( new Paragraph(primeroLunes,FontFactory.getFont(FontFactory.TIMES_BOLD,16,Font.ITALIC, Color.DARK_GRAY)));
                cell.setColspan(8); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
                break;
            case 2:
                cell = new PdfPCell( new Paragraph(primeroMartes,FontFactory.getFont(FontFactory.TIMES_BOLD,16,Font.ITALIC, harmony.java.awt.Color.DARK_GRAY)));
                cell.setColspan(8); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
                break;
            case 3:
                cell = new PdfPCell( new Paragraph(primeroMiercoles,FontFactory.getFont(FontFactory.TIMES_BOLD,16,Font.ITALIC, harmony.java.awt.Color.DARK_GRAY)));
                cell.setColspan(8); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
                break;
            case 4:
                cell = new PdfPCell( new Paragraph(primeroJueves,FontFactory.getFont(FontFactory.TIMES_BOLD,16,Font.ITALIC, harmony.java.awt.Color.DARK_GRAY)));
                cell.setColspan(8); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
                break;
            case 5:
                cell = new PdfPCell( new Paragraph(primeroViernes,FontFactory.getFont(FontFactory.TIMES_BOLD,16,Font.ITALIC, harmony.java.awt.Color.DARK_GRAY)));
                cell.setColspan(8); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
                break;
        }


        // cell.setBackgroundColor(harmony.java.awt.Color.BLUE);
        try {
            for (int i=0; i<arrayIngredients.length;i++){
                Log.d("NUM",Integer.toString(arrayIngredients[i]));
                if (arrayIngredients[i]==0) {}
                else{

                    Log.d("NUM ALERGEN",Integer.toString(arrayIngredients[i]));
                    switch (i){
                        //GLUTEN_INT=1,APIO_INT=2,PESCADO_INT=3,CRUSTACEOS_INT=4,HUEVOS_INT=5,CACAHUETES_INT=6,LACTEOS_INT=7,CASCARAS_INT=8, SULFITOS_INT=9, MOLUSCOS_INT=10;
                        case 1:
                            cell =createImageCell(GLUTEN);cell.setBorder(Rectangle.NO_BORDER);table.addCell(cell); break;
                        case 2:
                            cell =createImageCell(APIO);cell.setBorder(Rectangle.NO_BORDER);table.addCell(cell); break;
                        case 3:
                            cell =createImageCell(PESCADO);cell.setBorder(Rectangle.NO_BORDER);table.addCell(cell); break;
                        case 4:
                            cell =createImageCell(CRUSTACEOS);cell.setBorder(Rectangle.NO_BORDER);table.addCell(cell); break;
                        case 5:
                            cell =createImageCell(HUEVOS);cell.setBorder(Rectangle.NO_BORDER);table.addCell(cell); break;
                        case 6:
                            cell =createImageCell(CACAHUETES);cell.setBorder(Rectangle.NO_BORDER);table.addCell(cell); break;
                        case 7:
                            cell =createImageCell(LACTEOS);cell.setBorder(Rectangle.NO_BORDER);table.addCell(cell); break;
                        case 8:
                            cell =createImageCell(CASCARAS);cell.setBorder(Rectangle.NO_BORDER);table.addCell(cell); break;
                        case 9:
                            cell =createImageCell(SULFITOS);cell.setBorder(Rectangle.NO_BORDER);table.addCell(cell); break;
                        case 10:
                            cell =createImageCell(MOLUSCOS);cell.setBorder(Rectangle.NO_BORDER);table.addCell(cell); break;
                        default: break;
                    }
                }
            }

            switch (quantitatAlergens){
                case 0:  cell =createImageCell(NADA); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
                case 1:  cell =createImageCell(NADA); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
                case 2:  cell =createImageCell(NADA); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
                case 3:  cell =createImageCell(NADA); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
                case 4:  cell =createImageCell(NADA); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell);
                case 5: cell =createImageCell(NADA); cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell); break;
                default: break;
            }
        } catch (DocumentException de) {
        Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
    }
    public boolean checkPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Write calendar permission is necessary to write event!!!");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL);
                        }
                    });
                    android.app.AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    public static  byte[] getByteArrayFromImage( String imagen)
    {
        try {
            Bitmap selectedImage =  BitmapFactory.decodeFile(imagen);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            return stream.toByteArray();

        } catch (NullPointerException e) {
            Log.d("Null",e.getMessage());
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    crearPDF();
                } else {
                    Log.d("Prueba", "no aceptado");
                }
                break;
        }
    }

    public void crearPDF() {
        try {
            createPdf();
            Log.d("Prueba", "aceptado");
        } catch (Exception e) {
            Log.d("Prueba", "d");
        }
    }
}
