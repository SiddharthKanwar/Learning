package com.example.Learning;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.List;


public class ScrapDataCsv {

    public static void createCsv(List<ScrapData> dataList){
        File file=new File("/home/jyoti/Desktop/ScrapData2.csv");
        try{
            FileWriter outputFile=new FileWriter(file);
            CSVWriter writer=new CSVWriter(outputFile);
            String[] header= HPTourismClientCSVFormatter.keyMapper.keySet().toArray(String[]::new);
            writer.writeNext(header);
            dataList.stream().map(HPTourismClientCSVFormatter::format).forEach(scrapData ->
                    writer.writeNext(scrapData.values().stream().map(Object::toString).toArray(String[]::new)));
            writer.close();
            System.out.println("CSV file created");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
