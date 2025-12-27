package org.kathrynhuxtable.gdesc.gdescplugin.structview;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class GDescStructureViewRootElement extends GDescStructureViewElement {
	public GDescStructureViewRootElement(PsiFile element) {
		super(element);
	}

	@NotNull
	@Override
	public ItemPresentation getPresentation() {
		return new GDescRootPresentation((PsiFile) element);
	}
}
