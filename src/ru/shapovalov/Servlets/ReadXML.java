package ru.shapovalov.Servlets;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import ru.shapovalov.Class.DataBaseConection;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;
/**
 * Created by igor on 09.07.15.
 */

@WebServlet("/readXML")

public class ReadXML extends DispatcherServlet{
    private static boolean addCityDistance = true;
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<File> uploadFiles = uploadFiles(request);
//      We get the .xml file
        for(File file : uploadFiles)
        {

            if (validXML(file))
            {
                updateAndWriteDatabase(file);
                forward("/ReadDatabaseCityBetween", request, response);
            }
            else
            {
               request.setAttribute("infoXML", "File not been tested for validity !!!");
            }
        }
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

//      Updating the database if there is no such data, add them
    public static void updateAndWriteDatabase(File file){
        Locale.setDefault(Locale.ENGLISH);
        Statement statement = null;
        try {
            statement = DataBaseConection.dbconection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs;
//      Read xml file with the cities and the distance between them
        try {

            String fromCity;
            String toCity;
            int distance;
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
//            System.out.println("Root element :"
//                    + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("distanceBetweenCities");
            System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
//                System.out.println("\nCurrent Element :"
//                        + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    fromCity = eElement.getElementsByTagName("fromCity").item(0).getTextContent();
//                    System.out.println("From City : " + fromCity);
                    toCity = eElement.getElementsByTagName("ToCity").item(0).getTextContent();
//                    System.out.println("To City : " + toCity);
                    distance = Integer.parseInt(eElement.getElementsByTagName("distance").item(0).getTextContent());
//                    System.out.println("Distance : " + distance);
//      Check whether there is a basis of these cities
                    try {
                        rs = statement.executeQuery("SELECT FromCity, ToCity\n" +
                                "FROM DISTANCE " +
                                "WHERE FromCity = '" + fromCity + "' AND ToCity = '" + toCity + "'");
                        while ( rs.next()) {
                            if (rs.getString(1).equals(fromCity) && rs.getString(2).equals(toCity)) {
                                statement.executeQuery("UPDATE DISTANCE SET Distance = '" + distance + "'" +
                                        "WHERE FromCity = '" + fromCity + "' AND ToCity = '" + toCity + "'");
                                addCityDistance = false;
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if(addCityDistance){
                        statement.executeQuery("INSERT INTO DISTANCE (FromCity,ToCity,Distance) " +
                                "VALUES ('" + fromCity +"','" + toCity + "','" + distance + "')");
                    }
                }
                addCityDistance = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            DataBaseConection.dbconection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//      City and throw distance between the list
    private List<File> uploadFiles(HttpServletRequest req)
    {
        List<File> uploadedFiles = new ArrayList<File>();
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);

        if (isMultipart)
        {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            try
            {
                List items = upload.parseRequest(req);
                Iterator iterator = items.iterator();

                while (iterator.hasNext())
                {
                    FileItem item = (FileItem) iterator.next();

                    if (!item.isFormField())
                    {
                        String fileName = item.getName();
                        File uploadedFile = new File(fileName);
                        item.write(uploadedFile);
                        uploadedFiles.add(uploadedFile);
                    }
                }
            }
            catch (FileUploadException e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return uploadedFiles;
    }
//      Check the file on the validity of the accepted scheme
    private static boolean validXML(File xml)
    {
        boolean success = false;
        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        File schemaLocation = new File("/home/igor/distanceBetweenCities/shema.xml");

        try
        {
            Schema schema = factory.newSchema(schemaLocation);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(xml);
            validator.validate(source);
            success = true;
        }
        catch (SAXException ex)
        {
            ex.printStackTrace();
            success = false;
        }
        finally
        {
            return success;
        }
    }
}