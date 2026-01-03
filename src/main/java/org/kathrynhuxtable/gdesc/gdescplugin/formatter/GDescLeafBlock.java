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
