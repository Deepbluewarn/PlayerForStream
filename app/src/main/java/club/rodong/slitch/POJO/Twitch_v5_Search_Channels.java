package club.rodong.slitch.POJO;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Twitch_v5_Search_Channels {

    @SerializedName("_total")
    @Expose
    private Integer total;
    @SerializedName("channels")
    @Expose
    private List<Channel> channels = null;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public class Channel {

        @SerializedName("_id")
        @Expose
        private Integer id;
        @SerializedName("broadcaster_language")
        @Expose
        private String broadcasterLanguage;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("display_name")
        @Expose
        private String displayName;
        @SerializedName("followers")
        @Expose
        private Integer followers;
        @SerializedName("game")
        @Expose
        private String game;
        @SerializedName("language")
        @Expose
        private String language;
        @SerializedName("logo")
        @Expose
        private String logo;
        @SerializedName("mature")
        @Expose
        private Boolean mature;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("partner")
        @Expose
        private Boolean partner;
        @SerializedName("profile_banner")
        @Expose
        private String profileBanner;
        @SerializedName("profile_banner_background_color")
        @Expose
        private String profileBannerBackgroundColor;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("video_banner")
        @Expose
        private String videoBanner;
        @SerializedName("views")
        @Expose
        private Integer views;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getBroadcasterLanguage() {
            return broadcasterLanguage;
        }

        public void setBroadcasterLanguage(String broadcasterLanguage) {
            this.broadcasterLanguage = broadcasterLanguage;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public Integer getFollowers() {
            return followers;
        }

        public void setFollowers(Integer followers) {
            this.followers = followers;
        }

        public String getGame() {
            return game;
        }

        public void setGame(String game) {
            this.game = game;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public Boolean getMature() {
            return mature;
        }

        public void setMature(Boolean mature) {
            this.mature = mature;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getPartner() {
            return partner;
        }

        public void setPartner(Boolean partner) {
            this.partner = partner;
        }

        public String getProfileBanner() {
            return profileBanner;
        }

        public void setProfileBanner(String profileBanner) {
            this.profileBanner = profileBanner;
        }

        public String getProfileBannerBackgroundColor() {
            return profileBannerBackgroundColor;
        }

        public void setProfileBannerBackgroundColor(String profileBannerBackgroundColor) {
            this.profileBannerBackgroundColor = profileBannerBackgroundColor;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVideoBanner() {
            return videoBanner;
        }

        public void setVideoBanner(String videoBanner) {
            this.videoBanner = videoBanner;
        }

        public Integer getViews() {
            return views;
        }

        public void setViews(Integer views) {
            this.views = views;
        }

    }

}