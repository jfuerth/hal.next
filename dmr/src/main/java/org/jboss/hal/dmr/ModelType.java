/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package org.jboss.hal.dmr;

/**
* @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
*/
public enum ModelType {
    BIG_DECIMAL('d'),
    BIG_INTEGER('i'),
    BOOLEAN('Z'),
    BYTES('b'),
    DOUBLE('D'),
    EXPRESSION('e'),
    INT('I'),
    LIST('l'),
    LONG('J'),
    OBJECT('o'),
    PROPERTY('p'),
    STRING('s'),
    TYPE('t'),
    UNDEFINED('u');

    final char typeChar;

    ModelType(final char typeChar) {
        this.typeChar = typeChar;
    }

    char getTypeChar() {
        return typeChar;
    }

    static ModelType forChar(char c) {
        switch (c) {
             case 'J': return LONG;
             case 'I': return INT;
             case 'Z': return BOOLEAN;
             case 's': return STRING;
             case 'D': return DOUBLE;
             case 'd': return BIG_DECIMAL;
             case 'i': return BIG_INTEGER;
             case 'b': return BYTES;
             case 'l': return LIST;
             case 't': return TYPE;
             case 'o': return OBJECT;
             case 'p': return PROPERTY;
             case 'e': return EXPRESSION;
             case 'u': return UNDEFINED;
             default: throw new IllegalArgumentException("Invalid type character '" + c + "'");
        }
    }
}
