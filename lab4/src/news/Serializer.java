package news;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Collection;

public interface Serializer<T>{
	
		public void serialize(T object ,String name) throws IOException;
		
		void serializeCollection(Collection<T> objects, String name) throws IOException;

	    T deserialize(String name) throws IOException;

	    Collection<T> deserializeCollection(String name) throws IOException;

	}