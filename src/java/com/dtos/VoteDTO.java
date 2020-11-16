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
public class VoteDTO {

    /**
     * articleID varchar(20) NOT NULL FOREIGN KEY REFERENCES tblArticles(id),
     * usermail varchar(50) NOT NULL FOREIGN KEY REFERENCES tblUsers(email),
     * vote int, votedate datetime, isSeen bit, PRIMARY KEY(articleID,
     * usermail),
     */
    String articleID, userMail;
    int vote;
    Timestamp voteDate;
    boolean seen;

    public VoteDTO() {
    }

    public VoteDTO(String articleID, String userMail, int vote, Timestamp voteDate, boolean isSeen) {
        this.articleID = articleID;
        this.userMail = userMail;
        this.vote = vote;
        this.voteDate = voteDate;
        this.seen = isSeen;
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

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public Timestamp getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(Timestamp voteDate) {
        this.voteDate = voteDate;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setIsSeen(boolean isSeen) {
        this.seen = isSeen;
    }

}
