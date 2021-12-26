package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Parsing {
    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    static ArrayList<Sport> Pars() throws IOException, ParseException, SQLException {

        ArrayList<Sport> schoolsList = new ArrayList<Sport>();
        int i =0;
        List<String> readAllLines = Files.readAllLines(Paths.get("src/main/java/com/company/Sport (1).csv"));
        for (int j = 0; j < readAllLines.size(); j++) {
            if (i == 0) {
                i++;
                continue;
            }

            ArrayList<String> columnList = new ArrayList<String>();
            Collections.addAll(columnList, readAllLines.get(j).split(","));
            int n = 4;
            while (!columnList.get(n).replace("\"", "").matches("((?:19|20)[0-9][0-9])-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])"))
                n++;
            schoolsList.add(new Sport(
                    columnList.get(0),
                    columnList.get(1),
                    columnList.get(2),
                    columnList.get(3),
                    columnList.get(n).replace("\"", ""),
                    columnList.get(n + 1).replace("\"", ""),
                    columnList.get(n + 2),
                    Integer.parseInt(checkStringInInt(columnList,columnList.size() - 1))));

        }
        return schoolsList;
    }
    
    private static String checkStringInInt(ArrayList<String> columnList, int i){
        if(columnList.get(i).matches("^-?\\d+$"))
            return columnList.get(i);
        else
            return "0";
    }
}
