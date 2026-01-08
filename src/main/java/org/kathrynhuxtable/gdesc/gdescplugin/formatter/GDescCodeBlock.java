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

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.kathrynhuxtable.gdesc.gdescplugin.GDescLanguage;

import static org.kathrynhuxtable.gdesc.gdescplugin.GDescElementTypeService.COMMA;
import static org.kathrynhuxtable.gdesc.gdescplugin.GDescElementTypeService.SEMICOLON;
import static org.kathrynhuxtable.gdesc.gdescplugin.GDescParserDefinition.*;

public class GDescCodeBlock extends GDescAbstractBlock {
	GDescCodeBlock(GDescAbstractBlock parentBlock, ASTNode node, Wrap wrap, Alignment alignment, SpacingBuilder spacingBuilder, CodeStyleSettings settings, boolean isTopLevel) {
		super(parentBlock, node, wrap, alignment, spacingBuilder, settings, isTopLevel);
	}

	@Override
	public Indent getIndent() {
		return Indent.getNormalIndent();
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
		CommonCodeStyleSettings commonSettings = settings.getCommonSettings(GDescLanguage.INSTANCE);
		SpacingBuilder builder = new SpacingBuilder(commonSettings)
				// Add a space before the '('
				.before(LPAREN)
				.spaceIf(true)
				// Add a space after the ')'
				.after(RPAREN)
				.spaceIf(true)
				// Add a space after a ','
				.after(COMMA)
				.spaceIf(true)
				// Add spaces around ':'
				.around(COLON)
				.spaceIf(true)
				// Add a space after the ';'
				.after(SEMICOLON)
				.spaceIf(true);
		return builder.getSpacing(parentBlock, child1, child2);
	}
}
