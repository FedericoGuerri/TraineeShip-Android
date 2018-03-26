package com.unifi.federicoguerri.traineeship_android.core;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

public class DataWriterToFileUnitTest {

    private DataWriterToFile dataWriterToFile;
    private String path="file.txt";

    @Rule
    public TemporaryFolder folder= new TemporaryFolder();
    @Rule
    public ExpectedException writeRecordsFileException = ExpectedException.none();


    @Before
    public void init(){
        dataWriterToFile=new DataWriterToFile();
    }

    @Test
    public void dataWrite_filePathIsNullOnInit(){
        assertNull(dataWriterToFile.getFilePath());
    }

    @Test
    public void dataWrite_storeCorrectFilePath(){
        dataWriterToFile.setFilePath(path);
        assertEquals(path,dataWriterToFile.getFilePath());
    }

    @Test
    public void dataWrite_withNoSpecifiedPathThrowsException() throws Exception {
        writeRecordsFileException.expect(Exception.class);
        writeRecordsFileException.expectMessage("Failed to write to file");
        dataWriterToFile.writeToPath("message");
    }

    @Test
    public void dataWrite_writeStringToSpecifiedFile() throws Exception {
        folder.newFile(path);
        dataWriterToFile.setFilePath(folder.getRoot()+"/"+path);
        dataWriterToFile.writeToPath("22.2");
        String readed=readFromFile();
        assertTrue(readed.contains("22.2"));
    }


    @Test
    public void dataWrite_writeStringToSpecifiedFileAddingASpace() throws Exception {
        folder.newFile(path);
        dataWriterToFile.setFilePath(folder.getRoot()+"/"+path);
        dataWriterToFile.writeToPath("22.2");
        String readed=readFromFile();
        assertEquals("22.2 ",readed);
    }

    @Test
    public void dataWrite_shouldTerminateAllStringsWithASpace() throws Exception {
        folder.newFile(path);
        dataWriterToFile.setFilePath(folder.getRoot()+"/"+path);
        dataWriterToFile.writeToPath("22.2");
        dataWriterToFile.writeToPath("pathToMiniature");
        String readed=readFromFile();
        assertEquals("22.2 pathToMiniature ",readed);
    }

    @Test
    public void dataWrite_shouldNotWriteMoreSpacesToSpecifiedFile() throws Exception {
        folder.newFile(path);
        dataWriterToFile.setFilePath(folder.getRoot()+"/"+path);
        dataWriterToFile.writeToPath("     22.2");
        String readed=readFromFile();
        assertEquals("22.2 ",readed);
    }

    @Test
    public void dataWrite_shouldFormatStringToTerminateWithASpaceWhileThereAreMultipleSpaces() throws Exception {
        folder.newFile(path);
        dataWriterToFile.setFilePath(folder.getRoot()+"/"+path);
        dataWriterToFile.writeToPath("     22.2 -  22.2       pathToMiniature");
        String readed=readFromFile();
        assertEquals("22.2 - 22.2 pathToMiniature ",readed);
    }

    @Test
    public void dataWrite_shouldFormatMoreStringsToTerminateWithASpaceWhileThereAreMultipleSpaces() throws Exception {
        folder.newFile(path);
        dataWriterToFile.setFilePath(folder.getRoot()+"/"+path);
        dataWriterToFile.writeToPath("     22.2 -  22.2       pathToMiniature");
        dataWriterToFile.writeToPath("22.2        pathToMiniature");
        String readed=readFromFile();
        assertEquals("22.2 - 22.2 pathToMiniature 22.2 pathToMiniature ",readed);
    }


    @Test
    public void dataWrite_shouldNotReplaceOldStringsInTheSpecifiedFile() throws Exception {
        folder.newFile(path);
        dataWriterToFile.setFilePath(folder.getRoot()+"/"+path);
        dataWriterToFile.writeToPath("22.2old");
        dataWriterToFile.writeToPath("22.2new");
        String readed=readFromFile();
        assertEquals("22.2old 22.2new ",readed);
    }

    private String readFromFile() {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(folder.getRoot()+"/"+path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString().substring(0,contentBuilder.toString().length()-1);
    }


}
