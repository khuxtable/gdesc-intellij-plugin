package org.kathrynhuxtable.gdesc.gdescplugin.formatter;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.lang.ASTNode;

import static org.kathrynhuxtable.gdesc.gdescplugin.GDescParserDefinition.COLON;
import static org.kathrynhuxtable.gdesc.gdescplugin.GDescParserDefinition.QUESTION;

public class GDescTernaryExpr extends GDescBlock {
	Alignment operatorAlignment = Alignment.createAlignment();

	GDescTernaryExpr(GDescAbstractBlock parentBlock, ASTNode node, SpacingBuilder spacingBuilder, Alignment alignment) {
		super(parentBlock, node, spacingBuilder, alignment);
	}

	@Override
	public Alignment getAlignment(ASTNode node) {
		if (isElementType(node, QUESTION, COLON)) {
			return operatorAlignment;
		}
		return getAlignment();
	}
}
