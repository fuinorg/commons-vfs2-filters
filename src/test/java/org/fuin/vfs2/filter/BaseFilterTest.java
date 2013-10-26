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

import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSelectInfo;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;

/**
 * Base class for test cases.
 */
public abstract class BaseFilterTest {

    /**
     * Creates a file select info object for the given file.
     * 
     * @param file
     *            File to create an info for.
     * 
     * @return File selct info.
     */
    protected static FileSelectInfo createFileSelectInfo(final File file) {
        try {
            final FileSystemManager fsManager = VFS.getManager();
            final FileObject fileObject = fsManager.toFileObject(file);
            return new FileSelectInfo() {
                @Override
                public FileObject getFile() {
                    return fileObject;
                }

                @Override
                public int getDepth() {
                    return 0;
                }

                @Override
                public FileObject getBaseFolder() {
                    try {
                        return fileObject.getParent();
                    } catch (FileSystemException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            };
        } catch (FileSystemException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns a ZIP file object.
     * 
     * @param file
     *            File to resolve.
     * 
     * @return File object.
     * 
     * @throws FileSystemException
     *             Error resolving the file.
     */
    protected static FileObject getZipFileObject(final File file) throws FileSystemException {
        final FileSystemManager fsManager = VFS.getManager();
        return fsManager.resolveFile("zip:" + file.toURI());
    }

    /**
     * Asserts that the array contains the given file names.
     * 
     * @param files
     *            Array to check.
     * @param filenames
     *            File names to find.
     */
    protected void assertContains(final FileObject[] files, final String... filenames) {
        for (String filename : filenames) {
            if (!find(files, filename)) {
                fail("File '" + filename + "' nout found in: " + Arrays.asList(files));
            }
        }
    }

    private boolean find(final FileObject[] files, final String filename) {
        for (FileObject file : files) {
            final String name = file.getName().getBaseName();
            if (name.equals(filename)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the temporary directory.
     * 
     * @return java.io.tmpdir
     */
    protected static File getTempDir() {
        return new File(System.getProperty("java.io.tmpdir"));
    }

    /**
     * Returns a sub directory of the temporary directory.
     * 
     * @param name
     *            Name of the sub directory.
     * 
     * @return Sub directory of java.io.tmpdir.
     */
    protected static File getTestDir(final String name) {
        return new File(getTempDir(), name);
    }

}
