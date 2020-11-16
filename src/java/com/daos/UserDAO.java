/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daos;

import com.dtos.UserDTO;
import com.util.DBUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Lenovo
 */
public class UserDAO {

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

    public boolean createUser(UserDTO dto) throws Exception {
        boolean check = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                //INSERT INTO tblUsers(email,firstname,lastname,password,registerdate,roleID,statusID) VALUES ('admin','admin','nhan','1','09/16/2020',-1,-1)
                String sql = "INSERT INTO tblUsers "
                        + "VALUES(?,?,?,?,?,?,?) ";
                stm = conn.prepareStatement(sql);
                stm.setString(1, dto.getEmail());
                stm.setString(2, dto.getFirstname());
                stm.setString(3, dto.getLastname());
                stm.setString(4, new DigestUtils("SHA-256").digestAsHex(dto.getPassword()));
                stm.setDate(5, dto.getRegisterdate());
                stm.setInt(6, dto.getRoleid());
                stm.setInt(7, dto.getStatusid());

                check = stm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            check = false;
            System.out.println(e);
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean isExistedEmail(String email) {
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT email from tblUsers WHERE email = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, email);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }
        } catch (Exception e) {

        } finally {

        }
        return false;
    }

    public UserDTO checkLogin(String email, String password) throws Exception {
        UserDTO result = null;
        try {
            String hexing = new DigestUtils("SHA-256").digestAsHex(password);
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT email, firstname, lastname, registerdate, roleID, statusID FROM tblUsers WHERE email = ? AND password = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, hexing);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String firstname = rs.getNString("firstname");
                    String lastname = rs.getNString("lastname");
                    Date registerdate = rs.getDate("registerdate");
                    int roleid = rs.getInt("roleID");
                    int statusid = rs.getInt("statusID");
                    result = new UserDTO(email, firstname, lastname,  "***", registerdate, roleid, statusid);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection();
        }
        return result;
    }
}
