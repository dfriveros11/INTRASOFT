package co.com.infrasoft.documents.convertidor;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ZonedDateConvertidor {
	
	private final static ZoneId zona = ZoneId.of("America/Bogota");
	
	private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMM yyyy");

	/**
	 * Protege de la instanciación de la clase
	 */
	private ZonedDateConvertidor() {
		throw new AssertionError("ESta clase no se puede instanciar");
	};
	
    public static ZonedDateTime convertDateToZoned(Date date) {
        return ZonedDateTime.ofInstant(date.toInstant(), zona);
    }
    
    public static Date convertZonedToDate(ZonedDateTime zonedDateTime) {
        return Date.from(zonedDateTime.toInstant());
    }

	public static DateTimeFormatter getFormat() {
		return format;
	}
}
