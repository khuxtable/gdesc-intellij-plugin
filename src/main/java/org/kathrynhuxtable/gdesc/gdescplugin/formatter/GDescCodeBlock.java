package org.kathrynhuxtable.gdesc.gdescplugin.formatter;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GDescCodeBlock extends GDescAbstractBlock {
	GDescCodeBlock(GDescAbstractBlock parentBlock, ASTNode node, Wrap wrap, Alignment alignment, SpacingBuilder spacingBuilder, boolean isTopLevel) {
		super(parentBlock, node, wrap, alignment, spacingBuilder, isTopLevel);
	}

	@Override
	public Indent getIndent() {
		return isTopLevel ? Indent.getNoneIndent() : Indent.getNormalIndent();
	}

	@Override
	public Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
		return spacingBuilder.getSpacing(this, child1, child2);
	}

	@Override
	public boolean isLeaf() {
		return false;
	}
}
