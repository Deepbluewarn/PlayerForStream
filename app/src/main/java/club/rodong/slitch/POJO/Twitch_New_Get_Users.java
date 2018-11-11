package club.rodong.slitch.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bluewarn on 2018-03-22.
 */

public class Twitch_New_Get_Users {
    @SerializedName("data")
    @Expose
    private java.util.List<Data> data = null;

    public java.util.List<Data> getData() {
        return data;
    }

    public void setData(java.util.List<Data> data) {
        this.data = data;
    }

    public class Data {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("login")
        @Expose
        private String login;
        @SerializedName("display_name")
        @Expose
        private String displayName;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("broadcaster_type")
        @Expose
        private String broadcasterType;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("profile_image_url")
        @Expose
        private String profileImageUrl;
        @SerializedName("offline_image_url")
        @Expose
        private String offlineImageUrl;
        @SerializedName("view_count")
        @Expose
        private Integer viewCount;
        @SerializedName("email")
        @Expose
        private String email;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBroadcasterType() {
            return broadcasterType;
        }

        public void setBroadcasterType(String broadcasterType) {
            this.broadcasterType = broadcasterType;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getProfileImageUrl() {
            return profileImageUrl;
        }

        public void setProfileImageUrl(String profileImageUrl) {
            this.profileImageUrl = profileImageUrl;
        }

        public String getOfflineImageUrl() {
            return offlineImageUrl;
        }

        public void setOfflineImageUrl(String offlineImageUrl) {
            this.offlineImageUrl = offlineImageUrl;
        }

        public Integer getViewCount() {
            return viewCount;
        }

        public void setViewCount(Integer viewCount) {
            this.viewCount = viewCount;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    }
}
