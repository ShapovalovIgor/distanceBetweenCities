package ru.shapovalov.Servlets;

import ru.shapovalov.Class.DataBaseConection;
import ru.shapovalov.Class.Model.City;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by igor on 08.07.15.
 */

@WebServlet("/readDatabase")

public class ReadDatabase extends DispatcherServlet{
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean exceptionNull = false;

        String citySelect1;
        String citySelect2;
//      Declare the input text on index.jsp
        String text1 = "От ";
        String text2 = " до ";
        String text3 = " Метров";
        String text4 = "Или ";
        String text5 = " Километра.";
//      Get the selected city
        String selectSizeCity1 = request.getParameter("selectSizeCity1");
        String selectSizeCity2 = request.getParameter("selectSizeCity2");
//      We obtain and pass the city from the database
        Map<String, City> cityList = readDatabase();
            request.setAttribute("cityList", cityList);
//      We get the city by using its name
        City nameCity1 = cityList.get(selectSizeCity1);
        City nameCity2 = cityList.get(selectSizeCity2);
//      transfer data to jsp
        try {
            citySelect1 = nameCity1.getCity();
            citySelect2 = nameCity2.getCity();
            request.setAttribute("nameCity1", citySelect1);
            request.setAttribute("nameCity2", citySelect2);
        }catch (Exception e){
            exceptionNull = true;
            request.setAttribute("nameCity1", "There was a sudden error");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
//      If there is no error to calculate the distance between cities and return the result to the page
if(exceptionNull != true) {
    int m = CalculationOfDistance.calculation(nameCity1.getLatitude(), nameCity1.getLongitude(),
            nameCity2.getLatitude(), nameCity2.getLongitude());
    int km = m / 1000;
    request.setAttribute("m", m);
    request.setAttribute("km", km);
    request.setAttribute("text1", text1);
    request.setAttribute("text2", text2);
    request.setAttribute("text3", text3);
    request.setAttribute("text4", text4);
    request.setAttribute("text5", text5);
    citySelect1 = nameCity1.getCity();
    citySelect2 = nameCity2.getCity();
    request.setAttribute("text6", citySelect1);
    request.setAttribute("text7", citySelect2);
    request.getRequestDispatcher("/index.jsp").forward(request, response);
}
        exceptionNull = false;
    }
    public static Map readDatabase(){
        Locale.setDefault(Locale.ENGLISH);
        Statement statement = null;
        City city = null;
        Map<String, City> cityList = new HashMap<String, City>();
            try {
                statement = DataBaseConection.dbconection().createStatement();

            ResultSet rs = null;
                rs = statement.executeQuery("SELECT NAMECITY, LATITUDE, LONGITUDE\n" +
                        "FROM CITY");
            while (rs.next()) {
                city = new City();
                city.setCity(rs.getString(1));
                city.setLatitude(rs.getFloat(2));
                city.setLongitude(rs.getFloat(3));
                cityList.put(rs.getString(1), city);
            }
            rs.close();
            statement.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        return cityList;
    }
}