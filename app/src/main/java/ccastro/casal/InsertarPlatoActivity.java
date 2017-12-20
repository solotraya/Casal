package ccastro.casal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ccastro.casal.SQLite.DBInterface;
import ccastro.casal.Utils.Missatges;

public class InsertarPlatoActivity extends AppCompatActivity implements View.OnClickListener{
    DBInterface db;
    Boolean primerPlato;
    String id_plato;
    EditText nomPlato;
    TextView tipoPlato;
    String gluten="0", crustaceos="0", huevos="0",pescado="0", cacahuetes="0", lacteos="0", cascaras="0",apio="0", sulfitos="0", moluscos="0";

    private android.support.v7.widget.Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_plato);
        db = new DBInterface(this);
        nomPlato = (EditText) findViewById(R.id.editTextNomPlato);
        tipoPlato = (TextView) findViewById(R.id.textViewTipoPlato);
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar_plato);
        mToolbar.findViewById(R.id.buttonAñadirPlato).setOnClickListener( new View.OnClickListener(){
                 @Override
                 public void onClick(View view) {
                     if (nomPlato.getText().toString().length()>0){
                         db.obre();
                         if (primerPlato) db.InserirPrimerPlato(nomPlato.getText().toString(),gluten,crustaceos,huevos,pescado,cacahuetes,lacteos,cascaras,apio,sulfitos,moluscos);
                         else db.InserirSegundoPlato(nomPlato.getText().toString(),gluten,crustaceos,huevos,pescado,cacahuetes,lacteos,cascaras,apio,sulfitos,moluscos);
                         db.tanca();
                         finish();
                     } else {
                         Missatges.AlertMissatge("ERROR AL AÑADIR", "Introduce nombre de plato!", R.drawable.error2, InsertarPlatoActivity.this);
                     }
                 }
             }
        );
        findViewById(R.id.gluten).setOnClickListener(this); findViewById(R.id.crustaceos).setOnClickListener(this); findViewById(R.id.cacahuetes).setOnClickListener(this);
        findViewById(R.id.lacteos).setOnClickListener(this); findViewById(R.id.cascaras).setOnClickListener(this); findViewById(R.id.apio).setOnClickListener(this);
        findViewById(R.id.huevos).setOnClickListener(this);  findViewById(R.id.pescado).setOnClickListener(this); findViewById(R.id.azufre).setOnClickListener(this); findViewById(R.id.moluscos).setOnClickListener(this);
        findViewById(R.id.gluten2).setOnClickListener(this); findViewById(R.id.crustaceos2).setOnClickListener(this); findViewById(R.id.cacahuetes2).setOnClickListener(this);
        findViewById(R.id.lacteos2).setOnClickListener(this); findViewById(R.id.cascaras2).setOnClickListener(this); findViewById(R.id.apio2).setOnClickListener(this);
        findViewById(R.id.huevos2).setOnClickListener(this); findViewById(R.id.pescado2).setOnClickListener(this);  findViewById(R.id.azufre2).setOnClickListener(this); findViewById(R.id.moluscos2).setOnClickListener(this);
        getIntents();
    }
    public void getIntents(){
        if (getIntent().hasExtra("PRIMER_PLATO")){
            primerPlato = (getIntent().getExtras().getBoolean("PRIMER_PLATO"));
            if (primerPlato) tipoPlato.setText("Primer Plato");
            else tipoPlato.setText("Segundo Plato");
        }
        if (getIntent().hasExtra("ID_PLATO")){  // MODIFICAR
            id_plato = (getIntent().getExtras().getString("ID_PLATO"));
            mToolbar.findViewById(R.id.buttonModificarPlato).setVisibility(View.VISIBLE);
            mToolbar.findViewById(R.id.buttonAñadirPlato).setVisibility(View.GONE);
        } else { // AÑADIR
            mToolbar.findViewById(R.id.buttonAñadirPlato).setVisibility(View.VISIBLE);
            mToolbar.findViewById(R.id.buttonModificarPlato).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.gluten:
                findViewById(R.id.gluten).setVisibility(View.GONE);
                findViewById(R.id.gluten2).setVisibility(View.VISIBLE);
                gluten = "1";
                break;
            case R.id.gluten2:
                findViewById(R.id.gluten2).setVisibility(View.GONE);
                findViewById(R.id.gluten).setVisibility(View.VISIBLE);
                gluten = "0";
                break;
            case R.id.crustaceos:
                findViewById(R.id.crustaceos).setVisibility(View.GONE);
                findViewById(R.id.crustaceos2).setVisibility(View.VISIBLE);
                crustaceos = "1";
                break;
            case R.id.crustaceos2:
                findViewById(R.id.crustaceos2).setVisibility(View.GONE);
                findViewById(R.id.crustaceos).setVisibility(View.VISIBLE);
                crustaceos = "0";
                break;
            case R.id.cacahuetes:
                findViewById(R.id.cacahuetes).setVisibility(View.GONE);
                findViewById(R.id.cacahuetes2).setVisibility(View.VISIBLE);
                cacahuetes = "1";
                break;
            case R.id.cacahuetes2:
                findViewById(R.id.cacahuetes2).setVisibility(View.GONE);
                findViewById(R.id.cacahuetes).setVisibility(View.VISIBLE);
                cacahuetes = "0";
                break;
            case R.id.lacteos:
                findViewById(R.id.lacteos).setVisibility(View.GONE);
                findViewById(R.id.lacteos2).setVisibility(View.VISIBLE);
                lacteos = "1";
                break;
            case R.id.lacteos2:
                findViewById(R.id.lacteos2).setVisibility(View.GONE);
                findViewById(R.id.lacteos).setVisibility(View.VISIBLE);
                lacteos = "0";
                break;
            case R.id.cascaras:
                findViewById(R.id.cascaras).setVisibility(View.GONE);
                findViewById(R.id.cascaras2).setVisibility(View.VISIBLE);
                cascaras = "1";
                break;
            case R.id.cascaras2:
                findViewById(R.id.cascaras2).setVisibility(View.GONE);
                findViewById(R.id.cascaras).setVisibility(View.VISIBLE);
                cascaras = "0";
            case R.id.apio:
                findViewById(R.id.apio).setVisibility(View.GONE);
                findViewById(R.id.apio2).setVisibility(View.VISIBLE);
                apio = "1";
                break;
            case R.id.apio2:
                findViewById(R.id.apio2).setVisibility(View.GONE);
                findViewById(R.id.apio).setVisibility(View.VISIBLE);
                apio = "0";
                break;
            case R.id.huevos:
                findViewById(R.id.huevos).setVisibility(View.GONE);
                findViewById(R.id.huevos2).setVisibility(View.VISIBLE);
                huevos = "1";
                break;
            case R.id.huevos2:
                findViewById(R.id.huevos2).setVisibility(View.GONE);
                findViewById(R.id.huevos).setVisibility(View.VISIBLE);
                huevos = "0";
            case R.id.pescado:
                findViewById(R.id.pescado).setVisibility(View.GONE);
                findViewById(R.id.pescado2).setVisibility(View.VISIBLE);
                pescado = "1";
                break;
            case R.id.pescado2:
                findViewById(R.id.pescado2).setVisibility(View.GONE);
                findViewById(R.id.pescado).setVisibility(View.VISIBLE);
                pescado = "0";
            case R.id.azufre:
                findViewById(R.id.azufre).setVisibility(View.GONE);
                findViewById(R.id.azufre2).setVisibility(View.VISIBLE);
                sulfitos = "1";
                break;
            case R.id.azufre2:
                findViewById(R.id.azufre2).setVisibility(View.GONE);
                findViewById(R.id.azufre).setVisibility(View.VISIBLE);
                sulfitos = "0";
            case R.id.moluscos:
                findViewById(R.id.moluscos).setVisibility(View.GONE);
                findViewById(R.id.moluscos2).setVisibility(View.VISIBLE);
                moluscos = "1";
                break;
            case R.id.moluscos2:
                findViewById(R.id.moluscos2).setVisibility(View.GONE);
                findViewById(R.id.moluscos).setVisibility(View.VISIBLE);
                moluscos = "0";
                break;
        }
    }
}
