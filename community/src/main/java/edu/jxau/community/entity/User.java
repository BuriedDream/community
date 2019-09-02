package edu.jxau.community.entity;

import java.util.Date;
import java.util.Objects;

/**
 * @title: community
 * @ClassName User.java
 * @Description: 用户实体类
 * @Author: liam
 * @Version: 2.0
 **/
public class User {

    private int id;
    private String username;
    private String password;
    private String salt;
    private String email;
    /**
     * 0-普通用户; 1-超级管理员; 2-版主;
     */
    private int type;
    private int status;
    private String activationCode;
    private String headerUrl;
    private Date createTime;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 0-普通用户; 1-超级管理员; 2-版主;
     */
    public int getType() {
        return type;
    }

    /**
     * 0-普通用户; 1-超级管理员; 2-版主;
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * 0-未激活; 1-已激活;
     * @return
     */
    public int getStatus() {
        return status;
    }

    /**
     * 0-未激活; 1-已激活;
     * @param status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id &&
                type == user.type &&
                status == user.status &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(salt, user.salt) &&
                Objects.equals(email, user.email) &&
                Objects.equals(activationCode, user.activationCode) &&
                Objects.equals(headerUrl, user.headerUrl) &&
                Objects.equals(createTime, user.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, salt, email, type, status, activationCode, headerUrl, createTime);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", activationCode='" + activationCode + '\'' +
                ", headUrl='" + headerUrl + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
