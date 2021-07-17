package pers.wtk.pojo.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import pers.wtk.common.enums.MusicCharge;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wtk
 * @description
 * @date 2021-06-07
 */
@ApiModel("音乐实体")
@Data
public class Music {

    @ApiModelProperty(value = "id", required = true)
    private long id;
    @ApiModelProperty("音乐名")
    private String name;
    @ApiModelProperty("歌手")
    private List<Singer> singers = new ArrayList<>(1);
    @ApiModelProperty("歌词")
    private List<Lyric> lyrics = new ArrayList<>(1);
    @ApiModelProperty("媒体文件路径")
    private String mediaFilePath;
    @ApiModelProperty("歌曲发行时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;
    @ApiModelProperty("听歌权限")
    private int permission;
    @ApiModelProperty("价格")
    private float price;

    /**
     * @return 听歌权限的说明文本
     */
    public String getPermisssionMsg() {
        return MusicCharge.msgOf(permission);
    }

}
