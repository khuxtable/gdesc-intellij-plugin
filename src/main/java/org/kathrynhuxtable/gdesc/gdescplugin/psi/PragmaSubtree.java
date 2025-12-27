package org.kathrynhuxtable.gdesc.gdescplugin.psi;

import com.intellij.lang.ASTNode;
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

/** A subtree associated with a function definition.
 *  Its scope is the set of arguments.
 */
public class PragmaSubtree extends ANTLRPsiNode implements GDescDirectiveElement {
	public PragmaSubtree(@NotNull ASTNode node) {
		super(node);
	}
}
