package club.rodong.slitch.interfaces;

import java.util.ArrayList;

import club.rodong.slitch.POJO.Twitch_New_Get_Games;
import club.rodong.slitch.POJO.Twitch_New_Get_Streams;
import club.rodong.slitch.POJO.Twitch_New_Get_Users;
import club.rodong.slitch.POJO.Twitch_New_Get_Users_Follows;
import club.rodong.slitch.POJO.Twitch_Webhook_Body_Object;
import club.rodong.slitch.POJO.Twitch_v5_Get_Followed_Stream;
import club.rodong.slitch.POJO.Twitch_v5_Get_Stream_By_User;
import club.rodong.slitch.POJO.Twitch_v5_Get_User;
import club.rodong.slitch.POJO.Twitch_v5_Get_User_Follows;
import club.rodong.slitch.POJO.Twitch_v5_Search_Channels;
import club.rodong.slitch.POJO.Twitch_v5_Search_Streams;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Bluewarn on 2018-03-22.
 */

public interface Twitch_API {
    @GET("helix/users")
    Call<Twitch_New_Get_Users> Get_Users(
            @Header("Authorization") String Bearer
    );
    @GET("helix/users")
    Call<Twitch_New_Get_Users> Get_Id_Users(
            @Header("Authorization") String Bearer,
            @Query("id") int id
    );
    @GET("helix/users/follows")
    Call<Twitch_New_Get_Users_Follows> Get_User_Follows(
            @Header("Client-ID") String client_id,
            @Query("from_id") int user_id,
            @Query("first") int first,
            @Query("after") String after
    );
    @GET("helix/streams")
    Call<Twitch_New_Get_Streams> Get_Streams(
            @Header("Client-ID") String client_id,
            @Query("user_id") ArrayList<Integer> user_id,
            @Query("first") int first
    );
    @GET("helix/streams")
    Call<Twitch_New_Get_Streams> Get_Stream(
            @Header("Client-ID") String client_id,
            @Query("user_id") int user_id,
            @Query("first") int first
    );
    @GET("helix/games")
    Call<Twitch_New_Get_Games> Get_Games(
            @Header("Client-ID") String client_id,
            @Query("id") int id
    );
    @POST("helix/webhooks/hub")
    Call<String> Request_Subscribe(
            @Header("Content-Type") String content_type,
            @Header("Client-ID") String client_id,
            @Body Twitch_Webhook_Body_Object body_object
    );


    //Twitch_v5 API
    @GET("kraken/search/channels")
    Call<Twitch_v5_Search_Channels> Search_Channels(
            @Header("Accept") String accept,
            @Header("Client-ID") String client_id,
            @Query("query") String query
    );
    @GET("kraken/search/streams")
    Call<Twitch_v5_Search_Streams> Search_Stream(
            @Query("hls") boolean hls
    );
    @GET("kraken/user")
    Call<Twitch_v5_Get_User> Get_User(
            @Header("Accept") String accept,
            @Header("Client-ID") String client_id,
            @Header("Authorization") String auth
    );
    @GET("kraken/users/{userID}/follows/channels")
    Call<Twitch_v5_Get_User_Follows> Get_User_Follows(
            @Path("userID") String userID,
            @Header("Accept") String accept,
            @Header("Client-ID") String client_id
    );
    @GET("kraken/streams/followed")
    Call<Twitch_v5_Get_Followed_Stream> Get_Followed_Streams(
            @Header("Accept") String accept,
            @Header("Client-ID") String client_id,
            @Header("Authorization") String auth,
            @Query("limit") int limit
    );
    @GET("kraken/users/{userID}/emotes")
    Call<String> Twitch_v5_Get_User_Emotes(
            @Header("Accept") String accept,
            @Header("Client-ID") String client_id,
            @Header("Authorization") String auth,
            @Path("userID") int userID
    );
    @GET("kraken/streams/{channelID}")
    Call<Twitch_v5_Get_Stream_By_User> Twitch_v5_Get_Stream_By_User(
            @Header("Accept") String accept,
            @Header("Client-ID") String client_id,
            @Path("channelID") int id
    );
}
