package com.antonio.amdroid.editortextos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends Activity {
    private Button bt,bt2;
    private EditText et;
    private String ruta="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et=(EditText)findViewById(R.id.editText);
        bt=(Button)findViewById(R.id.button);
        bt2=(Button)findViewById(R.id.button2);

        Intent intent = getIntent();
        if(intent!=null) {
            Uri data = intent.getData();
            ruta = data.getPath();
            File f = new File(ruta);
            try {
                FileInputStream fin = new FileInputStream(f);
                InputStreamReader isr = new InputStreamReader(fin);
                BufferedReader br = new BufferedReader(isr);
                String linea = br.readLine();
                String c = "";
                while (linea != null) {
                    c += linea + "\n";
                    linea = br.readLine();
                }
                br.close();
                isr.close();
                et.setText(c);

            } catch (IOException e) {
                tostada(e.toString());
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
   public void guardar(View v)  {
        String texto=et.getText().toString();
        File f = new File(ruta);

        try {
            FileOutputStream fw = new FileOutputStream(f);
            OutputStreamWriter osw = new OutputStreamWriter(fw);
            osw.write(texto);
            osw.flush();
            osw.close();
            tostada(" texto guardado ");

        } catch (IOException e1) {
            e1.printStackTrace();
        }



   }
   public void salir( View v){
       AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MainActivity.this);
       dialogo1.setTitle("Salir...");
       dialogo1.setMessage("¿ Desea guardar los cambios realizados antes de salir ?");
       dialogo1.setCancelable(false);

       dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialogo1, int id) {
               String texto=et.getText().toString();
               File f = new File(ruta);

               try {
                   FileOutputStream fw = new FileOutputStream(f);
                   OutputStreamWriter osw = new OutputStreamWriter(fw);
                   osw.write(texto);
                   osw.flush();
                   osw.close();
                   tostada("guardado");

               } catch (IOException e1) {
                   e1.printStackTrace();
               }
               tostada("guardado");
               finish();
           }
       });
       dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialogo1, int id) {
               finish();
           }
       });
       dialogo1.show();
    }
    public Toast tostada(String t) {
        Toast toast =
                Toast.makeText(getApplicationContext(),
                        t + "", Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }
}
