package ccastro.casal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import ccastro.casal.RecyclerView.HeaderAdapterProducte;
import ccastro.casal.RecyclerView.HeaderProducte;
import ccastro.casal.SQLite.ContracteBD;
import ccastro.casal.SQLite.DBInterface;
import ccastro.casal.Utils.Cursors;
import ccastro.casal.Utils.Missatges;

public class ProductoActivity extends AppCompatActivity {
    DBInterface db;
    Integer tipoProducto,idProdcuteFactura;
    public static View viewAnterior;
    public static  String id_producte;
    private HeaderAdapterProducte headerAdapterProducte;
    private ArrayList<HeaderProducte> myDataset;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    public static boolean insertarProducto=false;
    private android.support.v7.widget.Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar_cliente);
        db = new DBInterface(this);
        myDataset = new ArrayList<>();
        headerAdapterProducte= new HeaderAdapterProducte(myDataset);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_consulta);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(headerAdapterProducte);
        mToolbar.findViewById(R.id.buttonAñadir).setOnClickListener( new View.OnClickListener(){
                 @Override
                 public void onClick(View view) {
                    startActivity(new Intent(ProductoActivity.this, InsertarProductoActivity.class));
                 }
             }
        );
        mToolbar.findViewById(R.id.buttonModificar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id_producte!= null){
                    Intent intent = new Intent (ProductoActivity.this,InsertarProductoActivity.class);
                    intent.putExtra("ID_PRODUCTO",id_producte);
                    startActivity(intent);
                    finish();
                } else Missatges.AlertMissatge("ERROR", "Selecciona un producto!", R.drawable.error2, ProductoActivity.this);
            }
        });

        mToolbar.findViewById(R.id.buttonEliminar).setOnClickListener( new View.OnClickListener(){
               @Override
               public void onClick(View view) {
                   if (id_producte!= null) {
                       db.obre();
                       Cursor cursorProducteFactura = db.EncontrarId_VentaFacturaSinPagarProducto(id_producte);
                       idProdcuteFactura = Cursors.cursorIDProducteFactura(cursorProducteFactura);
                       Log.d("IDPRODUCTE: ", Integer.toString(idProdcuteFactura));
                       if (idProdcuteFactura==-1){
                           long resultat = db.EliminarProducte(id_producte);
                           db.tanca();
                           if (resultat==1){
                               Missatges.AlertMissatge("PRODUCTE ELIMINADO", "El producto ha sido eliminado correctamente", R.drawable.papelera, ProductoActivity.this);
                           }
                       } else {
                           Missatges.AlertMissatge("ERROR AL ELIMINAR", "El producto está en facturas sin pagar!", R.drawable.error2, ProductoActivity.this);
                       }

                       retornarProductes();
                       headerAdapterProducte.actualitzaRecycler(myDataset);
                   } else Missatges.AlertMissatge("ERROR", "Selecciona un producto!", R.drawable.error2, ProductoActivity.this);
               }
           }
        );

        // TODO HACER UN LIST DE PRODUCTOS, PERO SOLO MOSTRAT SEGUN EL PARAMETRO ENVIADO EN EL INTENT DE PEDIDOS
        // POR EJEMPLO SI MANDO TIPO 0, SOLO SE MOSTRARAN LOS PRODUCTOS DE CAFE/TE EN LA CONSULTA
        getIntents();

    }
    public void getIntents(){
        if (getIntent().hasExtra("TIPO_PRODUCTO")){
            tipoProducto = (getIntent().getExtras().getInt("TIPO_PRODUCTO"));
        }
        if (getIntent().hasExtra("INSERTAR_PRODUCTO")){
            insertarProducto= true;
            mToolbar.setVisibility(View.VISIBLE);
        }
        if (getIntent().hasExtra("ID_PRODUCTE")){
            insertarProducto= true;
            mToolbar.setVisibility(View.VISIBLE);
            id_producte = (getIntent().getExtras().getString("ID_PRODUCTE"));
            tipoProducto = PedidoActivity.tipoProducto;
        }

    }
    public void retornarProductes(){
        db.obre();
        Cursor cursor = db.RetornaProductes(Integer.toString(tipoProducto));
        myDataset = mouCursor(cursor);
        db.tanca();
    }
    public ArrayList mouCursor(Cursor cursor) {
        if (cursor.moveToFirst()) {
            myDataset.clear();
            do {
                myDataset.add(new HeaderProducte(
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Producte._ID)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Producte.NOM_PRODUCTE)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Producte.PREU_PRODUCTE))));
            } while (cursor.moveToNext());
        }
        return myDataset;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        insertarProducto = false;
        id_producte=null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        id_producte=null;
        retornarProductes();
        headerAdapterProducte.actualitzaRecycler(myDataset);
    }
}
