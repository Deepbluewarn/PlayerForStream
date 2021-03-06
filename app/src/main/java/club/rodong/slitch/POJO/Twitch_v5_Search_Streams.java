package club.rodong.slitch.POJO;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Twitch_v5_Search_Streams {

    @SerializedName("_total")
    @Expose
    private Integer total;
    @SerializedName("streams")
    @Expose
    private List<Stream> streams = null;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Stream> getStreams() {
        return streams;
    }

    public void setStreams(List<Stream> streams) {
        this.streams = streams;
    }

    class Stream {

        @SerializedName("_id")
        @Expose
        private Integer id;
        @SerializedName("average_fps")
        @Expose
        private Double averageFps;
        @SerializedName("channel")
        @Expose
        private Channel channel;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("delay")
        @Expose
        private Integer delay;
        @SerializedName("game")
        @Expose
        private String game;
        @SerializedName("is_playlist")
        @Expose
        private Boolean isPlaylist;
        @SerializedName("preview")
        @Expose
        private Preview preview;
        @SerializedName("video_height")
        @Expose
        private Integer videoHeight;
        @SerializedName("viewers")
        @Expose
        private Integer viewers;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Double getAverageFps() {
            return averageFps;
        }

        public void setAverageFps(Double averageFps) {
            this.averageFps = averageFps;
        }

        public Channel getChannel() {
            return channel;
        }

        public void setChannel(Channel channel) {
            this.channel = channel;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Integer getDelay() {
            return delay;
        }

        public void setDelay(Integer delay) {
            this.delay = delay;
        }

        public String getGame() {
            return game;
        }

        public void setGame(String game) {
            this.game = game;
        }

        public Boolean getIsPlaylist() {
            return isPlaylist;
        }

        public void setIsPlaylist(Boolean isPlaylist) {
            this.isPlaylist = isPlaylist;
        }

        public Preview getPreview() {
            return preview;
        }

        public void setPreview(Preview preview) {
            this.preview = preview;
        }

        public Integer getVideoHeight() {
            return videoHeight;
        }

        public void setVideoHeight(Integer videoHeight) {
            this.videoHeight = videoHeight;
        }

        public Integer getViewers() {
            return viewers;
        }

        public void setViewers(Integer viewers) {
            this.viewers = viewers;
        }

    }

    class Preview {

        @SerializedName("large")
        @Expose
        private String large;
        @SerializedName("medium")
        @Expose
        private String medium;
        @SerializedName("small")
        @Expose
        private String small;
        @SerializedName("template")
        @Expose
        private String template;

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

    }

    class Channel {

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
