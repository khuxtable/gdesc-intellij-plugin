package org.kathrynhuxtable.gdesc.gdescplugin.psi;

import javax.swing.*;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import org.antlr.intellij.adaptor.psi.ScopeNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.kathrynhuxtable.gdesc.gdescplugin.GDescFileType;
import org.kathrynhuxtable.gdesc.gdescplugin.GDescIcons;
import org.kathrynhuxtable.gdesc.gdescplugin.GDescLanguage;
import org.kathrynhuxtable.gdesc.gdescplugin.GDescSymtabUtils;

public class GDescPSIFileRoot extends PsiFileBase implements ScopeNode {

	public GDescPSIFileRoot(@NotNull FileViewProvider viewProvider) {
		super(viewProvider, GDescLanguage.INSTANCE);
	}

	@NotNull
	@Override
	public FileType getFileType() {
		return GDescFileType.INSTANCE;
	}

	@Override
	public String toString() {
		return "GDesc File";
	}

	@Override
	public Icon getIcon(int flags) {
		return GDescIcons.FILE;
	}

	/**
	 * Return null since a file scope has no enclosing scope. It is
	 * not itself in a scope.
	 */
	@Override
	public ScopeNode getContext() {
		return null;
	}

	@Nullable
	@Override
	public PsiElement resolve(PsiNamedElement element) {
		System.out.println(getClass().getSimpleName()+
		                   ".resolve("+element.getName()+
		                   " at "+Integer.toHexString(element.hashCode())+")");
		return GDescSymtabUtils.resolve(
				this,
				GDescLanguage.INSTANCE,
				element,
				"/game/directive/*/IDENTIFIER",
				"/game/directive/flagDirective/flagClause/IDENTIFIER",
				"/game/directive/stateDirective/stateClause/IDENTIFIER");
	}
}
