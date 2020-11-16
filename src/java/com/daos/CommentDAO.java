/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daos;

import com.dtos.CommentDTO;
import com.util.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class CommentDAO {

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

    public List<CommentDTO> loadAllCommentByArticleID(String articleID) throws Exception {
        List<CommentDTO> list = new ArrayList<>();
//        SELECT c.id ,c.articleID,u.firstname, u.lastname,c.descripton,c.commentdate,c.isVisible
//        ,c.isSeen FROM tblComment c, tblArticles a
//        , tblUsers u
//        WHERE c
//        .articleID = a.id AND u
//        .email = c.usermail
        try {
            String sql = "SELECT c.id,c.usermail ,c.articleID,u.firstname, u.lastname,c.descripton,c.commentdate,c.isVisible,c.isSeen"
                    + " FROM tblComment c, tblArticles a, tblUsers u"
                    + " WHERE c.articleID = a.id AND u.email = c.usermail AND c.articleID = ? AND c.isVisible = ?";
            conn = DBUtils.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, articleID);
            stm.setBoolean(2, true);
            rs = stm.executeQuery();
            while (rs.next()) {
//                String id, articleID, userMail, description;
//                Timestamp commentDate;
//                boolean visible, seen;
                String id = rs.getString("id");
                String userMail = rs.getString("usermail");
                String fullName = rs.getNString("lastname") + " " + rs.getNString("firstname");
                String description = rs.getNString("descripton");
                Timestamp commentDate = rs.getTimestamp("commentdate");
                boolean visible = rs.getBoolean("isVisible");
                boolean seen = rs.getBoolean("isSeen");
                list.add(new CommentDTO(id, fullName, articleID, userMail, description, commentDate, visible, seen));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return list;
    }

    public String getLastID() throws Exception {
        String result = null;
        try {
            String sql = "SELECT MAX(id) FROM tblComment";
            conn = DBUtils.getConnection();
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                result = rs.getString(1);
            } else {
                result = "BETACMT/0001/0000000";
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean insertComment(CommentDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "INSERT INTO tblComment(id, articleID, usermail, descripton, commentdate, isVisible, isSeen)"
                    + " VALUES (?,?,?,?,?,?,?)";
            conn = DBUtils.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, dto.getId());
            stm.setString(2, dto.getArticleID());
            stm.setString(3, dto.getUserMail());
            stm.setNString(4, dto.getDescription());
            stm.setTimestamp(5, dto.getCommentDate());
            stm.setBoolean(6, dto.isVisible());
            stm.setBoolean(7, dto.isSeen());
            check = stm.executeUpdate() > 0;
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean hideComment(String id) throws Exception {
        boolean check = false;
        try {
            String sql = "UPDATE tblComment"
                    + " SET isVisible = ?"
                    + " WHERE id = ?";
            conn = DBUtils.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setBoolean(1, false);
            stm.setString(2, id);
            check = stm.executeUpdate() > 0;
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return check;
    }

}
