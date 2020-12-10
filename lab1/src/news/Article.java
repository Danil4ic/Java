package news;


import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;

public class Article{
	private String title;
	private LocalDate writingDate;
	int pages;
	Set<String> writers= new TreeSet<>();
	
	/////////////////////////////
	public Article() {
		this.addWriter(" ");
		this.title=" ";
		this.writingDate = LocalDate.of(1990, 01, 01);
		this.pages=0;
	}
	
	public Article(String name) {
		this.addWriter(" ");
		this.title=name;
		this.writingDate = LocalDate.of(1990, 01, 01);
		this.pages=0;
	}

	public Article(String name,Set<String> writers,LocalDate writingDate,int pages) {
		this.title=name;
		this.writers=writers;
		this.writingDate = writingDate;
		this.pages=pages;
	}
	/////////////////////////////
	
	//getters
	public String getTitle() {
		return title;
	}
	
	public LocalDate getwritingDate() {
		return writingDate;
	}
	
	public Set<String> getWriters() {
		return writers;
	}
	
	public int getPages() {
		return pages;
	}
	/////////////////////////////
	
	//setters
	public void setTitle(String title) {
		this.title = title;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public void setWriters(Set<String> writers) {
		this.writers = writers;
	}
	
	public void setwritingDate(LocalDate writingDate) {
		this.writingDate = writingDate;
	}

	/////////////////////////////
	
	//methods
	public void addWriter(String name) {
		writers.add(name);
	}
	
	
	public int getCountWriters() {
		return writers.size();
	}
	
	@Override
	public String toString() {
		String s = "Article name: " + title + ", Writing date: " + writingDate + "Pages: " + pages + " Writers: ";
		for(String i: writers)
			s+=i + " ";
		return s;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Article other = (Article) obj;
		if (title.equals(other.title))
			return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	
	public int compareTo(Object obj) {
		Article one = (Article)obj;
		if(one.title.length()<this.title.length())
			return -1;
		else return 1;
	}
	
	////////////////////////////////////////////////////





	public static void main(String[] args) {
	 }
}
