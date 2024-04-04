package citybike.utils;

import java.time.format.DateTimeFormatter;

import javax.ws.rs.ext.Provider;

@Provider
public interface LocalDateTimeAdapter {

	static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    


}
