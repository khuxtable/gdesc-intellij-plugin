package org.kathrynhuxtable.gdesc.gdescplugin.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public class VariableRef extends GDescElementRef {
	public VariableRef(@NotNull IdentifierPSINode element) {
		super(element);
	}

	@Override
	public boolean isDefSubtree(PsiElement def) {
		return def instanceof VardefSubtree;
	}
}
