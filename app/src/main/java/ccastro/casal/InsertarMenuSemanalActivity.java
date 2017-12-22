package ccastro.casal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import ccastro.casal.SQLite.DBInterface;

public class InsertarMenuSemanalActivity extends AppCompatActivity  implements View.OnClickListener{
    private android.support.v7.widget.Toolbar mToolbar;
    String semana;
    Integer primeroLunes,segundoLunes,primeroMartes,segundoMartes,primeroMiercoles,
            segundoMiercoles,primeroJueves,segundoJueves,primeroViernes,segundoViernes;
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
                findViewById(R.id.tool_bar_insertar_diasLunes).setVisibility(View.VISIBLE);
                findViewById(R.id.tool_bar_insertar_diasLunes).findViewById(R.id.introduirDia).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        findViewById(R.id.tool_bar_insertar_diasLunes).setVisibility(View.GONE);
                        findViewById(R.id.InsertarLunes).setBackgroundColor(Color.GREEN);
                    }
                });
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
                findViewById(R.id.tool_bar_insertar_diasMartes).setVisibility(View.VISIBLE);
                findViewById(R.id.tool_bar_insertar_diasMartes).findViewById(R.id.introduirDia).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        findViewById(R.id.tool_bar_insertar_diasMartes).setVisibility(View.GONE);
                        findViewById(R.id.InsertarMartes).setBackgroundColor(Color.GREEN);
                    }
                });
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
                findViewById(R.id.tool_bar_insertar_diasMiercoles).setVisibility(View.VISIBLE);
                findViewById(R.id.tool_bar_insertar_diasMiercoles).findViewById(R.id.introduirDia).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        findViewById(R.id.tool_bar_insertar_diasMiercoles).setVisibility(View.GONE);
                        findViewById(R.id.InsertarMiercoles).setBackgroundColor(Color.GREEN);
                    }
                });
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
                findViewById(R.id.tool_bar_insertar_diasJueves).setVisibility(View.VISIBLE);
                findViewById(R.id.tool_bar_insertar_diasJueves).findViewById(R.id.introduirDia).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        findViewById(R.id.tool_bar_insertar_diasJueves).setVisibility(View.GONE);
                        findViewById(R.id.InsertarJueves).setBackgroundColor(Color.GREEN);
                    }
                });
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
                findViewById(R.id.tool_bar_insertar_diasViernes).setVisibility(View.VISIBLE);
                findViewById(R.id.tool_bar_insertar_diasViernes).findViewById(R.id.introduirDia).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        findViewById(R.id.tool_bar_insertar_diasViernes).setVisibility(View.GONE);
                        findViewById(R.id.InsertarViernes).setBackgroundColor(Color.GREEN);
                    }
                });
                findViewById(R.id.tool_bar_insertar_diasViernes).findViewById(R.id.buttonPrimero).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent.putExtra("PRIMER_PLATO",true);
                        startActivityForResult(intent,5);
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
            // De lo contrario, recogemos el resultado de la segunda actividad.
            Boolean primer_plato = data.getExtras().getBoolean("PRIMER_PLATO");
            String plato = data.getExtras().getString("ID_PLATO");
            Integer id_plato = Integer.parseInt(plato);
            // Y tratamos el resultado en función de si se lanzó para rellenar el
            // nombre o el apellido.
            switch (requestCode) {
                case 1:  // LUNES
                    if (primer_plato)primeroLunes = id_plato;
                    else segundoLunes = id_plato;
                    break;
                case 2: // MARTES
                    if (primer_plato)primeroMartes = id_plato;
                    else segundoMartes = id_plato;
                    break;
                case 3: // MIERCOLES
                    if (primer_plato)primeroMiercoles = id_plato;
                    else segundoMiercoles = id_plato;
                    break;
                case 4: // JUEVES
                    if (primer_plato)primeroJueves = id_plato;
                    else segundoJueves = id_plato;
                    break;
                case 5: // VIERNES
                    if (primer_plato)primeroViernes = id_plato;
                    else segundoViernes = id_plato;
                    break;
            }
        }
    }
}
