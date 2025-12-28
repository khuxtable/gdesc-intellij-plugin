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

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.spellchecker.inspections.CommentSplitter;
import com.intellij.spellchecker.inspections.IdentifierSplitter;
import com.intellij.spellchecker.inspections.PlainTextSplitter;
import com.intellij.spellchecker.tokenizer.SpellcheckingStrategy;
import com.intellij.spellchecker.tokenizer.TokenConsumer;
import com.intellij.spellchecker.tokenizer.Tokenizer;
import org.jetbrains.annotations.NotNull;


final class GDescSpellcheckingStrategy extends SpellcheckingStrategy {

  @Override
  public @NotNull Tokenizer<?> getTokenizer(PsiElement element) {
    if (element instanceof PsiComment) {
      return new GDescCommentTokenizer();
    }

//    if (element instanceof GDescProperty) {
//      return new GDescPropertyTokenizer();
//    }

    return EMPTY_TOKENIZER;
  }

  private static class GDescCommentTokenizer extends Tokenizer<PsiComment> {

    @Override
    public void tokenize(@NotNull PsiComment element, @NotNull TokenConsumer consumer) {
      // Exclude the start of the comment with its # characters from spell checking
      int startIndex = 0;
      for (char c : element.textToCharArray()) {
        if (c == '#' || Character.isWhitespace(c)) {
          startIndex++;
        } else {
          break;
        }
      }
      consumer.consumeToken(element, element.getText(), false, 0,
          TextRange.create(startIndex, element.getTextLength()),
          CommentSplitter.getInstance());
    }

  }

//  private static class GDescPropertyTokenizer extends Tokenizer<GDescProperty> {
//
//    public void tokenize(@NotNull GDescProperty element, @NotNull TokenConsumer consumer) {
//      //Spell check the keys and values of properties with different splitters
//      final ASTNode key = element.getNode().findChildByType(GDescTypes.KEY);
//      if (key != null && key.getTextLength() > 0) {
//        final PsiElement keyPsi = key.getPsi();
//        final String text = key.getText();
//        //For keys, use a splitter for identifiers
//        //Note we set "useRename" to true so that keys will be properly refactored (renamed)
//        consumer.consumeToken(keyPsi, text, true, 0,
//            TextRange.allOf(text), IdentifierSplitter.getInstance());
//      }
//
//      final ASTNode value = element.getNode().findChildByType(GDescTypes.VALUE);
//      if (value != null && value.getTextLength() > 0) {
//        final PsiElement valuePsi = value.getPsi();
//        final String text = valuePsi.getText();
//        //For values, use a splitter for plain text
//        consumer.consumeToken(valuePsi, text, false, 0,
//            TextRange.allOf(text), PlainTextSplitter.getInstance());
//      }
//    }
//
//  }

}
