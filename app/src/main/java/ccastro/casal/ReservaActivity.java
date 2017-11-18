package ccastro.casal;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import ccastro.casal.RecyclerView.HeaderAdapterReserva;
import ccastro.casal.RecyclerView.HeaderReserva;
import ccastro.casal.SQLite.ContracteBD;
import ccastro.casal.SQLite.DBInterface;

public class ReservaActivity extends AppCompatActivity {
    TextView nombreMesaReserva;
    String idMesaReserva;
    DBInterface db;
    View v;
    private HeaderAdapterReserva headerAdapterReserva;
    private ArrayList<HeaderReserva> myDataset;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);
        db = new DBInterface(this);
        nombreMesaReserva = (TextView) findViewById(R.id.nombreMesaReserva);

        myDataset = new ArrayList<>();
        headerAdapterReserva = new HeaderAdapterReserva(myDataset);
        db = new DBInterface(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_consulta);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(headerAdapterReserva);

        cogerIntents();

    }
    public void cogerIntents(){
        if (getIntent().hasExtra("ID_MESA")){  // pasado desde HeaderAdapterMesa
            idMesaReserva = getIntent().getExtras().getString("ID_MESA");
        }
        if (getIntent().hasExtra("NOM_MESA")){
            nombreMesaReserva.setText(getIntent().getExtras().getString("NOM_MESA"));
        }
    }
    public ArrayList CursorBD(Cursor cursor) {
        if (cursor.moveToFirst()) {
            myDataset.clear();
            do {
                myDataset.add(new HeaderReserva(
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Client._ID)),
                        (cursor.getString(cursor.getColumnIndex(ContracteBD.Client.NOM_CLIENT))+" "+cursor.getString(cursor.getColumnIndex(ContracteBD.Client.COGNOMS_CLIENT))),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Client.TIPUS_CLIENT)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Reserva_Cliente.PAGADO)),
                        cursor.getString(cursor.getColumnIndex(ContracteBD.Reserva_Cliente.ASISTENCIA))));
            } while (cursor.moveToNext());
        }
        return myDataset;
    }

    @Override
    protected void onResume() {
        super.onResume();
        db.obre();
        Cursor cursor = db.RetornaClientsReservadosDataActualMesa(idMesaReserva);
        myDataset = CursorBD(cursor);
        db.tanca();
        headerAdapterReserva.actualitzaRecycler(myDataset);
    }
}
