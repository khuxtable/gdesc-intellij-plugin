/*
 * Copyright © 2025, 2026 Kathryn A Huxtable
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kathrynhuxtable.gdesc.gdescplugin;

import com.intellij.codeInsight.editorActions.QuoteHandler;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

public class GDescTokenSetQuoteHandler implements QuoteHandler {
    protected final TokenSet myLiteralTokenSet;

    public GDescTokenSetQuoteHandler() {
        myLiteralTokenSet = GDescParserDefinition.STRING;
    }

    public GDescTokenSetQuoteHandler(IElementType... _literalTokens) {
        this(TokenSet.create(_literalTokens));
    }

    public GDescTokenSetQuoteHandler(TokenSet tokenSet) {
        this.myLiteralTokenSet = tokenSet;
    }

    public boolean isClosingQuote(HighlighterIterator iterator, int offset) {
        IElementType tokenType = iterator.getTokenType();
        if (!this.myLiteralTokenSet.contains(tokenType)) {
            return false;
        } else {
            int start = iterator.getStart();
            int end = iterator.getEnd();
            return end - start >= 1 && offset == end - 1;
        }
    }

    public boolean isOpeningQuote(HighlighterIterator iterator, int offset) {
        if (this.myLiteralTokenSet.contains(iterator.getTokenType())) {
            int start = iterator.getStart();
            return offset == start;
        } else {
            return false;
        }
    }

    public boolean hasNonClosedLiteral(Editor editor, HighlighterIterator iterator, int offset) {
        int start = iterator.getStart();

        try {
            Document doc = editor.getDocument();
            CharSequence chars = doc.getCharsSequence();
            int lineEnd = doc.getLineEndOffset(doc.getLineNumber(offset));

            while(!iterator.atEnd() && iterator.getStart() < lineEnd) {
                IElementType tokenType = iterator.getTokenType();
                if (this.myLiteralTokenSet.contains(tokenType) && this.isNonClosedLiteral(iterator, chars)) {
	                return true;
                }

                iterator.advance();
            }

            return false;
        } finally {
            while(true) {
                if (!iterator.atEnd() && iterator.getStart() == start) {
                    ;
                } else {
                    iterator.retreat();
                }
            }
        }
    }

    protected boolean isNonClosedLiteral(HighlighterIterator iterator, CharSequence chars) {
        return iterator.getStart() >= iterator.getEnd() - 1 || chars.charAt(iterator.getEnd() - 1) != '"' && chars.charAt(iterator.getEnd() - 1) != '\'';
    }

    public boolean isInsideLiteral(HighlighterIterator iterator) {
        return this.myLiteralTokenSet.contains(iterator.getTokenType());
    }
}
