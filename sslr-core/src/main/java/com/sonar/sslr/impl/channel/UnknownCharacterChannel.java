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
package com.sonar.sslr.impl.channel;

import com.sonar.sslr.api.Token;
import com.sonar.sslr.impl.Lexer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.channel.Channel;
import org.sonar.channel.CodeReader;

import static com.sonar.sslr.api.GenericTokenType.UNKNOWN_CHAR;

/**
 * Creates token with type {@link #UNKNOWN_CHAR} for any character, which is not {@link #BOM_CHAR}.
 * This channel, if present, should be the last one.
 *
 * @since 1.2
 */
public class UnknownCharacterChannel extends Channel<Lexer> {

  private static final Logger LOG = LoggerFactory.getLogger(UnknownCharacterChannel.class);

  private boolean shouldLogWarning = false;
  private final Token.Builder tokenBuilder = Token.builder();

  public UnknownCharacterChannel() {
  }

  public UnknownCharacterChannel(boolean shouldLogWarning) {
    this.shouldLogWarning = shouldLogWarning;
  }

  @Override
  public boolean consume(CodeReader code, Lexer lexer) {
    if (code.peek() != -1) {
      char unknownChar = (char) code.pop();
      if (shouldLogWarning) {
        LOG.warn("Unknown char: \"" + unknownChar + "\" (" + lexer.getURI() + ":" + code.getLinePosition() + ":"
            + code.getColumnPosition() + ")");
      }

      Token token = tokenBuilder
          .setType(UNKNOWN_CHAR)
          .setValueAndOriginalValue(String.valueOf(unknownChar))
          .setURI(lexer.getURI())
          .setLine(code.getLinePosition())
          .setColumn(code.getColumnPosition() - 1)
          .build();

      lexer.addToken(token);

      return true;
    }
    return false;
  }

}
