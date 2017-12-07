package ccastro.casal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PedidoActivity extends AppCompatActivity implements View.OnClickListener {
    private android.support.v7.widget.Toolbar mToolbar;
    private Button buttonAñadirProductoFactura;
    private String id_producte, quantitat,id_cliente;
    private Integer tipoProducto=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar_pedido);
        buttonAñadirProductoFactura =  (Button) mToolbar.findViewById(R.id.buttonAñadirProductoFactura) ;
        findViewById(R.id.buttonCafes).setOnClickListener(this);
        findViewById(R.id.buttonRefrescos).setOnClickListener(this);
        findViewById(R.id.buttonAlimentacion).setOnClickListener(this);
        findViewById(R.id.buttonOtrosProductos).setOnClickListener(this);
        buttonAñadirProductoFactura.setOnClickListener(this);
        /*
        SI SE SELECCIONA BOTON CANCELAR, NO SE SELECCIONA NADA, ENVIAMOS:
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
         */
        getIntents();
    }
    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.buttonAñadirProductoFactura:
                //  ESTO ES LO QUE HABRA QUE DEVOLVER, CON TODOS LOS PRODUCTOS SELECCIONADOS  (Producto + Cantidad) Devolver un Array de Producto+ Cantidad
                // DE MIENTRAS LO PROGRAMO.. envio 2 carajillos

                if (id_producte!=null){
                    /*
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("ID_PRODUCTE",id_producte);
                    returnIntent.putExtra("QUANTITAT",quantitat);
                    setResult(Activity.RESULT_OK,returnIntent);*/
                    Intent intent = new Intent(this, FacturaActivity.class);
                    intent.putExtra("ID_PRODUCTO",id_producte);
                    intent.putExtra("QUANTITAT",quantitat);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Selecciona Producto!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonCafes:   tipoProducto = 0;  SeleccionaProducte(); break;
            case R.id.buttonRefrescos:  tipoProducto = 1; SeleccionaProducte(); break;
            case R.id.buttonAlimentacion:   tipoProducto = 2; SeleccionaProducte(); break;
            case R.id.buttonOtrosProductos:    tipoProducto = 3; SeleccionaProducte(); break;
            default: break;
        }

    }
    public void SeleccionaProducte(){
        if (tipoProducto==0 || tipoProducto==1 || tipoProducto==2 || tipoProducto==3){
            Intent intent = new Intent (PedidoActivity.this,ProductoActivity.class);
            intent.putExtra("TIPO_PRODUCTO",tipoProducto);
            //startActivityForResult(intent,1);
            startActivity(intent);
            finish();

        }
    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1){
            // AQUI OBTENDREMOS LOS PRODUCTOS DE PRODUCTO ACTIVITY:
            id_producte = data.getStringExtra("ID_PRODUCTE");
            quantitat = data.getStringExtra("QUANTITAT");
        }
    } */
    public void getIntents(){
        if(getIntent().hasExtra("ID_PRODUCTE")) {
            id_producte = (getIntent().getExtras().getString("ID_PRODUCTE"));
            quantitat = (getIntent().getExtras().getString("QUANTITAT"));
            TextView textViewQuantitat = (TextView) mToolbar.findViewById(R.id.textViewNumProductes);
            textViewQuantitat.setText(quantitat);
            TextView textViewNomProducte = (TextView) mToolbar.findViewById(R.id.textViewNombreProducto);
            textViewNomProducte.setText((getIntent().getExtras().getString("NOM_PRODUCTE")));
            TextView textViewTotalProducte = (TextView) mToolbar.findViewById(R.id.textViewTotal);
            textViewTotalProducte.setText((getIntent().getExtras().getString("TOTAL_PRODUCTE")));
        }
    }
}
