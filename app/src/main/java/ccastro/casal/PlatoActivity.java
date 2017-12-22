package ccastro.casal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import ccastro.casal.RecyclerView.HeaderAdapterPlato;
import ccastro.casal.RecyclerView.HeaderPlato;
import ccastro.casal.SQLite.ContracteBD;
import ccastro.casal.SQLite.DBInterface;
import ccastro.casal.Utils.Missatges;
import ccastro.casal.Utils.Statics;

public class PlatoActivity extends AppCompatActivity {
    public static View viewAnterior;
    public static String id_plato;
    public static Boolean seleccionarPlato = false;
    public static String nombrePlato;
    private ArrayList<HeaderPlato> myDataset;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    DBInterface db;
    private HeaderAdapterPlato headerAdapterPlato;
    private android.support.v7.widget.Toolbar mToolbar;
    Boolean primerPlato;
    String dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plato);
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar_cliente);

        mToolbar.findViewById(R.id.buttonAñadir).setOnClickListener( new View.OnClickListener(){
                 @Override
                 public void onClick(View view) {
                     if (seleccionarPlato==false){
                         Intent intent = new Intent (PlatoActivity.this,InsertarPlatoActivity.class);
                         intent.putExtra("PRIMER_PLATO",primerPlato);
                         startActivity(intent);
                     } else {
                         retornarPlato();
                     }
                 }
             }
        );
        mToolbar.findViewById(R.id.buttonModificar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id_plato != null){
                    Intent intent = new Intent (PlatoActivity.this,InsertarPlatoActivity.class);
                    intent.putExtra("ID_PLATO",id_plato);
                    intent.putExtra("PRIMER_PLATO",primerPlato);
                    startActivity(intent);
                } else Missatges.AlertMissatge("ERROR", "Selecciona un plato!", R.drawable.error2, PlatoActivity.this);
            }
        });
        mToolbar.findViewById(R.id.buttonEliminar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id_plato != null){
                    db.obre();
                    long resultat=0;
                    if (primerPlato) resultat = db.EliminarPrimerPlato(id_plato);
                    else resultat = db.EliminarSegundoPlato(id_plato);
                    db.tanca();
                    if (resultat==1){
                        Missatges.AlertMissatge("PLATO ELIMINADO", "El plato ha sido eliminado correctamente", R.drawable.papelera, PlatoActivity.this);
                    }
                    actualizarRecyclerView();
                    headerAdapterPlato.actualitzaRecycler(myDataset);
                } else Missatges.AlertMissatge("ERROR", "Selecciona un plato!", R.drawable.error2, PlatoActivity.this);
            }
        });
        getIntents();
        actualizarRecyclerView();
    }
    public void getIntents(){
        if (getIntent().hasExtra("PRIMER_PLATO")){
            primerPlato = (getIntent().getExtras().getBoolean("PRIMER_PLATO"));
        }
        if (getIntent().hasExtra("SELECCIONAR_PLATO_MENU")){
            seleccionarPlato = (getIntent().getExtras().getBoolean("SELECCIONAR_PLATO_MENU"));
            dia = (getIntent().getExtras().getString("DIA"));
            mToolbar.findViewById(R.id.buttonModificar).setVisibility(View.GONE);
            mToolbar.findViewById(R.id.buttonEliminar).setVisibility(View.GONE);
        }
    }
    public void retornarPlato(){
        if (id_plato!=null){
            Intent i = getIntent();
            i.putExtra("ID_PLATO", id_plato);
            i.putExtra("PRIMER_PLATO",primerPlato);
            i.putExtra("NOMBRE_PLATO",nombrePlato);
            setResult(RESULT_OK, i);
            finish();
        }
    }
    public void actualizarRecyclerView(){
        myDataset = new ArrayList<>();
        headerAdapterPlato = new HeaderAdapterPlato(myDataset);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_consulta);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(headerAdapterPlato);

        db = new DBInterface(this);
        db.obre();
        if (primerPlato){
            Cursor cursor = db.RetornaPrimerosPlatos();
            myDataset = mouCursorPrimerPlato(cursor);
        } else {
            Cursor cursor2 = db.RetornaSegundosPlatos();
            myDataset = mouCursorSegundoPlato(cursor2);
        }
        db.tanca();
    }
    public ArrayList mouCursorPrimerPlato(Cursor cursor) {
        if (cursor.moveToFirst()) {
            myDataset.clear();
            int contador = 0;
            do {
                String gluten = cursor.getString(cursor.getColumnIndex(ContracteBD.PrimerPlato.GLUTEN));
                String crustaceos = cursor.getString(cursor.getColumnIndex(ContracteBD.PrimerPlato.CRUSTACEOS));
                String huevos = cursor.getString(cursor.getColumnIndex(ContracteBD.PrimerPlato.HUEVOS));
                String pescado = cursor.getString(cursor.getColumnIndex(ContracteBD.PrimerPlato.PESCADO));
                String cacahuetes = cursor.getString(cursor.getColumnIndex(ContracteBD.PrimerPlato.CACAHUETES));
                String lacteos = cursor.getString(cursor.getColumnIndex(ContracteBD.PrimerPlato.LACTEOS));
                String cascaras = cursor.getString(cursor.getColumnIndex(ContracteBD.PrimerPlato.FRUTOS_DE_CASCARA));
                String apio = cursor.getString(cursor.getColumnIndex(ContracteBD.PrimerPlato.APIO));
                String sulfitos = cursor.getString(cursor.getColumnIndex(ContracteBD.PrimerPlato.DIOXIDO_AZUFRE_SULFITOS));
                String moluscos = cursor.getString(cursor.getColumnIndex(ContracteBD.PrimerPlato.MOLUSCOS));
                if (gluten.equals("0")) Statics.esconderGluten1.add(contador,true); else Statics.esconderGluten1.add(contador,false);
                if (crustaceos.equals("0")) Statics.esconderCrustaceo1.add(contador,true); else Statics.esconderCrustaceo1.add(contador,false);
                if (huevos.equals("0")) Statics.esconderHuevos1.add(contador,true); else Statics.esconderHuevos1.add(contador,false);
                if (pescado.equals("0")) Statics.esconderPescado1.add(contador,true); else Statics.esconderPescado1.add(contador,false);
                if (cacahuetes.equals("0")) Statics.esconderCacahuetes1.add(contador,true); else Statics.esconderCacahuetes1.add(contador,false);
                if (lacteos.equals("0")) Statics.esconderLacteos1.add(contador,true); else Statics.esconderLacteos1.add(contador,false);
                if (cascaras.equals("0")) Statics.esconderCascaras1.add(contador,true); else Statics.esconderCascaras1.add(contador,false);
                if (apio.equals("0")) Statics.esconderApio1.add(contador,true); else Statics.esconderApio1.add(contador,false);
                if (sulfitos.equals("0")) Statics.esconderSulfitos1.add(contador,true); else Statics.esconderSulfitos1.add(contador,false);
                if (moluscos.equals("0")) Statics.esconderMoluscos1.add(contador,true); else Statics.esconderMoluscos1.add(contador,false);
                myDataset.add(new HeaderPlato(
                        cursor.getString(cursor.getColumnIndex(ContracteBD.PrimerPlato._ID)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.PrimerPlato.NOMBRE_PLATO)),
                        gluten, crustaceos, huevos,pescado, cacahuetes, lacteos, cascaras,apio, sulfitos, moluscos
                ));
                contador++;
            } while (cursor.moveToNext());
        }
        return myDataset;
    }
    public ArrayList mouCursorSegundoPlato(Cursor cursor) {
        if (cursor.moveToFirst()) {
            myDataset.clear();
            int contador = 0;
            do {
                String gluten =cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.GLUTEN));
                String crustaceos = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.CRUSTACEOS));
                String huevos = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.HUEVOS));
                String pescado = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.PESCADO));
                String cacahuetes = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.CACAHUETES));
                String lacteos = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.LACTEOS));
                String cascaras = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.FRUTOS_DE_CASCARA));
                String apio = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.APIO));
                String sulfitos = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.DIOXIDO_AZUFRE_SULFITOS));
                String moluscos = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.MOLUSCOS));
                if (gluten.equals("0")) Statics.esconderGluten1.add(contador,true); else Statics.esconderGluten1.add(contador,false);
                if (crustaceos.equals("0")) Statics.esconderCrustaceo1.add(contador,true); else Statics.esconderCrustaceo1.add(contador,false);
                if (huevos.equals("0")) Statics.esconderHuevos1.add(contador,true); else Statics.esconderHuevos1.add(contador,false);
                if (pescado.equals("0")) Statics.esconderPescado1.add(contador,true); else Statics.esconderPescado1.add(contador,false);
                if (cacahuetes.equals("0")) Statics.esconderCacahuetes1.add(contador,true); else Statics.esconderCacahuetes1.add(contador,false);
                if (lacteos.equals("0")) Statics.esconderLacteos1.add(contador,true); else Statics.esconderLacteos1.add(contador,false);
                if (cascaras.equals("0")) Statics.esconderCascaras1.add(contador,true); else Statics.esconderCascaras1.add(contador,false);
                if (apio.equals("0")) Statics.esconderApio1.add(contador,true); else Statics.esconderApio1.add(contador,false);
                if (sulfitos.equals("0")) Statics.esconderSulfitos1.add(contador,true); else Statics.esconderSulfitos1.add(contador,false);
                if (moluscos.equals("0")) Statics.esconderMoluscos1.add(contador,true); else Statics.esconderMoluscos1.add(contador,false);
                myDataset.add(new HeaderPlato(
                        cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato._ID)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.NOMBRE_PLATO)),
                        gluten, crustaceos, huevos,pescado, cacahuetes, lacteos, cascaras,apio, sulfitos, moluscos

                ));
                contador++;
            } while (cursor.moveToNext());
        }
        return myDataset;
    }
    @Override
    protected void onResume() {
        super.onResume();
        id_plato = null;
        actualizarRecyclerView();
        headerAdapterPlato.actualitzaRecycler(myDataset);
    }
}
