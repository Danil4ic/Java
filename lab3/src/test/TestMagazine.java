package test;

import static org.testng.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import news.Article;
import news.Magazine;
import news.Article.ArticleBuilder;

public class TestMagazine {

	@Test
	  public void inputTest() {
		
				Set<String> Writers1 = new TreeSet<>();
				Writers1.add("Shevchenko");
				Writers1.add("Franko");
				
				Article first = new ArticleBuilder()
						.title("Fire in hotel")
						.writers(Writers1)
						.writingDate(LocalDate.of(2017, 10, 10))
						.build();
				
				TreeSet<Article> o=new TreeSet<>();
				o.add(first);
				
				Article second = new ArticleBuilder()
						.title("Football")
						.writers(Writers1)
						.writingDate(LocalDate.of(2017, 10, 10))
						.build();
				
				TreeSet<Article> o2=new TreeSet<>();
				o2.add(first);
				o2.add(second);	
				
			assertEquals(new Magazine("Automobiles", o, 50,LocalDate.of(2017, 10, 10)).findArticle("Fire in hotel"),true);
			assertEquals(new Magazine("Automobiles", o2, 50,LocalDate.of(2017, 10, 10)).findArticle("Football"),true);
			assertEquals(new Magazine("Automobiles", o, 50,LocalDate.of(2017, 10, 10)).findArticle("Football"),false);
		}
	
	@Test
	  public void countTest() {
		
				Set<String> Writers1 = new TreeSet<>();
				Writers1.add("Shevchenko");
				Writers1.add("Franko");
				
				Article first = new ArticleBuilder()
						.title("Fire in hotel")
						.writers(Writers1)
						.writingDate(LocalDate.of(2017, 10, 10))
						.build();
				
				TreeSet<Article> o=new TreeSet<>();
				o.add(first);
				
				Article second = new ArticleBuilder()
						.title("Football")
						.writers(Writers1)
						.writingDate(LocalDate.of(2017, 10, 10))
						.build();
				
				TreeSet<Article> o2=new TreeSet<>();
				o2.add(first);
				o2.add(second);	
				
			assertEquals(new Magazine("Automobiles", o, 50,LocalDate.of(2017, 10, 10)).countArticle(),1);
			assertEquals(new Magazine("Automobiles", o2, 50,LocalDate.of(2017, 10, 10)).countArticle(),2);
			assertEquals(new Magazine("Automobiles", o, 50,LocalDate.of(2017, 10, 10)).countArticle(),1);
		}
	
	
	@Test
	  public void findByWriterTest() {
		
				Set<String> Writers1 = new TreeSet<>();
				Writers1.add("Shevchenko");
				Writers1.add("Franko");
				
				Article first = new ArticleBuilder()
						.title("Fire in hotel")
						.writers(Writers1)
						.writingDate(LocalDate.of(2017, 10, 10))
						.build();
				
				TreeSet<Article> o=new TreeSet<>();
				o.add(first);
				
				Article second = new ArticleBuilder()
						.title("Football")
						.writers(Writers1)
						.writingDate(LocalDate.of(2017, 10, 10))
						.build();
				
				TreeSet<Article> o2=new TreeSet<>();
				o2.add(first);
				o2.add(second);	
				
			assertEquals(new Magazine("Automobiles", o, 50,LocalDate.of(2017, 10, 10)).findWriterInArticle("Shevchenko"),true);
			assertEquals(new Magazine("Automobiles", o2, 50,LocalDate.of(2017, 10, 10)).findWriterInArticle("Franko"),true);
			assertEquals(new Magazine("Automobiles", o, 50,LocalDate.of(2017, 10, 10)).findWriterInArticle("Football"),false);
		}
	

	@Test
	  public void counMagazinePages() {
		
				Set<String> Writers1 = new TreeSet<>();
				Writers1.add("Shevchenko");
				Writers1.add("Franko");
				
				Article first = new ArticleBuilder()
						.title("Fire in hotel")
						.writers(Writers1)
						.writingDate(LocalDate.of(2017, 10, 10))
						.pages(1)
						.build();
				
				TreeSet<Article> o=new TreeSet<>();
				o.add(first);
				
				Article second = new ArticleBuilder()
						.title("Football")
						.writers(Writers1)
						.writingDate(LocalDate.of(2017, 10, 10))
						.pages(2)
						.build();
				
				TreeSet<Article> o2=new TreeSet<>();
				o2.add(first);
				o2.add(second);	
				
			assertEquals(new Magazine("Automobiles", o, 50,LocalDate.of(2017, 10, 10)).countPagesFromArticles(),1);
			assertEquals(new Magazine("Automobiles", o2, 50,LocalDate.of(2017, 10, 10)).countPagesFromArticles(),3);
		}
}
