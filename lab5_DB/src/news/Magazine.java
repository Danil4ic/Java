package news;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.map.ObjectMapper;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import news.Article;

public class Magazine{
	private int magazineID;
	private String title;
	private int pages;
    private LocalDate publishDate;
    TreeSet<Article> articles=new TreeSet<>(new Comparator<Article>(){
    	public int compare(Article obj1,Article obj2) {
		return obj1.getTitle().compareTo(obj2.getTitle());
    	}
    });
    
/////////////////////////////
 public Magazine() {
	this.title=" ";
	this.pages=0;
	this.publishDate = LocalDate.of(1990, 01, 01);
	}
    
 public Magazine(String name,TreeSet<Article> articles,int pages,LocalDate publishDate) {
		this.title=name;
		this.articles=articles;
		this.pages=this.countPagesFromArticles();
		this.publishDate = publishDate;
	}
 
 	/////////////////////////////
	
	//getters
	public String getTitle() {
	return title;
	}
	
	public int getPages() {
	return pages;
	}
	
	public LocalDate getPublishDate() {
	return publishDate;
	}
	
	public Set<Article> getArticles() {
	return articles;
	}
	/////////////////////////////
	
	//setters
	public void setTitle(String title) {
	this.title = title;
	}
	
	public void setPages(int pages) {
	this.pages = pages;
	}
	
	
	public void setPublishDate(LocalDate publishDate) {
	this.publishDate = publishDate;
	}

	/////////////////////////////
	//methods
	
	public boolean findArticle(String name) {
		for(Article articles : this.articles) {
			if(articles.getTitle()==name)
				return true;
		}
		return false;
	}
	
	public boolean findWriterInArticle(String name) {
		for(Article articles : this.articles) {
			for(String i: articles.writers)
				if(i==name)
					return true;
		}
		return false;
	}
	
	
	
	public int countArticle() {
		int count=0;
		for(Article articles : this.articles) 
			count++;
		return count;
	}
	

	public int countPagesFromArticles() {
		int pagesCounter=0;
		for(Article articles : this.articles) 
			pagesCounter+=articles.getPages();
		return pagesCounter;
	}
	

	
	public void AddArticle(String name,Set<String> writers,LocalDate publishDate,int pages){
		articles.add(new Article(name,writers,publishDate,pages));
		this.pages+=pages;
	} 
	
	//streams
	public boolean findArticleStream(String title) {
		
		return  (articles.stream().filter(e->e.getTitle().compareTo(title)==0).count()!=0);
		
	}
	
	public long countWritersStream(String name) {
		long count=0;
		for(Article article : this.articles) {
			count=article.getWriters().stream().filter(e->e.compareTo(name)==0).count();
		}
		return count;
	}
	
	
	@Override
	public String toString() {
		String s = "Magazine name: " + title +  ", Pages: " + pages + ", publishDate: " + publishDate +  "\n" + "Articles: ";
		for(Article i: articles)
			s+=i + " ";
		return s;
	}
	
	public void setMagazineID(int magazineID) {
		this.magazineID = magazineID;
	}
	
	public int getMagazineID() {
		return magazineID;
	}
	
	public void addArticle(Article article) {
		articles.add(article);
	}

	public static void main(String[] args) {
		//parseXML();
		//ReadJSONFile();
		Set<String> Writers = new TreeSet<>();
		Writers.add("petro");
		Writers.add("vasyl");
		Article a = new Article("football",Writers,LocalDate.of(2017, 10, 10), 5);
		TreeSet<Article> Article1 = new TreeSet<>();
		Article1.add(a);
		Article1.add(a);
		Magazine m = new Magazine("sport",Article1,100,LocalDate.of(2017, 10, 11));
		//m.createJSONFile();
		//m.writeXML();
		//System.out.println(m.toString());*/
	}
}
