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

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.TokenType;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.kathrynhuxtable.gdesc.gdescplugin.GDescParserDefinition.LBRACK;
import static org.kathrynhuxtable.gdesc.gdescplugin.GDescParserDefinition.RBRACE;
import static org.kathrynhuxtable.gdesc.gdescplugin.GDescParserDefinition.LBRACK;
import static org.kathrynhuxtable.gdesc.gdescplugin.GDescParserDefinition.RBRACK;
import static org.kathrynhuxtable.gdesc.gdescplugin.GDescParserDefinition.LPAREN;
import static org.kathrynhuxtable.gdesc.gdescplugin.GDescParserDefinition.RPAREN;

final class GDescFormattingModelBuilder implements FormattingModelBuilder {

//	private static SpacingBuilder createSpaceBuilder(CodeStyleSettings settings) {
//		return new SpacingBuilder(settings, GDescLanguage.INSTANCE)
//				.around(GDescParserDefinition.OPERATOR)
//				.spaceIf(settings.getCommonSettings(GDescLanguage.INSTANCE.getID()).SPACE_AROUND_ASSIGNMENT_OPERATORS)
//				.before(GDescParserDefinition.STRING).none();
//	}

	@Override
	public @NotNull FormattingModel createModel(@NotNull FormattingContext formattingContext) {
		final CodeStyleSettings codeStyleSettings = formattingContext.getCodeStyleSettings();
		return FormattingModelProvider
				.createFormattingModelForPsiFile(formattingContext.getContainingFile(),
						new GDescBlock(null,//formattingContext.getNode(),
								Wrap.createWrap(WrapType.NONE, false),
								Alignment.createAlignment(),
								createSpaceBuilder(codeStyleSettings)),
						codeStyleSettings);
	}

	private SpacingBuilder createSpaceBuilder(CodeStyleSettings settings) {
		return new SpacingBuilder(settings, GDescLanguage.INSTANCE);
	}
}
