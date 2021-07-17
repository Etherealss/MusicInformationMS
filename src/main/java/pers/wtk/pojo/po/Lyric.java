package pers.wtk.pojo.po;

import lombok.Data;

/**
 * @author wtk
 * @description 歌词
 * @date 2021-06-07
 */
@Data
public class Lyric {

    private long musicId;
    private String language;
    private String lyricText;
}
