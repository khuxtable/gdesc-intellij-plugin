package org.kathrynhuxtable.gdesc.gdescplugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode;
import org.antlr.intellij.adaptor.psi.ScopeNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.kathrynhuxtable.gdesc.gdescplugin.GDescLanguage;
import org.kathrynhuxtable.gdesc.gdescplugin.GDescSymtabUtils;

public class BasicForSubtree extends ANTLRPsiNode implements ScopeNode {

	public static final String XPATH =
			"/*/forInit/localVariableDeclaration/variableDeclarator/IDENTIFIER";

	public BasicForSubtree(@NotNull ASTNode node) {
		super(node);
	}

	@Nullable
	@Override
	public PsiElement resolve(PsiNamedElement element) {
//		System.out.println(getClass().getSimpleName() +
//				".resolve(" + element.getName() +
//				" at " + Integer.toHexString(getNode().hashCode()) + ")");

		return GDescSymtabUtils.resolve(this, GDescLanguage.INSTANCE,
				element, XPATH);
	}
}
