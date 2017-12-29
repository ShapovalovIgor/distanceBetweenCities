package ru.shapovalov.Servlets;

import ru.shapovalov.Class.DataBaseConection;
import ru.shapovalov.Class.Model.City;
import ru.shapovalov.Class.Model.Distance;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by igor on 08.07.15.
 */

@WebServlet("/readDatabaseCityBetween")

public class ReadDatabaseCityBetween extends DispatcherServlet{
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//      Declare the input text on index.jsp
        String text1 = "От ";
        String text2 = " до ";
        String text3 = " Метров";
        String text4 = "Или ";
        String text5 = " Километра.";
        int distanceToCity = 0;
        Map<String, Integer> selectCityList = null;

//      Get the selected value from the list
        String selectSizeCity3 = request.getParameter("selectSizeCity3");
        String selectSizeCity4 = request.getParameter("selectSizeCity4");

//      Here, we keep the city and the distance between them
        Map<Distance, String> cityList = readDatabase();

        Set<String> cityListSelect1 = new HashSet<>();
//      Send the list of cities on the .jsp page
        for (Iterator iterator = cityList.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iterator.next();
            cityListSelect1.add((String)entry.getValue());
        }
        request.setAttribute("cityList1", cityListSelect1);

//      If the button 'Select City' is pressed on the .jsp page
// send the list of cities in which the distance is counted from the first Grod
        if (request.getParameter("selectCity") != null && !selectSizeCity3.equals(null)) {
           selectCityList = new HashMap<>();
            for (Iterator iterator = cityList.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Distance distance = (Distance) entry.getKey();
                if (selectSizeCity3.equals(entry.getValue())) {
                    selectCityList.put(distance.getToCity(), distance.getDistace());
                }
            }
            request.setAttribute("cityList2", selectCityList);
        }

//      If you press "View distance 'in both fields selected the city,
//      the distance between the output
        if (request.getParameter("viewSelect") != null && !selectSizeCity3.equals(null) && !selectSizeCity4.equals(null)) {
            for (Iterator iterator = cityList.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Distance distance = (Distance) entry.getKey();
                if (selectSizeCity3.equals(entry.getValue()) && distance.getToCity().equals(selectSizeCity4)) {
                   distanceToCity =  distance.getDistace();
                    request.setAttribute("text3", distanceToCity);
                }
            }
                request.setAttribute("nameCity1", selectSizeCity3);
                request.setAttribute("nameCity2", selectSizeCity4);

                int m = Integer.parseInt(String.valueOf(distanceToCity));
                int km = m / 1000;
                request.setAttribute("m", m);
                request.setAttribute("km", km);
                request.setAttribute("text1", text1);
                request.setAttribute("text2", text2);
                request.setAttribute("text3", text3);
                request.setAttribute("text4", text4);
                request.setAttribute("text5", text5);
                request.setAttribute("text6", selectSizeCity3);
                request.setAttribute("text7", selectSizeCity4);

            }
        request.getRequestDispatcher("/index.jsp").forward(request, response);
        }

    public static Map readDatabase(){
        Locale.setDefault(Locale.ENGLISH);
        Statement statement = null;
        Distance distance = null;
        Map<Distance, String> distanceList = new HashMap<Distance, String>();
            try {
                statement = DataBaseConection.dbconection().createStatement();

            ResultSet rs = null;
                rs = statement.executeQuery("SELECT FROMCITY, TOCITY, DISTANCE\n" +
                        "FROM DISTANCE");
            while (rs.next()) {
                distance = new Distance();
                distance.setFromCity(rs.getString(1));
                distance.setToCity(rs.getString(2));
                distance.setDistace(rs.getInt(3));
                distanceList.put(distance, rs.getString(1));
            }
            rs.close();
            statement.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        return distanceList;
    }
}