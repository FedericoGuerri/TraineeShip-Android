package com.unifi.federicoguerri.traineeship_android.core;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

public class DataLoaderFromFileUnitTest {

    private final String path = "file.txt";
    private DataLoaderFromFile dataLoaderFromFile;

    @Rule
    public TemporaryFolder folder= new TemporaryFolder();
    @Rule
    public ExpectedException readRecordsFileException = ExpectedException.none();


    @Before
    public void init(){
        dataLoaderFromFile=new DataLoaderFromFile();
    }

    @Test
    public void dataLoader_filePathIsNullOnInit(){
        assertNull(dataLoaderFromFile.getFilePath());
    }

    @Test
    public void dataLoader_storeFilePathCorrectly() throws IOException {
        dataLoaderFromFile.loadFileFromPath(folder.getRoot()+"/"+path);
        assertEquals(folder.getRoot()+"/"+path,dataLoaderFromFile.getFilePath());
    }


    @Test
    public void dataLoader_returnFalseIfSpecifiedFileExists() throws IOException {
        folder.newFile(path);
        assertFalse(dataLoaderFromFile.loadFileFromPath(folder.getRoot()+"/"+path));
    }


    @Test
    public void dataLoader_canCreateANewFileIfNotExistsFromFilePath() throws IOException {
        dataLoaderFromFile.loadFileFromPath(folder.getRoot()+"/"+path);
        assertTrue(new File(folder.getRoot()+"/"+path).exists());
    }


    @Test
    public void dataLoader_returnsTrueIfCreatedANewFile() throws IOException {
        assertTrue(dataLoaderFromFile.loadFileFromPath(folder.getRoot()+"/"+path));
    }


    @Test
    public void dataLoader_readNoRecordsIfThereAreNoRecordsInFile() throws Exception {
        writeToFileSpecifiedFromField("");
        assertEquals(0,dataLoaderFromFile.getRecords().size());
    }


    @Test
    public void dataLoader_throwsExceptionIfFilePathWasNotSpecified() throws Exception {
        readRecordsFileException.expect(NullPointerException.class);
        readRecordsFileException.expectMessage("Failed To read from file");
        assertEquals(0,dataLoaderFromFile.getRecords().size());
    }


    @Test
    public void dataLoader_throwsExceptionIfAMiniaturePathIsNotThereAtAll() throws Exception {
        readRecordsFileException.expect(NoSuchElementException.class);
        readRecordsFileException.expectMessage("Failed To read a miniature");
        writeToFileSpecifiedFromField("22.2\n");
        assertEquals(1,dataLoaderFromFile.getRecords().size());
    }

    @Test
    public void dataLoader_throwsExceptionIfAPriceCantBeConvertedToFloat() throws Exception {
        readRecordsFileException.expect(NumberFormatException.class);
        readRecordsFileException.expectMessage("Failed To read a price");
        writeToFileSpecifiedFromField("notFloatValue noMiniature\n");
        assertEquals(1,dataLoaderFromFile.getRecords().size());
    }


    @Test (expected = NoSuchElementException.class)
    public void dataLoader_throwsExceptionIfCantCreateAProperResourceElement() throws Exception {
        writeToFileSpecifiedFromField("22.2\n");
        assertEquals(1,dataLoaderFromFile.getRecords().size());
    }

    @Test
    public void dataLoader_readMoreRecordsIfInTheFile() throws Exception {
        writeToFileSpecifiedFromField("22.2 noMiniature 22.2 noMiniature 22.2 noMiniature 22.2 noMiniature ");
        assertEquals(4,dataLoaderFromFile.getRecords().size());
    }

    @Test
    public void dataLoader_readNullIfCantGetMiniatureFromSpecifiedPath() throws Exception {
        writeToFileSpecifiedFromField("22.2 noMiniature 22.2 noMiniature");
        assertEquals(null,dataLoaderFromFile.getRecords().get(1).getMiniature());
    }

    @Test
    public void dataLoader_readRightNumberRecordsIfInTheFile() throws Exception {
        writeToFileSpecifiedFromField("22.2 noMiniature 22.2  noMiniature ");
        assertEquals(22.2f,dataLoaderFromFile.getRecords().get(0).getPrice());
    }

    @Test
    public void dataLoader_readRightRecordsCastedToStringIgnoringSpaces() throws Exception {
        writeToFileSpecifiedFromField("       22.2 noMiniature 22.2  noMiniature ");
        assertEquals("22.2",String.valueOf(dataLoaderFromFile.getRecords().get(0).getPrice()));
    }

    private void writeToFileSpecifiedFromField(String data) throws Exception {
        folder.newFile(path);
        BufferedWriter bufferedWriter=new BufferedWriter( new FileWriter( folder.getRoot()+"/"+path));
        bufferedWriter.write(data);
        bufferedWriter.close();
        dataLoaderFromFile.loadFileFromPath(folder.getRoot()+"/"+path);
    }


}
