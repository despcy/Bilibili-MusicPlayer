package net.chenxiy.bilimusic.database.converters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.chenxiy.bilimusic.network.biliapi.pojo.av.Page;
import net.chenxiy.bilimusic.network.biliapi.pojo.av.download.Durl;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;

public class DurlConverter {
    @TypeConverter
    public static List<Durl> fromString(String value) {
        Type listType = new TypeToken<List<Durl>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<Durl> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
