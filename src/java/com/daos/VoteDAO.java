/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daos;

import com.dtos.VoteDTO;
import com.util.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Lenovo
 */
public class VoteDAO {

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

    public boolean updateVote(VoteDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "UPDATE tblVote"
                    + " SET vote = ?"
                    + " WHERE articleID = ? AND usermail = ?";
            conn = DBUtils.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setInt(1, dto.getVote());
            stm.setString(2, dto.getArticleID());
            stm.setString(3, dto.getUserMail());
            check = stm.executeUpdate() > 0;
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean insertNewVote(VoteDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "INSERT INTO tblVote (articleID, usermail,vote, votedate, isSeen) VALUES (?,?,?,?,?)";
            conn = DBUtils.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, dto.getArticleID());
            stm.setString(2, dto.getUserMail());
            stm.setInt(3, dto.getVote());
            stm.setTimestamp(4, dto.getVoteDate());
            stm.setBoolean(5, dto.isSeen());
            check = stm.executeUpdate() > 0;
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return check;
    }

    public long countVote(String articleID, int vote) throws Exception {
        long result = -1;
        try {
            String sql = "SELECT count(vote) FROM tblVote WHERE articleID = ? AND vote = ?";
            conn = DBUtils.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, articleID);
            stm.setInt(2, vote);
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean checkExistedVote(String articleID, String userMail) throws Exception {
        boolean check = false;
        try {
            String sql = "SELECT articleID,usermail FROM tblVote WHERE articleID = ? AND usermail = ?";
            conn = DBUtils.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, articleID);
            stm.setString(2, userMail);
            rs = stm.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return check;
    }
    
    public int getCurrentVoteStatus(String articleID, String userMail) throws Exception{
        int vote = 0;
        try {
            String sql = "SELECT vote FROM tblVote WHERE articleID = ? AND usermail = ?";
            conn = DBUtils.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, articleID);
            stm.setString(2, userMail);
            rs = stm.executeQuery();
            if (rs.next()){
                vote = rs.getInt(1);
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return vote;
    }
}
