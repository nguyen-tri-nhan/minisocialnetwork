/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daos;

import com.dtos.NotificationDTO;
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
public class NotificationDAO {

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

    public void insertNotification(NotificationDTO dto) throws Exception {
        try {
            String sql = "INSERT INTO tblNotification (frommail, receivermail,"
                    + " notitype, notidate,isSeen,articleID) VALUES (?,?,?,?,?,?)";
            conn = DBUtils.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, dto.getFromMail());
            stm.setString(2, dto.getToMail());
            stm.setBoolean(3, dto.getType());
            stm.setTimestamp(4, dto.getNotiDate());
            stm.setBoolean(5, dto.isSeen());
            stm.setString(6, dto.getArticleID());
            stm.executeUpdate();
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
    }

    public List<NotificationDTO> getListNoti(String usermail) throws Exception {
        List<NotificationDTO> list = new ArrayList<>();
        try {
            String sql = "SELECT frommail, firstname, lastname, notitype, notidate,isSeen,articleID"
                    + " FROM tblNotification, tblUsers WHERE receivermail = ? AND frommail = email"
                    + " ORDER BY notidate DESC";
            conn = DBUtils.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, usermail);
            rs = stm.executeQuery();
            while (rs.next()) {
                String fromMail = rs.getString("frommail");
                String fullName = rs.getString("firstname") + " " + rs.getString("lastname");
                boolean notitype = rs.getBoolean("notitype");
                Timestamp notidate = rs.getTimestamp("notidate");
                String articleID = rs.getString("articleID");
                boolean seen = rs.getBoolean("isSeen");
                list.add(new NotificationDTO(fromMail, fullName, usermail, articleID, notidate, notitype, seen));
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return list;
    }
}
