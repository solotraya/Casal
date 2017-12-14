package ccastro.casal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class InsertarProductoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_producto);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(InsertarProductoActivity.this,ProductoActivity.class);
        intent.putExtra("TIPO_PRODUCTO",PedidoActivity.tipoProducto);
        intent.putExtra("INSERTAR_PRODUCTO",true);
        startActivity(intent);
    }
}
