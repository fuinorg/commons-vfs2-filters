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
import org.fuin.vfs2.filter.CanWriteFileFilter;

/**
 * Example for using {@link CanWriteFileFilter}.
 */
// CHECKSTYLE:OFF Example code
public class CanWriteFileFilterExample {

    public static void main(String[] args) throws Exception {

        // Example, showing how to print out a list of the current directory's
        // writable files:
        {
            System.out.println("---CAN_WRITE---");
            FileSystemManager fsManager = VFS.getManager();
            FileObject dir = fsManager.toFileObject(new File("."));
            FileObject[] files = dir
                    .findFiles(new FileFilterSelector(CanWriteFileFilter.CAN_WRITE));
            for (int i = 0; i < files.length; i++) {
                System.out.println(files[i]);
            }
        }

        // Example, showing how to print out a list of the current directory's
        // un-writable files:
        {
            System.out.println("---CANNOT_WRITE---");
            FileSystemManager fsManager = VFS.getManager();
            FileObject dir = fsManager.toFileObject(new File("."));
            FileObject[] files = dir.findFiles(new FileFilterSelector(
                    CanWriteFileFilter.CANNOT_WRITE));
            for (int i = 0; i < files.length; i++) {
                System.out.println(files[i]);
            }
        }

    }

}
// CHECKSTYLE:ON

