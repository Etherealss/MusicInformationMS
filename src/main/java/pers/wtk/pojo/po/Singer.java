package pers.wtk.pojo.po;

import lombok.Data;

import java.util.Date;

/**
 * @author wtk
 * @description
 * @date 2021-06-09
 */
@Data
public class Singer {

    private long id;
    private String singerName;
    private boolean sex;
    private Date birthday;
    private String tel;
    private String description;

    public Singer(String singerName) {
        this.singerName = singerName;
    }

    public Singer() {
    }

    public Singer(int id, String singerName, boolean sex) {
        this.id = id;
        this.singerName = singerName;
        this.sex = sex;
    }
}
