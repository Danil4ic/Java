package news;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import news.Article;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.awt.List;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ArticleJackson implements Serializer<Article>{

		@Override
		public void serialize(Article art, String name) throws IOException {
			// TODO Auto-generated method stub
			File file= new File(name);
			ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	        StringWriter stringArt = new StringWriter();

	        objectMapper.writeValue(stringArt, art);

	        FileWriter fw = new FileWriter(file);
	        fw.write(stringArt.toString());
	        fw.close();
		}

		@Override
		public void serializeCollection(Collection<Article> objects, String name) throws IOException {
			// TODO Auto-generated method stub
			File file= new File(name);
			ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	        StringWriter stringArt = new StringWriter();

	        objectMapper.writeValue(stringArt, objects);
	        FileWriter fw = new FileWriter(file);
	        fw.write(stringArt.toString());
	        fw.close();
		}

		@Override
		public Article deserialize(String name) throws IOException {
			    File file= new File(name);
			    byte[] mapData = Files.readAllBytes(Paths.get(file.toString()));

		        ObjectMapper objectMapper = new ObjectMapper();
		        Article article = objectMapper.readValue(mapData, Article.class);
		       // System.out.println(department.toString());
		        return article;
		}

		@Override
		public Collection<Article> deserializeCollection(String name) throws IOException {
			 File file= new File(name);
			 ObjectMapper mapper = new ObjectMapper();//.registerModule(new Module());
		return mapper.readValue(file,mapper.getTypeFactory().constructCollectionType(Set.class, Article.class));
			
		}



	}