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
        dataWriterToFile.writeToPath("message",true);
    }

    @Test
    public void dataWrite_writeStringToSpecifiedFile() throws Exception {
        folder.newFile(path);
        dataWriterToFile.setFilePath(folder.getRoot()+"/"+path);
        dataWriterToFile.writeToPath("22.2",true);
        String readed=readFromFile();
        assertTrue(readed.contains("22.2"));
    }


    @Test
    public void dataWrite_writeStringToSpecifiedFileAddingASpace() throws Exception {
        folder.newFile(path);
        dataWriterToFile.setFilePath(folder.getRoot()+"/"+path);
        dataWriterToFile.writeToPath("22.2",true);
        String readed=readFromFile();
        assertEquals("22.2 ",readed);
    }

    @Test
    public void dataWrite_shouldTerminateAllStringsWithASpace() throws Exception {
        folder.newFile(path);
        dataWriterToFile.setFilePath(folder.getRoot()+"/"+path);
        dataWriterToFile.writeToPath("22.2",true);
        dataWriterToFile.writeToPath("pathToMiniature",true);
        String readed=readFromFile();
        assertEquals("22.2 pathToMiniature ",readed);
    }

    @Test
    public void dataWrite_shouldNotWriteMoreSpacesToSpecifiedFile() throws Exception {
        folder.newFile(path);
        dataWriterToFile.setFilePath(folder.getRoot()+"/"+path);
        dataWriterToFile.writeToPath("     22.2",true);
        String readed=readFromFile();
        assertEquals("22.2 ",readed);
    }

    @Test
    public void dataWrite_shouldFormatStringToTerminateWithASpaceWhileThereAreMultipleSpaces() throws Exception {
        folder.newFile(path);
        dataWriterToFile.setFilePath(folder.getRoot()+"/"+path);
        dataWriterToFile.writeToPath("     22.2 -  22.2       pathToMiniature",true);
        String readed=readFromFile();
        assertEquals("22.2 - 22.2 pathToMiniature ",readed);
    }

    @Test
    public void dataWrite_shouldFormatMoreStringsToTerminateWithASpaceWhileThereAreMultipleSpaces() throws Exception {
        folder.newFile(path);
        dataWriterToFile.setFilePath(folder.getRoot()+"/"+path);
        dataWriterToFile.writeToPath("     22.2 -  22.2       pathToMiniature",true);
        dataWriterToFile.writeToPath("22.2        pathToMiniature",true);
        String readed=readFromFile();
        assertEquals("22.2 - 22.2 pathToMiniature 22.2 pathToMiniature ",readed);
    }


    @Test
    public void dataWrite_shouldNotReplaceOldStringsInTheSpecifiedFile() throws Exception {
        folder.newFile(path);
        dataWriterToFile.setFilePath(folder.getRoot()+"/"+path);
        dataWriterToFile.writeToPath("22.2old",true);
        dataWriterToFile.writeToPath("22.2new",true);
        String readed=readFromFile();
        assertEquals("22.2old 22.2new ",readed);
    }

    @Test
    public void dataWrite_canAppendMessageToFile() throws Exception {
        folder.newFile(path);
        dataWriterToFile.setFilePath(folder.getRoot()+"/"+path);
        dataWriterToFile.writeToPath("22.2first",true);
        dataWriterToFile.writeToPath("22.2second",true);
        String readed=readFromFile();
        assertEquals("22.2first 22.2second ",readed);
    }

    @Test
    public void dataWrite_canOverrideFileContent() throws Exception {
        folder.newFile(path);
        dataWriterToFile.setFilePath(folder.getRoot()+"/"+path);
        dataWriterToFile.writeToPath("22.2first",true);
        dataWriterToFile.writeToPath("22.2second",false);
        String readed=readFromFile();
        assertEquals("22.2second ",readed);
    }

    @Test
    public void dataWrite_willOverrideAnyFileContent() throws Exception {
        folder.newFile(path);
        dataWriterToFile.setFilePath(folder.getRoot()+"/"+path);
        dataWriterToFile.writeToPath("22.2first pathToMiniature 22.2 - 123.22 pathToMiniature",true);
        dataWriterToFile.writeToPath("overrided",false);
        String readed=readFromFile();
        assertEquals("overrided ",readed);
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
