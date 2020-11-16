/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.daos.ArticleDAO;
import com.daos.CommentDAO;
import com.daos.NotificationDAO;
import com.dtos.CommentDTO;
import com.dtos.NotificationDTO;
import com.dtos.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Lenovo
 */
public class NewCommentController extends HttpServlet {

    private static final String ERROR = "invalid.html";
    private static final String SUCCESS = "ArticleDetailController";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        HttpSession session = request.getSession();
        try {
//            String id, articleID, userMail, description;
//            Timestamp commentDate;
//            boolean visible, seen;
            CommentDAO dao = new CommentDAO();
            ArticleDAO articleDao = new ArticleDAO();
            NotificationDAO notiDao = new NotificationDAO();
            String lastCommentID = dao.getLastID();
            String commentID = upCommentID(lastCommentID);
            String articleID = request.getParameter("txtArticleID");
            String receiver = articleDao.getArticleAuthorMail(articleID);
            UserDTO userdto = (UserDTO) session.getAttribute("USER");
            String userMail = userdto.getEmail();
            String description = request.getParameter("txtComment");
            Timestamp commentDate = new Timestamp(System.currentTimeMillis());
            boolean visible = true;
            boolean seen = false;
            boolean check = dao.insertComment(new CommentDTO(commentID, null, articleID, userMail, description, commentDate, visible, seen));
            if (check) {
                if (!userMail.equals(receiver)) {
                    notiDao.insertNotification(new NotificationDTO(userMail, null, receiver, articleID, commentDate, true, false));
                }
                url = SUCCESS;
            }
        } catch (Exception e) {
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    public String upCommentID(String lastCommentID) {
        String result = "";
        String[] temp = lastCommentID.split("/");
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
