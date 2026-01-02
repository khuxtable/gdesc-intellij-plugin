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

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.TokenType;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.kathrynhuxtable.gdesc.gdescplugin.GDescLanguage;
import org.kathrynhuxtable.gdesc.gdescplugin.GDescParserDefinition;

import static com.intellij.psi.TokenType.WHITE_SPACE;
import static org.kathrynhuxtable.gdesc.gdescplugin.GDescParserDefinition.*;

final class GDescFormattingModelBuilder implements FormattingModelBuilder {

	@Override
	public @NotNull FormattingModel createModel(@NotNull FormattingContext formattingContext) {
		final CodeStyleSettings codeStyleSettings = formattingContext.getCodeStyleSettings();
		return FormattingModelProvider
				.createFormattingModelForPsiFile(formattingContext.getContainingFile(),
						new GDescCodeBlock(null,
								formattingContext.getNode(),
								Wrap.createWrap(WrapType.NONE, false),
								null,
								createSpaceBuilder(codeStyleSettings),
								true),
						codeStyleSettings);
	}

	private SpacingBuilder createSpaceBuilder(CodeStyleSettings settings) {
		return new SpacingBuilder(settings, GDescLanguage.INSTANCE);
	}

	private boolean isList(ASTNode node) {
		return false;//node.getElementType() == GDescListElementType.LIST;
	}

	private boolean isRhsExpr(ASTNode node) {
		return false;//node.getElementType() == GDescExprElementType.RHS_EXPR;
	}

	private List<GDescAbstractBlock> buildChildren(GDescAbstractBlock parentBlock, ASTNode parentNode, SpacingBuilder spacingBuilder) {
		Alignment alignment = null;
		if (isList(parentNode) || isRhsExpr(parentNode)) {
			// Create alignment for args or for init/cond/update part of "for" stmt or for rhs of assignment
			alignment = Alignment.createAlignment();
		}
		return _buildChildren(parentBlock, parentNode, spacingBuilder, alignment);
	}

