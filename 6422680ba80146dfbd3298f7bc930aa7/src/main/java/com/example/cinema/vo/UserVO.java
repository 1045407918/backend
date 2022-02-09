package com.example.cinema.vo;

import com.example.cinema.po.User;

/**
 * @author fjj
 * @date 2019/4/11 3:22 PM
 */
public class UserVO {
    private Integer id;
    private String username;
    private String password;
    /**
     * 用户操作权限等级，观众为0，影院经理为1，管理员为2
     */
    private int userlevel;

    public UserVO(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.userlevel= user.getUserlevel();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserlevel(){
        return userlevel;
    }

    public void setUserlevel(int userlevel){
        this.userlevel=userlevel;
    }
}
