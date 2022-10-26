package mx.com.satoritech.database.converters;

import androidx.room.TypeConverter;

import java.util.Date;


public class DBTypeConverters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }
}