	private List<GDescAbstractBlock> _buildChildren(GDescAbstractBlock parentBlock, ASTNode parentNode, SpacingBuilder spacingBuilder, Alignment alignment) {
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

	private GDescAbstractBlock createBlock(GDescAbstractBlock parentBlock, ASTNode node, SpacingBuilder spacingBuilder, ASTNode child, Alignment alignment) {
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
//		} else if (child.getElementType() instanceof GDescStmtElementType || isList(child) ) {
//			return new GDescStmtBlock(parentBlock, child, false/*node.getElementType() == GDescParserDefinition.GDESC_FILE_ELEMENT_TYPE*/, spacingBuilder, alignment);
		} else if (isBinaryOrMethodCallExpr(child)) {
			return new GDescBinaryExpr(parentBlock, child, spacingBuilder, alignment);
		} else if (isElementType(child, TERNARY_EXPRESSION)) {
			return new GDescTernaryExpr(parentBlock, child, spacingBuilder, alignment);
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

	public abstract class GDescAbstractBlock extends AbstractBlock {
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
			childBlocks = GDescFormattingModelBuilder.this.buildChildren(this, getNode(), spacingBuilder);
			return new ArrayList<>(childBlocks);
		}

		public Alignment getOperatorAlignment() {
			return parentBlock == null ? null : parentBlock.getOperatorAlignment();
		}

		@Override
		public boolean isIncomplete() {
			boolean result = super.isIncomplete();
			return result;
		}

		//
		// This controls how the indenting/alignment works for the first line after "enter" pressed
		//
		@Override
		public @NotNull ChildAttributes getChildAttributes(int newChildIndex) {
			Alignment alignment = null;
			if (isList(getNode()) && childBlocks != null && childBlocks.size() >= 2) {
				// Align args to call based on alignment of first arg
				alignment = childBlocks.get(0).getAlignment();
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
	}

	public enum Pos {LEFT, RIGHT}

	public class GDescBinaryExpr extends GDescBlock {
		Alignment operandAlignment = Alignment.createAlignment();
		Alignment operatorAlignment;
		Map<ASTNode, Pos> childrenPos = new HashMap<>();

		GDescBinaryExpr(GDescAbstractBlock parentBlock, ASTNode node, SpacingBuilder spacingBuilder, Alignment alignment) {
			super(parentBlock, node, spacingBuilder, alignment);
			Pos pos = Pos.LEFT;
			// If we are a method call then include our binary expr children as ours so we pretend
			// that we are the binary expr
			boolean isMethodCall = isElementType(node, FUNC_REF);
			Predicate<ASTNode> flattenChildren = child -> false/*isMethodCall && isElementType(child, GDescExprElementType.BINARY_EXPR)*/;
			Alignment parentOpAlign = parentBlock.getOperatorAlignment();
			for (ASTNode child : Arrays.stream(getNode().getChildren(null))
					.flatMap(child -> flattenChildren.test(child) ? Arrays.stream(child.getChildren(null)) : Stream.of(child))
					.collect(Collectors.toList())) {
				IElementType elementType = child.getElementType();
				if (pos == Pos.LEFT && GDescParserDefinition.OPERATOR.contains(elementType)) {
					// Once we get to the operator we need to check on newlines positions to decide how to align
					// We only align on operators when they are the first thing on a line (i.e. previous whitespace included '\n')
					// and are not followed by a newline. We also align on operator if our parent is operator aligned.
					Function<ASTNode, Boolean> hasNewLine = n -> n instanceof PsiWhiteSpace && n.getText().contains("\n");
					if (parentOpAlign != null || hasNewLine.apply(child.getTreePrev()) && !hasNewLine.apply(child.getTreeNext())) {
						operatorAlignment = parentOpAlign == null ? Alignment.createAlignment() : parentOpAlign;
						pos = Pos.RIGHT;
					}
				}
				childrenPos.put(child, pos);
			}
		}

		public Alignment getOperatorAlignment() {
			return operatorAlignment;
		}

		public Alignment getAlignment(ASTNode child) {
			if (operatorAlignment == null) {
				// If we are not aligning on the operator then align everything together
				return operandAlignment;
			}
			Pos pos = childrenPos.get(child);
			if (pos == null) {
				pos = Pos.RIGHT;
			}
			return pos == Pos.LEFT ? getAlignment() : operatorAlignment;
		}
	}

	private static boolean isBinaryOrMethodCallExpr(ASTNode child) {
		// Need to ignore bracketed BinaryExpr since they are just "(" BinaryExpr ")" and have no operator to align on
		return //isElementType(child, GDescParserDefinition.BINARY_EXPR) &&
				!isElementType(getFirstChildNotWhiteSpace(child.getPsi()), LPAREN) ||
				isElementType(child, FUNC_REF);
	}

	public class GDescTernaryExpr extends GDescBlock {
		Alignment operatorAlignment = Alignment.createAlignment();

		GDescTernaryExpr(GDescAbstractBlock parentBlock, ASTNode node, SpacingBuilder spacingBuilder, Alignment alignment) {
			super(parentBlock, node, spacingBuilder, alignment);
		}

		@Override
		public Alignment getAlignment(ASTNode node) {
			IElementType elementType = node.getElementType();
			if (isElementType(node, QUESTION, COLON)) {
				return operatorAlignment;
			}
			return getAlignment();
		}
	}

	public class GDescLeafBlock extends GDescAbstractBlock {
		Indent indent;

		GDescLeafBlock(GDescAbstractBlock parentBlock, ASTNode node, SpacingBuilder spacingBuilder, Indent indent, Alignment alignment) {
			super(parentBlock, node, Wrap.createWrap(WrapType.NORMAL, false), alignment, spacingBuilder, false);
			this.indent = indent;
		}

		@Override
		protected List<Block> buildChildren() {
			return Collections.EMPTY_LIST;
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
			// FIXME What is this about?
//			if (myNode instanceof GDescPsiList) {
//				return Indent.getNormalIndent();
//			}
			return isTopLevel || myAlignment != null ? Indent.getNoneIndent() : Indent.getNormalIndent();
		}
	}

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
			return getAlignment() == null ? Indent.getContinuationWithoutFirstIndent() : Indent.getNoneIndent();
		}

		@Override
		public boolean isLeaf() {
			return false;
		}
	}

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
}
