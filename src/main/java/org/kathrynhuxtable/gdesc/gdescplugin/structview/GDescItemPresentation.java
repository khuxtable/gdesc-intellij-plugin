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

import javax.swing.*;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.TokenSet;
import org.antlr.intellij.adaptor.lexer.RuleIElementType;
import org.jetbrains.annotations.Nullable;

import org.kathrynhuxtable.gdesc.gdescplugin.GDescIcons;
import org.kathrynhuxtable.gdesc.parser.GameParser;

public class GDescItemPresentation implements ItemPresentation {
	protected final PsiElement element;

	protected GDescItemPresentation(PsiElement element) {
		this.element = element;
	}

	@Nullable
	@Override
	public Icon getIcon(boolean unused) {
		return GDescIcons.FUNC_ICON;
	}

	@Nullable
	@Override
	public String getPresentableText() {
		ASTNode node = element.getNode();
		RuleIElementType elementType = (RuleIElementType) node.getElementType();
		return switch (elementType.getRuleIndex()) {
			case GameParser.RULE_includePragma -> {
				ASTNode[] children = node.getChildren(TokenSet.ANY);
				yield children[2].getText() + " [include]";
			}
			case GameParser.RULE_namePragma -> {
				ASTNode[] children = node.getChildren(TokenSet.ANY);
				yield children[2].getText() + " [name]";
			}
			case GameParser.RULE_versionPragma -> {
				ASTNode[] children = node.getChildren(TokenSet.ANY);
				yield children[2].getText() + " [version]";
			}
			case GameParser.RULE_datePragma -> {
				ASTNode[] children = node.getChildren(TokenSet.ANY);
				yield children[2].getText() +  " [date]";
			}
			case GameParser.RULE_authorPragma -> {
				ASTNode[] children = node.getChildren(TokenSet.ANY);
				yield children[2].getText() + " [author]";
			}
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
			case GameParser.RULE_arrayDirective -> {
				ASTNode[] children = node.getChildren(TokenSet.ANY);
				yield children[2].getText() + " [array]";
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
			default -> "directive:" + elementType.getRuleIndex() + " [directive]";
		};
	}

	@Nullable
	@Override
	public String getLocationString() {
		return null;
	}
}
