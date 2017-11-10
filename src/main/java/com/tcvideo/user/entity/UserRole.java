package com.tcvideo.user.entity;

import java.io.Serializable;

public class UserRole implements Serializable {
    private Integer uId;

    private Integer rId;

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getrId() {
        return rId;
    }

    public void setrId(Integer rId) {
        this.rId = rId;
    }
}