/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.docrepo.main;

import java.io.File;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.SystemOutRule;

/**
 *
 * @author Anish Panthi
 */
public class DocumentRepositoryTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    private DocumentRepository classUnderTest;

    private File file;

    public DocumentRepositoryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
//        this.systemOutRule = new SystemOutRule().enableLog();
        this.file = new File("Main Project");
        int directoryFileLength = file.getAbsolutePath().length() - file.getName().length();
        this.classUnderTest = new DocumentRepository(directoryFileLength);
    }

    @After
    public void tearDown() {
        this.file = null;
        this.classUnderTest = null;
        systemOutRule.clearLog();
    }

    @Test
    public void getAllContentsGivenFileAndTabsPrintsDirectories() {
        this.classUnderTest.listAllFilesAndDirectories(file, 0);
        String logs = systemOutRule.getLog();
        assertTrue(logs.contains("Project: Main Project - URL: Main Project"));
    }

    @Test
    public void getAllContentsGivenNonExistingFilePrintsErrorMessage() {
        this.classUnderTest.listAllFilesAndDirectories(new File("wrong name"), 0);
        assertEquals("Need directory/folder name.", systemOutRule.getLog().trim());
    }

    @Test
    public void sortByExtensionGivenStringArrayReturnsSortedList() {
        String[] str = new String[]{"file1.txt", "doc1.dox", "app", "doc2.dox", "doc3.ppt", "file.dox", "doc0.dox"};

        List<String> sortedStr = this.classUnderTest.sortExtension(str);
        System.out.println(sortedStr);
        assertTrue(sortedStr.size() == 7);
        assertEquals("doc1.dox", sortedStr.get(0));
    }

}
