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
package org.kathrynhuxtable.gdesc.gdescplugin.formatter;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.Block;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.kathrynhuxtable.gdesc.gdescplugin.GDescLanguage;

import static org.kathrynhuxtable.gdesc.gdescplugin.GDescParserDefinition.COLON;
import static org.kathrynhuxtable.gdesc.gdescplugin.GDescParserDefinition.QUESTION;

public class GDescTernaryExpr extends GDescBlock {
	Alignment operatorAlignment = Alignment.createAlignment();

	GDescTernaryExpr(GDescAbstractBlock parentBlock, ASTNode node, Alignment alignment, SpacingBuilder spacingBuilder, CodeStyleSettings settings) {
		super(parentBlock, node, alignment, spacingBuilder, settings);
	}

	@Override
	public Alignment getAlignment(ASTNode node) {
		if (isElementType(node, QUESTION, COLON)) {
			return operatorAlignment;
		}
		return getAlignment();
	}

	@Override
	public @Nullable Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
		CommonCodeStyleSettings commonSettings = settings.getCommonSettings(GDescLanguage.INSTANCE);
		SpacingBuilder builder = new SpacingBuilder(commonSettings)
				// Add a space before and after the '?'
				.around(QUESTION)
				.spaceIf(true)
				// Add a space before and after the ':'
				.around(COLON)
				.spaceIf(true);
		return builder.getSpacing(parentBlock, child1, child2);
	}
}
