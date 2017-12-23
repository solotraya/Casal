package ccastro.casal;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ccastro.casal.SQLite.ContracteBD;
import ccastro.casal.SQLite.DBInterface;

public class InsertarMenuSemanalActivity extends AppCompatActivity  implements View.OnClickListener{
    private android.support.v7.widget.Toolbar mToolbar;
    String semana;
    Integer idMenu;
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
        findViewById(R.id.tool_bar_insertar_diasMartes).setBackgroundColor(Color.parseColor("#263237"));
        findViewById(R.id.tool_bar_insertar_diasJueves).setBackgroundColor(Color.parseColor("#263237"));
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
        mToolbar.findViewById(R.id.buttonModificar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.obre();
                db.ActualitzarMenuPlato(idMenu,primeroLunes,segundoLunes,"1");
                db.ActualitzarMenuPlato(idMenu,primeroMartes,segundoMartes,"2");
                db.ActualitzarMenuPlato(idMenu,primeroMiercoles,segundoMiercoles,"3");
                db.ActualitzarMenuPlato(idMenu,primeroJueves,segundoJueves,"4");
                db.ActualitzarMenuPlato(idMenu,primeroViernes,segundoViernes,"5");
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
        if (getIntent().hasExtra("MODIFICAR")) {
            if (getIntent().hasExtra("ID_MENU")) {
                String menuid = (getIntent().getExtras().getString("ID_MENU"));
                idMenu = Integer.parseInt(menuid);
            }

            mToolbar.findViewById(R.id.buttonAñadir).setVisibility(View.GONE);
            mToolbar.findViewById(R.id.buttonModificar).setVisibility(View.VISIBLE);
            db.obre();
            Cursor cursor = db.obtenirMenuSetmana(semana);
            mouCursor(cursor);
            db.tanca();
            actualizarTextViews();
        } else {
            mToolbar.findViewById(R.id.buttonAñadir).setVisibility(View.VISIBLE);
            mToolbar.findViewById(R.id.buttonModificar).setVisibility(View.GONE);
        }
    }
    public void actualizarTextViews(){
        if (pLunes!=null){
            Button lunesP = findViewById(R.id.tool_bar_insertar_diasLunes).findViewById(R.id.buttonPrimero);
            lunesP.setText(pLunes);
            lunesP.setTextColor(Color.parseColor("#ffff8800"));
        }
        if (sLunes!=null){
            Button lunesS = findViewById(R.id.tool_bar_insertar_diasLunes).findViewById(R.id.buttonSegundo);
            lunesS.setText(sLunes);
            lunesS.setTextColor(Color.parseColor("#ffff8800"));
        }

        //findViewById(R.id.tool_bar_insertar_diasLunes).setVisibility(View.VISIBLE);

        if (pMartes!=null){
            Button martesP = findViewById(R.id.tool_bar_insertar_diasMartes).findViewById(R.id.buttonPrimero);
            martesP.setText(pMartes);
            martesP.setTextColor(Color.parseColor("#ffff8800"));
        }
        if (sMartes!= null){
            Button martesS = findViewById(R.id.tool_bar_insertar_diasMartes).findViewById(R.id.buttonSegundo);
            martesS.setText(sMartes);
            martesS.setTextColor(Color.parseColor("#ffff8800"));
        }

       // findViewById(R.id.tool_bar_insertar_diasMartes).setVisibility(View.VISIBLE);

        if (pMiercoles!=null){
            Button miercolesP = findViewById(R.id.tool_bar_insertar_diasMiercoles).findViewById(R.id.buttonPrimero);
            miercolesP.setText(pMiercoles);
            miercolesP.setTextColor(Color.parseColor("#ffff8800"));
        }
        if (sMiercoles!=null){
            Button miercolesS = findViewById(R.id.tool_bar_insertar_diasMiercoles).findViewById(R.id.buttonSegundo);
            miercolesS.setText(sMiercoles);
            miercolesS.setTextColor(Color.parseColor("#ffff8800"));
        }

       // findViewById(R.id.tool_bar_insertar_diasMiercoles).setVisibility(View.VISIBLE);
        if (pJueves!=null){
            Button juevesP = findViewById(R.id.tool_bar_insertar_diasJueves).findViewById(R.id.buttonPrimero);
            juevesP.setText(pJueves);
            juevesP.setTextColor(Color.parseColor("#ffff8800"));
        }
        if (sJueves!=null){
            Button juevesS = findViewById(R.id.tool_bar_insertar_diasJueves).findViewById(R.id.buttonSegundo);
            juevesS.setText(sJueves);
            juevesS.setTextColor(Color.parseColor("#ffff8800"));
        }
       // findViewById(R.id.tool_bar_insertar_diasJueves).setVisibility(View.VISIBLE);
        if (pViernes!=null){
            Button viernesP = findViewById(R.id.tool_bar_insertar_diasViernes).findViewById(R.id.buttonPrimero);
            viernesP.setText(pViernes);
            viernesP.setTextColor(Color.parseColor("#ffff8800"));
        }
        if (sViernes!=null){
            Button viernesS = findViewById(R.id.tool_bar_insertar_diasViernes).findViewById(R.id.buttonSegundo);
            viernesS.setText(sViernes);
            viernesS.setTextColor(Color.parseColor("#ffff8800"));
        }

       // findViewById(R.id.tool_bar_insertar_diasViernes).setVisibility(View.VISIBLE);
    }
    public void mouCursor(Cursor cursor){
        if(cursor.moveToFirst()) {
            do {
                String dia = cursor.getString(cursor.getColumnIndex(ContracteBD.MenuPlato.DIA_MENU));
                String primerPlato = cursor.getString(cursor.getColumnIndex("primerPlato"));
                String segundoPlato = cursor.getString(cursor.getColumnIndex("segundoPlato"));
                String idPrimerPlato = cursor.getString(cursor.getColumnIndex(ContracteBD.MenuPlato.PRIMER_PLATO));
                String idSegundoPlato = cursor.getString(cursor.getColumnIndex(ContracteBD.MenuPlato.SEGUNDO_PLATO));
                switch (Integer.parseInt(dia)){
                    case 1:
                        pLunes = primerPlato;sLunes= segundoPlato;
                        if (pLunes!=null) primeroLunes = Integer.parseInt(idPrimerPlato);
                        if (sLunes!=null) segundoLunes = Integer.parseInt(idSegundoPlato);

                        break;
                    case 2:
                        pMartes = primerPlato;sMartes = segundoPlato;
                        if (pMartes!=null) primeroMartes = Integer.parseInt(idPrimerPlato);
                        if (sMartes!=null) segundoMartes = Integer.parseInt(idSegundoPlato);
                        break;
                    case 3:
                        pMiercoles = primerPlato; sMiercoles = segundoPlato;
                        if (pMiercoles!=null) primeroMiercoles = Integer.parseInt(idPrimerPlato);
                        if (sMiercoles!=null)segundoMiercoles = Integer.parseInt(idSegundoPlato);
                        break;
                    case 4:
                        pJueves = primerPlato;sJueves = segundoPlato;
                        if (pJueves!=null) primeroJueves = Integer.parseInt(idPrimerPlato);
                        if (sJueves!=null) segundoJueves = Integer.parseInt(idSegundoPlato);
                        break;
                    case 5:
                        pViernes = primerPlato;sViernes = segundoPlato;
                        if (pViernes!=null)primeroViernes = Integer.parseInt(idPrimerPlato);
                        if (sViernes!=null) segundoViernes = Integer.parseInt(idSegundoPlato);
                        break;
                }
            } while(cursor.moveToNext());
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Selecciona Plato!", Toast.LENGTH_SHORT).show();
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
                        if (pLunes!=null && sLunes!=null){
                            if (pLunes.length()>0 && sLunes.length()>0) lunesComplert();
                        }

                    }
                    else {
                        segundoLunes = id_plato;
                        sLunes = nombrePlato;
                        buton = findViewById(R.id.tool_bar_insertar_diasLunes).findViewById(R.id.buttonSegundo);
                        buton.setText(nombrePlato);
                        buton.setTextColor(Color.parseColor("#ffff8800"));
                        if (pLunes!=null && sLunes!=null) {
                            if (pLunes.length() > 0 && sLunes.length() > 0) lunesComplert();
                        }
                    }
                    break;
                case 2: // MARTES
                    if (primer_plato){
                        primeroMartes = id_plato;
                        pMartes = nombrePlato;
                        buton = findViewById(R.id.tool_bar_insertar_diasMartes).findViewById(R.id.buttonPrimero);
                        buton.setText(nombrePlato);
                        buton.setTextColor(Color.parseColor("#ffff8800"));
                        if (pMartes!=null && sMartes!=null){
                            if (pMartes.length()>0 && sMartes.length()>0) martesComplert();
                        }

                    }
                    else {
                        segundoMartes = id_plato;
                        sMartes = nombrePlato;
                        buton = findViewById(R.id.tool_bar_insertar_diasMartes).findViewById(R.id.buttonSegundo);
                        buton.setText(nombrePlato);
                        buton.setTextColor(Color.parseColor("#ffff8800"));
                        if (pMartes!=null && sMartes!=null){
                            if (pMartes.length()>0 && sMartes.length()>0) martesComplert();
                        }
                    }
                    break;
                case 3: // MIERCOLES
                    if (primer_plato){
                        primeroMiercoles = id_plato;
                        pMiercoles = nombrePlato;
                        buton = findViewById(R.id.tool_bar_insertar_diasMiercoles).findViewById(R.id.buttonPrimero);
                        buton.setText(nombrePlato);
                        buton.setTextColor(Color.parseColor("#ffff8800"));
                        if (pMiercoles!=null && sMiercoles!=null){
                            if (pMiercoles.length()>0 && sMiercoles.length()>0) miercolesComplert();
                        }
                    }
                    else {
                        sMiercoles = nombrePlato;
                        segundoMiercoles = id_plato;
                        buton = findViewById(R.id.tool_bar_insertar_diasMiercoles).findViewById(R.id.buttonSegundo);
                        buton.setText(nombrePlato);
                        buton.setTextColor(Color.parseColor("#ffff8800"));
                        if (pMiercoles!=null && sMiercoles!=null){
                            if (pMiercoles.length()>0 && sMiercoles.length()>0) miercolesComplert();
                        }
                    }
                    break;
                case 4: // JUEVES
                    if (primer_plato){
                        pJueves = nombrePlato;
                        primeroJueves = id_plato;
                        buton = findViewById(R.id.tool_bar_insertar_diasJueves).findViewById(R.id.buttonPrimero);
                        buton.setText(nombrePlato);
                        buton.setTextColor(Color.parseColor("#ffff8800"));
                        if (pJueves!=null && sJueves!=null){
                            if (pJueves.length()>0 && sJueves.length()>0) juevesComplert();
                        }

                    }
                    else {
                        sJueves = nombrePlato;
                        segundoJueves = id_plato;
                        buton = findViewById(R.id.tool_bar_insertar_diasJueves).findViewById(R.id.buttonSegundo);
                        buton.setText(nombrePlato);
                        buton.setTextColor(Color.parseColor("#ffff8800"));
                        if (pJueves!=null && sJueves!=null){
                            if (pJueves.length()>0 && sJueves.length()>0) juevesComplert();
                        }
                    }
                    break;
                case 5: // VIERNES
                    if (primer_plato){
                        pViernes = nombrePlato;
                        primeroViernes = id_plato;
                        buton = findViewById(R.id.tool_bar_insertar_diasViernes).findViewById(R.id.buttonPrimero);
                        buton.setText(nombrePlato);
                        buton.setTextColor(Color.parseColor("#ffff8800"));
                        if (pViernes!=null && sViernes!=null){
                            if (pViernes.length()>0 && sViernes.length()>0) viernesComplert();
                        }
                    }
                    else {
                        sViernes = nombrePlato;
                        segundoViernes = id_plato;
                        buton = findViewById(R.id.tool_bar_insertar_diasViernes).findViewById(R.id.buttonSegundo);
                        buton.setText(nombrePlato);
                        buton.setTextColor(Color.parseColor("#ffff8800"));
                        if (pViernes!=null && sViernes!=null){
                            if (pViernes.length()>0 && sViernes.length()>0) viernesComplert();
                        }
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
