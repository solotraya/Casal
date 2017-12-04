package ccastro.casal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ProductoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        Intent returnIntent = new Intent();
        returnIntent.putExtra("ID_PRODUCTE","1");
        returnIntent.putExtra("QUANTITAT","2");
        setResult(Activity.RESULT_OK,returnIntent);
        finish();


        /*
        SI SE SELECCIONA BOTON CANCELAR, NO SE SELECCIONA NADA, ENVIAMOS:
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
         */
    }
}
