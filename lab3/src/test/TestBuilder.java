package test;

import static org.testng.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import news.Article;
import news.Article.ArticleBuilder;

public class TestBuilder {

@Test(expectedExceptions = IllegalArgumentException.class)
public void testDedpartmentBuilder1() {
	Article a = new ArticleBuilder()
			.title("Fire in hotel")
			.writingDate(LocalDate.of(2017, 10, 10))
			.pages(2)
			.build();
	String str="Fire in hotel";
	assertEquals(a.getTitle(),str);
	
	Article b = new ArticleBuilder()
			.title("Football")
			.writingDate(LocalDate.of(2017, 10, 10))
			.pages(2)
			.build();
	String str2="Football";
	assertEquals(b.getTitle(),str2);
	
	
}

}
