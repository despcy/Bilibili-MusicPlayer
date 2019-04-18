package net.chenxiy.biliapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static net.chenxiy.biliapi.Constants.BASE_URL;

public class RetrofitInstance {
    private static Retrofit retrofit=null;
    public static Retrofit getInstance(){
        if(retrofit==null){
            Gson gson=new GsonBuilder().create();
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
