package ccastro.casal;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ccastro.casal.SQLite.ContracteBD.Client;
import ccastro.casal.SQLite.DBInterface;
public class ClientActivity extends AppCompatActivity {
    DBInterface db;
    ListView listView ;
    ArrayList<String> clients = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        db = new DBInterface(this);
        listView = (ListView) findViewById(R.id.listView);

        clients= new ArrayList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,
                android.R.layout.simple_list_item_1, android.R.id.text1, clients);
        // Assign adapter to ListView
        listView.setAdapter(adapter);

        retornaClients();



    }
    public ArrayList<String> CursorBD(Cursor cursor){
        if(cursor.moveToFirst()){
            do {
                System.out.println( cursor.getString(cursor.getColumnIndex(Client.NOM_CLIENT)));


            }while(cursor.moveToNext());
        }
        return clients;
    }
    public void retornaClients(){
        db.obre();
        Cursor cursor= db.RetornaTotsElsClients();
        if (cursor.moveToFirst()) {
            do {
                clients.add(cursor.getString(cursor.getColumnIndex(Client._ID))+" "+cursor.getString(cursor.getColumnIndex(Client.NOM_CLIENT))
                        +" "+cursor.getString(cursor.getColumnIndex(Client.COGNOMS_CLIENT))+" "+cursor.getString(cursor.getColumnIndex(Client.TIPUS_CLIENT)));
            } while (cursor.moveToNext());
        }
        db.tanca();
        //
    }
}