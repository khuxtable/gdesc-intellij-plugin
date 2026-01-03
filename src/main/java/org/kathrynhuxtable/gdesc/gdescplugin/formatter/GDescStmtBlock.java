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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.kathrynhuxtable.gdesc.gdescplugin.GDescParserDefinition.*;

public class GDescStmtBlock extends GDescAbstractBlock {
	GDescStmtBlock(GDescAbstractBlock parentBlock, ASTNode node, boolean isTopLevel, SpacingBuilder spacingBuilder, Alignment alignment) {
		super(parentBlock, node, null, alignment, spacingBuilder, isTopLevel);
	}

	@Override
	public @Nullable Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public @Nullable Indent getIndent() {
		if (isElementType(myNode, OPTIONAL_EXPRESSION_LIST)) {
			return Indent.getNormalIndent();
		} else if (isElementType(myNode, INCLUDE_PRAGMA, INFO_PRAGMA, FLAG_DIRECTIVE, STATE_DIRECTIVE, NOISE_DIRECTIVE,
				VERB_DIRECTIVE, VARIABLE_DIRECTIVE, TEXT_DIRECTIVE, FRAGMENT_DIRECTIVE, PLACE_DIRECTIVE, OBJECT_DIRECTIVE,
				ACTION_DIRECTIVE, PROC_DIRECTIVE, INITIAL_DIRECTIVE, REPEAT_DIRECTIVE)) {
			return null;
		}
		return isTopLevel || myAlignment != null ? Indent.getNoneIndent() : Indent.getNormalIndent();
	}
}
