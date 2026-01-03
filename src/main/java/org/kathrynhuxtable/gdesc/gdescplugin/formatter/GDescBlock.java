package org.kathrynhuxtable.gdesc.gdescplugin.formatter;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.kathrynhuxtable.gdesc.gdescplugin.GDescParserDefinition.DIRECTIVE;
import static org.kathrynhuxtable.gdesc.gdescplugin.GDescParserDefinition.GAME;

public class GDescBlock extends GDescAbstractBlock {
	GDescBlock(GDescAbstractBlock parentBlock, ASTNode node, SpacingBuilder spacingBuilder, Alignment alignment) {
		super(parentBlock, node, null, alignment, spacingBuilder, false);
	}

	@Override
	public @Nullable Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
		return null;
	}

	@Override
	public @Nullable Indent getIndent() {
		if (isElementType(myNode, GAME, DIRECTIVE)) {
			return Indent.getNoneIndent();
		} else if (getAlignment() == null) {
			return Indent.getContinuationWithoutFirstIndent();
		} else {
			return Indent.getNoneIndent();
		}
	}

	@Override
	public boolean isLeaf() {
		return false;
	}
}
