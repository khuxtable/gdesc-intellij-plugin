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

import java.util.Collections;
import java.util.List;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GDescLeafBlock extends GDescAbstractBlock {
	Indent indent;

	GDescLeafBlock(GDescAbstractBlock parentBlock, ASTNode node, SpacingBuilder spacingBuilder, Indent indent, Alignment alignment) {
		super(parentBlock, node, Wrap.createWrap(WrapType.NORMAL, false), alignment, spacingBuilder, false);
		this.indent = indent;
	}

	@Override
	protected List<Block> buildChildren() {
		return Collections.emptyList();
	}

	@Override
	public @Nullable Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
		return null;
	}

	@Override
	public @Nullable Indent getIndent() {
		return indent;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}
}
