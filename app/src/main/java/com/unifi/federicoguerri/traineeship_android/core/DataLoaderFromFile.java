package com.unifi.federicoguerri.traineeship_android.core;


import android.graphics.drawable.Drawable;

import com.unifi.federicoguerri.traineeship_android.CustomDataSet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DataLoaderFromFile {

    private String filePath;
    private ArrayList<String> records;
    private ArrayList<CustomDataSet> loadedData;

    public DataLoaderFromFile(){
        records=new ArrayList<>();
        loadedData=new ArrayList<>();
    }

    public boolean loadFileFromPath(String filePath) throws IOException {
        this.filePath = filePath;
        return new File(filePath).createNewFile();
    }


    public String getFilePath() {
        return filePath;
    }


    public ArrayList<CustomDataSet> getRecords() throws Exception {
        readRecordsFromFile();
        loadDataFromReadRecords();
        return loadedData;
    }

    private void readRecordsFromFile() throws Exception {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
        } catch (NullPointerException e) {
            throw new NullPointerException("Failed To read from file");
        }
        String read;
        while ((read = reader.readLine()) != null) {
            String[] splittedLine = read.split("\\s+");
            for (String record : splittedLine) {
                if(record.contains(" ")){
                    record=record.replaceAll(" ","");
                }
                if(!record.equals("")) {
                    records.add(record);
                }
            }
        }
        try {
            reader.close();
        } catch (Exception e) {
            throw new Exception("Failed To close file");
        }
    }

    private void loadDataFromReadRecords() throws Exception{
        Iterator<String> iterator=records.iterator();
        while (iterator.hasNext()){
            String stringPrice=iterator.next();
            String stringMiniaturePath;
            try {
                stringMiniaturePath = iterator.next();
            }catch (NoSuchElementException e ){
                throw new NoSuchElementException("Failed To read a miniature");
            }
            Drawable miniature=null;
            if(!stringMiniaturePath.equals("noMiniature")){
                miniature = Drawable.createFromPath(stringMiniaturePath);
            }
            try {
                loadedData.add(new CustomDataSet(Float.valueOf(stringPrice),miniature));
            }catch (NumberFormatException e){
                throw new NumberFormatException("Failed To read a price");
            }
        }
    }
}
