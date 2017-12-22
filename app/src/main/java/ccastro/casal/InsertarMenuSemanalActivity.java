package ccastro.casal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class InsertarMenuSemanalActivity extends AppCompatActivity  implements View.OnClickListener{
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
    }
    @Override
    public void onClick(View view) {
        intent = new Intent (InsertarMenuSemanalActivity.this, InsertarMenuDiaActivity.class);
        switch (view.getId()){
            case R.id.InsertarLunes:
                intent.putExtra("DIA","1"); break;
            case R.id.InsertarMartes:
                intent.putExtra("DIA","2"); break;
            case R.id.InsertarMiercoles:
                intent.putExtra("DIA","3"); break;
            case R.id.InsertarJueves:
                intent.putExtra("DIA","4"); break;
            case R.id.InsertarViernes:
                intent.putExtra("DIA","5"); break;
        }
        startActivity(intent);
    }
}
