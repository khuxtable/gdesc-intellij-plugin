package org.kathrynhuxtable.gdesc.gdescplugin;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import org.kathrynhuxtable.gdesc.gdescplugin.psi.GDescTypes;

final class GDescCompletionContributor extends CompletionContributor {

  GDescCompletionContributor() {
    extend(CompletionType.BASIC, PlatformPatterns.psiElement(GDescTypes.VALUE),
        new CompletionProvider<>() {
          public void addCompletions(@NotNull CompletionParameters parameters,
                                     @NotNull ProcessingContext context,
                                     @NotNull CompletionResultSet resultSet) {
            resultSet.addElement(LookupElementBuilder.create("Hello"));
          }
        }
    );
  }

}
