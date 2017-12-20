package ccastro.casal;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import ccastro.casal.RecyclerView.HeaderAdapterPlato;
import ccastro.casal.RecyclerView.HeaderPlato;
import ccastro.casal.SQLite.ContracteBD;
import ccastro.casal.SQLite.DBInterface;
import ccastro.casal.Utils.Statics;

public class PlatoActivity extends AppCompatActivity {
    private ArrayList<HeaderPlato> myDataset;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    DBInterface db;
    private HeaderAdapterPlato headerAdapterPlato;
    private android.support.v7.widget.Toolbar mToolbar;
    Boolean primerPlato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plato);
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar_cliente);
        getIntents();
        actualizarRecyclerView();
    }
    public void getIntents(){
        if (getIntent().hasExtra("PRIMER_PLATO")){
            primerPlato = (getIntent().getExtras().getBoolean("PRIMER_PLATO"));
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
        primerPlato = null;
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
                String cacahuetes = cursor.getString(cursor.getColumnIndex(ContracteBD.PrimerPlato.CACAHUETES));
                String lacteos = cursor.getString(cursor.getColumnIndex(ContracteBD.PrimerPlato.LACTEOS));
                String cascaras = cursor.getString(cursor.getColumnIndex(ContracteBD.PrimerPlato.FRUTOS_DE_CASCARA));
                String apio = cursor.getString(cursor.getColumnIndex(ContracteBD.PrimerPlato.APIO));
                String sulfitos = cursor.getString(cursor.getColumnIndex(ContracteBD.PrimerPlato.DIOXIDO_AZUFRE_SULFITOS));
                String moluscos = cursor.getString(cursor.getColumnIndex(ContracteBD.PrimerPlato.MOLUSCOS));
                if (gluten.equals("0")) Statics.esconderGluten1.add(contador,true); else Statics.esconderGluten1.add(contador,false);
                if (crustaceos.equals("0")) Statics.esconderCrustaceo1.add(contador,true); else Statics.esconderCrustaceo1.add(contador,false);
                if (huevos.equals("0")) Statics.esconderHuevos1.add(contador,true); else Statics.esconderHuevos1.add(contador,false);
                if (cacahuetes.equals("0")) Statics.esconderCacahuetes1.add(contador,true); else Statics.esconderCacahuetes1.add(contador,false);
                if (lacteos.equals("0")) Statics.esconderLacteos1.add(contador,true); else Statics.esconderLacteos1.add(contador,false);
                if (cascaras.equals("0")) Statics.esconderCascaras1.add(contador,true); else Statics.esconderCascaras1.add(contador,false);
                if (apio.equals("0")) Statics.esconderApio1.add(contador,true); else Statics.esconderApio1.add(contador,false);
                if (sulfitos.equals("0")) Statics.esconderSulfitos1.add(contador,true); else Statics.esconderSulfitos1.add(contador,false);
                if (moluscos.equals("0")) Statics.esconderMoluscos1.add(contador,true); else Statics.esconderMoluscos1.add(contador,false);
                myDataset.add(new HeaderPlato(
                        cursor.getString(cursor.getColumnIndex(ContracteBD.PrimerPlato._ID)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.PrimerPlato.NOMBRE_PLATO)),
                        gluten, crustaceos, huevos, cacahuetes, lacteos, cascaras,apio, sulfitos, moluscos
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
                String cacahuetes = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.CACAHUETES));
                String lacteos = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.LACTEOS));
                String cascaras = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.FRUTOS_DE_CASCARA));
                String apio = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.APIO));
                String sulfitos = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.DIOXIDO_AZUFRE_SULFITOS));
                String moluscos = cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.MOLUSCOS));
                if (gluten.equals("0")) Statics.esconderGluten1.add(contador,true); else Statics.esconderGluten1.add(contador,false);
                if (crustaceos.equals("0")) Statics.esconderCrustaceo1.add(contador,true); else Statics.esconderCrustaceo1.add(contador,false);
                if (huevos.equals("0")) Statics.esconderHuevos1.add(contador,true); else Statics.esconderHuevos1.add(contador,false);
                if (cacahuetes.equals("0")) Statics.esconderCacahuetes1.add(contador,true); else Statics.esconderCacahuetes1.add(contador,false);
                if (lacteos.equals("0")) Statics.esconderLacteos1.add(contador,true); else Statics.esconderLacteos1.add(contador,false);
                if (cascaras.equals("0")) Statics.esconderCascaras1.add(contador,true); else Statics.esconderCascaras1.add(contador,false);
                if (apio.equals("0")) Statics.esconderApio1.add(contador,true); else Statics.esconderApio1.add(contador,false);
                if (sulfitos.equals("0")) Statics.esconderSulfitos1.add(contador,true); else Statics.esconderSulfitos1.add(contador,false);
                if (moluscos.equals("0")) Statics.esconderMoluscos1.add(contador,true); else Statics.esconderMoluscos1.add(contador,false);
                myDataset.add(new HeaderPlato(
                        cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato._ID)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.SegundoPlato.NOMBRE_PLATO)),
                        gluten, crustaceos, huevos, cacahuetes, lacteos, cascaras,apio, sulfitos, moluscos

                ));
                contador++;
            } while (cursor.moveToNext());
        }
        return myDataset;
    }
}
