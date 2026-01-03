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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.TokenType;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import org.kathrynhuxtable.gdesc.gdescplugin.GDescParserDefinition;

import static com.intellij.psi.TokenType.WHITE_SPACE;
import static org.kathrynhuxtable.gdesc.gdescplugin.GDescParserDefinition.*;

public abstract class GDescAbstractBlock extends AbstractBlock {

	public enum Pos {LEFT, RIGHT}

	boolean isTopLevel;
	SpacingBuilder spacingBuilder;
	List<GDescAbstractBlock> childBlocks;
	Alignment alignment;
	GDescAbstractBlock parentBlock;

	GDescAbstractBlock(GDescAbstractBlock parentBlock, ASTNode node, Wrap wrap, Alignment alignment, SpacingBuilder spacingBuilder, boolean isTopLevel) {
		super(node, /*wrap*/ null, alignment);
		this.parentBlock = parentBlock;
		this.spacingBuilder = spacingBuilder;
		this.isTopLevel = isTopLevel;
		this.alignment = alignment;
	}

	@Override
	protected List<Block> buildChildren() {
		childBlocks = GDescAbstractBlock.this.buildChildren(this, getNode(), spacingBuilder);
		return new ArrayList<>(childBlocks);
	}

	public Alignment getOperatorAlignment() {
		return parentBlock == null ? null : parentBlock.getOperatorAlignment();
	}

	@Override
	public boolean isIncomplete() {
		return super.isIncomplete();
	}

	//
	// This controls how the indenting/alignment works for the first line after "enter" pressed
	//
	@Override
	public @NotNull ChildAttributes getChildAttributes(int newChildIndex) {
		Alignment alignment = null;
		if (isList(getNode()) && childBlocks != null && childBlocks.size() >= 2) {
			// Align args to call based on alignment of first arg
			alignment = childBlocks.getFirst().getAlignment();
		} else if (getNode().getElementType() == BASIC_FOR_STATEMENT && childBlocks != null) {
			alignment = childBlocks.stream().map(Block::getAlignment).filter(Objects::nonNull).findFirst().orElse(null);
		} else if (newChildIndex == 0) {
			alignment = getAlignment();    // If parent is aligned then first child should be aligned
		}
		return new ChildAttributes(isTopLevel && !(this instanceof GDescStmtBlock) ? Indent.getNoneIndent() : Indent.getNormalIndent(), alignment);
	}

	public Alignment getAlignment(ASTNode node) {
		return getAlignment();
	}

	@Override
	public Alignment getAlignment() {
		return alignment;
	}

	protected boolean isList(ASTNode node) {
		return node.getElementType() == OPTIONAL_EXPRESSION_LIST;
	}

	protected boolean isRhsExpr(ASTNode node) {
		return true;//node.getElementType() == GDescExprElementType.RHS_EXPR;
	}

	protected List<GDescAbstractBlock> buildChildren(GDescAbstractBlock parentBlock, ASTNode parentNode, SpacingBuilder spacingBuilder) {
		Alignment alignment = null;
//		if (isList(parentNode) || isRhsExpr(parentNode)) {
//			// Create alignment for args or for init/cond/update part of "for" stmt or for rhs of assignment
//			alignment = Alignment.createAlignment();
//		}
		return _buildChildren(parentBlock, parentNode, spacingBuilder, alignment);
	}

	protected List<GDescAbstractBlock> _buildChildren(GDescAbstractBlock parentBlock, ASTNode parentNode, SpacingBuilder spacingBuilder, Alignment alignment) {
		List<GDescAbstractBlock> blocks = new ArrayList<>();
		for (ASTNode child = parentNode.getFirstChildNode(); child != null; child = child.getTreeNext()) {
//			// If we are a method call then subsume binary expr children into ours to make alignement
//			// work better with auto-indent and aligning on '.' of method calls. This is due to how
//			// auto-indent looks for candidate alignments. Only if text range of element overlaps with
//			// place of indent will it then drill down into children to look for alignments.
//			if (isElementType(parentNode, GDescParserDefinition.FUNC_REF) && isElementType(child, GDescExprElementType.BINARY_EXPR)) {
//				blocks.addAll(_buildChildren(parentBlock, child, spacingBuilder, alignment));
//				continue;
//			}
			GDescAbstractBlock block = createBlock(parentBlock, parentNode, spacingBuilder, child, alignment == null ? parentBlock.getAlignment(child) : alignment);
			if (block != null) {
				blocks.add(block);
			}
		}
		return blocks;
	}

