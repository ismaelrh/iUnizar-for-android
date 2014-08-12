package com.ismaro3.iunizar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;


public class Main extends Activity {
    private ListView lista;
    private TextView date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        lista = (ListView) findViewById(R.id.header_list);
        date = (TextView) findViewById(R.id.date);
        //Bajamos y poblamos la lista de titulares DEBUG
        new RetrieveHeaderCollection().execute(5000);

        //Al clickar en objeto de lista, se baja su noticia y se lanza actividad
        lista.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long rowid) {
               new RetrieveContent().execute(((Header)parent.getItemAtPosition(position)).getLink());

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Se encarga de bajar los titulares de un ID concreto, y puebla la lista
     * */
    private class RetrieveHeaderCollection extends AsyncTask<Integer, Void, HeaderCollection> {

        private Exception exception;

        protected HeaderCollection doInBackground(Integer... ids) {
            try {
                Integer id = ids[0];

                return new HeaderCollection(id);
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        protected void onPostExecute(HeaderCollection feed) {
            // TODO: check this.exception
            // TODO: do something with the feed


            final HeaderAdapter adapter = new HeaderAdapter(getApplicationContext(),0, feed);
            date.setText("iUnizar - " + feed.getDate());
            lista.setAdapter(adapter);
        }
    }

    /**
     * Se encarga de bajar la noticia de una URL concreta, y lanzar la actividad correspondiente.
     * */
    private class RetrieveContent extends AsyncTask<String, Void,Content> {

        private Exception exception;
       private  ProgressDialog  progressDialog;
        protected Content doInBackground(String... urls) {
            try {
               String url = urls[0];

                return new Content(url);
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        protected void onPreExecute(){
            super.onPreExecute();
        progressDialog = ProgressDialog.show(Main.this, "Wait", "Downloading...");
        }

        protected void onPostExecute(Content feed) {
            progressDialog.dismiss();
            // TODO: check this.exception
            // TODO: do something with the feed

            //Tenemos que coger el contenido
            if(feed!=null) {


                Intent intent = new Intent(
                        getApplicationContext(),
                        ShowContent.class
                );
                //Convertimos el objeto a JSON para pasarlo en la actividad
                intent.putExtra("contentObject", new Gson().toJson(feed));
                startActivity(intent);
            }
        }
    }
}
