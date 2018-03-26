package com.unifi.federicoguerri.traineeship_android.core;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class DataWriterToFile {

    private String filePath;

    public void writeToPath(String message) throws Exception {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filePath,true));
            message= message.trim().replaceAll(" +", " ");
            out.write(message+" ");
            out.close();
        } catch (Exception e) {
            throw new Exception("Failed to write to file");
        }
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
