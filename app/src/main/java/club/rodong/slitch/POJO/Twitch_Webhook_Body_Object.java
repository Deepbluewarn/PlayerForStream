package club.rodong.slitch.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Twitch_Webhook_Body_Object {

    public Twitch_Webhook_Body_Object(
            String hubCallback, String hubMode, String hubTopic, int hubLeaseSeconds, String hubSecret){
        this.hubCallback = hubCallback;
        this.hubMode = hubMode;
        this.hubTopic = hubTopic;
        this.hubLeaseSeconds = hubLeaseSeconds;
        this.hubSecret = hubSecret;
    }

    @SerializedName("hub.callback")
    @Expose
    private String hubCallback;
    @SerializedName("hub.mode")
    @Expose
    private String hubMode;
    @SerializedName("hub.topic")
    @Expose
    private String hubTopic;
    @SerializedName("hub.lease_seconds")
    @Expose
    private Integer hubLeaseSeconds;
    @SerializedName("hub.secret")
    @Expose
    private String hubSecret;

    public String getHubCallback() {
        return hubCallback;
    }

    public void setHubCallback(String hubCallback) {
        this.hubCallback = hubCallback;
    }

    public String getHubMode() {
        return hubMode;
    }

    public void setHubMode(String hubMode) {
        this.hubMode = hubMode;
    }

    public String getHubTopic() {
        return hubTopic;
    }

    public void setHubTopic(String hubTopic) {
        this.hubTopic = hubTopic;
    }

    public Integer getHubLeaseSeconds() {
        return hubLeaseSeconds;
    }

    public void setHubLeaseSeconds(Integer hubLeaseSeconds) {
        this.hubLeaseSeconds = hubLeaseSeconds;
    }

    public String getHubSecret() {
        return hubSecret;
    }

    public void setHubSecret(String hubSecret) {
        this.hubSecret = hubSecret;
    }

}
