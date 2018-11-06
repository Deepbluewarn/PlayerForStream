package club.rodong.playerforstream.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Twitch_Badges {
    @GET("v1/badges/channels/{STID}/display")
    Call<String> Get_Badges(
            @Path("STID") int stid
    );
}
