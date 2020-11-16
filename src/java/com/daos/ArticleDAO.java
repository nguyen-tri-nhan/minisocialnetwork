/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daos;

import com.dtos.ArticleDTO;
import com.util.DBUtils;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class ArticleDAO {

    private Connection conn = null;
    private PreparedStatement stm = null;
    private ResultSet rs = null;

    private void closeConnection() throws Exception {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {

        }

    }

    public List<ArticleDTO> getListArticles(int page) throws Exception {
        List<ArticleDTO> list = new ArrayList<>();
        try {
            //SELECT * FROM tblArticles ORDER BY createdate DESC
            /**
             * CREATE TABLE tblArticles( id varchar(20) NOT NULL PRIMARY KEY,
             * usermail varchar(50) FOREIGN KEY REFERENCES tblUsers(email),
             * title nvarchar(100), descripton nvarchar(1000), img varchar(100),
             * createdate datetime, upvote bigint, downvote bigint, isVisible
             * bit, )
             */
            String sql = "SELECT id, title, usermail, descripton, img, createdate, upvote,"
                    + "downvote, isVisible, firstname, lastname FROM tblArticles, tblUsers "
                    + " WHERE isVisible = 'True' AND usermail = email"
                    + " ORDER BY createdate DESC"
                    + " OFFSET (? - 1)* 5 ROWS"
                    + " FETCH NEXT 5 ROWS ONLY";
            conn = DBUtils.getConnection();
            if (conn != null) {
                stm = conn.prepareStatement(sql);
                stm.setInt(1, page);
                rs = stm.executeQuery();
                while (rs.next()) {
                    /**
                     * String articleID, userMail, title, description,
                     * imgAddress; long upvote, downvote; Date createDate;
                     * boolean visible;
                     */
                    String articleID = rs.getString("id");
                    String title = rs.getNString("title");
                    String userMail = rs.getString("usermail");
                    String fullName = rs.getString("lastname") + " " + rs.getString("firstname");
                    String description = rs.getNString("descripton");
                    String imgAddress = rs.getString("img");
                    Timestamp createDate = rs.getTimestamp("createdate");
                    long upvote = rs.getLong("upvote");
                    long downvote = rs.getLong("downvote");
                    boolean visible = rs.getBoolean("isVisible");
                    list.add(new ArticleDTO(articleID, userMail, fullName, title, description, imgAddress, upvote, downvote, createDate, visible));
                }
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return list;
    }

    public List<ArticleDTO> getListArticles(String search) throws Exception {
        List<ArticleDTO> list = new ArrayList<>();
        try {
            //SELECT * FROM tblArticles ORDER BY createdate DESC
            /**
             * CREATE TABLE tblArticles( id varchar(20) NOT NULL PRIMARY KEY,
             * usermail varchar(50) FOREIGN KEY REFERENCES tblUsers(email),
             * title nvarchar(100), descripton nvarchar(1000), img varchar(100),
             * createdate datetime, upvote bigint, downvote bigint, isVisible
             * bit, )
             */
            String sql = "SELECT id, usermail, title, descripton, img, createdate, upvote, downvote, isVisible, firstname, lastname"
                    + " FROM tblArticles, tblUsers WHERE isVisible = 'true'"
                    + " AND usermail = email AND descripton like ? ORDER BY createdate DESC";
            conn = DBUtils.getConnection();
            if (conn != null) {
                stm = conn.prepareStatement(sql);
//                stm.setBoolean(1, true);
                stm.setString(1, "%" + search + "%");
                rs = stm.executeQuery();
                while (rs.next()) {
                    /**
                     * String articleID, userMail, title, description,
                     * imgAddress; long upvote, downvote; Date createDate;
                     * boolean visible;
                     */
                    String articleID = rs.getString("id");
                    String title = rs.getNString("title");
                    String userMail = rs.getString("usermail");
                    String fullName = rs.getNString("lastname") + " " + rs.getNString("firstname");
                    String description = rs.getNString("descripton");
                    String imgAddress = rs.getString("img");
                    Timestamp createDate = rs.getTimestamp("createdate");
                    long upvote = rs.getLong("upvote");
                    long downvote = rs.getLong("downvote");
                    boolean visible = rs.getBoolean("isVisible");
                    list.add(new ArticleDTO(articleID, userMail, fullName, title, description, imgAddress, upvote, downvote, createDate, visible));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return list;
    }

    public ArticleDTO performArticleDetails(String articleID) throws Exception {
        ArticleDTO result = null;
        try {
            String sql = "SELECT  title, usermail, descripton, img, createdate, upvote,"
                    + "downvote, isVisible, firstname, lastname FROM tblArticles, tblUsers WHERE usermail = email and id = ?";
            conn = DBUtils.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, articleID);
            rs = stm.executeQuery();
            if (rs.next()) {
                String title = rs.getString("title");
                String userMail = rs.getString("usermail");
                String fullName = rs.getString("lastname") + " " + rs.getString("firstname");
                String description = rs.getNString("descripton");
                String imgAddress = rs.getString("img");
                Timestamp createDate = rs.getTimestamp("createdate");
                long upvote = rs.getLong("upvote");
                long downvote = rs.getLong("downvote");
                boolean visible = rs.getBoolean("isVisible");
                result = new ArticleDTO(articleID, userMail, fullName, title, description, imgAddress, upvote, downvote, createDate, visible);
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean insertArticle(ArticleDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "INSERT INTO tblArticles(id,usermail,title,descripton,img,createdate,upvote, downvote, isVisible) VALUES (?,?,?,?,?,?,?,?,?)";
            conn = DBUtils.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, dto.getArticleID());
            stm.setString(2, dto.getUserMail());
            stm.setString(3, dto.getTitle());
            stm.setString(4, dto.getDescription());
            stm.setString(5, dto.getImgAddress());
            stm.setTimestamp(6, dto.getCreateDate());
            stm.setLong(7, dto.getUpvote());
            stm.setLong(8, dto.getDownvote());
            stm.setBoolean(9, dto.isVisible());
            check = stm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println(e);
            check = false;
        } finally {
            closeConnection();
        }
        return check;
    }

    public String getLastID() throws Exception {
        String result = null;
        try {
            String sql = "SELECT MAX(id) FROM tblArticles";
            conn = DBUtils.getConnection();
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                result = rs.getString(1);
            } else {
                result = "BETA/0001/0000000";
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public void updateVote(String articleID) throws Exception {
        try {
            String sql = "UPDATE tblArticles"
                    + " SET upvote = (SELECT count(vote) FROM tblVote WHERE vote = ? AND articleID = ?),"
                    + " downvote = (SELECT count(vote) FROM tblVote WHERE vote = ? AND articleID = ?)"
                    + " WHERE id = ?";
            conn = DBUtils.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setInt(1, 1);
            stm.setString(2, articleID);
            stm.setInt(3, -1);
            stm.setString(4, articleID);
            stm.setString(5, articleID);
            stm.executeUpdate();
            System.out.println(sql);
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
    }
    
    public boolean hideArticle(String articleID) throws Exception{
        boolean check = false;
        try {
            String sql = "UPDATE tblArticles"
                    + " SET isVisible = ?"
                    + " WHERE id = ?";
            conn = DBUtils.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setBoolean(1, false);
            stm.setString(2, articleID);
            check = stm.executeUpdate() > 0;
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return check;
    }
    
    public String getArticleAuthorMail(String articleID) throws Exception{
        String userMail = "";
        try {
            String sql = "SELECT usermail FROM tblArticles WHERE id = ?";
            conn = DBUtils.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, articleID);
            rs = stm.executeQuery();
            if (rs.next()){
                userMail = rs.getString("usermail");
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return userMail;
    }
    
    public int countArticle() throws Exception{
        int count = 0;
        try {
            String sql = "SELECT COUNT(id) FROM tblArticles WHERE isVisible = 'True'";
            conn = DBUtils.getConnection();
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()){
                count = rs.getInt(1);
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return count;
    }
}
