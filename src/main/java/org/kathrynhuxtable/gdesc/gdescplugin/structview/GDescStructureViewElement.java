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
package org.kathrynhuxtable.gdesc.gdescplugin.structview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.TokenSet;
import org.antlr.intellij.adaptor.lexer.RuleIElementType;
import org.antlr.intellij.adaptor.xpath.XPath;
import org.jetbrains.annotations.NotNull;

import org.kathrynhuxtable.gdesc.gdescplugin.GDescLanguage;
import org.kathrynhuxtable.gdesc.gdescplugin.psi.GDescPSIFileRoot;
import org.kathrynhuxtable.gdesc.parser.GameParser;

public class GDescStructureViewElement implements StructureViewTreeElement, SortableTreeElement {
	protected final PsiElement element;

	public GDescStructureViewElement(PsiElement element) {
		this.element = element;
	}

	@Override
	public Object getValue() {
		return element;
	}

	@Override
	public void navigate(boolean requestFocus) {
		if (element instanceof NavigationItem) {
			((NavigationItem) element).navigate(requestFocus);
		}
	}

	@Override
	public boolean canNavigate() {
		return element instanceof NavigationItem &&
				((NavigationItem) element).canNavigate();
	}

	@Override
	public boolean canNavigateToSource() {
		return element instanceof NavigationItem &&
				((NavigationItem) element).canNavigateToSource();
	}

	@NotNull
	@Override
	public String getAlphaSortKey() {
		ASTNode node = element.getNode();
		RuleIElementType elementType = (RuleIElementType) node.getElementType();
		return switch (elementType.getRuleIndex()) {
			case GameParser.RULE_includePragma, GameParser.RULE_infoPragma -> element.getText();
			case GameParser.RULE_flagDirective -> {
				ASTNode[] children = node.getChildren(TokenSet.ANY);
				yield children[2].getText() + " [flag]";
			}
			case GameParser.RULE_stateDirective -> {
				ASTNode[] children = node.getChildren(TokenSet.ANY);
				yield children[2].getText() + " [state]";
			}
			case GameParser.RULE_noiseDirective -> "noise";
			case GameParser.RULE_verbDirective -> {
				ASTNode[] children = node.getChildren(TokenSet.ANY);
				yield children[2].getText() + " [verb]";
			}
			case GameParser.RULE_variableDirective -> {
				ASTNode[] children = node.getChildren(TokenSet.ANY);
				yield children[2].getText() + " [variable]";
			}
			case GameParser.RULE_textDirective -> {
				ASTNode[] children = node.getChildren(TokenSet.ANY);
				yield children[2].getText() + " [text]";
			}
			case GameParser.RULE_fragmentDirective -> {
				ASTNode[] children = node.getChildren(TokenSet.ANY);
				yield children[2].getText() + " [fragment]";
			}
			case GameParser.RULE_placeDirective -> {
				ASTNode[] children = node.getChildren(TokenSet.ANY);
				yield children[2].getText() + " [place]";
			}
			case GameParser.RULE_objectDirective -> {
				ASTNode[] children = node.getChildren(TokenSet.ANY);
				yield children[2].getText() + " [object]";
			}
			case GameParser.RULE_actionDirective -> {
				ASTNode[] children = node.getChildren(TokenSet.ANY);
				yield children[2].getText() + " [action]";
			}
			case GameParser.RULE_procDirective -> {
				ASTNode[] children = node.getChildren(TokenSet.ANY);
				yield children[2].getText() + " [proc]";
			}
			case GameParser.RULE_initialDirective -> "initial [initial]";
			case GameParser.RULE_repeatDirective -> "repeat [repeat]";
			default -> "unknown key";
		};
	}

	@NotNull
	public String getTypeSortKey() {
		ASTNode node = element.getNode();
		RuleIElementType elementType = (RuleIElementType) node.getElementType();
		return switch (elementType.getRuleIndex()) {
			case GameParser.RULE_includePragma, GameParser.RULE_infoPragma -> "Pragma";
			case GameParser.RULE_flagDirective -> "Flag";
			case GameParser.RULE_stateDirective -> "State";
			case GameParser.RULE_noiseDirective -> "Noise";
			case GameParser.RULE_verbDirective -> "Verb";
			case GameParser.RULE_variableDirective -> "Variable";
			case GameParser.RULE_textDirective -> "Text";
			case GameParser.RULE_fragmentDirective -> "Fragment";
			case GameParser.RULE_placeDirective -> "Place";
			case GameParser.RULE_objectDirective -> "Object";
			case GameParser.RULE_actionDirective -> "Action";
			case GameParser.RULE_procDirective -> "Proc";
			case GameParser.RULE_initialDirective -> "Initial";
			case GameParser.RULE_repeatDirective -> "Repeat";
			default -> "unknown key";
		};
	}

	@NotNull
	@Override
	public ItemPresentation getPresentation() {
		return new GDescItemPresentation(element);
	}

	@Override
	public TreeElement @NotNull [] getChildren() {
		if (element instanceof GDescPSIFileRoot) {
			Collection<? extends PsiElement> directives = XPath.findAll(GDescLanguage.INSTANCE, element, "/game/directive/*");
			List<TreeElement> treeElements = new ArrayList<>(directives.size());
			for (PsiElement el : directives) {
				treeElements.add(new GDescStructureViewElement(el));
			}
			return treeElements.toArray(new TreeElement[directives.size()]);
		}
		return new TreeElement[0];
	}
}
