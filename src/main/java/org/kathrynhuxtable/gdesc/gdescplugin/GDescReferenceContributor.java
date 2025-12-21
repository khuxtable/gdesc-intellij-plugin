package org.kathrynhuxtable.gdesc.gdescplugin;

import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

final class GDescReferenceContributor extends PsiReferenceContributor {

	@Override
	public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
//		registrar.registerReferenceProvider(PlatformPatterns.psiElement(PsiLiteralExpression.class),
//				new PsiReferenceProvider() {
//					@Override
//					public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element,
//					                                                       @NotNull ProcessingContext context) {
//						PsiLiteralExpression literalExpression = (PsiLiteralExpression) element;
//						String value = literalExpression.getValue() instanceof String ?
//								(String) literalExpression.getValue() : null;
//						if ((value != null && value.startsWith(GDESC_PREFIX_STR + GDESC_SEPARATOR_STR))) {
//							TextRange property = new TextRange(GDESC_PREFIX_STR.length() + GDESC_SEPARATOR_STR.length() + 1,
//									value.length() + 1);
//							return new PsiReference[]{new GDescReference(element, property)};
//						}
//						return PsiReference.EMPTY_ARRAY;
//					}
//				});
	}

}
