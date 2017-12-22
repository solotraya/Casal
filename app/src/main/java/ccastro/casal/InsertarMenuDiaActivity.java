package ccastro.casal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class InsertarMenuDiaActivity extends AppCompatActivity{
    String dia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntents();
    }
    public void getIntents(){
        if (getIntent().hasExtra("DIA")){
            dia = (getIntent().getExtras().getString("DIA"));
        }
    }
}
