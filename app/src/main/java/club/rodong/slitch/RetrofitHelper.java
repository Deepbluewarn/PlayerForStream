package club.rodong.slitch;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Bluewarn on 2018-03-15.
 */

public class RetrofitHelper {
    String baseUrl;
    private static Retrofit retrofit_json;
    private static Retrofit retrofit_string;
    public RetrofitHelper(String baseUrl){
        this.baseUrl = baseUrl;
    }

    public static Retrofit getRetrofit_Json(String baseUrl){
        if(retrofit_json == null){
            return new retrofit2.Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(createOkHttpClient())
                    .build();
        }
        return retrofit_json;
    }
    public static Retrofit getRetrofit_String(String baseUrl){
        if(retrofit_string == null){
            return new retrofit2.Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(createOkHttpClient())
                    .build();
        }
        return retrofit_string;
    }

    private static OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        return builder.build();
    }
}
