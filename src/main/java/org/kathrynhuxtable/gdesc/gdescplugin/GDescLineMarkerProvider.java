package org.kathrynhuxtable.gdesc.gdescplugin;

import java.util.Collection;
import java.util.List;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.impl.source.tree.java.PsiJavaTokenImpl;
import org.jetbrains.annotations.NotNull;

import org.kathrynhuxtable.gdesc.gdescplugin.psi.GDescProperty;

final class GDescLineMarkerProvider extends RelatedItemLineMarkerProvider {

  @Override
  protected void collectNavigationMarkers(@NotNull PsiElement element,
                                          @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result) {
    // This must be an element with a literal expression as a parent
    if (!(element instanceof PsiJavaTokenImpl) || !(element.getParent() instanceof PsiLiteralExpression literalExpression)) {
      return;
    }

    // The literal expression must start with the GDesc language literal expression
    String value = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
    GDescAnnotator gdescAnnotator;
    if ((value == null) ||
        !value.startsWith(GDescAnnotator.GDESC_PREFIX_STR + GDescAnnotator.GDESC_SEPARATOR_STR)) {
      return;
    }

    // Get the GDesc language property usage
    com.intellij.openapi.project.@NotNull Project project = element.getProject();
    String possibleProperties = value.substring(
        GDescAnnotator.GDESC_PREFIX_STR.length() + GDescAnnotator.GDESC_SEPARATOR_STR.length()
    );
    final List<GDescProperty> properties = GDescUtil.findProperties(project, possibleProperties);
    if (!properties.isEmpty()) {
      // Add the property to a collection of line marker info
      NavigationGutterIconBuilder<PsiElement> builder =
          NavigationGutterIconBuilder.create(GDescIcons.FILE)
              .setTargets(properties)
              .setTooltipText("Navigate to GDesc language property");
      result.add(builder.createLineMarkerInfo(element));
    }
  }

}
