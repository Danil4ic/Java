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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import news.Article;

public class Magazine{
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
	
	private void addNewMagazine(Document document) throws TransformerFactoryConfigurationError, DOMException {
	        Node root = document.getDocumentElement();
	        Element magazine = document.createElement("magazine");
	        
	        Element title = document.createElement("title");
	        title.setTextContent(this.getTitle());

	        Element page = document.createElement("pages");
	        String tempforpages = this.getPages() + "";
	        page.setTextContent(tempforpages);
	        
	        Element date = document.createElement("date");
	        String tempfordate = this.getPublishDate() + "";
	        date.setTextContent(tempfordate);

	        magazine.appendChild(title);
	        magazine.appendChild(page);
	        magazine.appendChild(date);
	        
	        for(Article i: articles)
	        {
	        	Element articles = document.createElement("article");
	        	
	        	Element atitle = document.createElement("title");
	 	        atitle.setTextContent(i.getTitle());
	 	        articles.appendChild(atitle);
	 	        
	 	       Element apages = document.createElement("pages");
	 	       String tempforapages = i.getPages() + "";
	 	       apages.setTextContent(tempforapages);
	 	       articles.appendChild(apages);
	 	       
	 	       Element adate = document.createElement("date");
	 	       String tempforadate= i.getwritingDate() + "";
	 	       adate.setTextContent(tempforadate);
	 	       articles.appendChild(adate);
	 	      
	 	       for(String j: i.writers)
		 	   {
	 	    	  
	 	    	   
	 	    	   Element writer = document.createElement("writer");
		 	       String tempforauthor = j + "";
		 	       System.out.println(tempforauthor);
		 	       writer.setTextContent(tempforauthor);
		 	       articles.appendChild(writer);
	 	       }
	 	      
	        	magazine.appendChild(articles);
	        }
	        root.appendChild(magazine);
	        writeDocument(document);
	    }
	 
	    private static void writeDocument(Document document) throws TransformerFactoryConfigurationError {
	        try {
	            Transformer tr = TransformerFactory.newInstance().newTransformer();
	            DOMSource source = new DOMSource(document);
	            FileOutputStream fos = new FileOutputStream("output.xml");
	            StreamResult result = new StreamResult(fos);
	            tr.transform(source, result);
	        } catch (TransformerException | IOException e) {
	            e.printStackTrace(System.out);
	        }
	    }
	    
	void writeXML()
	{
		try {
           DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
           Document document = documentBuilder.parse("output.xml");
           addNewMagazine(document);

       } catch (ParserConfigurationException ex) {
           ex.printStackTrace(System.out);
       } catch (SAXException ex) {
           ex.printStackTrace(System.out);
       } catch (IOException ex) {
           ex.printStackTrace(System.out);
       }
   }
	
	public void findSetters(String field,String value)
	{
		switch(field) {
		case "title":
			System.out.println(field + "," + value);
			this.setTitle(value);
			break;
		case "pages":
			System.out.println(field + "," + value);
			int pages = Integer.valueOf(value);
			this.setPages(pages);
			break;
		}
	}
	
	
	static void parseXML()
	{
		try {
			
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse("output.xml");
 
            Node root = document.getDocumentElement();
            
            NodeList news = root.getChildNodes();
            for (int i = 0; i < news.getLength(); i++) {
                Node magazine = news.item(i);
              // Magazine[] a = new Magazine[news.getLength()];
                
                if (magazine.getNodeType() != Node.TEXT_NODE) {
                    NodeList magazineProps = magazine.getChildNodes();
                    for(int j = 0; j < magazineProps.getLength(); j++) {
                        Node magazineProp = magazineProps.item(j);

                        if (magazineProp.getNodeType() != Node.TEXT_NODE) {
                        	if(magazineProp.getNodeName() != "article") {
                        	String NodeName=magazineProp.getNodeName();
                        	String NodeChild=magazineProp.getChildNodes().item(0).getTextContent();
                        	//a[i].findSetters(NodeName,NodeChild); 
                            System.out.println(NodeName + ":" + NodeChild);
                            }
                        	else {

                        		 NodeList articleProps = magazineProp.getChildNodes();
                        		 for(int k = 0; k < articleProps.getLength(); k++) {
                                      Node articleProp = articleProps.item(k);
                                      if (articleProp.getNodeType() != Node.TEXT_NODE) {
                                      	String NodeName=articleProp.getNodeName();
                                      	String NodeChild=articleProp.getChildNodes().item(0).getTextContent();
                                      	//a[i].findSetters(NodeName,NodeChild); 
                                          System.out.println(NodeName + ":" + NodeChild);
                                          }
                                      	
                           }
                        }
                    }
                  }
               }
            }


        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
	}

	@SuppressWarnings("unchecked")
	public void createJSONFile(){
		JSONObject obj = new JSONObject();
		obj.put("title", this.getTitle());
		obj.put("pages", this.getPages());
		obj.put("date", this.getPublishDate());
		
		for(Article i: this.articles)
		{
			obj.put("article name",i.getTitle());
			obj.put("article pages",i.getPages());
			obj.put("article date",i.getwritingDate());
			JSONArray writers = new JSONArray();
			for(String j: i.writers)
				writers.add(j);
			obj.put("writers", writers);
			
		}
		

		
		try(FileWriter file = new FileWriter("myjson.json"))
		{
			file.write(obj.toString());
			file.flush();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		System.out.println(obj);
	}
	
	
	
	static public void ReadJSONFile() {
		JSONParser parser = new JSONParser();
		
		try
		{
			Object obj = parser.parse(new FileReader("myjson.json"));
			JSONObject jsonObject = (JSONObject) obj;
			String name = (String) jsonObject.get("name");
			System.out.println("Name is: " + name);
		}
		catch(FileNotFoundException e) { e.printStackTrace();}
		catch(IOException e) {e.printStackTrace();}
		catch(ParseException e) {e.printStackTrace();}
		catch(Exception e) {e.printStackTrace();}
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
		m.writeXML();
		//System.out.println(m.toString());*/
	}
}
