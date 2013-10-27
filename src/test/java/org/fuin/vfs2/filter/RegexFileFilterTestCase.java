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
import static org.junit.Assert.fail;

import java.io.File;
import java.util.regex.Pattern;

import org.apache.commons.vfs2.FileFilter;
import org.junit.Test;

/**
 * Test for {@link RegexFileFilter}.
 */
// CHECKSTYLE:OFF Test code
public class RegexFileFilterTestCase extends BaseFilterTest {

    @Test
    public void testRegex() throws Exception {

        FileFilter filter;

        filter = new RegexFileFilter("^.*[tT]est(-\\d+)?\\.java$");
        assertThat(filter.accept(createFSI(new File("Test.java")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test-10.java")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test-.java")))).isFalse();

        filter = new RegexFileFilter("^[Tt]est.java$");
        assertThat(filter.accept(createFSI(new File("Test.java")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test.java")))).isTrue();
        assertThat(filter.accept(createFSI(new File("tEST.java")))).isFalse();

        filter = new RegexFileFilter(Pattern.compile("^test.java$", Pattern.CASE_INSENSITIVE));
        assertThat(filter.accept(createFSI(new File("Test.java")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test.java")))).isTrue();
        assertThat(filter.accept(createFSI(new File("tEST.java")))).isTrue();

        filter = new RegexFileFilter("^test.java$", Pattern.CASE_INSENSITIVE);
        assertThat(filter.accept(createFSI(new File("Test.java")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test.java")))).isTrue();
        assertThat(filter.accept(createFSI(new File("tEST.java")))).isTrue();

        filter = new RegexFileFilter("^test.java$", IOCase.INSENSITIVE);
        assertThat(filter.accept(createFSI(new File("Test.java")))).isTrue();
        assertThat(filter.accept(createFSI(new File("test.java")))).isTrue();
        assertThat(filter.accept(createFSI(new File("tEST.java")))).isTrue();

    }

    @Test
    public void testStringNullArgConstruction() {
        try {
            new RegexFileFilter((String) null);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage()).isEqualTo(RegexFileFilter.PATTERN_IS_MISSING);
        }
    }

    @Test
    public void testPatternNullArgConstruction() {
        try {
            new RegexFileFilter((Pattern) null);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage()).isEqualTo(RegexFileFilter.PATTERN_IS_MISSING);
        }
    }

    @Test
    public void testStringPatternNullArgConstruction() {
        try {
            new RegexFileFilter((String) null, Pattern.CASE_INSENSITIVE);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage()).isEqualTo(RegexFileFilter.PATTERN_IS_MISSING);
        }
    }

    @Test
    public void testStringIOCaseNullArgConstruction() {
        try {
            new RegexFileFilter((String) null, IOCase.INSENSITIVE);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage()).isEqualTo(RegexFileFilter.PATTERN_IS_MISSING);
        }
    }

}
// CHECKSTYLE:ON
