package ru.shapovalov.Servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/dispatcherServlet")

public class DispatcherServlet extends HttpServlet {

    public void forward(String to, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispetcher = getServletContext().getRequestDispatcher(to);
        dispetcher.forward(request, response);
    }
    public DispatcherServlet(){}

}
