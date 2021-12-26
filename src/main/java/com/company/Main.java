package com.company;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.data.category.DefaultCategoryDataset;

public class Main {
    public static void main(String[] args) throws SQLException, IOException, ParseException {
        ArrayList<Sport> sports = Parsing.Pars();
        DataBase dataBase = new DataBase();
        /*for (Sport sport : sports) {
            dataBase.addInDataBase(sport);
        }*/
        System.out.println("Task1");
        var dataset = new DefaultCategoryDataset();
        for (var section : getAllSections(dataBase.connection)){
            int task1 = task1(section, dataBase.connection);
            System.out.println(String.format("%s -> %s",section, task1));
            dataset.setValue(task1, "Количество участников по виду спорта", section);
        }

        System.out.println("\nTask2");
        int i =0;
        ArrayList<String> previousCountry = new ArrayList<String>();
        List<String> countries = getAllCountries(dataBase.connection);
        for (String data : getAllData(dataBase.connection).split("\n")){
            String[] startAndStop = data.split(" ");
            if(Integer.parseInt(startAndStop[0]) == 2008 || Integer.parseInt(startAndStop[1]) ==2008)
            {
                String country = countries.get(i);
                if(!previousCountry.contains(country)){
                    int task2 = task2(countries.get(i),dataBase.connection);
                    System.out.println(String.format("%s -> %s",countries.get(i).replace("\"", ""), task2));
                }
                previousCountry.add(countries.get(i)) ;
            }
            i++;
        }


        System.out.println("\nTask3");
        task3(dataBase.connection);

        ChartUtils.saveChartAsPNG(new File("graphicFromTask1.png"),
                ChartFactory.createBarChart(
                        "Количество участников по виду спорта",
                        "Вид спорта", "Количество участников",
                        dataset), 50000, 3000);
    }

    public static List<String> getAllSections(Connection connection) throws SQLException {
        PreparedStatement sections = connection.prepareStatement("SELECT DISTINCT(section) FROM sport");
        List<String> list = new ArrayList<>();
        ResultSet e = sections.executeQuery();
        while(e.next()){
            list.add(e.getString(1));
        }
        return list;
    }

    public static List<String> getAllCountries(Connection connection) throws SQLException {
        PreparedStatement country = connection.prepareStatement("SELECT address FROM sport");
        List<String> list = new ArrayList<>();
        ResultSet e = country.executeQuery();
        while(e.next()){
            list.add(e.getString(1));
        }
        return list;
    }

    public static String getAllData(Connection connection) throws SQLException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        PreparedStatement startAndStop = connection.prepareStatement("SELECT start, stop FROM sport");
        StringBuilder str = new StringBuilder();
        ResultSet e = startAndStop.executeQuery();
        while(e.next()){
            str.append(getYear(formatter.parse(e.getString(1))) + " " + getYear(formatter.parse(e.getString(2))) + "\n");
        }
        return str.toString();
    }

    public static int getYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int task1(String str, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT SUM(participants) FROM sport WHERE section == ?");
        statement.setObject(1, str);
        return statement.executeQuery().getInt(1);
    }

    public static int task2(String str, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT SUM(participants) FROM sport WHERE address == ?");
        statement.setObject(1, str);
        return statement.executeQuery().getInt(1);
    }

    public static void task3(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT MAX(participants) FROM sport WHERE (section == '\"Восточное боевое единоборство\"') AND (subsection == '\"Молодежный (резервный) состав\"') ORDER BY participants ");
        System.out.println(statement.executeQuery().getInt(1));
    }
}
