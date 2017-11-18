package ccastro.casal;

import android.Manifest;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import ccastro.casal.FingerPrint.FingerprintHandler;
import ccastro.casal.SQLite.ContracteBD;
import ccastro.casal.SQLite.DBInterface;

public class LoginActivity extends AppCompatActivity {
    DBInterface db;
    Button buttonEntrar, buttonExemples;
    EditText textUserName, textPassword;
    View v;
    Cursor cursor;
    String huella = null;
    public static String ID_TREBALLADOR=null, NOM_USUARI="Administrador";
    private KeyStore keyStore;
    private static final String KEY_NAME = "EDMTDev";
    private Cipher cipher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DBInterface(this);
        db.obre();
        // TRABAJADOR admin PARA CUANDO ELIMINE INTRODUCCION DE EJEMPLOS
        db.InserirTreballador("Administrador"," ","admin","xxx");

       // ************* HAY QUE INSERTAR MENU DIARIO en PRODUCTO ID1 y QUE NO SE PUEDA ELIMINAR, SI MODIFICAR *************
        db.InserirProducte("Menú Diario","5.50","Menu");
        db.tanca();
        cargarPreferencias();
        buttonEntrar = (Button) findViewById(R.id.buttonEntrar);
        buttonExemples = (Button) findViewById(R.id.buttonExemples);
        textPassword = (EditText) findViewById(R.id.textPassword);
        textUserName = (EditText) findViewById(R.id.textUserName);
        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = textUserName.getText().toString();
                String password = textPassword.getText().toString();
                db = new DBInterface(getApplicationContext());
                db.obre();
                cursor = db.verificarLogin(userName,password);
                if ((cursor != null) && (cursor.getCount() > 0)){
                    v=view;
                    mouCursor(cursor); // Recogemos el id_usuario y nombre trabajador

                    if (huella == null && ! textUserName.getText().toString().equalsIgnoreCase("admin")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setMessage("Quieres relacionar tu contraseña con tu huella dactilar?")
                                .setTitle("Atención!!")
                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        huella = "NO";
                                        guardarPreferencias();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        dialog.cancel();
                                    }
                                })
                                .setPositiveButton("Acceptar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                                huella = "SI";
                                                Toast.makeText(v.getContext(),"Introduce huella digital", Toast.LENGTH_SHORT).show();
                                                guardarPreferencias();
                                                activarFingerPrint();
                                            }
                                        }
                                );


                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                } else {
                    Toast.makeText(view.getContext(),"Login Incorrecto!", Toast.LENGTH_SHORT).show();
                }
                db.tanca();

            }
        });
        buttonExemples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "S'han esborrat les dades de la base de dades i s'han tornat a crear.", Toast.LENGTH_SHORT).show();
                CrearExemplesBD();
            }
        });
    }
    public void guardarPreferencias(){
        SharedPreferences.Editor editor = getSharedPreferences("HUELLA_CONFIG", MODE_PRIVATE).edit();
        editor.putString("HUELLA",huella);
        editor.putString("ID_TRABAJADOR",ID_TREBALLADOR);
        editor.putString("NOM_USUARI",NOM_USUARI);
        editor.apply();
    }
    public void cargarPreferencias(){
        SharedPreferences prefs = getSharedPreferences("HUELLA_CONFIG", MODE_PRIVATE);
        huella = prefs.getString("HUELLA", null);
        if (huella != null) {
            if (huella.equalsIgnoreCase("SI")){
                NOM_USUARI = prefs.getString("NOM_USUARI", "");//"No name defined" is the default value.
                ID_TREBALLADOR = prefs.getString("ID_TRABAJADOR", ""); //0 is the default value.
                activarFingerPrint();
            }

        }
    }
    public void mouCursor(Cursor cursor) {
        if (cursor.moveToFirst()) {
            do {
                ID_TREBALLADOR = cursor.getString(cursor.getColumnIndex(ContracteBD.Treballador._ID));
                NOM_USUARI = cursor.getString(cursor.getColumnIndex(ContracteBD.Treballador.NOM_TREBALLADOR))+" "+
                cursor.getString(cursor.getColumnIndex(ContracteBD.Treballador.COGNOMS_TREBALLADOR));
            } while (cursor.moveToNext());
        }
    }
    public void CrearExemplesBD(){
        db.obre();
        db.Esborra();


        // nom - cognoms - tipusClient - mesaFavorita


        // nom, cognoms, username, password
        db.InserirTreballador("Diego","Castro Hurtado","diego","1986");
        db.InserirTreballador("Maria","Cañabate Méndez","maria","1986");

        db.InserirMesa("Mesa 1");db.InserirMesa("Mesa 2");db.InserirMesa("Mesa 3");db.InserirMesa("Mesa 4");

        db.InserirClient("Manuela","Torres Cobijo","Comedor",2);
        db.InserirClient("Manel","Garcia","Comedor",1);
        db.InserirClient("Remedios","Luque","Ayuntamiento",2);
        db.InserirClient("Juan","Gomez Fuentes","Llevar",3);

        // nomProducte, preu, tipus
        db.InserirProducte("Menú Diario","5.50","Menu");
        db.InserirProducte("Café Solo","1.10","Café");
        db.InserirProducte("Café con leche","1.20","Café");
        db.InserirProducte("Carajillo","1.50","Café");
        db.InserirProducte("Cocacola","1.30","Bebidas");
        db.InserirProducte("Agua","0.90","Bebidas");
        db.InserirProducte("Bocadillo grande","2.50","Bocadillos");
        db.InserirProducte("Bocadillo pequeño","2","Bocadillos");



        //id_client,id_treballador,dataVenta,Cobrada,TotalVenta
        db.InserirVenta(1,1,"2017 11 18","0","10:15");  // SIN ID_FACTURA!!
        db.InserirVenta(2,2,"2017 11 18","1","11:00");

                 //id_producte,id_venta/quantitatProducte
        db.InserirFactura(2,1,1);db.InserirFactura(3,1,2);db.InserirFactura(5,1,4);
        db.InserirFactura(1,2,2);db.InserirFactura(4,2,1);db.InserirFactura(6,2,3);


        //dia, assistencia(0 SI por defecto, 1 NO), pagado (0 NO, 1 SI),idCliente, idMesa

      //  db.InserirReserva_Cliente("2017 11 18","0","1",2,2);
      //   db.InserirReserva_Cliente("2017 11 18","0","0",4,2);
      //   db.InserirReserva_Cliente("2017 11 18","0","0",1,2); db.InserirReserva_Cliente("2017 11 19","0","0",2,2);
      //  db.InserirReserva_Cliente("2017 11 18","0","0",3,2);
        db.tanca();
    }


    public void activarFingerPrint() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        if (!fingerprintManager.isHardwareDetected())
            Toast.makeText(this, "Fingerprint authentication permission not enable", Toast.LENGTH_SHORT).show();
        else {
            if (!fingerprintManager.hasEnrolledFingerprints())
                Toast.makeText(this, "Register at least one fingerprint in Settings", Toast.LENGTH_SHORT).show();
            else {
                if (!keyguardManager.isKeyguardSecure())
                    Toast.makeText(this, "Lock screen security not enabled in Settings", Toast.LENGTH_SHORT).show();
                else
                    genKey();

                if (cipherInit()) {
                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    FingerprintHandler helper = new FingerprintHandler(this);
                    helper.startAuthentication(fingerprintManager, cryptoObject);

                }
            }
        }
    }




    private boolean cipherInit() {

        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES+"/"+ KeyProperties.BLOCK_MODE_CBC+"/"+ KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey)keyStore.getKey(KEY_NAME,null);
            cipher.init(Cipher.ENCRYPT_MODE,key);
            return true;
        } catch (IOException e1) {

            e1.printStackTrace();
            return false;
        } catch (NoSuchAlgorithmException e1) {

            e1.printStackTrace();
            return false;
        } catch (CertificateException e1) {

            e1.printStackTrace();
            return false;
        } catch (UnrecoverableKeyException e1) {

            e1.printStackTrace();
            return false;
        } catch (KeyStoreException e1) {

            e1.printStackTrace();
            return false;
        } catch (InvalidKeyException e1) {

            e1.printStackTrace();
            return false;
        }

    }

    private void genKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        KeyGenerator keyGenerator = null;

        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,"AndroidKeyStore");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).build()
            );
            keyGenerator.generateKey();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        catch (InvalidAlgorithmParameterException e)
        {
            e.printStackTrace();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (huella!=null)activarFingerPrint();
    }
}