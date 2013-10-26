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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.vfs2.FileFilter;
import org.apache.commons.vfs2.FileSelectInfo;

/**
 * A {@link java.io.FileFilter} providing conditional OR logic across a list of
 * file filters. This filter returns {@code true} if any filters in the list
 * return {@code true}. Otherwise, it returns {@code false}. Checking of the
 * file filter list stops when the first filter returns {@code true}.
 * 
 * @author This code was originally ported from Apache Commons IO File Filter
 * @see http://commons.apache.org/proper/commons-io/
 */
public class OrFileFilter implements FileFilter, ConditionalFileFilter, Serializable {

    private static final long serialVersionUID = 1L;

    /** The list of file filters. */
    private final List<FileFilter> fileFilters;

    /**
     * Default constructor.
     */
    public OrFileFilter() {
        this.fileFilters = new ArrayList<FileFilter>();
    }

    /**
     * Constructs a new file filter that ORs the result of other filters.
     * 
     * @param filters
     *            array of filters, must not be null or empty
     */
    public OrFileFilter(final FileFilter... filters) {
        if (filters == null || filters.length == 0) {
            throw new IllegalArgumentException("The filters must not be null or empty");
        }
        for (final FileFilter filter : filters) {
            if (filter == null) {
                throw new IllegalArgumentException("Null filters are not allowed");
            }
        }
        this.fileFilters = new ArrayList<FileFilter>(Arrays.asList(filters));
    }

    /**
     * Constructs a new instance of <code>OrFileFilter</code> with the specified
     * filters.
     * 
     * @param fileFilters
     *            the file filters for this filter, copied, null ignored
     */
    public OrFileFilter(final List<FileFilter> fileFilters) {
        if (fileFilters == null) {
            this.fileFilters = new ArrayList<FileFilter>();
        } else {
            this.fileFilters = new ArrayList<FileFilter>(fileFilters);
        }
    }

    @Override
    public boolean accept(final FileSelectInfo fileInfo) {
        for (final FileFilter fileFilter : fileFilters) {
            if (fileFilter.accept(fileInfo)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addFileFilter(final FileFilter fileFilter) {
        this.fileFilters.add(fileFilter);
    }

    @Override
    public List<FileFilter> getFileFilters() {
        return Collections.unmodifiableList(this.fileFilters);
    }

    @Override
    public boolean removeFileFilter(final FileFilter fileFilter) {
        return this.fileFilters.remove(fileFilter);
    }

    @Override
    public void setFileFilters(final List<FileFilter> fileFilters) {
        this.fileFilters.clear();
        this.fileFilters.addAll(fileFilters);
    }

    /**
     * Provide a String representation of this file filter.
     * 
     * @return a String representation
     */
    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(super.toString());
        buffer.append("(");
        if (fileFilters != null) {
            for (int i = 0; i < fileFilters.size(); i++) {
                if (i > 0) {
                    buffer.append(",");
                }
                final Object filter = fileFilters.get(i);
                buffer.append(filter == null ? "null" : filter.toString());
            }
        }
        buffer.append(")");
        return buffer.toString();
    }

}
