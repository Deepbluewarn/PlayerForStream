package club.rodong.playerforstream.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bluewarn on 2018-03-22.
 */

public class Twitch_New_Get_Users_Follows {
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("data")
    @Expose
    private java.util.List<Data> data = null;
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

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
    public class Pagination {

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

        @SerializedName("from_id")
        @Expose
        private String fromId;
        @SerializedName("to_id")
        @Expose
        private String toId;
        @SerializedName("followed_at")
        @Expose
        private String followedAt;

        public String getFromId() {
            return fromId;
        }

        public void setFromId(String fromId) {
            this.fromId = fromId;
        }

        public String getToId() {
            return toId;
        }

        public void setToId(String toId) {
            this.toId = toId;
        }

        public String getFollowedAt() {
            return followedAt;
        }

        public void setFollowedAt(String followedAt) {
            this.followedAt = followedAt;
        }

    }
}
