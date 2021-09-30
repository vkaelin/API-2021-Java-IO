package ch.heigvd.api.labio.impl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *       *** IMPORTANT WARNING : DO NOT EDIT THIS FILE ***
 *
 * This file is used to specify what you have to implement. To check your work,
 * we will run our own copy of the automated tests. If you change this file,
 * then you will introduce a change of specification!!!
 *
 * @author Olivier Liechti, Miguel Santamaria
 */
public class ApplicationTest {
    private static final Logger LOG = Logger.getLogger(ApplicationTest.class.getName());

    private static int NUMBER_OF_QUOTES = 20;

    private static void deleteDirectory() throws IOException {
        String rootDirectory = Application.WORKSPACE_DIRECTORY;
        FileUtils.deleteDirectory(new File(rootDirectory));
    }

    @BeforeAll
    public static void invokeApplication() throws IOException {
        deleteDirectory();

        Application application = new Application();
        application.fetchAndStoreQuotes(NUMBER_OF_QUOTES);
        application.processQuoteFiles();
    }

    @AfterAll
    public static void clearFiles() throws IOException {
        deleteDirectory();
    }

    @Test
    public void theApplicationShouldGenerateTheCorrectNumberOfQuoteFiles() {
        String[] extensions = {"utf8"};
        Collection<File> files = FileUtils.listFiles(new File(Application.WORKSPACE_DIRECTORY), extensions, true);
        assertEquals(NUMBER_OF_QUOTES, files.size());
    }

    @Test
    public void theApplicationShouldUseTheCorrectSyntaxToNameTheQuoteFiles() {
        String[] extensions = {"utf8"};
        Collection<File> files = FileUtils.listFiles(new File(Application.WORKSPACE_DIRECTORY), extensions, true);
        for (File file : files) {
            String filename = file.getName();
            Pattern pattern = Pattern.compile("quote-\\d*.utf8");
            Matcher matcher = pattern.matcher(filename);
            assertTrue(matcher.matches());
        }
    }

    @Test
    public void theApplicationShouldGenerateTheCorrectNumberOfOutputFiles() {
        Collection<File> files = FileUtils.listFiles(new File(Application.WORKSPACE_DIRECTORY), new IOFileFilter() {

            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".utf8.out");
            }

            @Override
            public boolean accept(File dir, String file) {
                return file.endsWith(".utf8.out");
            }
        }, TrueFileFilter.INSTANCE);
        assertEquals(NUMBER_OF_QUOTES, files.size());
    }
}
