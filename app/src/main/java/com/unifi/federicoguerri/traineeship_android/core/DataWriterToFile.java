package com.unifi.federicoguerri.traineeship_android.core;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class DataWriterToFile {

    private String filePath;

    public void writeToPath(String message, boolean append) throws CustomException {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(filePath,append))){
            message= message.trim().replaceAll(" +", " ");
            out.write(message+" ");
        } catch (Exception e) {
            throw new CustomException("Failed to write to file");
        }
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
