/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import jakarta.servlet.http.HttpSession;
import java.util.Collections;


@WebServlet("/customizetablecontroller")
public class CustomizeTableController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int rowsPerPage = Integer.parseInt(request.getParameter("rowsPerPage"));
        String[] selectedColumns = request.getParameterValues("columns");

        HttpSession session = request.getSession();
        session.setAttribute("rowsPerPage", rowsPerPage);
        try{
            session.setAttribute("columns", Arrays.asList(selectedColumns));
        }catch(NullPointerException e){
            session.setAttribute("columns", Collections.EMPTY_LIST);
        }
        
        response.sendRedirect(request.getContextPath()+"/PostListController");
    }
}