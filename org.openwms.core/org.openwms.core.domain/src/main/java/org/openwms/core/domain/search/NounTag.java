/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.core.domain.search;

/**
 * A NounTag represents a noun.
 * 
 * @GlossaryTerm
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.2
 */
public class NounTag extends Tag {

    private static final long serialVersionUID = -3760619386394202171L;

    /* ----------------------------- constructors ------------------- */
    /**
     * Create a new NounTag.
     * 
     */
    public NounTag() {
        super();
    }

    /**
     * Create a new NounTag.
     * 
     * @param name
     *            The name of this Tag
     */
    public NounTag(String name) {
        super(name);
    }

    /* ----------------------------- methods ------------------- */
}