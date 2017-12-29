package ru.shapovalov.Servlets;
import ru.shapovalov.Class.DataBaseConection;
import ru.shapovalov.Class.Model.City;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by igor on 08.07.15.
 */

@WebServlet("/calculationOfDistance")

public class CalculationOfDistance extends DispatcherServlet{
    private static boolean addCityDistance = true;
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//      maximum and minimum values of latitude and longitude
        float maxLatitude = 90;
        float maxLongitude = 180;
        float minLatitude = -90;
        float minLongitude = -180;

        float A1;
        float A2;
        float B1;
        float B2;
//      Get the values entered on index.jsp
        String latitudeA1 = request.getParameter("latitudeA1");
        String latitudeA2 = request.getParameter("latitudeA2");
        String longitudeA1 = request.getParameter("longitudeA1");
        String longitudeA2 = request.getParameter("longitudeA2");
        String latitudeB1 = request.getParameter("latitudeB1");
        String latitudeB2 = request.getParameter("latitudeB2");
        String longitudeB1 = request.getParameter("longitudeB1");
        String longitudeB2 = request.getParameter("longitudeB2");
        String nameCity1 = request.getParameter("nameCity1");
        String nameCity2 = request.getParameter("nameCity2");
//      Declare the input text on index.jsp
        String text1 = "От ";
        String text2 = " до ";
        String text3 = " Метров";
        String text4 = "Или ";
        String text5 = " Километра.";

            if (Float.parseFloat(latitudeA1) == maxLatitude) {
                A1 = 90;
                request.setAttribute("latitudeA1", A1);
            } else if (Float.parseFloat(latitudeA1) == minLatitude) {
                A1 = -90;
                request.setAttribute("latitudeA1", A1);
            } else {
                A1 = Float.parseFloat(latitudeA1 + "." + latitudeA2);
                request.setAttribute("latitudeA1", latitudeA1);
                request.setAttribute("latitudeA2", latitudeA2);
            }
//      We check the correctness of the entered data if invalid data to correct them
            if (Float.parseFloat(longitudeA1) == maxLongitude) {
                A2 = 180;
                request.setAttribute("longitudeA1", A1);
            } else if (Float.parseFloat(longitudeA1) == minLongitude) {
                A2 = -180;
                request.setAttribute("longitudeA1", A1);
            } else {
                A2 = Float.parseFloat(longitudeA1 + "." + longitudeA2);
                request.setAttribute("longitudeA1", A1);
                request.setAttribute("longitudeA2", A2);
            }
            if (Float.parseFloat(latitudeB1) == maxLatitude) {
                B1 = 90;
                request.setAttribute("latitudeB1", B1);
            } else if (Float.parseFloat(latitudeB1) == minLatitude) {
                B1 = -90;
                request.setAttribute("latitudeB1", B1);
            } else {
                B1 = Float.parseFloat(latitudeB1 + "." + latitudeB2);
                request.setAttribute("latitudeB1", latitudeB1);
                request.setAttribute("latitudeB2", latitudeB2);
            }
            if (Float.parseFloat(longitudeB1) == maxLongitude) {
                B2 = 180;
                request.setAttribute("longitudeB1", B1);
            } else if (Float.parseFloat(longitudeB1) == minLongitude) {
                B2 = -180;
                request.setAttribute("longitudeB1", B1);
            } else {
                B2 = Float.parseFloat(longitudeB1 + "." + longitudeB2);
                request.setAttribute("longitudeB1", B1);
                request.setAttribute("longitudeB2", B2);
            }
//      We send latitude and longitude of the two settlements in the calculation of the distance between them
        int m = calculation(A1, A2, B1, B2);

            int km = m/1000;
//      Conclusions The results
            request.setAttribute("m", m);
            request.setAttribute("km", km);
            request.setAttribute("nameCity1", nameCity1);
            request.setAttribute("nameCity2", nameCity2);
            request.setAttribute("text1", text1);
            request.setAttribute("text2", text2);
            request.setAttribute("text3", text3);
            request.setAttribute("text4", text4);
            request.setAttribute("text5", text5);
//      Write result to database
            writeCityInDatabase(nameCity1, A1, A2);
            writeCityInDatabase(nameCity2, B1, B2);

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    public static int calculation(float A1, float A2, float B1, float B2) {
        int  R = 6372795;//Radius Earth
//      translate coordinates in radians
        double latitudeA = A1*Math.PI/180;
        double latitudeB = B1*Math.PI/180;
        double longitudeA = A2*Math.PI/180;
        double longitudeB = B2*Math.PI/180;
//      cosine and sine of the latitude and longitude difference
        double cl1 = Math.cos(latitudeA);
        double cl2 = Math.cos(latitudeB);
        double sl1 = Math.sin(latitudeA);
        double sl2 = Math.sin(latitudeB);
        double delta = longitudeA - longitudeB;
        double cdelta = Math.cos(delta);
        double sdelta = Math.sin(delta);
//      calculation of the length of the great circle
        double y = Math.sqrt(Math.pow(cl2*sdelta, 2)+Math.pow(cl1 * sl2 - sl1 * cl2 * cdelta, 2));
        double x = sl1*sl2+cl1*cl2*cdelta;

        double ad = Math.atan2(y, x);
        int dist = new BigDecimal(ad * R).setScale(3, RoundingMode.UP).intValue();
        return dist;//Output in meters
    }

    //Проверяем уникальность имён, если имя существут обновляем данные в базе, а если имя уникально добовляем новый город в базу
    public void writeCityInDatabase(String nameCity, float latitude, float longitude){

        Locale.setDefault(Locale.ENGLISH);
        Statement statement = null;
        try {
            statement = DataBaseConection.dbconection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs;
        try {
            rs = statement.executeQuery("SELECT NAMECITY\n" +
                    "FROM CITY\n" +
                    "WHERE NAMECITY = '" + nameCity + "'");
            while ( rs.next()) {
                if (rs.getString(1).equals(nameCity)) {
                    statement.executeQuery("UPDATE CITY SET LATITUDE = '" + latitude + "', LONGITUDE = '" + longitude + "' "+
                            "WHERE NAMECITY = '" + nameCity + "'");
                    addCityDistance = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(addCityDistance){
            try {
                statement.executeQuery("INSERT INTO CITY (NAMECITY, LATITUDE, LONGITUDE) " +
                        "VALUES ('" + nameCity +"','" + latitude + "','" + longitude + "')");
                addCityDistance = true;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            DataBaseConection.dbconection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}