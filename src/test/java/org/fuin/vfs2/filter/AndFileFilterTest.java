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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.vfs2.FileFilter;
import org.apache.commons.vfs2.FileSelectInfo;
import org.junit.Test;

// CHECKSTYLE:OFF Test code
public class AndFileFilterTest extends BaseFilterTest {

    @Test
    public void testAndFileFilterFileFilter() {

        // PREPARE
        FileFilter filter1 = new DummyFilter();
        FileFilter filter2 = new DummyFilter();
        FileFilter filter3 = new DummyFilter();

        // TEST
        AndFileFilter testee = new AndFileFilter(filter1, filter2, filter3);

        // VERIFY
        assertThat(testee.getFileFilters()).contains(filter1, filter2, filter3);
        assertThat(testee.getFileFilters()).hasSize(3);

    }

    @Test
    public void testAndFileFilterList() {

        // PREPARE
        FileFilter filter1 = new DummyFilter();
        FileFilter filter2 = new DummyFilter();
        FileFilter filter3 = new DummyFilter();
        List<FileFilter> list = new ArrayList<FileFilter>();
        list.add(filter1);
        list.add(filter2);
        list.add(filter3);

        // TEST
        AndFileFilter testee = new AndFileFilter(list);

        // VERIFY
        assertThat(testee.getFileFilters()).contains(filter1, filter2, filter3);
        assertThat(testee.getFileFilters()).hasSize(3);

    }

    @Test
    public void testAddFileFilter() {

        // PREPARE
        FileFilter filter1 = new DummyFilter();
        FileFilter filter2 = new DummyFilter();
        FileFilter filter3 = new DummyFilter();

        // TEST
        AndFileFilter testee = new AndFileFilter();
        testee.addFileFilter(filter1);
        testee.addFileFilter(filter2);
        testee.addFileFilter(filter3);

        // VERIFY
        assertThat(testee.getFileFilters()).contains(filter1, filter2, filter3);
        assertThat(testee.getFileFilters()).hasSize(3);

    }

    @Test
    public void testRemoveFileFilter() {

        // PREPARE
        FileFilter filter1 = new DummyFilter();
        FileFilter filter2 = new DummyFilter();
        FileFilter filter3 = new DummyFilter();
        AndFileFilter testee = new AndFileFilter(filter1, filter2, filter3);

        // TEST
        testee.removeFileFilter(filter2);

        // VERIFY
        assertThat(testee.getFileFilters()).contains(filter1, filter3);
        assertThat(testee.getFileFilters()).hasSize(2);

    }

    @Test
    public void testSetFileFilters() {

        // PREPARE
        FileFilter filter1 = new DummyFilter();
        FileFilter filter2 = new DummyFilter();
        FileFilter filter3 = new DummyFilter();
        List<FileFilter> list = new ArrayList<FileFilter>();
        list.add(filter1);
        list.add(filter2);
        list.add(filter3);
        AndFileFilter testee = new AndFileFilter();

        // TEST
        testee.setFileFilters(list);

        // VERIFY
        assertThat(testee.getFileFilters()).contains(filter1, filter2, filter3);
        assertThat(testee.getFileFilters()).hasSize(3);

    }

    @Test
    public void testAccept() {

        FileSelectInfo any = createFSI(new File("anyfile"));

        // Empty
        assertThat(new AndFileFilter().accept(any)).isFalse();

        // True
        assertThat(new AndFileFilter(new True()).accept(any)).isTrue();
        assertThat(new AndFileFilter(new True(), new True()).accept(any)).isTrue();

        // False
        assertThat(new AndFileFilter(new False()).accept(any)).isFalse();
        assertThat(new AndFileFilter(new False(), new False()).accept(any)).isFalse();
        assertThat(new AndFileFilter(new False(), new True()).accept(any)).isFalse();
        assertThat(new AndFileFilter(new True(), new False()).accept(any)).isFalse();

    }

    /**
     * Just a filter class.
     */
    private static class DummyFilter implements FileFilter {

        @Override
        public boolean accept(FileSelectInfo fileInfo) {
            return false;
        }

    }

    /**
     * Always TRUE.
     */
    private static class True implements FileFilter {

        @Override
        public boolean accept(FileSelectInfo fileInfo) {
            return true;
        }

    }

    /**
     * Always FALSE.
     */
    private static class False implements FileFilter {

        @Override
        public boolean accept(FileSelectInfo fileInfo) {
            return false;
        }

    }

}
// CHECKSTYLE:ON
