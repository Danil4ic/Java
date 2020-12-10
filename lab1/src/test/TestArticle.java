package test;

import static org.testng.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import news.Article;

public class TestArticle {

	@Test(dataProvider = "inputProvider")
	  public void inputTest(String name, String title) {
			assertEquals(new Article(name).getTitle(),title);
		}
	  
		@DataProvider
		public Object[][] inputProvider() {
			return new Object[][] {{ "hello", "hello"}} ;
		}
	  
		@Test(dataProvider = "countProvider")
		  public void TestGetCount(String title,Set<String> writers,LocalDate publishDate,int pages,int count) {
				assertEquals(new Article(title,writers,publishDate,pages).getCountWriters(),count);
			}
		  
		@DataProvider
		public Object[][] countProvider() {
				Set<String> Writers1 = new TreeSet<>();
				Set<String> Writers2 = new TreeSet<>();
				Writers1.add("Shevchenko");
				Writers1.add("Franko");
				Writers1.add("Ykrainka");
				Writers2.add("Petro");
				return new Object[][] {{ "Sport", Writers1, LocalDate.of(2017, 10, 10), 1, 3},{"Music",Writers2,LocalDate.of(2017, 10, 10),1,1}};
			}
}
