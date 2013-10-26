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
 * Test for {@link NameFileFilter}.
 */
// CHECKSTYLE:OFF Test code
public class NameFileFilterTest extends BaseFilterTest {

    @Test
    public void testAcceptList() {

        // PREPARE
        List<String> list = new ArrayList<String>();
        list.add("test1.txt");
        list.add("test2.txt");
        NameFileFilter filter = new NameFileFilter(list);

        // TEST
        assertThat(filter.accept(createFSI(new File("test1.txt")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test2.txt")))).isTrue();
        assertThat(filter.accept(createFSI(new File("Test2.txt")))).isFalse();
        assertThat(filter.accept(createFSI(new File("test.xxx")))).isFalse();

    }

    @Test
    public void testAcceptListIOCaseInsensitive() {

        // PREPARE
        List<String> list = new ArrayList<String>();
        list.add("test1.txt");
        list.add("test2.txt");
        NameFileFilter filter = new NameFileFilter(IOCase.INSENSITIVE, list);

        // TEST
        assertThat(filter.accept(createFSI(new File("TEST1.txt")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test2.txt")))).isTrue();
        assertThat(filter.accept(createFSI(new File("Test2.txt")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test.xxx")))).isFalse();

    }

    @Test
    public void testAcceptListIOCaseSensitive() {

        // PREPARE
        List<String> list = new ArrayList<String>();
        list.add("test1.txt");
        list.add("test2.txt");
        NameFileFilter filter = new NameFileFilter(IOCase.SENSITIVE, list);

        // TEST
        assertThat(filter.accept(createFSI(new File("TEST1.txt")))).isFalse();
        assertThat(filter.accept(createFSI(new File("test2.txt")))).isTrue();
        assertThat(filter.accept(createFSI(new File("Test2.txt")))).isFalse();
        assertThat(filter.accept(createFSI(new File("test.xxx")))).isFalse();

    }

    @Test
    public void testAcceptString() {

        // PREPARE
        NameFileFilter filter = new NameFileFilter("test1.txt");

        // TEST
        assertThat(filter.accept(createFSI(new File("test1.txt")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test2.txt")))).isFalse();
        assertThat(filter.accept(createFSI(new File("Test2.txt")))).isFalse();
        assertThat(filter.accept(createFSI(new File("test.xxx")))).isFalse();

    }

    @Test
    public void testAcceptStringIOCaseInsensitive() {

        // PREPARE
        NameFileFilter filter = new NameFileFilter(IOCase.INSENSITIVE, "test2.txt");

        // TEST
        assertThat(filter.accept(createFSI(new File("test1.txt")))).isFalse();
        assertThat(filter.accept(createFSI(new File("test2.txt")))).isTrue();
        assertThat(filter.accept(createFSI(new File("Test2.txt")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test.xxx")))).isFalse();

    }

    @Test
    public void testAcceptStringIOCaseSensitive() {

        // PREPARE
        NameFileFilter filter = new NameFileFilter(IOCase.SENSITIVE, "test2.txt");

        // TEST
        assertThat(filter.accept(createFSI(new File("test1.txt")))).isFalse();
        assertThat(filter.accept(createFSI(new File("test2.txt")))).isTrue();
        assertThat(filter.accept(createFSI(new File("Test2.txt")))).isFalse();
        assertThat(filter.accept(createFSI(new File("test.xxx")))).isFalse();

    }

}
// CHECKSTYLE:ON
