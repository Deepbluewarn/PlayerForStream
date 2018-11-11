package club.rodong.slitch.POJO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Twitch_Channel_Subscriber_Badges {

    @SerializedName("badge_sets")
    @Expose
    private BadgeSets badgeSets;

    public BadgeSets getBadgeSets() {
        return badgeSets;
    }

    public void setBadgeSets(BadgeSets badgeSets) {
        this.badgeSets = badgeSets;
    }
    public class BadgeSets {

        @SerializedName("subscriber")
        @Expose
        private Subscriber subscriber;

        public Subscriber getSubscriber() {
            return subscriber;
        }

        public void setSubscriber(Subscriber subscriber) {
            this.subscriber = subscriber;
        }

    }
    public class Subscriber {

        @SerializedName("versions")
        @Expose
        private Versions versions;

        public Versions getVersions() {
            return versions;
        }

        public void setVersions(Versions versions) {
            this.versions = versions;
        }

    }
    public class Versions {

        @SerializedName("0")
        @Expose
        private _0 _0;
        @SerializedName("12")
        @Expose
        private _12 _12;
        @SerializedName("3")
        @Expose
        private _3 _3;
        @SerializedName("6")
        @Expose
        private _6 _6;

        public _0 get0() {
            return _0;
        }

        public void set0(_0 _0) {
            this._0 = _0;
        }

        public _12 get12() {
            return _12;
        }

        public void set12(_12 _12) {
            this._12 = _12;
        }

        public _3 get3() {
            return _3;
        }

        public void set3(_3 _3) {
            this._3 = _3;
        }

        public _6 get6() {
            return _6;
        }

        public void set6(_6 _6) {
            this._6 = _6;
        }

    }
    public class _0 {

        @SerializedName("image_url_1x")
        @Expose
        private String imageUrl1x;
        @SerializedName("image_url_2x")
        @Expose
        private String imageUrl2x;
        @SerializedName("image_url_4x")
        @Expose
        private String imageUrl4x;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("click_action")
        @Expose
        private String clickAction;
        @SerializedName("click_url")
        @Expose
        private String clickUrl;

        public String getImageUrl1x() {
            return imageUrl1x;
        }

        public void setImageUrl1x(String imageUrl1x) {
            this.imageUrl1x = imageUrl1x;
        }

        public String getImageUrl2x() {
            return imageUrl2x;
        }

        public void setImageUrl2x(String imageUrl2x) {
            this.imageUrl2x = imageUrl2x;
        }

        public String getImageUrl4x() {
            return imageUrl4x;
        }

        public void setImageUrl4x(String imageUrl4x) {
            this.imageUrl4x = imageUrl4x;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getClickAction() {
            return clickAction;
        }

        public void setClickAction(String clickAction) {
            this.clickAction = clickAction;
        }

        public String getClickUrl() {
            return clickUrl;
        }

        public void setClickUrl(String clickUrl) {
            this.clickUrl = clickUrl;
        }

    }
    public class _12 {

        @SerializedName("image_url_1x")
        @Expose
        private String imageUrl1x;
        @SerializedName("image_url_2x")
        @Expose
        private String imageUrl2x;
        @SerializedName("image_url_4x")
        @Expose
        private String imageUrl4x;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("click_action")
        @Expose
        private String clickAction;
        @SerializedName("click_url")
        @Expose
        private String clickUrl;

        public String getImageUrl1x() {
            return imageUrl1x;
        }

        public void setImageUrl1x(String imageUrl1x) {
            this.imageUrl1x = imageUrl1x;
        }

        public String getImageUrl2x() {
            return imageUrl2x;
        }

        public void setImageUrl2x(String imageUrl2x) {
            this.imageUrl2x = imageUrl2x;
        }

        public String getImageUrl4x() {
            return imageUrl4x;
        }

        public void setImageUrl4x(String imageUrl4x) {
            this.imageUrl4x = imageUrl4x;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getClickAction() {
            return clickAction;
        }

        public void setClickAction(String clickAction) {
            this.clickAction = clickAction;
        }

        public String getClickUrl() {
            return clickUrl;
        }

        public void setClickUrl(String clickUrl) {
            this.clickUrl = clickUrl;
        }

    }
    public class _3 {

        @SerializedName("image_url_1x")
        @Expose
        private String imageUrl1x;
        @SerializedName("image_url_2x")
        @Expose
        private String imageUrl2x;
        @SerializedName("image_url_4x")
        @Expose
        private String imageUrl4x;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("click_action")
        @Expose
        private String clickAction;
        @SerializedName("click_url")
        @Expose
        private String clickUrl;

        public String getImageUrl1x() {
            return imageUrl1x;
        }

        public void setImageUrl1x(String imageUrl1x) {
            this.imageUrl1x = imageUrl1x;
        }

        public String getImageUrl2x() {
            return imageUrl2x;
        }

        public void setImageUrl2x(String imageUrl2x) {
            this.imageUrl2x = imageUrl2x;
        }

        public String getImageUrl4x() {
            return imageUrl4x;
        }

        public void setImageUrl4x(String imageUrl4x) {
            this.imageUrl4x = imageUrl4x;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getClickAction() {
            return clickAction;
        }

        public void setClickAction(String clickAction) {
            this.clickAction = clickAction;
        }

        public String getClickUrl() {
            return clickUrl;
        }

        public void setClickUrl(String clickUrl) {
            this.clickUrl = clickUrl;
        }

    }
    public class _6 {

        @SerializedName("image_url_1x")
        @Expose
        private String imageUrl1x;
        @SerializedName("image_url_2x")
        @Expose
        private String imageUrl2x;
        @SerializedName("image_url_4x")
        @Expose
        private String imageUrl4x;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("click_action")
        @Expose
        private String clickAction;
        @SerializedName("click_url")
        @Expose
        private String clickUrl;

        public String getImageUrl1x() {
            return imageUrl1x;
        }

        public void setImageUrl1x(String imageUrl1x) {
            this.imageUrl1x = imageUrl1x;
        }

        public String getImageUrl2x() {
            return imageUrl2x;
        }

        public void setImageUrl2x(String imageUrl2x) {
            this.imageUrl2x = imageUrl2x;
        }

        public String getImageUrl4x() {
            return imageUrl4x;
        }

        public void setImageUrl4x(String imageUrl4x) {
            this.imageUrl4x = imageUrl4x;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getClickAction() {
            return clickAction;
        }

        public void setClickAction(String clickAction) {
            this.clickAction = clickAction;
        }

        public String getClickUrl() {
            return clickUrl;
        }

        public void setClickUrl(String clickUrl) {
            this.clickUrl = clickUrl;
        }

    }
}