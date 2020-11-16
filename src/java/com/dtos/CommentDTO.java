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
public class CommentDTO {

    /**
     * CREATE TABLE tblComment( id varchar(20) NOT NULL PRIMARY KEY, articleID
     * varchar(20) NOT NULL FOREIGN KEY REFERENCES tblArticles(id), usermail
     * varchar(50) NOT NULL FOREIGN KEY REFERENCES tblUsers(email), descripton
     * nvarchar(200), commentdate datetime, isVisible bit, isSeen bit, )
     *
     */
    private String id, fullname, articleID, userMail, description;
    Timestamp commentDate;
    boolean visible, seen;

    public CommentDTO() {
    }

    public CommentDTO(String id, String fullname, String articleID, String userMail, String description, Timestamp commentDate, boolean visible, boolean seen) {
        this.id = id;
        this.fullname = fullname;
        this.articleID = articleID;
        this.userMail = userMail;
        this.description = description;
        this.commentDate = commentDate;
        this.visible = visible;
        this.seen = seen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getArticleID() {
        return articleID;
    }

    public void setArticleID(String articleID) {
        this.articleID = articleID;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Timestamp commentDate) {
        this.commentDate = commentDate;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

}
