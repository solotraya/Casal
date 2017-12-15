package ccastro.casal;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ccastro.casal.SQLite.ContracteBD;
import ccastro.casal.SQLite.DBInterface;
import ccastro.casal.Utils.Cursors;
import ccastro.casal.Utils.Missatges;

public class InsertarProductoActivity extends AppCompatActivity {
    TextView titulo,tipoProducto;
    EditText nomProducte,precioProducte,precioProducteDecimal;
    String id_producto,preuFinal;
    Integer idProdcuteFactura;
    DBInterface db;
    private android.support.v7.widget.Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_producto);
        db = new DBInterface(this);
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar_producto);
        titulo = (TextView) findViewById(R.id.textViewOperacion);
        titulo.setPaintFlags(titulo.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        nomProducte = (EditText) findViewById(R.id.editTextNomProducte);
        precioProducte = (EditText) findViewById(R.id.editTextPrecioProducte);
        precioProducteDecimal = (EditText) findViewById(R.id.editTextPrecioProducteDecimal);
        tipoProducto = (TextView) findViewById(R.id.textViewTipoProducto);
        tipoProducto.setText(setTipoString());
        mToolbar.findViewById(R.id.buttonAñadirProducto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nomProducte.getText().toString().length()>0 && getPreu()){
                    db.obre();
                    long posicio = db.InserirProducte(nomProducte.getText().toString(),preuFinal,Integer.toString(PedidoActivity.tipoProducto));
                    db.tanca();
                    if (posicio!=-1) {
                        Missatges.AlertMissatge("PRODUCTO AÑADIDO", "Producto "+nomProducte.getText().toString()+" añadido satisfactoriamente!", R.drawable.acierto, InsertarProductoActivity.this);
                    } else Missatges.AlertMissatge("ERROR AL AÑADIR", "El producto "+nomProducte.getText().toString()+" ya existe", R.drawable.error2, InsertarProductoActivity.this);
                } else {
                    Missatges.AlertMissatge("ERROR AL AÑADIR", "El producto no ha podido añadirse!", R.drawable.error2, InsertarProductoActivity.this);
                }
            }
        });
        mToolbar.findViewById(R.id.buttonModificarProducto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nomProducte.getText().toString().length()>0 && getPreu()){
                    db.obre();
                    Cursor cursorProducteFactura = db.EncontrarId_VentaFacturaSinPagarProducto(id_producto);
                    idProdcuteFactura = Cursors.cursorIDProducteFactura(cursorProducteFactura);
                    Log.d("IDPRODUCTE: ", Integer.toString(idProdcuteFactura));
                    if (idProdcuteFactura==-1){
                        long resultat = db.ActualitzarProducte(Integer.parseInt(id_producto),nomProducte.getText().toString(),preuFinal);
                        db.tanca();
                        Log.d("ACTUALIZADO",Long.toString(resultat));
                        if (resultat!=0){
                            Missatges.AlertMissatge("PRODUCTO MODIFICADO", "Producto modificado satisfactoriamente!", R.drawable.acierto, InsertarProductoActivity.this);
                        } else Missatges.AlertMissatge("ERROR AL AÑADIR", "El producto "+nomProducte.getText().toString()+" ya existe", R.drawable.error2, InsertarProductoActivity.this);
                    }else {
                        Missatges.AlertMissatge("ERROR AL MODIFICAR", "El producto está en facturas sin pagar!", R.drawable.error2, InsertarProductoActivity.this);
                    }


            }  else Missatges.AlertMissatge("ERROR AL MODIFICAR", "El producto no ha podido modificarse", R.drawable.error2, InsertarProductoActivity.this);
            }
        });
        getIntents();
    }
    public Boolean getPreu(){
        char [] arrayPreu = precioProducte.getText().toString().toCharArray();
        char [] arrayPreuDecimal = precioProducteDecimal.getText().toString().toCharArray();
        for (int i=0; i<arrayPreu.length;i++){
            if (!Character.isDigit(arrayPreu[i])) return false;
        }
        for (int i=0; i<arrayPreuDecimal.length;i++){
            if (!Character.isDigit(arrayPreuDecimal[i])) return false;
        }
        preuFinal = precioProducte.getText().toString()+"."+precioProducteDecimal.getText().toString();
        return true;
    }
    public void getIntents(){
        if (getIntent().hasExtra("ID_PRODUCTO")){  // pasado desde ProductoActivity
            mToolbar.findViewById(R.id.buttonAñadirProducto).setVisibility(View.GONE);
            mToolbar.findViewById(R.id.buttonModificarProducto).setVisibility(View.VISIBLE);
            id_producto = getIntent().getExtras().getString("ID_PRODUCTO");
            db.obre();
            Cursor cursor = db.obtenirDadesProductetPerId(id_producto);
            mouCursor(cursor);
            db.tanca();
            titulo.setText("MOFIFICAR PRODUCTO");
        } else{
            mToolbar.findViewById(R.id.buttonModificarProducto).setVisibility(View.GONE);
        }
    }
    public String setTipoString(){
        switch (PedidoActivity.tipoProducto){
            case 0: return "Cafe e Infusiones";
            case 1: return "Refrescos i cervezas";
            case 2: return "Alimentación";
            case 3: return "Otros productos";
        }
        return null;
    }

    public void mouCursor(Cursor cursor){
        if(cursor.moveToFirst()) {
            do {
                nomProducte.setText(cursor.getString(cursor.getColumnIndex(ContracteBD.Producte.NOM_PRODUCTE)));
                String precio= (cursor.getString(cursor.getColumnIndex(ContracteBD.Producte.PREU_PRODUCTE)));
                String[] precioFinal = precio.split("\\.");
                String precio1 = precioFinal[0];
                String precio2="0";
                if (precioFinal.length>1) precio2 = precioFinal[1];
                precioProducte.setText(precio1);
                precioProducteDecimal.setText(precio2);
            } while(cursor.moveToNext());
        }
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
