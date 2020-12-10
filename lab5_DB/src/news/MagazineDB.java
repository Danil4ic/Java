package news;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Properties;
import java.util.TreeSet;

import com.mysql.*;

import news.Article;
import news.Magazine;

public class MagazineDB {
	private final static String USERNAME = "root";
	private final static String PASSWORD = "123456987";
	private final static String URL = "jdbc:mysql://localhost:3306/news";

	
	public static Connection getNewConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Properties properties = new Properties();
		properties.setProperty("user", USERNAME);
		properties.setProperty("password", PASSWORD);
		return DriverManager.getConnection(URL, properties);
		}
	 
	  private static void createmagazineTable(Connection conn) throws SQLException {
			  String prepCreate="CREATE TABLE magazine\n" +
		                "(magazine_id INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,\n" +
		                " magazine_title CHAR(100) NOT NULL,\n" +
		                " magazine_pages INT NOT NULL,\n" +
		                " magazine_date DATE);";
			  PreparedStatement prep =  conn.prepareStatement(prepCreate);
			  prep.executeUpdate(); 

	    }
	  
	  private static void createarticleTable(Connection conn) throws SQLException {
			  String prepCreate="CREATE TABLE article\n" +
		                "(article_id INTEGER PRIMARY KEY  AUTO_INCREMENT NOT NULL,\n" +
		                " article_name CHAR(100) NOT NULL,\n" +
		                " article_pages INT NOT NULL,\n" +
		                " article_date DATE,\n" +
		                " magazine_id INTEGER REFERENCES magazine(magazine_id) );";
			  PreparedStatement prep =  conn.prepareStatement(prepCreate);
			  prep.executeUpdate();   
	    }
	  
	  private static void createwritersTable(Connection conn) throws SQLException {
			  String prepCreate="CREATE TABLE writers\n" +
		                "(   writer_id INTEGER PRIMARY KEY  AUTO_INCREMENT NOT NULL,\n" +
		                "    writer_name CHAR(100) NOT NULL,\n" +  
		                "    article_id INTEGER REFERENCES article(article_id) );";
			  PreparedStatement prep =  conn.prepareStatement(prepCreate);
			  prep.executeUpdate();   

		    } 
	 

	  public static void setDatabase() throws SQLException, ClassNotFoundException {
	        Connection conn = getNewConnection();
	        createmagazineTable(conn);
	        createarticleTable(conn);
	        createwritersTable(conn);
	        conn.close();
	    }
	 
	  private static TreeSet<String> getwriters(Article article) throws SQLException, ClassNotFoundException{
		  TreeSet<String> writerNames = new TreeSet<>();
		  Connection conn = getNewConnection();
		  String preparedQuerry1="Select writer_name from writers where article_id = ?;";
		  PreparedStatement preparedStmt =  conn.prepareStatement(preparedQuerry1);
		  preparedStmt.setInt(1, article.getArticleID());
		  ResultSet rs1=preparedStmt.executeQuery();
          while(rs1.next())
          	{
	           writerNames.add(rs1.getString("writer_name"));
          	}
          conn.close();
		  return writerNames;
	  }
	  

	  
	  private static void addwriters(Article article) throws SQLException, ClassNotFoundException{
		  Connection conn = getNewConnection();
		  Statement st = conn.createStatement();
		  String preparedQuerry1="Insert into writers (writer_name,article_id) values(?,?)";
		  PreparedStatement preparedStmt =  conn.prepareStatement(preparedQuerry1);
		  preparedStmt.setInt(2,article.getArticleID()); 
		  for(String writerName : article.getWriters()) {
			  preparedStmt.setString(1,writerName);
			  preparedStmt.executeUpdate();
		  }
		  conn.close();
	  }
	  

	  
	  private static int deletewriters(Article article) throws SQLException, ClassNotFoundException{
		  Connection conn = getNewConnection();
		  String preparedDelete1="Delete from writers where article_id= ?;" ;
		  PreparedStatement preparedStmt =  conn.prepareStatement(preparedDelete1);
		  preparedStmt.setInt(1, article.getArticleID());
		  return preparedStmt.executeUpdate();
	  }
	  
	  
	  public static void addarticle(Article article,Magazine magazine) throws ClassNotFoundException, SQLException {
		  Connection conn = getNewConnection();
		  String preparedUpdate1="Insert into article (article_title,article_pages,article_date,magazine_id) values(?,?,?,?);";
		  String preparedQuery1="Select article_id FROM article WHERE article_name=? and magazine_id = ?;";
		  
		  PreparedStatement pr1= conn.prepareStatement(preparedUpdate1);
		  pr1.setString(1,article.getTitle());
		  pr1.setInt(2,article.getPages());
		  pr1.setDate(3, java.sql.Date.valueOf(article.getwritingDate()));
		  pr1.setInt(4, magazine.getMagazineID());
		  pr1.executeUpdate();
		  
		  PreparedStatement pr2= conn.prepareStatement(preparedQuery1);
		  pr2.setString(1,article.getTitle());
		  pr2.setInt(2,article.getPages());
		  pr2.setDate(3, java.sql.Date.valueOf(article.getwritingDate()));
		  pr2.setInt(4, magazine.getMagazineID());
		  ResultSet rs=pr2.executeQuery();
		  
	      if(rs.next()) {
			  article.setArticleID(rs.getInt("article_id"));
			  addwriters(article);
			  }
	      article.setMagazineID(magazine.getMagazineID());
		  }
	  
	  public static Magazine addMag(Magazine magazine) throws ClassNotFoundException, SQLException{
		  Connection conn = getNewConnection();
		  String preparedUpdate1="Insert into magazine (magazine_title,magazine_pages,magazine_date) values(?,?,?);";
		  PreparedStatement pr1= conn.prepareStatement(preparedUpdate1);
		  pr1.setString(1, magazine.getTitle());
		  pr1.setInt(2,magazine.getPages());
		  pr1.setDate(3, java.sql.Date.valueOf(magazine.getPublishDate()));
		  pr1.executeUpdate();
		  
		  String preparedQuery1="Select magazine_id FROM magazine WHERE magazine_title=? ;";
		  PreparedStatement pr2= conn.prepareStatement(preparedQuery1);
		  pr2.setString(1, magazine.getTitle());
		  ResultSet rs=pr2.executeQuery();
		  if(rs.next())
			  magazine.setMagazineID(rs.getInt("magazine_id"));
		  return magazine;
	  }
	  
	  public static void addmagazine(Magazine magazine) throws ClassNotFoundException, SQLException {
		  magazine=addMag(magazine);
		  for(Article article : magazine.getArticles()) {
			 addarticle(article, magazine);
		  }
	   }
	  
	  public static Article getarticle(String namearticle) throws SQLException, ClassNotFoundException {
	        Connection conn = getNewConnection();
	        String preparedQuerry="SELECT a.article_title as article_title,"
	        		+ "a.article_id as article_id, a.article_pages as article_pages, a.article_date as article_date"
	        		+ "w.writer_name as writer_name ,a.magazine_id as magazine_id FROM article a,writers w"
	        		+" WHERE a.article_id=w.article_id"
	        		+ "and a.article_name=?;";
	        
	        PreparedStatement preparedStmt1=conn.prepareStatement(preparedQuerry);
	        preparedStmt1.setString(1,namearticle);
	        ResultSet rs = preparedStmt1.executeQuery();
	        
	        Article article = null;
	        if(rs.next()) {
	        	 article = new Article();
	        	 article.setTitle(rs.getString("article_name"));
	        	 article.setPages(rs.getInt("article_pages"));
	        	 //date to local date
	        	 Date input = rs.getDate("article_date");
	        	 LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	        	 article.setwritingDate(date);
	        	 article.setArticleID(rs.getInt("article_id"));
	        	 article.setMagazineID(rs.getInt("magazine_id"));
	        	 do  {
		            article.addWriter(rs.getString("writer_name"));
		        } while(rs.next());
	        }
	        conn.close();
	        return article;
	    }
	    
	  public static TreeSet<Article> getArticlesFromMagazine(Magazine magazine) throws SQLException, ClassNotFoundException {
	        Connection conn = getNewConnection();
	        String preparedQuerry="SELECT * FROM article WHERE magazine_id=?;";
	        
	        PreparedStatement preparedStmt1=conn.prepareStatement(preparedQuerry);
	        preparedStmt1.setInt(1,magazine.getMagazineID());
	        ResultSet rs = preparedStmt1.executeQuery();
	        TreeSet<Article> Resarticle = new TreeSet<>();
	        Article article = null;
	        
	        while (rs.next()) {
	        	 article = new Article();
	        	 article.setTitle(rs.getString("article_name"));
	        	 article.setPages(rs.getInt("article_pages"));
	        	 //date to local date
	        	 Date input = rs.getDate("article_date");
	        	 LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	        	 article.setwritingDate(date);
	        	 article.setArticleID(rs.getInt("article_id"));
	        	 article.setMagazineID(rs.getInt("magazine_id"));
	        	 do  {
		            article.addWriter(rs.getString("writer_name"));
		        } while(rs.next());
	        }
	        conn.close();
	        return Resarticle;
	    }
	  
	  public static Magazine getMagazineByName(String namemagazine) throws SQLException, ClassNotFoundException {
	        Connection conn = getNewConnection();
	        String preparedQuerry1="SELECT * FROM magazine WHERE magazine_title=?;"; 
	        
	        PreparedStatement preparedStmt1=conn.prepareStatement(preparedQuerry1);
	        preparedStmt1.setString(1,namemagazine);
	        ResultSet rs = preparedStmt1.executeQuery();
	        Magazine magazine = null;
	        
	        if (rs.next()) {
	            magazine = new Magazine();
	            magazine.setTitle(rs.getString("magazine_title"));
	            magazine.setMagazineID(rs.getInt("magazine_id"));
	            magazine.setPages(rs.getInt("magazine_pages"));
	            Date input = rs.getDate("magazine_date");
	        	LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	        	magazine.setPublishDate(date);
	        	TreeSet<Article> article = new TreeSet<>();
	            article.addAll(getArticlesFromMagazine(magazine));
	            for(Article art : article) {
	         	   magazine.addArticle(art); 
	           }
	        }
	        
	        conn.close();
	        return magazine;
	    }
	  
	  public static void updatearticle(Article article) throws ClassNotFoundException, SQLException {
		    Connection conn = getNewConnection();
	        
		    String preparedUpdate1="UPDATE article Set article_name= ? ,"
		    		+"magazine_id = ? where article_id= ?;";
		    
		    PreparedStatement pr1=conn.prepareStatement(preparedUpdate1);
		    pr1.setString(1, article.getTitle());
		    pr1.setInt(2, article.getMagazineID());
		    pr1.setInt(3, article.getArticleID());
	        
	        int deleteWriteRes=deletewriters(article);
	        if(deleteWriteRes!=0) {
	           addwriters(article);
	        } else {
	        	conn.close();
	        	throw new IllegalArgumentException("Writers not found");
	        }
	        conn.close();
	  }
	  
	  public static void updatemagazine(Magazine magazine) throws ClassNotFoundException, SQLException {
		    Connection conn = getNewConnection();
		    String prepUpdate="Update magazine set magazine_title= ? "
		    		+ "where magazine_id= ? ;";
		    String prepQuery="Select * from article where magazine_id= ? ;";
		    
		    PreparedStatement pr1= conn.prepareStatement(prepUpdate);
	        pr1.setString(1, magazine.getTitle());
	        pr1.setInt(2, magazine.getMagazineID());
		    int res= pr1.executeUpdate();
		    
		    PreparedStatement pr2= conn.prepareStatement(prepQuery);
	        pr2.setInt(1, magazine.getMagazineID());
	        ResultSet rs1 = pr2.executeQuery();
	        
	        while(rs1.next())
	        	deletearticle(rs1.getString("article_name"));
	        for(Article art :  magazine.getArticles()) {
	        	addarticle(art,magazine);    
	        }
	        conn.close();
	        if(res == 0) throw new IllegalArgumentException("magazine not found");
	  }
	
	   public static void deletearticle(String namearticle) throws SQLException, ClassNotFoundException {
	        Connection conn = getNewConnection();
	        
	        String prepQuery1="Select * from article where article_name= ?;";
	        String prepDelete1="DELETE FROM article WHERE article_name= ? ;";
	        PreparedStatement pr1= conn.prepareStatement(prepQuery1);
	        pr1.setString(1, namearticle);
	        ResultSet r1= pr1.executeQuery();
	        
	        if(r1.next())
	        	{Article art = new Article();
	        	 art.setTitle(namearticle);
		         art.setArticleID(r1.getInt("article_id"));
		         
		         PreparedStatement pr2= conn.prepareStatement(prepDelete1);
		         pr2.setString(1, art.getTitle());
		         int rs = pr2.executeUpdate();
		                 	
		         int res2 = deletewriters(art);
		         
	            conn.close();
	            if(rs == 0) throw new IllegalArgumentException("article not found");
	   		   }
	   }
	   
	   public static void deletemagazine(String namemagazine) throws SQLException, ClassNotFoundException {
	        Connection conn = getNewConnection();
	        String prepQuery1="Select magazine_id from magazine where magazine_title= ? ;";
	        String prepDelete1="DELETE FROM magazine WHERE magazine_title= ?;";
	        String prepQuery2="Select article_name from article where magazine_id = ?;";
	        PreparedStatement pr1 = conn.prepareStatement(prepQuery1);
	        pr1.setString(1, namemagazine);
	        ResultSet r1= pr1.executeQuery();
	        if(r1.next())
	        	{int idFac = r1.getInt("magazine_id");
		         
	        	 PreparedStatement pr2 = conn.prepareStatement(prepDelete1);
	        	 pr2.setString(1, namemagazine);
		         int rs = pr2.executeUpdate();
		         
		         PreparedStatement pr3 = conn.prepareStatement(prepQuery2);
	        	 pr3.setInt(1, idFac);
		         ResultSet r2 = pr3.executeQuery();
		         
			    while(r2.next()) {
			    	deletearticle(r2.getString("article_name"));
			    }
	            conn.close();
	            if(rs == 0) throw new IllegalArgumentException("article not found");
	   		   }
	   }
	   
	   private static void drop() throws SQLException, ClassNotFoundException {
	        Connection conn = getNewConnection();
	        Statement st = conn.createStatement();
	        st.executeUpdate("DROP TABLE IF EXISTS 'writers';");
	        st.executeUpdate("DROP TABLE IF EXISTS 'article';");
	        st.executeUpdate("DROP TABLE IF EXISTS 'magazine';");
	        conn.close();
	    }

}
