package pers.wtk.pojo.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author wtk
 * @description 用户/管理员
 * @date 2021-06-07
 */
@Data
public class User {

    private Long id;
    /** 用户名 */
    private String username;
    private String password;
    /** 男性为true */
    private boolean sex;
    /** 注册时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date registerDate;
    /** 余额 */
    private float balance;
    /** 管理员权限（包括非管理员） */
    private List<String> permissions;

}
