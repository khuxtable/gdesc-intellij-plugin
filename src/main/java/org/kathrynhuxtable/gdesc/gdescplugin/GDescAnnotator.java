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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.annotations.NotNull;

import org.kathrynhuxtable.gdesc.gdescplugin.psi.IdentifierPSINode;
import org.kathrynhuxtable.gdesc.parser.GameInfo;


final class GDescAnnotator implements Annotator {

	// Define strings for the GDesc prefix - used for annotations, line markers, etc.
	public final Set<String> internalFunctions = new GameInfo().getInternalFunctionNames();

	// Define strings for the GDesc prefix - used for annotations, line markers, etc.
	public static final String GDESC_PREFIX_STR = "gdesc";
	public static final String GDESC_SEPARATOR_STR = ":";

	@Override
	public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
//		// Ensure the PSI Element is an expression
//		if (!(element instanceof PsiAnnotation annotationExpression)) {
//			return;
//		}
//
//		// Ensure the PSI element contains a string that starts with the prefix and separator
//		String value = annotationExpression.getValue() instanceof String ? (String) annotationExpression.getValue() : null;
//		if (value == null || !value.startsWith(GDESC_PREFIX_STR + GDESC_SEPARATOR_STR)) {
//			return;
//		}
//
//		// Define the text ranges (start is inclusive, end is exclusive)
//		// "gdesc:key"
//		//  01234567890
//		TextRange prefixRange = TextRange.from(element.getTextRange().getStartOffset(), GDESC_PREFIX_STR.length() + 1);
//		TextRange separatorRange = TextRange.from(prefixRange.getEndOffset(), GDESC_SEPARATOR_STR.length());
//		TextRange keyRange = new TextRange(separatorRange.getEndOffset(), element.getTextRange().getEndOffset() - 1);
//
//		// highlight "gdesc" prefix and ":" separator
//		holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
//				.range(prefixRange).textAttributes(DefaultLanguageHighlighterColors.KEYWORD).create();
//		holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
//				.range(separatorRange).textAttributes(GDescSyntaxHighlighter.SEPARATOR).create();
//
//
//		// Get the list of properties for given key
//		String key = value.substring(GDESC_PREFIX_STR.length() + GDESC_SEPARATOR_STR.length());
//		List<GDescProperty> properties = GDescUtil.findProperties(element.getProject(), key);
//		if (properties.isEmpty()) {
//			holder.newAnnotation(HighlightSeverity.ERROR, "Unresolved property")
//					.range(keyRange)
//					.highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
//					// ** Tutorial step 19. - Add a quick fix for the string containing possible properties
//					.withFix(new GDescCreatePropertyQuickFix(key))
//					.create();
//		} else {
//			// Found at least one property, force the text attributes to GDesc syntax value character
//			holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
//					.range(keyRange).textAttributes(GDescSyntaxHighlighter.VALUE).create();
//		}
	}

}
