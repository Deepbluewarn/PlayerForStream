package club.rodong.playerforstream.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bluewarn on 2018-03-22.
 */

public class Twitch_New_Get_Streams {
    @SerializedName("data")
    @Expose
    private java.util.List<Data> data = null;
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public java.util.List<Data> getData() {
        return data;
    }

    public void setData(java.util.List<Data> data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
    class Pagination {

        @SerializedName("cursor")
        @Expose
        private String cursor;

        public String getCursor() {
            return cursor;
        }

        public void setCursor(String cursor) {
            this.cursor = cursor;
        }

    }
    public class Data {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("game_id")
        @Expose
        private String gameId;
        @SerializedName("community_ids")
        @Expose
        private java.util.List<String> communityIds = null;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("viewer_count")
        @Expose
        private Integer viewerCount;
        @SerializedName("started_at")
        @Expose
        private String startedAt;
        @SerializedName("language")
        @Expose
        private String language;
        @SerializedName("thumbnail_url")
        @Expose
        private String thumbnailUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getGameId() {
            return gameId;
        }

        public void setGameId(String gameId) {
            this.gameId = gameId;
        }

        public java.util.List<String> getCommunityIds() {
            return communityIds;
        }

        public void setCommunityIds(java.util.List<String> communityIds) {
            this.communityIds = communityIds;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getViewerCount() {
            return viewerCount;
        }

        public void setViewerCount(Integer viewerCount) {
            this.viewerCount = viewerCount;
        }

        public String getStartedAt() {
            return startedAt;
        }

        public void setStartedAt(String startedAt) {
            this.startedAt = startedAt;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }

    }
}
