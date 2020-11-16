/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dtos;

import java.sql.Timestamp;

/**
 *
 * @author Lenovo
 */
public class NotificationDTO {

    String fromMail, toMail, articleID, fullName;
    Timestamp notiDate;
    boolean type, seen;

    public NotificationDTO() {
    }

    public NotificationDTO(String fromMail, String fullName, String toMail, String articleID, Timestamp notiDate, boolean type, boolean seen) {
        this.fromMail = fromMail;
        this.toMail = toMail;
        this.fullName = fullName;
        this.articleID = articleID;
        this.notiDate = notiDate;
        this.type = type;
        this.seen = seen;
    }

    public String getFromMail() {
        return fromMail;
    }

    public void setFromMail(String fromMail) {
        this.fromMail = fromMail;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getToMail() {
        return toMail;
    }

    public void setToMail(String toMail) {
        this.toMail = toMail;
    }

    public String getArticleID() {
        return articleID;
    }

    public void setArticleID(String articleID) {
        this.articleID = articleID;
    }

    public Timestamp getNotiDate() {
        return notiDate;
    }

    public void setNotiDate(Timestamp notiDate) {
        this.notiDate = notiDate;
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

}
