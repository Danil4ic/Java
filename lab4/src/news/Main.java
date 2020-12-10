package news;

import news.Magazine;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

public class Main {
	public static void main(String[] args) {
			//Magazine.parseXML();
			Magazine.ReadJSONFile();
			Set<String> Writers = new TreeSet<>();
			Writers.add("petro");
			Writers.add("vasyl");
			Article a = new Article("football",Writers,LocalDate.of(2017, 10, 10), 5);
			TreeSet<Article> Article1 = new TreeSet<>();				Article1.add(a);
			Article1.add(a);
			Magazine m = new Magazine("sport",Article1,100,LocalDate.of(2017, 10, 11));
			//m.createJSONFile();				
			//m.writeXML();
			//System.out.println(m.toString());*/
		
	}
}
