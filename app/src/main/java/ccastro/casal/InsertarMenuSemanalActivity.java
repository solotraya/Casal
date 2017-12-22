package ccastro.casal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class InsertarMenuSemanalActivity extends AppCompatActivity  implements View.OnClickListener{
    String dia;
    private android.support.v7.widget.Toolbar mToolbar;
    android.support.v7.widget.Toolbar toolbarLunes;
    Boolean lunes, martes, mieroles, jueves, viernes;
    Intent intent;
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
        intent = new Intent (InsertarMenuSemanalActivity.this, ProductoActivity.class);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.InsertarLunes:
                toolbarLunes = findViewById(R.id.tool_bar_insertar_diasLunes);
                toolbarLunes.setVisibility(View.VISIBLE);
                dia="1";
                toolbarLunes.findViewById(R.id.introduirDia).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toolbarLunes.setVisibility(View.GONE);
                        findViewById(R.id.InsertarLunes).setBackgroundColor(Color.GREEN);
                        lunes = true;
                    }
                });
                toolbarLunes.findViewById(R.id.buttonPrimero).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent.putExtra("PRIMER_PLATO",true);
                        // TODO SEGUIR POR AQUI, HAY QUE MIRAR QUE PASA CON ISERTAR PRODUCTE en el ProductoActiviy, modificar para que se pueda abrir
                        startActivityForResult(intent,1);
                    }
                });
                toolbarLunes.findViewById(R.id.buttonSegundo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent.putExtra("PRIMER_PLATO",false);
                        startActivityForResult(intent,1);
                    }
                });
                break;
            case R.id.InsertarMartes:
                findViewById(R.id.tool_bar_insertar_diasMartes).setVisibility(View.VISIBLE);
                dia="2";
                break;
            case R.id.InsertarMiercoles:
                findViewById(R.id.tool_bar_insertar_diasMiercoles).setVisibility(View.VISIBLE);
                dia="3";
                break;
            case R.id.InsertarJueves:
                findViewById(R.id.tool_bar_insertar_diasJueves).setVisibility(View.VISIBLE);
                dia="4";
                break;
            case R.id.InsertarViernes:
                findViewById(R.id.tool_bar_insertar_diasViernes).setVisibility(View.VISIBLE);
                dia="5";
                break;
        }
    }
}
