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

import java.util.HashMap;
import java.util.Map;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.Block;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import org.kathrynhuxtable.gdesc.gdescplugin.GDescLanguage;

import static org.kathrynhuxtable.gdesc.gdescplugin.GDescParserDefinition.LPAREN;
import static org.kathrynhuxtable.gdesc.gdescplugin.GDescParserDefinition.RPAREN;

public class GDescFuncCallExprBlock extends GDescBlock {
	Alignment operandAlignment = Alignment.createAlignment();
	@Getter
	Alignment operatorAlignment;
	Map<ASTNode, Pos> childrenPos = new HashMap<>();

	GDescFuncCallExprBlock(GDescAbstractBlock parentBlock, ASTNode node, Alignment alignment, SpacingBuilder spacingBuilder, CodeStyleSettings settings) {
		super(parentBlock, node, alignment, spacingBuilder, settings);
	}

	public Alignment getAlignment(ASTNode child) {
		if (operatorAlignment == null) {
			// If we are not aligning on the operator then align everything together
			return operandAlignment;
		}
		Pos pos = childrenPos.get(child);
		if (pos == null) {
			pos = Pos.RIGHT;
		}
		return pos == Pos.LEFT ? getAlignment() : operatorAlignment;
	}

	@Override
	public Spacing getSpacing(Block child1, @NotNull Block child2) {
		CommonCodeStyleSettings commonSettings = settings.getCommonSettings(GDescLanguage.INSTANCE);
		SpacingBuilder builder = new SpacingBuilder(commonSettings)
				.around(LPAREN)
				.spaceIf(false)
				.around(RPAREN)
				.spaceIf(false);
		return builder.getSpacing(parentBlock, child1, child2);
	}
}
