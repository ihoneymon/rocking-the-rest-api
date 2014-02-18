package kr.pe.ihoney.jco.restapi.web.support.serializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateSerializer extends JsonSerializer<Date> {

    private String datePattern = "yyyy/MM/dd HH:mm";
    
    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        String formattedDate = formatter.format(value);
        jgen.writeString(formattedDate);
    }
}
