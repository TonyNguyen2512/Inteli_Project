package org.onlinequizapp.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.onlinequizapp.daos.UserDAO;
import org.onlinequizapp.dtos.UserDTO;

public class LoginController extends HttpServlet {

    private static final String SUCCESS = "admindashboard.html";
    private static final String Student = "studentdashboard.html";
    private static final String Teacher = "teacherdashboard.html";
    private static final String ERROR = "login.html";
    private static final String SHOPPING = "index.html";

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
        try {
            String userID = request.getParameter("userID");
            String password = request.getParameter("password");
            UserDAO dao = new UserDAO();
            UserDTO user = dao.checkLogin(userID, password);
            HttpSession session = request.getSession();

            if (user != null) {
                session.setAttribute("LOGIN_USER", user);
                if (user.getRole().contains("AD")) {
                    url = SUCCESS;
                } else if (user.getRole().contains("C") || user.getRole().contains("M")) {
                    url = Teacher;
                } else if (user.getRole().contains("G") || user.getRole().contains("S")) {
                    url = Student;
                } else {
                    url = SHOPPING;
                }
            }
            String rememberLogin = request.getParameter("rememberMe");
            if ("on".equals(rememberLogin)) {
                //add cookie to user device if user choose to remember their login info
                Cookie cookieID = new Cookie("USERID", userID);
                cookieID.setMaxAge(0);

                Cookie cookiePassWord = new Cookie("PASSWORD", password);
                cookiePassWord.setMaxAge(0);

                response.addCookie(cookieID);
                response.addCookie(cookiePassWord);
            }
        } catch (Exception e) {
            log("Error at LoginController:" + e.toString());
        } finally {
            response.sendRedirect(url);

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
