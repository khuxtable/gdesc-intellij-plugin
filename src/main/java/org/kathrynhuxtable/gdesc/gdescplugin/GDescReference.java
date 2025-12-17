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

import org.kathrynhuxtable.gdesc.gdescplugin.psi.GDescProperty;

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
    List<GDescProperty> properties = GDescUtil.findProperties(project, key);
    List<ResolveResult> results = new ArrayList<>();
    for (GDescProperty property : properties) {
      results.add(new PsiElementResolveResult(property));
    }
    return results.toArray(new ResolveResult[0]);
  }

  @Override
  public Object @NotNull [] getVariants() {
    Project project = myElement.getProject();
    List<GDescProperty> properties = GDescUtil.findProperties(project);
    List<LookupElement> variants = new ArrayList<>();
    for (GDescProperty property : properties) {
      if (property.getKey() != null && !property.getKey().isEmpty()) {
        variants.add(LookupElementBuilder
            .create(property).withIcon(GDescIcons.FILE)
            .withTypeText(property.getContainingFile().getName())
        );
      }
    }
    return variants.toArray();
  }

}
