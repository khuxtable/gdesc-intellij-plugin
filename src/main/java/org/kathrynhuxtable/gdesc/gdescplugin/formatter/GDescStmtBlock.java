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
