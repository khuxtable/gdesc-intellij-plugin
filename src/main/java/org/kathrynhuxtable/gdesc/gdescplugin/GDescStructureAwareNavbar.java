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
package org.kathrynhuxtable.gdesc.gdescplugin;

import javax.swing.*;

import com.intellij.ide.navigationToolbar.StructureAwareNavBarModelExtension;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.psi.tree.TokenSet;
import org.antlr.intellij.adaptor.lexer.RuleIElementType;
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.kathrynhuxtable.gdesc.gdescplugin.psi.*;
import org.kathrynhuxtable.gdesc.parser.GameParser;

final class GDescStructureAwareNavbar extends StructureAwareNavBarModelExtension {

	@NotNull
	@Override
	protected Language getLanguage() {
		return GDescLanguage.INSTANCE;
	}

	@Override
	public @Nullable String getPresentableText(Object object) {
		if (object instanceof GDescPSIFileRoot) {
			return ((GDescPSIFileRoot) object).getVirtualFile().getNameWithoutExtension();
		} else if (object instanceof ANTLRPsiNode antlrPsiNode) {
			ASTNode node = antlrPsiNode.getNode();
			RuleIElementType elementType = (RuleIElementType) node.getElementType();
			return switch (elementType.getRuleIndex()) {
				case GameParser.RULE_includePragma, GameParser.RULE_infoPragma, GameParser.RULE_flagDirective,
				     GameParser.RULE_stateDirective, GameParser.RULE_verbDirective, GameParser.RULE_noiseDirective, GameParser.RULE_variableDirective,
				     GameParser.RULE_textDirective, GameParser.RULE_fragmentDirective, GameParser.RULE_placeDirective,
				     GameParser.RULE_objectDirective, GameParser.RULE_actionDirective, GameParser.RULE_procDirective -> {
					ASTNode[] children = node.getChildren(TokenSet.ANY);
					yield children[2].getText();
				}
				case GameParser.RULE_initialDirective -> "initial";
				case GameParser.RULE_repeatDirective -> "repeat";
				default -> "directive:" + elementType.getRuleIndex();
			};
		} else {
			return null;
		}
	}

	@Override
	@Nullable
	public Icon getIcon(Object object) {
		return switch (object) {
			case IncludePragmaSubtree include -> GDescIcons.INCLUDE_PRAGMA_ICON;
			case InfoPragmaSubtree info -> GDescIcons.INFO_PRAGMA_ICON;
			case FlagDirectiveSubtree flag -> GDescIcons.FLAG_DIRECTIVE_ICON;
			case StateDirectiveSubtree state -> GDescIcons.STATE_DIRECTIVE_ICON;
			case NoiseDirectiveSubtree noise -> GDescIcons.NOISE_DIRECTIVE_ICON;
			case VerbDirectiveSubtree verb -> GDescIcons.VERB_DIRECTIVE_ICON;
			case VariableDirectiveSubtree variable -> GDescIcons.VARIABLE_DIRECTIVE_ICON;
			case TextDirectiveSubtree text ->
				text.isFragment() ? GDescIcons.FRAGMENT_DIRECTIVE_ICON : GDescIcons.TEXT_DIRECTIVE_ICON;
			case PlaceDirectiveSubtree place -> GDescIcons.PLACE_DIRECTIVE_ICON;
			case ObjectDirectiveSubtree obj -> GDescIcons.OBJECT_DIRECTIVE_ICON;
			case ActionDirectiveSubtree action -> GDescIcons.ACTION_DIRECTIVE_ICON;
			case ProcDirectiveSubtree proc -> GDescIcons.PROC_DIRECTIVE_ICON;
			case MainBlockSubtree initial ->
				initial.isInitial() ? GDescIcons.INITIAL_DIRECTIVE_ICON : GDescIcons.REPEAT_DIRECTIVE_ICON;
			default -> null;
		};
	}
}
