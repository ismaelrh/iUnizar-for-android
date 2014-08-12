package com.ismaro3.iunizar;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HeaderCollection extends ArrayList<Header> {

	private static final String HEADERS_BASE_URL = "http://www.unizar.es/actualidad/boletin.php?f=";
    private static final String TYPE_ESTUDIANTES = "&l=estudiantesunizar";
	private static final String TYPE_PERSONAL = "&l=personalunizar";
	private static final String HEADERS_BASE_URL_DEBUG = "http://www.unizar.es/actualidad/resumen_ng.php?id=";
	private String date;
	private boolean valid;

	/**
	 * Qué contiene una página:
	 *
	 * Fecha del boletín Tipo del boletín ( PAS / ESTUDIANTES )
	 *
	 * CATEGORÍA (Subcategoría->opcional) Artículo
	 *
	 * FORMATO DE URL:
	 * http://www.unizar.es/actualidad/boletin.php?f=AAAA-MM-DD&l
	 * =estudiantesunizar
	 * http://www.unizar.es/actualidad/boletin.php?f=AAAA-MM-DD&l=personalunizar
	 *
	 *
	 * Como comprobar si hay correspondiente a esa fecha -> Sale página de no
	 * existe si metemos fecha incorrecta.
	 */

    public HeaderCollection(int id) {
        super();

        // Formamos la URL
        //String urlDate = "" + year + "-" + String.format("%02d", month) + "-"
        //+ day;
        //String url = HEADERS_BASE_URL + urlDate + TYPE_ESTUDIANTES;
        String url = HEADERS_BASE_URL_DEBUG + id;
        //System.out.println(url);
        try {

            Document doc = Jsoup.connect(url).get();
            Element fecha = doc.getElementsByClass("fecha").first();
            if(fecha!=null){
                valid = true;
                date = fecha.text().replaceAll("[a-z ]", "");
            }
            else{
                valid = false;
            }




            Elements elements = doc.body().select("*");
            String currentCategory = "None";
			/*Va recorriendo todos los elementos del HTML*/
            for (Element element : elements) {

                String clase = element.className();
                //Nueva categoría encontrada
                if(clase.equals("categoria")){
                    currentCategory = element.text();
                }
                //Nuevo articulo encontrado
                else if(clase.equals("noticia") && element.nodeName().equals("article")){

                    Element info = element.select("a").first();
                    String link = info.attr("href");
                    System.out.println(link);
                    String title = info.text();
                    Header temp = new Header(title, link,currentCategory);
                    this.add(temp);

                }


            }



        } catch (IOException ex) {

        }

    }

	public boolean isValid() {
		return valid;
	}

	public String getDate(){
		return date;
	}


}
