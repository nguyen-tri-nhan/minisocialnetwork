/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.daos.ArticleDAO;
import com.daos.NotificationDAO;
import com.daos.VoteDAO;
import com.dtos.NotificationDTO;
import com.dtos.UserDTO;
import com.dtos.VoteDTO;
import java.io.IOException;
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
public class VoteController extends HttpServlet {

    private static final String INSIDE = "ArticleDetailController";
    private static final String OUTSIDE = "PagingController";
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        HttpSession session = request.getSession();
        try {
            ArticleDAO articleDAO = new ArticleDAO();
            UserDTO userDto = (UserDTO) session.getAttribute("USER");
            VoteDAO dao = new VoteDAO();
            NotificationDAO notiDao = new NotificationDAO();
            String voteState = request.getParameter("vote");
            String articleID = request.getParameter("txtArticleID");
            String userMail = userDto.getEmail();
            String location = request.getParameter("location");
            String receiver = articleDAO.getArticleAuthorMail(articleID);
            Timestamp voteDate = new Timestamp(System.currentTimeMillis());
            boolean checkExist = dao.checkExistedVote(articleID, userMail);
            if (checkExist) {
                int vote = dao.getCurrentVoteStatus(articleID, userMail);
                if (voteState.equals("down")) {
                    if (vote == -1) {
                        vote = 0;
                    } else {
                        vote = -1;
                    }
                } else {
                    if (vote == 1) {
                        vote = 0;
                    } else {
                        vote = 1;
                    }
                }
                boolean update = dao.updateVote(new VoteDTO(articleID, userMail, vote, voteDate, false));
                articleDAO.updateVote(articleID);
                if (update) {
                    if (location.equals("inside")) {
                        url = INSIDE;
                    } else {
                        url = OUTSIDE;
                    }
                }
            } else {
                int vote = 0;
                if (voteState.equals("down")) {
                    vote = -1;
                } else if (voteState.equals("up")) {
                    vote = 1;
                }
                boolean insert = dao.insertNewVote(new VoteDTO(articleID, userMail, vote, voteDate, false));
                articleDAO.updateVote(articleID);
                if (!userMail.equals(receiver)) {
                    notiDao.insertNotification(new NotificationDTO(userMail, null, receiver, articleID, voteDate, false, false));
                }
                if (insert) {
                    if (location.equals("inside")) {
                        url = INSIDE;
                    } else {
                        url = OUTSIDE;
                    }
                }
            }
        } catch (Exception e) {
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
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
