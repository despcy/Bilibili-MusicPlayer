package net.chenxiy.bilimusic.database.converters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.Cover;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;

public class FolderCoverConverter {
    @TypeConverter
    public static List<Cover> fromString(String value) {
        Type listType = new TypeToken<List<Cover>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<Cover> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
