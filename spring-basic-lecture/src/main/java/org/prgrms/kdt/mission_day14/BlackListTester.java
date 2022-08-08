package org.prgrms.kdt.mission_day14;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;

public class BlackListTester {
    public static void main(String[] args) throws FileNotFoundException {
        BlackListTester blackListTester = new BlackListTester();
        System.out.println(MessageFormat.format("BlackList : {0}", blackListTester.readCSV()));
    }

    private List<List<String>> readCSV() throws FileNotFoundException {
        List<List<String>> blackList = new ArrayList<List<String>>();
        String path = "C:\\Users\\user\\Desktop\\stopeun\\SpringBasic\\spring-basic-lecture\\src\\main\\java\\org\\prgrms\\kdt\\mission_day14";
        File file = new File(MessageFormat.format("{0}\\customer_blacklist.csv", path));
        BufferedReader br = new BufferedReader(new BufferedReader(new FileReader(file)));
        String line = "";

        try{
            while ((line = br.readLine()) != null) {
                List<String> aLine = new ArrayList<String>();
                String[] lineArr = line.split(",");
                aLine = Arrays.asList(lineArr);
                blackList.add(aLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close(); // 사용 후 BufferedReader를 닫아준다.
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return blackList;
    }
}
