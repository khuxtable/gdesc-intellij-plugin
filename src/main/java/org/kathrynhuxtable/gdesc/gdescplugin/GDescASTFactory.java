package org.kathrynhuxtable.gdesc.gdescplugin;

import com.intellij.lang.DefaultASTFactoryImpl;
import com.intellij.lang.ParserDefinition;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.impl.source.tree.PsiCommentImpl;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.lexer.TokenIElementType;

import org.kathrynhuxtable.gdesc.parser.GameLexer;
import org.kathrynhuxtable.gdesc.gdescplugin.psi.IdentifierPSINode;

import org.jetbrains.annotations.NotNull;

/**
 * How to create parse tree nodes (Jetbrains calls them AST nodes). Later
 * non-leaf nodes are converted to PSI nodes by the {@link ParserDefinition}.
 * Leaf nodes are already considered PSI nodes.  This is mostly just
 * {@link DefaultASTFactoryImpl} but with comments on the methods that you might want
 * to override.
 */
public class GDescASTFactory extends DefaultASTFactoryImpl {

	/**
	 * Create an internal parse tree node. FileElement for root or a parse tree CompositeElement (not
	 * PSI) for the token.
	 * <br/>
	 * The FileElement is a parse tree node, which is converted to a PsiFile
	 * by {@link ParserDefinition#createFile}.
	 */
	@NotNull
	@Override
	public CompositeElement createComposite(@NotNull IElementType type) {
		return super.createComposite(type);
	}

	/**
	 * Create a parse tree (AST) leaf node from a token. Doubles as a PSI leaf node.
	 * Does not see whitespace tokens.  Default impl makes {@link LeafPsiElement}
	 * or {@link PsiCommentImpl} depending on {@link ParserDefinition#getCommentTokens()}.
	 */
	@NotNull
	@Override
	public LeafElement createLeaf(@NotNull IElementType type, @NotNull CharSequence text) {
		if (type instanceof TokenIElementType &&
				((TokenIElementType) type).getANTLRTokenType() == GameLexer.IDENTIFIER) {
			// found an IDENTIFIER node; here we do not distinguish between definitions and references
			// because we have no context information here. All we know is that
			// we have an identifier node that will be connected somewhere in a tree.
			//
			// You can only rename, find usages, etc... on leaves implementing PsiNamedElement
			//
			// TODO: try not to create one for IDs under declaration subtree roots like variable, proc args
			return new IdentifierPSINode(type, text);
		} else {
			return super.createLeaf(type, text);
		}
	}
}
