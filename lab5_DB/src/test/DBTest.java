package test;

import static org.testng.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.testng.annotations.Test;

import news.Magazine;
import news.Article;
import news.MagazineDB;


public class DBTest {

    
    public void testGetNewConnection() throws Exception {
        new MagazineDB().getNewConnection();
    }
    
    
    public void testSetDatabase() throws Exception {
        new MagazineDB().setDatabase();;
    }
    
    
    public void testAddArticle() throws Exception {
    	Set<String> Writers = new TreeSet<>();
		Writers.add("petro");
		Writers.add("vasyl");
		Article a = new Article("football",Writers,LocalDate.of(2017, 10, 10), 5);
		TreeSet<Article> Article1 = new TreeSet<>();
		Article1.add(a);
		Magazine m = new Magazine("sport",Article1,100,LocalDate.of(2017, 10, 11));
        new MagazineDB().addarticle(a,m);
    }
    
    
    public void testAddMagazine() throws Exception {
    	Set<String> Writers = new TreeSet<>();
		Writers.add("petro");
		Writers.add("vasyl");
		Article a = new Article("football",Writers,LocalDate.of(2017, 10, 10), 5);
		TreeSet<Article> Article1 = new TreeSet<>();
		Article1.add(a);
		Magazine m = new Magazine("sport",Article1,100,LocalDate.of(2017, 10, 11));
        new MagazineDB().addmagazine(m);
    }
    

    @Test
    public void testGetMagazine() throws Exception {
    	Set<String> Writers = new TreeSet<>();
		Writers.add("petro");
		Writers.add("vasyl");
		Article a = new Article("football",Writers,LocalDate.of(2017, 10, 10), 5);
		TreeSet<Article> Article1 = new TreeSet<>();
		Article1.add(a);
		Magazine m = new Magazine("sport",Article1,100,LocalDate.of(2017, 10, 11));
        assertEquals(new MagazineDB().getMagazineByName("sport"),m);
    }
    
    
    public void testUpdateMagazine() throws Exception{
    	Set<String> Writers = new TreeSet<>();
		Writers.add("petro");
		Writers.add("vasyl");
		Article a = new Article("football",Writers,LocalDate.of(2017, 10, 10), 5);
		TreeSet<Article> Article1 = new TreeSet<>();
		Article1.add(a);
		Magazine m = new Magazine("sport",Article1,100,LocalDate.of(2017, 10, 11));
	  	new MagazineDB().updatemagazine(m);
    }
    

    public void testDeleteArticle() throws Exception {
    	new MagazineDB().deletearticle("fire");
    } 
    

    public void testDeleteMagazine() throws Exception {
    	new MagazineDB().deletemagazine("football");
    }
}