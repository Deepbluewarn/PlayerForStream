package club.rodong.playerforstream.POJO;

import lombok.Data;

/**
 * Created by Bluewarn on 2018-01-11.
 */

public @Data class LiveDetail {
    private int viewType;
    private String thumbnail_url;
    private String icon_url;
    private String title;
    private String platform;
    private String name;
    private String display_name;
    private int clap;
    private long viewcount;
    private long id;
}
