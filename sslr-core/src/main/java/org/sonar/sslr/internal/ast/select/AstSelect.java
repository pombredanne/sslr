/*
 * SonarSource Language Recognizer
 * Copyright (C) 2010 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.sslr.internal.ast.select;

import com.google.common.base.Predicate;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;

import java.util.Iterator;

/**
 * An immutable ordered collection of AST nodes with operations for selection.
 * Use <code>{@link AstNode#select()}</code> to obtain an instance of this interface.
 *
 * <p>This interface is not intended to be implemented by clients.</p>
 *
 * TODO Godin: should be moved into API package
 *
 * @since 1.18
 */
public interface AstSelect extends Iterable<AstNode> {

  /**
   * Returns new selection, which contains children of this selection.
   */
  AstSelect children();

  /**
   * Returns new selection, which contains children of a given type of this selection.
   * <p>
   * In the following case, {@code children("B")} would return "B2" and "B3":
   * <pre>
   * A1
   *  |__ C1
   *  |    |__ B1
   *  |__ B2
   *  |__ B3
   * </pre>
   */
  AstSelect children(AstNodeType type);

  /**
   * Returns new selection, which contains children of a given types of this selection.
   *
   * @see #children(AstNodeType)
   */
  AstSelect children(AstNodeType... types);

  /**
   * Returns new selection, which contains descendants of a given type of this selection.
   * Be careful, this method searches among all descendants whatever is their depth, so favor {@link #children(AstNodeType)} when possible.
   * <p>
   * In the following case, {@code getDescendants("B")} would return "B1", "B2" and "B3":
   * <pre>
   * A1
   *  |__ C1
   *  |    |__ B1
   *  |__ B2
   *  |__ D1
   *  |__ B3
   * </pre>
   */
  AstSelect descendants(AstNodeType type);

  /**
   * Returns new selection, which contains descendants of a given types of this selection.
   *
   * @see #descendants(AstNodeType)
   */
  AstSelect descendants(AstNodeType... types);

  /**
   * Returns <tt>true</tt> if this selection contains no elements.
   *
   * @return <tt>true</tt> if this selection contains no elements
   */
  boolean isEmpty();

  /**
   * Returns <tt>true</tt> if this selection contains elements.
   *
   * @return <tt>true</tt> if this selection contains elements
   */
  boolean isNotEmpty();

  /**
   * Returns new selection, which contains elements of this selection that satisfy a predicate.
   */
  AstSelect filter(Predicate<AstNode> predicate);

  /**
   * Returns the number of elements in this selection.
   *
   * @return the number of elements in this selection
   */
  int size();

  /**
   * Returns the element at the specified position in this selection.
   *
   * @param  index index of the element to return
   * @return the element at the specified position in this selection
   * @throws IndexOutOfBoundsException if the index is out of range
   *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
   */
  AstNode get(int index);

  /**
   * Returns an iterator over the elements in this selection.
   *
   * @return an iterator over the elements in this selection
   */
  Iterator<AstNode> iterator();

}
