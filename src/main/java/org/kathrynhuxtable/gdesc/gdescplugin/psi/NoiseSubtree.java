package org.kathrynhuxtable.gdesc.gdescplugin.psi;

import com.intellij.lang.ASTNode;
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

/** A subtree associated with a function definition.
 *  Its scope is the set of arguments.
 */
public class NoiseSubtree extends ANTLRPsiNode implements GDescDirectiveElement {
	public NoiseSubtree(@NotNull ASTNode node) {
		super(node);
	}
}
