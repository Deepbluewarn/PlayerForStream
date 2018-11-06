package club.rodong.playerforstream.POJO;

import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class Twitch_v5_Get_Followed_Stream {

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
    public class Stream {

        @SerializedName("_id")
        @Expose
        private Long id;
        @SerializedName("game")
        @Expose
        private String game;
        @SerializedName("broadcast_platform")
        @Expose
        private String broadcastPlatform;
        @SerializedName("community_id")
        @Expose
        private String communityId;
        @SerializedName("community_ids")
        @Expose
        private List<Object> communityIds = null;
        @SerializedName("viewers")
        @Expose
        private Long viewers;
        @SerializedName("video_height")
        @Expose
        private Integer videoHeight;
        @SerializedName("average_fps")
        @Expose
        private Double averageFps;
        @SerializedName("delay")
        @Expose
        private Integer delay;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("is_playlist")
        @Expose
        private Boolean isPlaylist;
        @SerializedName("stream_type")
        @Expose
        private String streamType;
        @SerializedName("preview")
        @Expose
        private Preview preview;
        @SerializedName("channel")
        @Expose
        private Channel channel;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getGame() {
            return game;
        }

        public void setGame(String game) {
            this.game = game;
        }

        public String getBroadcastPlatform() {
            return broadcastPlatform;
        }

        public void setBroadcastPlatform(String broadcastPlatform) {
            this.broadcastPlatform = broadcastPlatform;
        }

        public String getCommunityId() {
            return communityId;
        }

        public void setCommunityId(String communityId) {
            this.communityId = communityId;
        }

        public List<Object> getCommunityIds() {
            return communityIds;
        }

        public void setCommunityIds(List<Object> communityIds) {
            this.communityIds = communityIds;
        }

        public Long getViewers() {
            return viewers;
        }

        public void setViewers(Long viewers) {
            this.viewers = viewers;
        }

        public Integer getVideoHeight() {
            return videoHeight;
        }

        public void setVideoHeight(Integer videoHeight) {
            this.videoHeight = videoHeight;
        }

        public Double getAverageFps() {
            return averageFps;
        }

        public void setAverageFps(Double averageFps) {
            this.averageFps = averageFps;
        }

        public Integer getDelay() {
            return delay;
        }

        public void setDelay(Integer delay) {
            this.delay = delay;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Boolean getIsPlaylist() {
            return isPlaylist;
        }

        public void setIsPlaylist(Boolean isPlaylist) {
            this.isPlaylist = isPlaylist;
        }

        public String getStreamType() {
            return streamType;
        }

        public void setStreamType(String streamType) {
            this.streamType = streamType;
        }

        public Preview getPreview() {
            return preview;
        }

        public void setPreview(Preview preview) {
            this.preview = preview;
        }

        public Channel getChannel() {
            return channel;
        }

        public void setChannel(Channel channel) {
            this.channel = channel;
        }

    }
    public class Preview {

        @SerializedName("small")
        @Expose
        private String small;
        @SerializedName("medium")
        @Expose
        private String medium;
        @SerializedName("large")
        @Expose
        private String large;
        @SerializedName("template")
        @Expose
        private String template;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

    }
    public class Channel {

        @SerializedName("mature")
        @Expose
        private Boolean mature;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("broadcaster_language")
        @Expose
        private String broadcasterLanguage;
        @SerializedName("broadcaster_software")
        @Expose
        private String broadcasterSoftware;
        @SerializedName("display_name")
        @Expose
        private String displayName;
        @SerializedName("game")
        @Expose
        private String game;
        @SerializedName("language")
        @Expose
        private String language;
        @SerializedName("_id")
        @Expose
        private Long id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("partner")
        @Expose
        private Boolean partner;
        @SerializedName("logo")
        @Expose
        private String logo;
        @SerializedName("video_banner")
        @Expose
        private String videoBanner;
        @SerializedName("profile_banner")
        @Expose
        private String profileBanner;
        @SerializedName("profile_banner_background_color")
        @Expose
        private String profileBannerBackgroundColor;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("views")
        @Expose
        private Long views;
        @SerializedName("followers")
        @Expose
        private Long followers;
        @SerializedName("broadcaster_type")
        @Expose
        private String broadcasterType;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("private_video")
        @Expose
        private Boolean privateVideo;
        @SerializedName("privacy_options_enabled")
        @Expose
        private Boolean privacyOptionsEnabled;

        public Boolean getMature() {
            return mature;
        }

        public void setMature(Boolean mature) {
            this.mature = mature;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBroadcasterLanguage() {
            return broadcasterLanguage;
        }

        public void setBroadcasterLanguage(String broadcasterLanguage) {
            this.broadcasterLanguage = broadcasterLanguage;
        }

        public String getBroadcasterSoftware() {
            return broadcasterSoftware;
        }

        public void setBroadcasterSoftware(String broadcasterSoftware) {
            this.broadcasterSoftware = broadcasterSoftware;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
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

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Boolean getPartner() {
            return partner;
        }

        public void setPartner(Boolean partner) {
            this.partner = partner;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getVideoBanner() {
            return videoBanner;
        }

        public void setVideoBanner(String videoBanner) {
            this.videoBanner = videoBanner;
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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Long getViews() {
            return views;
        }

        public void setViews(Long views) {
            this.views = views;
        }

        public Long getFollowers() {
            return followers;
        }

        public void setFollowers(Long followers) {
            this.followers = followers;
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

        public Boolean getPrivateVideo() {
            return privateVideo;
        }

        public void setPrivateVideo(Boolean privateVideo) {
            this.privateVideo = privateVideo;
        }

        public Boolean getPrivacyOptionsEnabled() {
            return privacyOptionsEnabled;
        }

        public void setPrivacyOptionsEnabled(Boolean privacyOptionsEnabled) {
            this.privacyOptionsEnabled = privacyOptionsEnabled;
        }

    }
}
