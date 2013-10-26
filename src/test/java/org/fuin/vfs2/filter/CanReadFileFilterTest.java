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
 * Test for {@link CanReadFileFilter}.
 */
// CHECKSTYLE:OFF Test code
public class CanReadFileFilterTest extends BaseFilterTest {

    private static final String WRITEABLE = "writeable.txt";

    private static final String READONLY = "readonly.txt";

    private static File testDir;

    private static File writeableFile;

    private static FileSelectInfo writeableFileInfo;

    private static File readOnlyFile;

    private static FileSelectInfo readOnlyFileInfo;

    private static File notExistingFile;

    private static FileSelectInfo notExistingFileInfo;

    private static File zipFile;

    private static FileObject zipFileObj;

    @BeforeClass
    public static void beforeClass() throws IOException {

        testDir = getTestDir(CanReadFileFilterTest.class.getName());

        writeableFile = new File(testDir, WRITEABLE);
        writeableFileInfo = createFileSelectInfo(writeableFile);
        FileUtils.touch(writeableFile);

        readOnlyFile = new File(testDir, READONLY);
        readOnlyFileInfo = createFileSelectInfo(readOnlyFile);
        FileUtils.touch(readOnlyFile);
        readOnlyFile.setReadable(true);
        readOnlyFile.setWritable(false);

        notExistingFile = new File(testDir, "not-existing-file.txt");
        notExistingFileInfo = createFileSelectInfo(notExistingFile);

        zipFile = new File(getTempDir(), CanReadFileFilterTest.class.getName() + ".zip");
        Utils4J.zipDir(testDir, "", zipFile);
        zipFileObj = getZipFileObject(zipFile);

    }

    @AfterClass
    public static void afterClass() throws IOException {

        writeableFileInfo = null;
        writeableFile.delete();
        writeableFile = null;

        readOnlyFileInfo = null;
        readOnlyFile.delete();
        readOnlyFile = null;

        notExistingFileInfo = null;
        notExistingFile = null;

        zipFileObj.close();
        FileUtils.deleteQuietly(zipFile);
        zipFile = null;

        FileUtils.deleteDirectory(testDir);
        testDir = null;

    }

    @Test
    public void testAcceptCanRead() {

        assertThat(CanReadFileFilter.CAN_READ.accept(writeableFileInfo)).isTrue();
        assertThat(CanReadFileFilter.CAN_READ.accept(readOnlyFileInfo)).isTrue();
        assertThat(CanReadFileFilter.CAN_READ.accept(notExistingFileInfo)).isFalse();

    }

    @Test
    public void testAcceptCannotRead() {

        assertThat(CanReadFileFilter.CANNOT_READ.accept(writeableFileInfo)).isFalse();
        assertThat(CanReadFileFilter.CANNOT_READ.accept(readOnlyFileInfo)).isFalse();
        assertThat(CanReadFileFilter.CANNOT_READ.accept(notExistingFileInfo)).isTrue();

    }

    @Test
    public void testAcceptReadOnly() {

        assertThat(CanReadFileFilter.READ_ONLY.accept(writeableFileInfo)).isFalse();
        assertThat(CanReadFileFilter.READ_ONLY.accept(readOnlyFileInfo)).isTrue();
        assertThat(CanReadFileFilter.READ_ONLY.accept(notExistingFileInfo)).isFalse();

    }

    @Test
    public void testAcceptZipFile() throws FileSystemException {

        FileObject[] files;

        // CAN_READ Filter
        files = zipFileObj.findFiles(new FileFilterSelector(CanReadFileFilter.CAN_READ));
        assertContains(files, READONLY, WRITEABLE);
        assertThat(files).hasSize(2);

        // CANNOT_READ Filter
        files = zipFileObj.findFiles(new FileFilterSelector(CanReadFileFilter.CANNOT_READ));
        assertThat(files).isEmpty();

        // READ_ONLY Filter
        files = zipFileObj.findFiles(new FileFilterSelector(CanReadFileFilter.READ_ONLY));
        assertContains(files, READONLY, WRITEABLE);
        assertThat(files).hasSize(2);

    }

}
// CHECKSTYLE:ON
