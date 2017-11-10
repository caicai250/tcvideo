package com.tcvideo.user.entity;

import java.io.Serializable;

public class RolePermission implements Serializable {
    private Integer rId;

    private Integer pId;

    public Integer getrId() {
        return rId;
    }

    public void setrId(Integer rId) {
        this.rId = rId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }
}