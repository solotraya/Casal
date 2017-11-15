package ccastro.casal;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ccastro.casal.RecyclerView.HeaderAdapterFactura;
import ccastro.casal.RecyclerView.HeaderFactura;
import ccastro.casal.SQLite.ContracteBD;
import ccastro.casal.SQLite.DBInterface;

public class FacturaActivity extends AppCompatActivity {
    DBInterface db;
    TextView dataVenta,horaVenta,nomClient,estatVenta,preuTotalFactura;
    String idVenta;
    private HeaderAdapterFactura headerAdapterFactura;
    private ArrayList<HeaderFactura> myDataset;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);
        db = new DBInterface(this);
        dataVenta = (TextView) findViewById(R.id.dataVenta);
        horaVenta = (TextView) findViewById(R.id.horaVenta);
        nomClient = (TextView) findViewById(R.id.nomClient);
        estatVenta = (TextView) findViewById(R.id.ventaPagada);
        preuTotalFactura = (TextView) findViewById(R.id.precioTotalFactura);
        cogerIntents();
        myDataset = new ArrayList<>();
        headerAdapterFactura= new HeaderAdapterFactura(myDataset);
        db = new DBInterface(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_consulta);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(headerAdapterFactura);

        db.obre();
        Cursor cursor = db.RetornaFacturaId_Venta(idVenta);
        myDataset = CursorBD(cursor);
        db.tanca();
    }

    public  ArrayList CursorBD(Cursor cursor){
        float preuProducteQuantitat=0;
        float preuTotal=0;
        ArrayList <String> factura = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.00");
        df.setMaximumFractionDigits(2);
        if(cursor.moveToFirst()){
            factura.clear();
            do {
                myDataset.add(new HeaderFactura(
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Producte.NOM_PRODUCTE)),
                                cursor.getString(cursor.getColumnIndex(ContracteBD.Producte.PREU_PRODUCTE)),
                                cursor.getString(cursor.getColumnIndex(ContracteBD.Producte.TIPUS_PRODUCTE)),
                                cursor.getString(cursor.getColumnIndex(ContracteBD.Factura.QUANTITAT_PRODUCTE)),
                                df.format(Float.parseFloat(cursor.getString(cursor.getColumnIndex(ContracteBD.Producte.PREU_PRODUCTE)))
                                * Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContracteBD.Factura.QUANTITAT_PRODUCTE))))
                        ));
                preuProducteQuantitat = Float.parseFloat(cursor.getString(cursor.getColumnIndex(ContracteBD.Producte.PREU_PRODUCTE)))
                        * Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContracteBD.Factura.QUANTITAT_PRODUCTE)));
                preuTotal = preuTotal + preuProducteQuantitat;
                preuTotalFactura.setText(df.format(preuTotal)+"€");
            } while(cursor.moveToNext());
        }
        return myDataset;
    }

    public String ventaPagada(String ventaPagada){
        if (ventaPagada.equalsIgnoreCase("0")) return "Falta Pagar";
        else return "Pagado";
    }

    public void cogerIntents(){
        if (getIntent().hasExtra("ID_VENTA")){  // pasado desde HeaderAdapterVenta
            idVenta = getIntent().getExtras().getString("ID_VENTA");
        }
        if (getIntent().hasExtra("DATA_VENTA")){
            dataVenta.setText(getIntent().getExtras().getString("DATA_VENTA"));
        }
        if (getIntent().hasExtra("NOM_CLIENT")){
            nomClient.setText(getIntent().getExtras().getString("NOM_CLIENT"));
        }
        if (getIntent().hasExtra("DATA_VENTA")){
            estatVenta.setText(getIntent().getExtras().getString("ESTAT_VENTA"));
        }
        if (getIntent().hasExtra("HORA_VENTA")){
            horaVenta.setText(getIntent().getExtras().getString("HORA_VENTA"));
        }
    }
}
