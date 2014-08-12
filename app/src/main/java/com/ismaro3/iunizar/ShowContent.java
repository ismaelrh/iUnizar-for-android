package com.ismaro3.iunizar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ismaro3.iunizar.R;

public class ShowContent extends Activity {

    private TextView title_lbl;
    private TextView content_lbl;
    private TextView category_lbl;
    private TextView subtitle_lbl;
    private ImageView image;
    private Content content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_content);
        Bundle extras = getIntent().getExtras();

        title_lbl = (TextView) findViewById(R.id.content_title);
        content_lbl =  (TextView) findViewById(R.id.content_body);
        category_lbl =  (TextView) findViewById(R.id.content_category);
        subtitle_lbl = (TextView) findViewById(R.id.content_subtitle);
        image = (ImageView) findViewById(R.id.content_image);

        //Para poder pinchar en el TextView
        content_lbl.setMovementMethod(LinkMovementMethod.getInstance());


        if (extras != null) {
            String jsonMyObject = "";
            jsonMyObject = extras.getString("contentObject");

            content = new Gson().fromJson(jsonMyObject, Content.class);

            //Sacamos la primera letra de la categoría
            String upper = content.getCategory().toUpperCase();
            String first = upper.substring(0, 3);
            int sumaColor = (upper.charAt(0)+upper.charAt(1)+upper.charAt(2))/3;

            //Rellenamos contenidos
            title_lbl.setText(content.getTitle());
            subtitle_lbl.setText(Html.fromHtml(content.getSubtitle()));
            content_lbl.setText(Html.fromHtml(content.getContent()));
            category_lbl.setText(content.getCategory());
            image.setImageDrawable(new CharacterDrawable(first, 0xFF805781 + sumaColor*9999));

            //Cambiar el color de la actionbar TODO: REVISAR SOPORTE OTRAS VERSIONES
            // ¿SEGURO QUE QUIERO ESO?
            ActionBar bar = getActionBar();
            bar.setBackgroundDrawable(new ColorDrawable(0xFF805781 + sumaColor*9999));
        }
        else{
            Log.w("error","Content object was not passed throught intent");
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_web) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(content.getUrl()));
            startActivity(browserIntent);

        }
        return super.onOptionsItemSelected(item);
    }
}
