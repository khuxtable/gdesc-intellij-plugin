package org.kathrynhuxtable.gdesc.gdescplugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import org.kathrynhuxtable.gdesc.gdescplugin.psi.IdentifierPSINode;


final class GDescAnnotator implements Annotator {

	// Define strings for the GDesc prefix - used for annotations, line markers, etc.
	public static final Set<String> internalFunctions = new HashSet<>(Arrays.asList(
			"anyof",
			"append",
			"apport",
			"atplace",
			"chance",
			"clearflag",
			"describe",
			"drop",
			"flush",
			"get",
			"goto",
			"input",
			"inrange",
			"isat",
			"isflag",
			"ishave",
			"ishere",
			"isnear",
			"key",
			"move",
			"needcmd",
			"query",
			"quip",
			"respond",
			"say",
			"setflag",
			"smove",
			"stop",
			"tie",
			"typed",
			"varis",
			"vocab"));

	@Override
	public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
//		if (!(element instanceof IdentifierPSINode callSubtree)) {
//			return;
//		}
//
//		String name = callSubtree.getName();
//		if (!internalFunctions.contains(name)) {
//			return;
//		}
//
//		// Found at least one property, force the text attributes to GDesc syntax value character
//		holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
//				.range(element.getTextRange()).textAttributes(GDescSyntaxHighlighter.INTERNAL_FUNCTION).create();
	}

}
