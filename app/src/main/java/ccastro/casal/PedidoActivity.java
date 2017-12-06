package ccastro.casal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PedidoActivity extends AppCompatActivity implements View.OnClickListener {
    private android.support.v7.widget.Toolbar mToolbar;
    private Button buttonAñadirProductoFactura;
    private String id_producte, quantitat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar_pedido);
        buttonAñadirProductoFactura =  (Button) mToolbar.findViewById(R.id.buttonAñadirProductoFactura) ;
        findViewById(R.id.buttonCafes).setOnClickListener(this); ;
        findViewById(R.id.buttonRefrescos) ;
        findViewById(R.id.buttonAlimentacion) ;
        findViewById(R.id.buttonOtrosProductos) ;

        buttonAñadirProductoFactura.setOnClickListener(this);


        /*
        SI SE SELECCIONA BOTON CANCELAR, NO SE SELECCIONA NADA, ENVIAMOS:
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
         */
    }
    @Override
    public void onClick(View view) {
        Integer tipoProducto=-1;
        switch(view.getId()){
            case R.id.buttonAñadirProductoFactura:
                //  ESTO ES LO QUE HABRA QUE DEVOLVER, CON TODOS LOS PRODUCTOS SELECCIONADOS  (Producto + Cantidad) Devolver un Array de Producto+ Cantidad
                // DE MIENTRAS LO PROGRAMO.. envio 2 carajillos

                if (id_producte!=null){
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("ID_PRODUCTE",id_producte);
                    returnIntent.putExtra("QUANTITAT",quantitat);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                } else {
                    Toast.makeText(this, "Selecciona Producto!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonCafes:   tipoProducto = 0;  break;
            case R.id.buttonRefrescos:  tipoProducto = 1; break;
            case R.id.buttonAlimentacion:   tipoProducto = 2; break;
            case R.id.buttonOtrosProductos:    tipoProducto = 3; break;
            default: break;
        }
        if (tipoProducto==0 || tipoProducto==1 || tipoProducto==2 || tipoProducto==3){
            Intent intent = new Intent (PedidoActivity.this,ProductoActivity.class);
            intent.putExtra("TIPO_PRODUCTO",tipoProducto);
            startActivityForResult(intent,1);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1){
            // AQUI OBTENDREMOS LOS PRODUCTOS DE PRODUCTO ACTIVITY:
            id_producte = data.getStringExtra("ID_PRODUCTE");
            quantitat = data.getStringExtra("QUANTITAT");
        }
    }
}
