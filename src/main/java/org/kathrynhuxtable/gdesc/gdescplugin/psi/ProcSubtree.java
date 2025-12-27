package org.kathrynhuxtable.gdesc.gdescplugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.psi.IdentifierDefSubtree;
import org.antlr.intellij.adaptor.psi.ScopeNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.kathrynhuxtable.gdesc.gdescplugin.GDescLanguage;
import org.kathrynhuxtable.gdesc.gdescplugin.GDescSymtabUtils;

/**
 * A subtree associated with a function definition.
 * Its scope is the set of arguments.
 */
public class ProcSubtree extends IdentifierDefSubtree implements ScopeNode, GDescDirectiveElement {
	public ProcSubtree(@NotNull ASTNode node, @NotNull IElementType idElementType) {
		super(node, idElementType);
	}

	@Nullable
	@Override
	public PsiElement resolve(PsiNamedElement element) {
		System.out.println(getClass().getSimpleName() +
				".resolve(" + getNode() +
				" at " + Integer.toHexString(getNode().hashCode()) + ")");

		return GDescSymtabUtils.resolve(this, GDescLanguage.INSTANCE,
				element, "/procDirective/IDENTIFIER");
	}
}