	protected GDescAbstractBlock createBlock(GDescAbstractBlock parentBlock, ASTNode node, SpacingBuilder spacingBuilder, ASTNode child, Alignment alignment) {
		if (child.getElementType() == TokenType.WHITE_SPACE) {
			return null;
		}
		if (child.getFirstChildNode() == null) {
			boolean isSpecialChar = isElementType(child, LBRACE, RBRACE, LBRACK, RBRACK, LPAREN, RPAREN);
			Indent indent = isSpecialChar ? Indent.getNoneIndent() :
					isElementType(child, GDescParserDefinition.COMMENT, GDescParserDefinition.LINE_COMMENT) ? Indent.getNormalIndent() :
							alignment != null ? Indent.getNoneIndent()
									: Indent.getContinuationWithoutFirstIndent();
			return new GDescLeafBlock(parentBlock, child, spacingBuilder, indent, alignment);
		} else if (isElementType(child, BLOCK) /*&& isElementType(child.getTreeNext(), LBRACE)*/) {
			return new GDescCodeBlock(parentBlock, child, Wrap.createWrap(WrapType.NONE, false), alignment, spacingBuilder, false);
		} else if (isElementType(child, BLOCK, EMPTY_STATEMENT, LOCAL_VARIABLE_DECLARATION_STATEMENT, EXPRESSION_STATEMENT,
				BREAK_STATEMENT, CONTINUE_STATEMENT, RETURN_STATEMENT, IF_STATEMENT, IF_THEN_ELSE_STATEMENT,
				WHILE_STATEMENT, REPEAT_STATEMENT, BASIC_FOR_STATEMENT, ENHANCED_FOR_STATEMENT) || isList(child)) {
			return new GDescStmtBlock(parentBlock, child, false/*node.getElementType() == GDescParserDefinition.GDESC_FILE_ELEMENT_TYPE*/, spacingBuilder, alignment);
		} else if (isBinaryOrMethodCallExpr(child)) {
			return new GDescBinaryExpr(parentBlock, child, spacingBuilder, alignment);
		} else if (isElementType(child, TERNARY_EXPRESSION)) {
			return new GDescTernaryExpr(parentBlock, child, spacingBuilder, alignment);
// GDesc does not implement closures, so I don't think I need this, but I'm not deleting it yet.
//		} else if (child.getElementType() instanceof GDescExprElementType) {
//			// Don't align closures even when passed as args to calls
//			return new GDescBlock(parentBlock, child, spacingBuilder, isElementType(child, GDescExprElementType.CLOSURE) ? null : alignment);
		} else {
			return new GDescBlock(parentBlock, child, spacingBuilder, alignment);
		}
	}

	public static boolean isElementType(PsiElement element, IElementType... types) {
		return element != null && isElementType(element.getNode(), types);
	}

	public static boolean isElementType(ASTNode node, IElementType... types) {
		if (node == null) {
			return false;
		}
		return Arrays.stream(types).anyMatch(t -> node.getElementType() == t);
	}

	// Get first non-comment, non-whitespace child
	public static PsiElement getFirstChildNotWhiteSpace(PsiElement parent) {
		return getFirstChild(parent, child -> Stream.of(GDescParserDefinition.COMMENT, GDescParserDefinition.WHITESPACE, WHITE_SPACE)
				.noneMatch(type -> child.getNode().getElementType().equals(type)));
	}

	public static PsiElement getFirstChild(PsiElement parent, Predicate<PsiElement> matcher) {
		for (PsiElement child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
			if (matcher.test(child)) {
				return child;
			}
		}
		return null;
	}

	protected static boolean isBinaryOrMethodCallExpr(ASTNode child) {
		// Need to ignore bracketed BinaryExpr since they are just "(" BinaryExpr ")" and have no operator to align on
		return isElementType(child, CONDITIONAL_OR_EXPRESSION, CONDITIONAL_AND_EXPRESSION, INCLUSIVE_OR_EXPRESSION,
				EXCLUSIVE_OR_EXPRESSION, AND_EXPRESSION, RELATIONAL_EXPRESSION, SHIFT_EXPRESSION, ADDITIVE_EXPRESSION,
				MULTIPLICATIVE_EXPRESSION) &&
				!isElementType(getFirstChildNotWhiteSpace(child.getPsi()), LPAREN) ||
				isElementType(child, FUNC_REF);
	}

}
