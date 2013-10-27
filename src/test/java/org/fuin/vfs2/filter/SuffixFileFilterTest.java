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

import org.junit.Test;

/**
 * Test for {@link SuffixFileFilter}.
 */
// CHECKSTYLE:OFF Test code
public class SuffixFileFilterTest extends BaseFilterTest {

    @Test
    public void testAcceptList() {

        // PREPARE
        List<String> list = new ArrayList<String>();
        list.add(".txt");
        list.add(".bin");
        SuffixFileFilter filter = new SuffixFileFilter(list);

        // TEST
        assertThat(filter.accept(createFSI(new File("test1.txt")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test2.bin")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test2.BIN")))).isFalse();
        assertThat(filter.accept(createFSI(new File("test.xxx")))).isFalse();

    }

    @Test
    public void testAcceptListIOCaseInsensitive() {

        // PREPARE
        List<String> list = new ArrayList<String>();
        list.add(".txt");
        list.add(".bin");
        SuffixFileFilter filter = new SuffixFileFilter(IOCase.INSENSITIVE, list);

        // TEST
        assertThat(filter.accept(createFSI(new File("TEST1.txt")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test2.bin")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test2.TXT")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test.xxx")))).isFalse();

    }

    @Test
    public void testAcceptListIOCaseSensitive() {

        // PREPARE
        List<String> list = new ArrayList<String>();
        list.add(".txt");
        list.add(".bin");
        SuffixFileFilter filter = new SuffixFileFilter(IOCase.SENSITIVE, list);

        // TEST
        assertThat(filter.accept(createFSI(new File("test1.Txt")))).isFalse();
        assertThat(filter.accept(createFSI(new File("test2.txt")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test2.BIN")))).isFalse();
        assertThat(filter.accept(createFSI(new File("test.xxx")))).isFalse();

    }

    @Test
    public void testAcceptString() {

        // PREPARE
        SuffixFileFilter filter = new SuffixFileFilter(".txt", ".xxx");

        // TEST
        assertThat(filter.accept(createFSI(new File("test1.txt")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test2.txt")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test2.TXT")))).isFalse();
        assertThat(filter.accept(createFSI(new File("test.xxx")))).isTrue();

    }

    @Test
    public void testAcceptStringIOCaseInsensitive() {

        // PREPARE
        SuffixFileFilter filter = new SuffixFileFilter(IOCase.INSENSITIVE, ".txt", ".xxx");

        // TEST
        assertThat(filter.accept(createFSI(new File("test1.txt")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test2.txt")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test2.TXT")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test.xxx")))).isTrue();

    }

    @Test
    public void testAcceptStringIOCaseSensitive() {

        // PREPARE
        SuffixFileFilter filter = new SuffixFileFilter(IOCase.SENSITIVE, ".txt", ".xxx");

        // TEST
        assertThat(filter.accept(createFSI(new File("test1.txt")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test2.txt")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test2.TXT")))).isFalse();
        assertThat(filter.accept(createFSI(new File("test.xxx")))).isTrue();

    }

}
// CHECKSTYLE:ON
