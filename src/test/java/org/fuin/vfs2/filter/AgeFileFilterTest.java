/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fuin.vfs2.filter;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.vfs2.FileFilterSelector;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSelectInfo;
import org.apache.commons.vfs2.FileSystemException;
import org.fuin.utils4j.Utils4J;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test for {@link AgeFileFilter}.
 */
// CHECKSTYLE:OFF Test code
public class AgeFileFilterTest extends BaseFilterTest {

    private static long DAY_MILLIS = 24 * 60 * 60 * 1000;

    private static long NOW = System.currentTimeMillis();

    private static long TWO_DAYS_AGO = NOW - (2 * DAY_MILLIS);

    private static long TWO_DAYS_LATER = NOW + (2 * DAY_MILLIS);

    private static File testDir;

    private static File oldFile;

    private static FileSelectInfo oldFileInfo;

    private static File newFile;

    private static FileSelectInfo newFileInfo;

    private static File currentFile;

    private static FileSelectInfo currentFileInfo;

    private static File zipFile;

    private static FileObject zipFileObj;

    @BeforeClass
    public static void beforeClass() throws IOException {
        testDir = getTestDir(AgeFileFilterTest.class.getName());

        // Set the file's time stamp two days back
        oldFile = new File(testDir, "old.txt");
        FileUtils.touch(oldFile);
        oldFile.setLastModified(TWO_DAYS_AGO);
        oldFileInfo = createFileSelectInfo(oldFile);

        // Reference file
        currentFile = new File(testDir, "current.txt");
        FileUtils.touch(currentFile);
        currentFile.setLastModified(NOW);
        currentFileInfo = createFileSelectInfo(currentFile);

        // Set the file's time stamp two days into the future
        newFile = new File(testDir, "new.txt");
        FileUtils.touch(newFile);
        newFile.setLastModified(TWO_DAYS_LATER);
        newFileInfo = createFileSelectInfo(newFile);

        // Zip the test directory
        zipFile = new File(getTempDir(), AgeFileFilterTest.class.getName() + ".zip");
        Utils4J.zipDir(testDir, "", zipFile);
        zipFileObj = getZipFileObject(zipFile);

    }

    @AfterClass
    public static void afterClass() throws IOException {
        newFileInfo = null;
        newFile = null;

        currentFileInfo = null;
        currentFile = null;

        oldFileInfo = null;
        oldFile = null;

        zipFileObj.close();
        // FileUtils.deleteQuietly(zipFile);
        zipFile = null;

        // FileUtils.deleteDirectory(testDir);
        testDir = null;
    }

    @Test
    public void testAgeFileFilterDate() {

        AgeFileFilter testee = new AgeFileFilter(new Date());
        assertThat(testee.accept(oldFileInfo)).isTrue();
        assertThat(testee.accept(currentFileInfo)).isTrue();
        assertThat(testee.accept(newFileInfo)).isFalse();

    }

    @Test
    public void testAgeFileFilterDateBoolean() {

        AgeFileFilter testee;

        testee = new AgeFileFilter(new Date(), true);
        assertThat(testee.accept(oldFileInfo)).isTrue();
        assertThat(testee.accept(currentFileInfo)).isTrue();
        assertThat(testee.accept(newFileInfo)).isFalse();

        testee = new AgeFileFilter(new Date(), false);
        assertThat(testee.accept(oldFileInfo)).isFalse();
        assertThat(testee.accept(currentFileInfo)).isFalse();
        assertThat(testee.accept(newFileInfo)).isTrue();

    }

    @Test
    public void testAgeFileFilterFile() throws FileSystemException {

        AgeFileFilter testee = new AgeFileFilter(currentFileInfo.getFile());
        assertThat(testee.accept(oldFileInfo)).isTrue();
        assertThat(testee.accept(currentFileInfo)).isTrue();
        assertThat(testee.accept(newFileInfo)).isFalse();

    }

    @Test
    public void testAgeFileFilterFileBoolean() throws FileSystemException {

        AgeFileFilter testee;

        testee = new AgeFileFilter(currentFileInfo.getFile(), true);
        assertThat(testee.accept(oldFileInfo)).isTrue();
        assertThat(testee.accept(currentFileInfo)).isTrue();
        assertThat(testee.accept(newFileInfo)).isFalse();

        testee = new AgeFileFilter(currentFileInfo.getFile(), false);
        assertThat(testee.accept(oldFileInfo)).isFalse();
        assertThat(testee.accept(currentFileInfo)).isFalse();
        assertThat(testee.accept(newFileInfo)).isTrue();

    }

    @Test
    public void testAgeFileFilterLong() {

        AgeFileFilter testee = new AgeFileFilter(NOW);
        assertThat(testee.accept(oldFileInfo)).isTrue();
        assertThat(testee.accept(currentFileInfo)).isTrue();
        assertThat(testee.accept(newFileInfo)).isFalse();

    }

    @Test
    public void testAgeFileFilterLongBoolean() throws FileSystemException {

        AgeFileFilter testee;

        testee = new AgeFileFilter(NOW, true);
        assertThat(testee.accept(oldFileInfo)).isTrue();
        assertThat(testee.accept(currentFileInfo)).isTrue();
        assertThat(testee.accept(newFileInfo)).isFalse();

        testee = new AgeFileFilter(NOW, false);
        assertThat(testee.accept(oldFileInfo)).isFalse();
        assertThat(testee.accept(currentFileInfo)).isFalse();
        assertThat(testee.accept(newFileInfo)).isTrue();

        // Same test with ZIP file
        FileObject[] files;

        files = zipFileObj.findFiles(new FileFilterSelector(new AgeFileFilter(NOW, true)));
        assertContains(files, oldFile.getName(), currentFile.getName());
        assertThat(files).hasSize(2);

        files = zipFileObj.findFiles(new FileFilterSelector(new AgeFileFilter(NOW, false)));
        assertContains(files, newFile.getName());
        assertThat(files).hasSize(1);

    }

}
// CHECKSTYLE:ON
