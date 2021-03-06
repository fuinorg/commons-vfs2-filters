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

import java.io.Serializable;

import org.apache.commons.vfs2.Capability;
import org.apache.commons.vfs2.FileFilter;
import org.apache.commons.vfs2.FileSelectInfo;
import org.apache.commons.vfs2.FileSystem;
import org.apache.commons.vfs2.FileSystemException;

/**
 * This filter accepts <code>File</code>s that can be written to.
 * <p>
 * Example, showing how to print out a list of the current directory's
 * <i>writable</i> files:
 * 
 * <pre>
 * FileSystemManager fsManager = VFS.getManager();
 * FileObject dir = fsManager.toFileObject(new File(&quot;.&quot;));
 * FileObject[] files = dir.findFiles(new FileFilterSelector(CanWriteFileFilter.CAN_WRITE));
 * for (int i = 0; i &lt; files.length; i++) {
 *     System.out.println(files[i]);
 * }
 * </pre>
 * 
 * <p>
 * Example, showing how to print out a list of the current directory's
 * <i>un-writable</i> files:
 * 
 * <pre>
 * FileSystemManager fsManager = VFS.getManager();
 * FileObject dir = fsManager.toFileObject(new File(&quot;.&quot;));
 * FileObject[] files = dir.findFiles(new FileFilterSelector(CanWriteFileFilter.CANNOT_WRITE));
 * for (int i = 0; i &lt; files.length; i++) {
 *     System.out.println(files[i]);
 * }
 * </pre>
 * 
 * <p>
 * <b>N.B.</b> For read-only files, use <code>CanReadFileFilter.READ_ONLY</code>.
 * 
 * @author This code was originally ported from Apache Commons IO File Filter
 * @see "http://commons.apache.org/proper/commons-io/"
 */
public class CanWriteFileFilter implements FileFilter, Serializable {

    private static final long serialVersionUID = 1L;

    /** Singleton instance of <i>writable</i> filter. */
    public static final FileFilter CAN_WRITE = new CanWriteFileFilter();

    /** Singleton instance of not <i>writable</i> filter. */
    public static final FileFilter CANNOT_WRITE = new NotFileFilter(CAN_WRITE);

    /**
     * Restrictive constructor.
     */
    protected CanWriteFileFilter() {
    }

    /**
     * Checks to see if the file can be written to.
     * 
     * @param fileInfo
     *            the File to check
     * 
     * @return {@code true} if the file can be written to, otherwise
     *         {@code false}.
     */
    @Override
    public boolean accept(final FileSelectInfo fileInfo) {
        try {
            final FileSystem fileSystem = fileInfo.getFile().getFileSystem();
            if (fileInfo.getFile().exists()) {
                if (!fileSystem.hasCapability(Capability.WRITE_CONTENT)) {
                    return false;
                }
                return fileInfo.getFile().isWriteable();
            } else {
                if (!fileSystem.hasCapability(Capability.CREATE)) {
                    return false;
                }
                return fileInfo.getFile().getParent().isWriteable();
            }
        } catch (final FileSystemException ex) {
            throw new RuntimeException(ex);
        }
    }

}
