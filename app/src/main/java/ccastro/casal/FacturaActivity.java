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
import java.util.ArrayList;
import java.util.Calendar;

import ccastro.casal.RecyclerView.HeaderAdapterFactura;
import ccastro.casal.RecyclerView.HeaderFactura;
import ccastro.casal.SQLite.ContracteBD;
import ccastro.casal.SQLite.DBInterface;

import static ccastro.casal.LoginActivity.NOM_USUARI;
import static ccastro.casal.R.id.ventaPagada;

public class FacturaActivity extends AppCompatActivity {
    DBInterface db;
    TextView dataVenta,horaVenta,nomClient,nomTreballador,estatVenta,preuTotalFactura;
    Button buttonPagar;
    String idVenta,fechaReserva, id_cliente; // id_cliente lo cogemos de la reserva.
    View v;
    Boolean actualizarReserva = false;
    String data;
    String pagar = "pagar", pagada= "pagada";
    boolean reembolsar = false;
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
                builder.setMessage("Verifica que quieras "+pagar+" la factura antes de aceptar")
                        .setTitle("Atencion!!")
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        Toast.makeText(FacturaActivity.this, "Factura "+pagada, Toast.LENGTH_LONG).show();

                                        buttonPagar.setVisibility(View.INVISIBLE);
                                        DBInterface db=new DBInterface(v.getContext());
                                        db.obre();
                                        if (reembolsar){
                                            db.ActalitzaEstatVenta(idVenta,"4");
                                            estatVenta.setText("Reembolsado");
                                        } else {
                                            db.ActalitzaEstatVenta(idVenta,"1");
                                            estatVenta.setText("Pagado");
                                        }
                                        db.tanca();

                                        // TODO ACTUALIZAR PAGO DE RESERVA, QUIZAS SE PUEDE QUITAR SI SE QUITA RESERVADA_PAGADA Y SE RELACIONA CON ID_VENTA
                                        db.obre();
                                        Log.d("prueba: "," actualizad");
                                      //  db.ActalitzarPagoReservaFecha(id_cliente,obtenerFechaReserva());
                                        db.ActalitzarPagoReservaFecha(id_cliente);
                                        db.tanca();
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
                if (!actualizarReserva) id_cliente = cursor.getString(cursor.getColumnIndex(ContracteBD.Venta.ID_CLIENT));

                preuProducteQuantitat = Float.parseFloat(cursor.getString(cursor.getColumnIndex(ContracteBD.Producte.PREU_PRODUCTE)))
                        * Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContracteBD.Factura.QUANTITAT_PRODUCTE)));
                preuTotal = preuTotal + preuProducteQuantitat;
                preuTotalFactura.setText(df.format(preuTotal)+"€");
                data = cursor.getString(cursor.getColumnIndex(ContracteBD.Venta.DATA_VENTA));
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
        else if (estado.equalsIgnoreCase("1")){
            return "Pagado";
        }
        else if (estado.equalsIgnoreCase("2")){
            return "Anulado";
        }
        else if (estado.equalsIgnoreCase("3")){
            buttonPagar.setText("Reembolsar Factura");
            pagar =  "reembolsar"; pagada = "reembolsada";
            reembolsar = true;
            return "Reembolsar";
        }
        else if (estado.equalsIgnoreCase("4")){
            return "Reembolsado";
        }
        return "";
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
    public String obtenerFechaReserva (){
        Calendar ahoraCal = Calendar.getInstance();
        // PARECE QUE EL MES EMPIEZA DESDE 0, HAY QUE SUMAR UNO.
        ahoraCal.getTime();
        return ahoraCal.get(Calendar.YEAR)+" "+(ahoraCal.get(Calendar.MONTH)+1)+
                " "+ahoraCal.get(Calendar.DATE);
    }
    public void cogerIntents(){
        if (getIntent().hasExtra("ID_CLIENT")){   // VIENE DE COMEDOR
            id_cliente = getIntent().getExtras().getString("ID_CLIENT");
            actualizarReserva=true;
            fechaReserva = obtenerFechaReserva();
            Log.d("Fecha", fechaReserva);
            if (getIntent().hasExtra("NOM_CLIENT_RESERVA")){
               nomClient.setText(getIntent().getExtras().getString("NOM_CLIENT_RESERVA"));
            }
            // AÑADIR PRIMERO MENU A PAGAR
            // MIRAR SI SE PUEDE HACER LO SIGUIENTE:
            // UNA VEZ ADJUDICAS RESERVADEMENU EN LA INTERFICE QUE HARE
            // ABRIR VENTA + FACTURA + SI TIENE COSAS PENDIENTES

            // BUSCAMOS EL ID_VENTA para RELACIONARLO CON LA FACTURA
            db.obre();
            Cursor cursorVentaFactura = db.EncontrarId_VentaFacturaSinPagar(id_cliente);
            Integer idVentaFactura = cursorIDVentaFactura(cursorVentaFactura);
            idVenta = Integer.toString(idVentaFactura);
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
                if (estatVenta.getText().toString().equalsIgnoreCase("Pagado") || estatVenta.getText().toString().equalsIgnoreCase("Anulado")
                        || estatVenta.getText().toString().equalsIgnoreCase("Reembolsado")){
                    buttonPagar.setVisibility(View.INVISIBLE);
                }
            }
            if (getIntent().hasExtra("HORA_VENTA")){
                horaVenta.setText(getIntent().getExtras().getString("HORA_VENTA"));
            }
            if (getIntent().hasExtra("ID_CLIENT_VENTA")){
                id_cliente = getIntent().getExtras().getString("ID_CLIENT_VENTA");
            }
            db.obre();
            Cursor cursor = db.RetornaFacturaId_Venta(idVenta);
            myDataset = CursorBD(cursor);
            db.tanca();
        }
    }
}
