package club.rodong.slitch.POJO;

import lombok.Data;

public @Data class EmoteObj {
    private String emote_url;
    private int emote_id;
    private int emoticon_sets;
    private String emote_code;

    private boolean isDivider;
}
