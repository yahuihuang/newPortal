package com.tssco.userservice.dto;

import com.tssco.userservice.entity.JWT;
import com.tssco.userservice.entity.User;

public class UserLoginDTO {
    private JWT jwt;

    private User user;

    public JWT getJwt() {
        return jwt;
    }

    public void setJwt(JWT jwt) {
        this.jwt = jwt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
