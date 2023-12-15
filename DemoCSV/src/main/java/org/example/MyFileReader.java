package org.example;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public  class MyFileReader {
    public CsvObjectStructure[] read(File file) throws  Exception{
        CsvObjectStructure csvObject;
        String[] records;
        CSVReader csvReader = null;
        final int MAX_RECORDS = 1000;
        CsvObjectStructure[] allRecords = new CsvObjectStructure[MAX_RECORDS];
        int recordCount = 0;

        try {
            CSVParser csvParser = new CSVParserBuilder()
                    .withSeparator(',') // Set the separator (comma in this case)
                    .build();

            // Create a CSVReader using the CSVParser and the File
            csvReader = new CSVReaderBuilder(new FileReader(file)) // Assuming 'file' is a File object
                    .withCSVParser(csvParser) // Set the created CSVParser
                    .build();

            while((records = csvReader.readNext()) != null){
                if(records.length == 9){
                    String ideaName = records[0];
                    String ideaDesc = records[1];
                    String voteCount = records[2];
                    String topic1 = records[3];
                    String topic2 = records[4];
                    String topic3 = records[5];
                    String status = records[6];
                    String authorsName = records[7];
                    String authorsEmail = records[8];

                    try{
                        int numericValue = Integer.parseInt(voteCount);
//                        System.out.println("Idea Name: " + ideaName + " " +"Vote Count: " + numericValue                        );
//                        System.out.println("Idea Description: " + ideaDesc);
//                        System.out.println("Topic 1: " + topic1 + " " + "Topic 2: " + topic2 + " " + "Topic 3: " + topic3 +
//                                " " + "Status: " + status + "Authors Name: " + authorsName + "Authors Email: " + authorsEmail);

                        csvObject = new CsvObjectStructure(
                                ideaName,
                                ideaDesc,
                                numericValue,
                                topic1,
                                topic2,
                                topic3,
                                status,
                                authorsName,
                                authorsEmail);

                        allRecords[recordCount] = csvObject;
                        recordCount++;



                    }catch (NumberFormatException e){

                        System.err.println("Invalid value: " + voteCount);
                    }
                    }else {
                    System.err.println("Invalid tuple length: " + String.join(",", records));
                }
                }

        csvReader.close();
            }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return Arrays.copyOf(allRecords, recordCount);
    }
}

