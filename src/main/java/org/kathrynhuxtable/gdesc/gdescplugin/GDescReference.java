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

import java.util.ArrayList;
import java.util.List;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiPolyVariantReferenceBase;
import com.intellij.psi.ResolveResult;
import org.jetbrains.annotations.NotNull;

final class GDescReference extends PsiPolyVariantReferenceBase<PsiElement> {

  private final String key;

  GDescReference(@NotNull PsiElement element, TextRange textRange) {
    super(element, textRange);
    key = element.getText()
        .substring(textRange.getStartOffset(), textRange.getEndOffset());
  }

  @Override
  public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
    Project project = myElement.getProject();
//    List<GDescProperty> properties = GDescUtil.findProperties(project, key);
    List<ResolveResult> results = new ArrayList<>();
//    for (GDescProperty property : properties) {
//      results.add(new PsiElementResolveResult(property));
//    }
    return results.toArray(new ResolveResult[0]);
  }

  @Override
  public Object @NotNull [] getVariants() {
    Project project = myElement.getProject();
//    List<GDescProperty> properties = GDescUtil.findProperties(project);
    List<LookupElement> variants = new ArrayList<>();
//    for (GDescProperty property : properties) {
//      if (property.getKey() != null && !property.getKey().isEmpty()) {
//        variants.add(LookupElementBuilder
//            .create(property).withIcon(GDescIcons.FILE)
//            .withTypeText(property.getContainingFile().getName())
//        );
//      }
//    }
    return variants.toArray();
  }

}
