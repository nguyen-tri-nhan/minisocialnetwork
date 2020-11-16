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
public class ArticleDTO {
//    CREATE TABLE tblArticles(
//	id varchar(20) NOT NULL PRIMARY KEY,
//	usermail varchar(50) FOREIGN KEY REFERENCES tblUsers(email),
//	title nvarchar(100),
//	descripton nvarchar(1000),
//	img nvarchar,
//	createdate datetime,
//	upvote bigint,
//	downvote bigint,
//	isVisible bit,
//)

    String articleID, userMail, fullName, title, description, imgAddress;
    long upvote, downvote;
    Timestamp createDate;
    boolean visible;

    public ArticleDTO() {
    }

    public ArticleDTO(String articleID, String userMail, String fullName, String title, String description, String imgAddress, long upvote, long downvote, Timestamp createDate, boolean visible) {
        this.articleID = articleID;
        this.userMail = userMail;
        this.fullName = fullName;
        this.title = title;
        this.description = description;
        this.imgAddress = imgAddress;
        this.upvote = upvote;
        this.downvote = downvote;
        this.createDate = createDate;
        this.visible = visible;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    public long getUpvote() {
        return upvote;
    }

    public void setUpvote(long upvote) {
        this.upvote = upvote;
    }

    public long getDownvote() {
        return downvote;
    }

    public void setDownvote(long downvote) {
        this.downvote = downvote;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
