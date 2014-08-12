package com.ismaro3.iunizar;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Content {

	private String title;
    private String subtitle;
	private String content;



    private String url;


    private String category;
	private ArrayList<Link> links;
	
	public Content(String url) {
		super();
        this.url = url;
		try {
            String html = Jsoup.connect(url).get().html();
			Document doc = Jsoup.parse(html);
			Element noticia_content = doc.getElementsByClass("noticia-content").first();

			content = "";
            category = noticia_content.getElementsByClass("categoria").first().text();
			title = noticia_content.select("h1").text();
			subtitle = noticia_content.getElementsByClass("subtitulo").first().html();
			Element cuerpo = noticia_content.getElementsByClass("cuerpo").first();
            doc.getElementsByClass("compartir-rs").first().remove();
            content = cuerpo.html();
			links = new ArrayList<Link>();
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
        Log.w("myApp", "Objeto creado!");
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ArrayList<Link> getLinks() {
		return links;
	}
	public void setLinks(ArrayList<Link> links) {
		this.links = links;
	}
	
	/*Returns a Content object based on the link*/
	public static Content generateContent(String url){
		
		try {
			Document doc = Jsoup.connect(url).get();
			Element noticia_content = doc.getElementsByClass("noticia-content").first();
			
			String contenido;
			
			String title = noticia_content.select("h1").text();
			String subtitle = noticia_content.getElementsByClass("subtitulo").first().text();
			Elements cuerpo = noticia_content.getElementsByClass("cuerpo").select("*");
			for(Element element: cuerpo){
				if(element.tagName().equals("p")){
					contenido = element.text();
				}
			}
			return null;
		} catch (IOException e) {
			
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 
	 * Document doc = Jsoup.connect(url).get();
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
	 * 
	 */

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
