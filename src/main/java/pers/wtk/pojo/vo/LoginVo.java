package pers.wtk.pojo.vo;

import lombok.Data;

/**
 * @author wtk
 * @description
 * @date 2021-06-19
 */
public class LoginVo {
    private Long userId;
    private String password;
    private Boolean isAdminLogin;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "userId=" + userId +
                ", password='" + password + '\'' +
                ", isAdminLogin=" + isAdminLogin +
                '}';
    }

    public boolean getIsAdminLogin() {
        return isAdminLogin;
    }

    public void setAdminLogin(boolean adminLogin) {
        isAdminLogin = adminLogin;
    }

    public LoginVo() {
    }
}
