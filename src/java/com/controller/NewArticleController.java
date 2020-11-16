/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.daos.ArticleDAO;
import com.dtos.ArticleDTO;
import com.dtos.UserDTO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Lenovo
 */
@MultipartConfig
public class NewArticleController extends HttpServlet {

    private static final String SUCCESS = "default.jsp";
    private static final String ERROR = "invalid.html";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
//    String articleID, userMail, title, description, imgAddress;
//    long upvote, downvote;
//    Date createDate;
//    boolean visible;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        HttpSession session = request.getSession(true);
        try {
            ArticleDTO dto = null;
            ArticleDAO dao = new ArticleDAO();
            UserDTO userdto = (UserDTO) session.getAttribute("USER");
            String maxArticleID = dao.getLastID();
            String articleID = upArticleID(maxArticleID);
            String title = request.getParameter("txtPostTitle");
            String description = request.getParameter("txtPostDescription");
            Part filePart = request.getPart("fileIMG");
            String fileName = articleID.split("/")[2] + ".jpg";
            String imageAddress = "images/" + fileName;
            uploadIMG(fileName, filePart);
            String usermail = userdto.getEmail();
            long upvote = 0, downvote = 0;
            Timestamp createdate = new Timestamp(System.currentTimeMillis());
            boolean visible = true;
            dto = new ArticleDTO(articleID, usermail, null, title, description, imageAddress, upvote, downvote, createdate, visible);
            if (dao.insertArticle(dto)) {
                url = SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }

    }

    public void uploadIMG(String fileName, Part filePart) {
        try {
            String applicationPath = "E:\\Project\\Java\\LAB231\\J3LP0010\\web";
            String basePath = applicationPath + File.separator + "images" + File.separator;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                File outputFilePath = new File(basePath + fileName);
                inputStream = filePart.getInputStream();
                outputStream = new FileOutputStream(outputFilePath);
                int read = 0;
                final byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String upArticleID(String maxArticleID) {
        String result = "";
        String[] temp = maxArticleID.split("/");
        int parsing = Integer.parseInt(temp[2]) + 1;
        if (parsing < 10) {
            result = temp[0] + "/" + temp[1] + "/" + "000000" + String.valueOf(parsing);
        } else if (parsing < 100) {
            result = temp[0] + "/" + temp[1] + "/" + "00000" + String.valueOf(parsing);
        } else if (parsing < 1000) {
            result = temp[0] + "/" + temp[1] + "/" + "0000" + String.valueOf(parsing);
        } else if (parsing < 10000) {
            result = temp[0] + "/" + temp[1] + "/" + "000" + String.valueOf(parsing);
        } else if (parsing < 100000) {
            result = temp[0] + "/" + temp[1] + "/" + "00" + String.valueOf(parsing);
        } else if (parsing < 1000000) {
            result = temp[0] + "/" + temp[1] + "/" + "0" + String.valueOf(parsing);
        } else {
            result = temp[0] + "/" + temp[1] + "/" + String.valueOf(parsing);
        }
        return result;
    }
  
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
