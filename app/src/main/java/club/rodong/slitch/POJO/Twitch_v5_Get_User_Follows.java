package club.rodong.slitch.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bluewarn on 2018-03-20.
 */

public class Twitch_v5_Get_User_Follows {
    @SerializedName("_total")
    @Expose
    private Integer total;
    @SerializedName("follows")
    @Expose
    private java.util.List<Follow> follows = null;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public java.util.List<Follow> getFollows() {
        return follows;
    }

    public void setFollows(java.util.List<Follow> follows) {
        this.follows = follows;
    }
    class Follow {

        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("notifications")
        @Expose
        private Boolean notifications;
        @SerializedName("channel")
        @Expose
        private Channel channel;

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Boolean getNotifications() {
            return notifications;
        }

        public void setNotifications(Boolean notifications) {
            this.notifications = notifications;
        }

        public Channel getChannel() {
            return channel;
        }

        public void setChannel(Channel channel) {
            this.channel = channel;
        }

    }
    class Channel {

        @SerializedName("_id")
        @Expose
        private Integer id;
        @SerializedName("background")
        @Expose
        private Object background;
        @SerializedName("banner")
        @Expose
        private Object banner;
        @SerializedName("broadcaster_language")
        @Expose
        private String broadcasterLanguage;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("delay")
        @Expose
        private Object delay;
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
        private Object profileBannerBackgroundColor;
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

        public Object getBackground() {
            return background;
        }

        public void setBackground(Object background) {
            this.background = background;
        }

        public Object getBanner() {
            return banner;
        }

        public void setBanner(Object banner) {
            this.banner = banner;
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

        public Object getDelay() {
            return delay;
        }

        public void setDelay(Object delay) {
            this.delay = delay;
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

        public Object getProfileBannerBackgroundColor() {
            return profileBannerBackgroundColor;
        }

        public void setProfileBannerBackgroundColor(Object profileBannerBackgroundColor) {
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
