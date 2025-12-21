// Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

package org.kathrynhuxtable.gdesc.gdescplugin;

import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import org.antlr.intellij.adaptor.lexer.RuleIElementType;
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.kathrynhuxtable.gdesc.gdescplugin.psi.FunctionSubtree;
import org.kathrynhuxtable.gdesc.gdescplugin.psi.IdentifierPSINode;
import org.kathrynhuxtable.gdesc.gdescplugin.psi.VardefSubtree;

import static org.kathrynhuxtable.gdesc.gdescplugin.parser.GameParser.*;

public class GDescFindUsagesProvider implements FindUsagesProvider {
	/**
	 * Is "find usages" meaningful for a kind of definition subtree?
	 */
	@Override
	public boolean canFindUsagesFor(PsiElement psiElement) {
		return psiElement instanceof IdentifierPSINode || // the case where we highlight the ID in def subtree itself
				psiElement instanceof FunctionSubtree ||   // remaining cases are for resolve() results
				psiElement instanceof VardefSubtree;
	}

	@Nullable
	@Override
	public WordsScanner getWordsScanner() {
		return null; // null implies use SimpleWordScanner default
	}

	@Nullable
	@Override
	public String getHelpId(PsiElement psiElement) {
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
		switch (elType.getRuleIndex()) {
		case RULE_functionInvocation:
			return "function";
		case RULE_variableDeclarator:
		case RULE_directive:
			return "variable";
		case RULE_statement:
		case RULE_expression:
		case RULE_primary:
			return "variable";
		}
		return "";
	}

	@NotNull
	@Override
	public String getDescriptiveName(PsiElement element) {
		return element.getText();
	}

	@NotNull
	@Override
	public String getNodeText(PsiElement element, boolean useFullName) {
		String text = element.getText();
		return text;
	}
}
