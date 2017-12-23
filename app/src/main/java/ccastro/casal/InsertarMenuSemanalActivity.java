package ccastro.casal;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ccastro.casal.SQLite.DBInterface;

public class InsertarMenuSemanalActivity extends AppCompatActivity  implements View.OnClickListener{
    private android.support.v7.widget.Toolbar mToolbar;
    String semana;
    Integer primeroLunes,segundoLunes,primeroMartes,segundoMartes,primeroMiercoles,
            segundoMiercoles,primeroJueves,segundoJueves,primeroViernes,segundoViernes;
    String pLunes="",sLunes="",pMartes="",sMartes="",pMiercoles="",sMiercoles="",pJueves="",sJueves="",pViernes="",sViernes="";
    Intent intent;
    DBInterface  db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_menu_semanal);
        findViewById(R.id.InsertarLunes).setOnClickListener(this);
        findViewById(R.id.InsertarMartes).setOnClickListener(this);
        findViewById(R.id.InsertarMiercoles).setOnClickListener(this);
        findViewById(R.id.InsertarJueves).setOnClickListener(this);
        findViewById(R.id.InsertarViernes).setOnClickListener(this);
        mToolbar= findViewById(R.id.tool_bar_menu);
        mToolbar.findViewById(R.id.buttonModificar).setVisibility(View.GONE);
        db = new DBInterface(this);

        mToolbar.findViewById(R.id.buttonAñadir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.obre();
                long posicio = db.InserirMenu(semana);
                int idMenu = (int)posicio;
                db.InserirMenuPlato(idMenu,primeroLunes,segundoLunes,"1");
                db.InserirMenuPlato(idMenu,primeroMartes,segundoMartes,"2");
                db.InserirMenuPlato(idMenu,primeroMiercoles,segundoMiercoles,"3");
                db.InserirMenuPlato(idMenu,primeroJueves,segundoJueves,"4");
                db.InserirMenuPlato(idMenu,primeroViernes,segundoViernes,"5");
                db.tanca();
                finish();
            }
        });
        intent = new Intent (InsertarMenuSemanalActivity.this, PlatoActivity.class);
        intent.putExtra("SELECCIONAR_PLATO_MENU",true);
        getIntents();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.InsertarLunes:
                if (findViewById(R.id.tool_bar_insertar_diasLunes).getVisibility()==View.GONE){
                    findViewById(R.id.tool_bar_insertar_diasLunes).setVisibility(View.VISIBLE);
                } else findViewById(R.id.tool_bar_insertar_diasLunes).setVisibility(View.GONE);
                findViewById(R.id.tool_bar_insertar_diasLunes).findViewById(R.id.buttonPrimero).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent.putExtra("PRIMER_PLATO",true);
                        startActivityForResult(intent,1);
                    }
                });
                findViewById(R.id.tool_bar_insertar_diasLunes).findViewById(R.id.buttonSegundo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent.putExtra("PRIMER_PLATO",false);
                        startActivityForResult(intent,1);
                    }
                });
                break;
            case R.id.InsertarMartes:
                if (findViewById(R.id.tool_bar_insertar_diasMartes).getVisibility()==View.GONE){
                    findViewById(R.id.tool_bar_insertar_diasMartes).setVisibility(View.VISIBLE);
                } else findViewById(R.id.tool_bar_insertar_diasMartes).setVisibility(View.GONE);
                findViewById(R.id.tool_bar_insertar_diasMartes).findViewById(R.id.buttonPrimero).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent.putExtra("PRIMER_PLATO",true);
                        startActivityForResult(intent,2);
                    }
                });
                findViewById(R.id.tool_bar_insertar_diasMartes).findViewById(R.id.buttonSegundo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent.putExtra("PRIMER_PLATO",false);
                        startActivityForResult(intent,2);
                    }
                });
                break;
            case R.id.InsertarMiercoles:
                if (findViewById(R.id.tool_bar_insertar_diasMiercoles).getVisibility()==View.GONE){
                    findViewById(R.id.tool_bar_insertar_diasMiercoles).setVisibility(View.VISIBLE);
                } else findViewById(R.id.tool_bar_insertar_diasMiercoles).setVisibility(View.GONE);
                findViewById(R.id.tool_bar_insertar_diasMiercoles).findViewById(R.id.buttonPrimero).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent.putExtra("PRIMER_PLATO",true);
                        startActivityForResult(intent,3);
                    }
                });
                findViewById(R.id.tool_bar_insertar_diasMiercoles).findViewById(R.id.buttonSegundo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent.putExtra("PRIMER_PLATO",false);
                        startActivityForResult(intent,3);
                    }
                });
                break;
            case R.id.InsertarJueves:
                if (findViewById(R.id.tool_bar_insertar_diasJueves).getVisibility()==View.GONE){
                    findViewById(R.id.tool_bar_insertar_diasJueves).setVisibility(View.VISIBLE);
                } else findViewById(R.id.tool_bar_insertar_diasJueves).setVisibility(View.GONE);
                findViewById(R.id.tool_bar_insertar_diasJueves).findViewById(R.id.buttonPrimero).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent.putExtra("PRIMER_PLATO",true);
                        startActivityForResult(intent,4);
                    }
                });
                findViewById(R.id.tool_bar_insertar_diasJueves).findViewById(R.id.buttonSegundo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent.putExtra("PRIMER_PLATO",false);
                        startActivityForResult(intent,4);
                    }
                });
                break;
            case R.id.InsertarViernes:
                if (findViewById(R.id.tool_bar_insertar_diasViernes).getVisibility()==View.GONE){
                    findViewById(R.id.tool_bar_insertar_diasViernes).setVisibility(View.VISIBLE);
                } else findViewById(R.id.tool_bar_insertar_diasViernes).setVisibility(View.GONE);
                findViewById(R.id.tool_bar_insertar_diasViernes).findViewById(R.id.buttonPrimero).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent.putExtra("PRIMER_PLATO",true);
                        startActivityForResult(intent,5);
                        findViewById(R.id.tool_bar_insertar_diasViernes).findViewById(R.id.buttonPrimero);
                    }
                });
                findViewById(R.id.tool_bar_insertar_diasViernes).findViewById(R.id.buttonSegundo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent.putExtra("PRIMER_PLATO",false);
                        startActivityForResult(intent,5);
                    }
                });
                break;
        }
    }
    public void getIntents() {
        if (getIntent().hasExtra("SEMANA")) {
            semana = (getIntent().getExtras().getString("SEMANA"));
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Comprobamos si el resultado de la segunda actividad es "RESULT_CANCELED".
        if (resultCode == RESULT_CANCELED) {
            // Si es así mostramos mensaje de cancelado por pantalla.
            Toast.makeText(this, "Resultado cancelado", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Boolean primer_plato = data.getExtras().getBoolean("PRIMER_PLATO");
            String plato = data.getExtras().getString("ID_PLATO");
            String nombrePlato = data.getExtras().getString("NOMBRE_PLATO");
            Integer id_plato = Integer.parseInt(plato);
            Button buton;
            switch (requestCode) {
                case 1:  // LUNES
                    if (primer_plato){
                        primeroLunes = id_plato;
                        pLunes = nombrePlato;
                        buton = findViewById(R.id.tool_bar_insertar_diasLunes).findViewById(R.id.buttonPrimero);
                        buton.setText(nombrePlato);
                        buton.setTextColor(Color.parseColor("#ffff8800"));
                        if (pLunes.length()>0 && sLunes.length()>0) lunesComplert();
                    }
                    else {
                        segundoLunes = id_plato;
                        sLunes = nombrePlato;
                        buton = findViewById(R.id.tool_bar_insertar_diasLunes).findViewById(R.id.buttonSegundo);
                        buton.setText(nombrePlato);
                        buton.setTextColor(Color.parseColor("#ffff8800"));
                        if (pLunes.length()>0 && sLunes.length()>0) lunesComplert();
                    }
                    break;
                case 2: // MARTES
                    if (primer_plato){
                        primeroMartes = id_plato;
                        pMartes = nombrePlato;
                        buton = findViewById(R.id.tool_bar_insertar_diasMartes).findViewById(R.id.buttonPrimero);
                        buton.setText(nombrePlato);
                        buton.setTextColor(Color.parseColor("#ffff8800"));
                        if (pMartes.length()>0 && sMartes.length()>0) martesComplert();
                    }
                    else {
                        segundoMartes = id_plato;
                        sMartes = nombrePlato;
                        buton = findViewById(R.id.tool_bar_insertar_diasMartes).findViewById(R.id.buttonSegundo);
                        buton.setText(nombrePlato);
                        buton.setTextColor(Color.parseColor("#ffff8800"));
                        if (pMartes.length()>0 && sMartes.length()>0) martesComplert();
                    }
                    break;
                case 3: // MIERCOLES
                    if (primer_plato){
                        primeroMiercoles = id_plato;
                        pMiercoles = nombrePlato;
                        buton = findViewById(R.id.tool_bar_insertar_diasMiercoles).findViewById(R.id.buttonPrimero);
                        buton.setText(nombrePlato);
                        buton.setTextColor(Color.parseColor("#ffff8800"));
                        if (pMiercoles.length()>0 && sMiercoles.length()>0) miercolesComplert();
                    }
                    else {
                        sMiercoles = nombrePlato;
                        segundoMiercoles = id_plato;
                        buton = findViewById(R.id.tool_bar_insertar_diasMiercoles).findViewById(R.id.buttonSegundo);
                        buton.setText(nombrePlato);
                        buton.setTextColor(Color.parseColor("#ffff8800"));
                        if (pMiercoles.length()>0 && sMiercoles.length()>0) miercolesComplert();
                    }
                    break;
                case 4: // JUEVES
                    if (primer_plato){
                        pJueves = nombrePlato;
                        primeroJueves = id_plato;
                        buton = findViewById(R.id.tool_bar_insertar_diasJueves).findViewById(R.id.buttonPrimero);
                        buton.setText(nombrePlato);
                        buton.setTextColor(Color.parseColor("#ffff8800"));
                        if (pJueves.length()>0 && sJueves.length()>0) juevesComplert();
                    }
                    else {
                        sJueves = nombrePlato;
                        segundoJueves = id_plato;
                        buton = findViewById(R.id.tool_bar_insertar_diasJueves).findViewById(R.id.buttonSegundo);
                        buton.setText(nombrePlato);
                        buton.setTextColor(Color.parseColor("#ffff8800"));
                        if (pJueves.length()>0 && sJueves.length()>0) juevesComplert();
                    }
                    break;
                case 5: // VIERNES
                    if (primer_plato){
                        pViernes = nombrePlato;
                        primeroViernes = id_plato;
                        buton = findViewById(R.id.tool_bar_insertar_diasViernes).findViewById(R.id.buttonPrimero);
                        buton.setText(nombrePlato);
                        buton.setTextColor(Color.parseColor("#ffff8800"));
                        if (pViernes.length()>0 && sViernes.length()>0) viernesComplert();
                    }
                    else {
                        sViernes = nombrePlato;
                        segundoViernes = id_plato;
                        buton = findViewById(R.id.tool_bar_insertar_diasViernes).findViewById(R.id.buttonSegundo);
                        buton.setText(nombrePlato);
                        buton.setTextColor(Color.parseColor("#ffff8800"));
                        if (pViernes.length()>0 && sViernes.length()>0) viernesComplert();
                    }
                    break;
            }
        }
    }
    public void lunesComplert(){
        findViewById(R.id.tool_bar_insertar_diasLunes).setVisibility(View.GONE);
        Button button = findViewById(R.id.InsertarLunes);
        button.setTextColor(Color.RED);
        button.setTypeface(null, Typeface.BOLD);
    }
    public void martesComplert(){
        findViewById(R.id.tool_bar_insertar_diasMartes).setVisibility(View.GONE);
        Button button = findViewById(R.id.InsertarMartes);
        button.setTextColor(Color.RED);
        button.setTypeface(null, Typeface.BOLD);
    }
    public void miercolesComplert(){
        findViewById(R.id.tool_bar_insertar_diasMiercoles).setVisibility(View.GONE);
        Button button = findViewById(R.id.InsertarMiercoles);
        button.setTextColor(Color.RED);
        button.setTypeface(null, Typeface.BOLD);
    }
    public void juevesComplert(){
        findViewById(R.id.tool_bar_insertar_diasJueves).setVisibility(View.GONE);
        Button button = findViewById(R.id.InsertarJueves);
        button.setTextColor(Color.RED);
        button.setTypeface(null, Typeface.BOLD);
    }
    public void viernesComplert(){
        findViewById(R.id.tool_bar_insertar_diasViernes).setVisibility(View.GONE);
        Button button = findViewById(R.id.InsertarViernes);
        button.setTextColor(Color.RED);
        button.setTypeface(null, Typeface.BOLD);
    }
}
