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
package org.fuin.vfs2.filter.examples;

import java.io.File;

import org.apache.commons.vfs2.FileFilterSelector;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.fuin.vfs2.filter.AgeFileFilter;

/**
 * Example for using {@link AgeFileFilter}.
 */
// CHECKSTYLE:OFF Example code
public class AgeFileFilterExample {

    public static void main(String[] args) throws Exception {

        FileSystemManager fsManager = VFS.getManager();
        FileObject dir = fsManager.toFileObject(new File("."));

        // We are interested in files older than one day
        long cutoff = System.currentTimeMillis() - (24 * 60 * 60 * 1000);
        AgeFileFilter filter = new AgeFileFilter(cutoff);

        FileObject[] files = dir.findFiles(new FileFilterSelector(filter));
        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i]);
        }

    }

}
// CHECKSTYLE:ON

