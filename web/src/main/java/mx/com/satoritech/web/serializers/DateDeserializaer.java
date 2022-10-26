package mx.com.satoritech.web.serializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by TEA 2 on 4/2/2018.
 *
 */
public class DateDeserializaer implements JsonDeserializer<Date> {
    private static final SimpleDateFormat dateFormatter =
            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static final SimpleDateFormat dateTimeFormatter =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    @Override
    public Date deserialize(JsonElement json, Type typeOfT,
                            JsonDeserializationContext context)
            throws JsonParseException {
        try {
            if (json == null) {
                return null;
            }

            if (json.getAsString().length() > 10) {
                return dateTimeFormatter.parse(json.getAsString());
            } else {
                return dateFormatter.parse(json.getAsString());
            }
        } catch (ParseException e) {
            return null;
        }
    }
}
