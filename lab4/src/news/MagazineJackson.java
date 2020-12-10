package news;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import news.Article;
import news.Magazine;

public class MagazineJackson implements Serializer<Magazine>{
    public void serialize(Magazine magaz, String name) throws IOException {
    	File file= new File(name);
    	ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        StringWriter stringDep = new StringWriter();

        objectMapper.writeValue(stringDep, magaz);

        FileWriter fw = new FileWriter(file);
        fw.write(stringDep.toString());
        fw.close();
    }
    
    @Override
    public Magazine deserialize(String name) throws IOException {
    	File file= new File(name);
        byte[] mapData = Files.readAllBytes(Paths.get(file.toString()));

        ObjectMapper objectMapper = new ObjectMapper();
        Magazine Magazine = objectMapper.readValue(mapData, Magazine.class);

        return Magazine;
    }

	@Override
	public void serializeCollection(Collection<Magazine> objects, String name) throws IOException {
		// TODO Auto-generated method stub
		File file= new File(name);
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        StringWriter stringDep = new StringWriter();

        objectMapper.writeValue(stringDep, objects);
        FileWriter fw = new FileWriter(file);
        fw.write(stringDep.toString());
        fw.close();
	}

	@Override
	public Collection<Magazine> deserializeCollection(String name) throws IOException {
		File file= new File(name);
		 ObjectMapper mapper = new ObjectMapper();//.registerModule(new Module());
	return mapper.readValue(file,mapper.getTypeFactory().constructCollectionType(Set.class, Article.class));
	}
}