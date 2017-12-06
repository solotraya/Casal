package ccastro.casal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class ProductoActivity extends AppCompatActivity {
    Integer tipoProducto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        // TODO HACER UN LIST DE PRODUCTOS, PERO SOLO MOSTRAT SEGUN EL PARAMETRO ENVIADO EN EL INTENT DE PEDIDOS
        // POR EJEMPLO SI MANDO TIPO 0, SOLO SE MOSTRARAN LOS PRODUCTOS DE CAFE/TE EN LA CONSULTA
        getIntents();
    }
    public void getIntents(){
        if (getIntent().hasExtra("TIPO_PRODUCTO")){
            tipoProducto = (getIntent().getExtras().getInt("TIPO_PRODUCTO"));
            Toast.makeText(this, "Producto tipo "+Integer.toString(tipoProducto), Toast.LENGTH_SHORT).show();
            // PRUEBA DE ENVIO DE CARAJILLOS
            if (tipoProducto==0){
                Intent returnIntent = new Intent();
                returnIntent.putExtra("ID_PRODUCTE","6");
                returnIntent.putExtra("QUANTITAT","2");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        }
    }
}
