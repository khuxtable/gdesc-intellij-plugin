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

import org.kathrynhuxtable.gdesc.gdescplugin.GDescElementTypeService;
import org.kathrynhuxtable.gdesc.gdescplugin.GDescLanguage;

public class GDescListBlock extends GDescAbstractBlock {
	GDescListBlock(GDescAbstractBlock parentBlock, ASTNode node, Alignment alignment, SpacingBuilder spacingBuilder, CodeStyleSettings settings) {
		super(parentBlock, node, null, alignment, spacingBuilder, settings, false);
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public @Nullable Indent getIndent() {
		return Indent.getNormalIndent();
	}

	@Override
	public @Nullable Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
		CommonCodeStyleSettings commonSettings = settings.getCommonSettings(GDescLanguage.INSTANCE);
		SpacingBuilder spacingBuilder = new SpacingBuilder(commonSettings).after(GDescElementTypeService.COMMA).spaces(1);
		return commonSettings.SPACE_AFTER_COMMA ? spacingBuilder.getSpacing(parentBlock, child1, child2) : null;
	}
}
