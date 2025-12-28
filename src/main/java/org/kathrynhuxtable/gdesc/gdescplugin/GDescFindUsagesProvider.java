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

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.TokenSet;
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor;
import org.antlr.intellij.adaptor.lexer.RuleIElementType;
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.kathrynhuxtable.gdesc.gdescplugin.psi.*;
import org.kathrynhuxtable.gdesc.parser.GameLexer;

import static org.kathrynhuxtable.gdesc.parser.GameParser.*;

public class GDescFindUsagesProvider implements FindUsagesProvider {
	/**
	 * Is "find usages" meaningful for a kind of definition subtree?
	 */
	@Override
	public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
		return psiElement instanceof IdentifierPSINode || // the case where we highlight the ID in def subtree itself
				psiElement instanceof GlobalDefSubtree ||
				psiElement instanceof ProcSubtree ||   // remaining cases are for resolve() results
				psiElement instanceof VardefSubtree ||
				psiElement instanceof VariableRef;
	}

	@Nullable
	@Override
	public WordsScanner getWordsScanner() {
		GameLexer lexer = new GameLexer(null);
		ANTLRLexerAdaptor myLexer = new ANTLRLexerAdaptor(GDescLanguage.INSTANCE, lexer);
		return new DefaultWordsScanner(myLexer,
				GDescParserDefinition.IDENTIFIERS,
				GDescParserDefinition.COMMENTS,
				TokenSet.EMPTY);
	}

	@Nullable
	@Override
	public String getHelpId(@NotNull PsiElement psiElement) {
		return null;
	}

	/**
	 * What kind of thing is the ID node? Can group by in "Find Usages" dialog
	 */
	@NotNull
	@Override
	public String getType(PsiElement element) {
		// The parent of an ID node will be a RuleIElementType:
		// function, vardef, formal_arg, statement, expr, call_expr, primary
		ANTLRPsiNode parent = (ANTLRPsiNode) element.getParent();
		RuleIElementType elType = (RuleIElementType) parent.getNode().getElementType();
		return switch (elType.getRuleIndex()) {
			case RULE_functionInvocation -> "function";
			case RULE_stateClause -> "state";
			case RULE_flagClause -> "flag";
			case RULE_verbDirective -> "verb";
			case RULE_variableDirective -> "global variable";
			case RULE_arrayDirective -> "global array";
			case RULE_textDirective -> "text";
			case RULE_fragmentDirective -> "fragment";
			case RULE_placeDirective -> "place";
			case RULE_objectDirective -> "object";
			case RULE_variableDeclarator -> "variable";
			case RULE_statement, RULE_expression, RULE_primary -> "variable";
			default -> "";
		};
	}

	@NotNull
	@Override
	public String getDescriptiveName(PsiElement element) {
		return element.getText();
	}

	@NotNull
	@Override
	public String getNodeText(PsiElement element, boolean useFullName) {
		return element.getText();
	}
}
