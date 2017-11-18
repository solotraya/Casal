package ccastro.casal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ccastro.casal.RecyclerView.HeaderAdapterFactura;
import ccastro.casal.RecyclerView.HeaderFactura;
import ccastro.casal.SQLite.ContracteBD;
import ccastro.casal.SQLite.DBInterface;

import static ccastro.casal.LoginActivity.ID_TREBALLADOR;
import static ccastro.casal.LoginActivity.NOM_USUARI;
import static ccastro.casal.R.id.ventaPagada;

public class FacturaActivity extends AppCompatActivity {
    DBInterface db;
    TextView dataVenta,horaVenta,nomClient,nomTreballador,estatVenta,preuTotalFactura;
    Button buttonPagar;
    String idVenta,fechaReserva, id_cliente; // id_cliente lo cogemos de la reserva.
    View v;
    Boolean actualizarReserva = false;
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
        nomTreballador = (TextView) findViewById(R.id.nomTreballadorF);
        estatVenta = (TextView) findViewById(ventaPagada);
        preuTotalFactura = (TextView) findViewById(R.id.precioTotalFactura);
        buttonPagar = (Button) findViewById(R.id.buttonPagar);
        buttonPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v=view;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Verifica que quieras pagar la factura antes de aceptar")
                        .setTitle("Atenció!!")
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Acceptar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        Toast.makeText(FacturaActivity.this, "Factura Pagada", Toast.LENGTH_LONG).show();
                                        estatVenta.setText("Pagado");
                                        buttonPagar.setVisibility(View.INVISIBLE);
                                        DBInterface db=new DBInterface(v.getContext());
                                        db.obre();
                                        db.ActalitzaEstatVenta(idVenta);
                                        db.tanca();
                                        if (actualizarReserva){
                                            db.obre();
                                            db.ActalitzarPagoReservaFecha(id_cliente,fechaReserva);
                                            db.tanca();
                                        }
                                    }
                                }
                        );
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        myDataset = new ArrayList<>();
        headerAdapterFactura= new HeaderAdapterFactura(myDataset);
        db = new DBInterface(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_consulta);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(headerAdapterFactura);
        cogerIntents();

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
                String data = cursor.getString(cursor.getColumnIndex(ContracteBD.Venta.DATA_VENTA));
                String dataCorrecta[] = data.split(" ");
                String dataFormatSpain = dataCorrecta[2]+"/"+dataCorrecta[1]+"/"+dataCorrecta[0];
                dataVenta.setText(dataFormatSpain);
                horaVenta.setText(cursor.getString(cursor.getColumnIndex(ContracteBD.Venta.HORA_VENTA)));
                estatVenta.setText(verificarEstadoFactura(cursor.getString(cursor.getColumnIndex(ContracteBD.Venta.VENTA_COBRADA))));
                nomTreballador.setText(NOM_USUARI);

            } while(cursor.moveToNext());
        }
        return myDataset;
    }
    public String verificarEstadoFactura (String estado){
        if (estado.equalsIgnoreCase("0")){
            return "Falta pagar";
        }
        else return "Pagado";
    }
    public  Integer cursorIDVentaFactura(Cursor cursor){
        Integer idVenta=-1;
        if(cursor.moveToFirst()){
            do {
                idVenta=Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContracteBD.Venta._ID)));
            } while(cursor.moveToNext());
        }
        return idVenta;
    }
    public void cogerIntents(){
        if (getIntent().hasExtra("ID_CLIENT")){   // VIENE DE COMEDOR
            id_cliente = getIntent().getExtras().getString("ID_CLIENT");
            actualizarReserva=true;
            Calendar ahoraCal = Calendar.getInstance();
            // PARECE QUE EL MES EMPIEZA DESDE 0, HAY QUE SUMAR UNO.
            ahoraCal.getTime();
            fechaReserva = ahoraCal.get(Calendar.YEAR)+" "+(ahoraCal.get(Calendar.MONTH)+1)+
                    " "+ahoraCal.get(Calendar.DATE);
            Log.d("Fecha", fechaReserva);
            if (getIntent().hasExtra("NOM_CLIENT_RESERVA")){
               nomClient.setText(getIntent().getExtras().getString("NOM_CLIENT_RESERVA"));
            }
            // AÑADIR PRIMERO MENU A PAGAR
            db.obre();
            Cursor cursorVentaFactura = db.EncontrarId_VentaFactura(id_cliente);
            Integer idVentaFactura = cursorIDVentaFactura(cursorVentaFactura);
            idVenta = Integer.toString(idVentaFactura);
            Log.d("IDVENTA: ", Integer.toString(idVentaFactura));
            if (idVentaFactura==-1){


                Date ahora = new Date();
                SimpleDateFormat formateador = new SimpleDateFormat("hh:mm");
                String hora = formateador.format(ahora);
                                                                    //       *** CAMBIAR POR FEHCA Y HORA ACTUAL ***
                db.InserirVenta(Integer.parseInt(id_cliente),Integer.parseInt(ID_TREBALLADOR),fechaReserva,"0",hora);
                cursorVentaFactura = db.EncontrarId_VentaFactura(id_cliente);
                idVentaFactura = cursorIDVentaFactura(cursorVentaFactura);
                idVenta = Integer.toString(idVentaFactura);
            }
            db.InserirFactura(1,idVentaFactura,1);
            db.tanca();
            db.obre();
            Cursor cursor = db.RetornaFacturaIdCliente(id_cliente);
            myDataset = CursorBD(cursor);
            db.tanca();


        } else {  // VIENE DE LISTADO DE VENTAS
            if (getIntent().hasExtra("ID_VENTA")){  // pasado desde HeaderAdapterVenta
                idVenta = getIntent().getExtras().getString("ID_VENTA");
            }
            if (getIntent().hasExtra("DATA_VENTA")){
                dataVenta.setText(getIntent().getExtras().getString("DATA_VENTA"));
            }
            if (getIntent().hasExtra("NOM_CLIENT")){
                nomClient.setText(getIntent().getExtras().getString("NOM_CLIENT"));
            }
            if (getIntent().hasExtra("NOM_TREBALLADOR")){
                nomTreballador.setText(getIntent().getExtras().getString("NOM_TREBALLADOR"));
            }
            if (getIntent().hasExtra("ESTAT_VENTA")){
                estatVenta.setText(getIntent().getExtras().getString("ESTAT_VENTA"));
                Log.d("ESTADO: ",estatVenta.getText().toString());
                if (estatVenta.getText().toString().equalsIgnoreCase("Pagado")){
                    buttonPagar.setVisibility(View.INVISIBLE);
                }
            }
            if (getIntent().hasExtra("HORA_VENTA")){
                horaVenta.setText(getIntent().getExtras().getString("HORA_VENTA"));
            }
            db.obre();
            Cursor cursor = db.RetornaFacturaId_Venta(idVenta);
            myDataset = CursorBD(cursor);
            db.tanca();
        }
    }
}
