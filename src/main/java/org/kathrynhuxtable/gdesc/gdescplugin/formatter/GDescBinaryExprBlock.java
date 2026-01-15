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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.Block;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.psi.tree.IElementType;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import org.kathrynhuxtable.gdesc.gdescplugin.GDescElementTypeService;
import org.kathrynhuxtable.gdesc.gdescplugin.GDescLanguage;

import static org.kathrynhuxtable.gdesc.gdescplugin.GDescParserDefinition.FUNC_REF;

public class GDescBinaryExprBlock extends GDescBlock {
	Alignment operandAlignment = Alignment.createAlignment();
	@Getter
	Alignment operatorAlignment;
	Map<ASTNode, Pos> childrenPos = new HashMap<>();

	GDescBinaryExprBlock(GDescAbstractBlock parentBlock, ASTNode node, Alignment alignment, SpacingBuilder spacingBuilder, CodeStyleSettings settings) {
		super(parentBlock, node, alignment, spacingBuilder, settings);
		Pos pos = Pos.LEFT;
		// If we are a method call then include our binary expr children as ours so we pretend
		// that we are the binary expr
		boolean isMethodCall = isElementType(node, FUNC_REF);
		Predicate<ASTNode> flattenChildren = child -> isMethodCall && isBinaryExpr(child);
		Alignment parentOpAlign = parentBlock.getOperatorAlignment();
		for (ASTNode child : Arrays.stream(getNode().getChildren(null))
				.flatMap(child -> flattenChildren.test(child) ? Arrays.stream(child.getChildren(null)) : Stream.of(child))
				.toList()) {
			IElementType elementType = child.getElementType();
			if (pos == Pos.LEFT && GDescElementTypeService.OPERATOR.contains(elementType)) {
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

	@Override
	public Spacing getSpacing(Block child1, @NotNull Block child2) {
		CommonCodeStyleSettings commonSettings = settings.getCommonSettings(GDescLanguage.INSTANCE);
		return Spacing.createSpacing(commonSettings.SPACE_AROUND_ASSIGNMENT_OPERATORS ? 1 : 0, Integer.MAX_VALUE, 0, true, 1);
	}
}
