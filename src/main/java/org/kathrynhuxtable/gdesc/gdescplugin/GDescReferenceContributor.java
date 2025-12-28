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

import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

final class GDescReferenceContributor extends PsiReferenceContributor {

	@Override
	public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
		registrar.registerReferenceProvider(PlatformPatterns.psiElement(PsiLiteralExpression.class),
				new PsiReferenceProvider() {
					@Override
					public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element,
					                                                       @NotNull ProcessingContext context) {
						// TODO Implement this correctly.
//						PsiLiteralExpression literalExpression = (PsiLiteralExpression) element;
//						String value = literalExpression.getValue() instanceof String ?
//								(String) literalExpression.getValue() : null;
//						if ((value != null && value.startsWith(GDESC_PREFIX_STR + GDESC_SEPARATOR_STR))) {
//							TextRange property = new TextRange(GDESC_PREFIX_STR.length() + GDESC_SEPARATOR_STR.length() + 1,
//									value.length() + 1);
//							return new PsiReference[]{new GDescReference(element, property)};
//						}
						return PsiReference.EMPTY_ARRAY;
					}
				});
	}

}
