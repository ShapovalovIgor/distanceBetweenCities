package ru.shapovalov.Servlets;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by igor on 18.03.15.
 */
public class ErrorServlet extends DispatcherServlet{
    // Responsible for sudden error
    String loginUser;
    String passwordUser;
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        super.forward("/error.jsp", request, response);
    }
}
