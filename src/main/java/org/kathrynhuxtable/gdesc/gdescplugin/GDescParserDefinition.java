package org.kathrynhuxtable.gdesc.gdescplugin;

import java.util.List;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor;
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory;
import org.antlr.intellij.adaptor.lexer.RuleIElementType;
import org.antlr.intellij.adaptor.lexer.TokenIElementType;
import org.antlr.intellij.adaptor.parser.ANTLRParserAdaptor;
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jetbrains.annotations.NotNull;

import org.kathrynhuxtable.gdesc.parser.GameLexer;
import org.kathrynhuxtable.gdesc.parser.GameParser;
import org.kathrynhuxtable.gdesc.gdescplugin.psi.BlockSubtree;
import org.kathrynhuxtable.gdesc.gdescplugin.psi.CallSubtree;
import org.kathrynhuxtable.gdesc.gdescplugin.psi.GDescPSIFileRoot;

public class GDescParserDefinition implements ParserDefinition {
	public static final IFileElementType FILE =
			new IFileElementType(GDesc.INSTANCE);

	public static TokenIElementType ID;

	static {
		PSIElementTypeFactory.defineLanguageIElementTypes(GDesc.INSTANCE,
				GameParser.tokenNames,
				GameParser.ruleNames);
		List<TokenIElementType> tokenIElementTypes =
				PSIElementTypeFactory.getTokenIElementTypes(GDesc.INSTANCE);
		ID = tokenIElementTypes.get(GameLexer.IDENTIFIER);
	}

	public static final TokenSet COMMENTS =
			PSIElementTypeFactory.createTokenSet(
					GDesc.INSTANCE,
					GameLexer.COMMENT,
					GameLexer.LINE_COMMENT);

	public static final TokenSet WHITESPACE =
			PSIElementTypeFactory.createTokenSet(
					GDesc.INSTANCE,
					GameLexer.WS);

	public static final TokenSet STRING =
			PSIElementTypeFactory.createTokenSet(
					GDesc.INSTANCE,
					GameLexer.CHAR_LITERAL,
					GameLexer.STRING_LITERAL,
					GameLexer.TEXT_BLOCK);

	@NotNull
	@Override
	public Lexer createLexer(Project project) {
		GameLexer lexer = new GameLexer(null);
		return new ANTLRLexerAdaptor(GDesc.INSTANCE, lexer);
	}

	@NotNull
	@Override
	public PsiParser createParser(final Project project) {
		final GameParser parser = new GameParser(null);
		return new ANTLRParserAdaptor(GDesc.INSTANCE, parser) {
			@Override
			protected ParseTree parse(Parser parser, IElementType root) {
				// start rule depends on root passed in; sometimes we want to create an ID node etc...
				if (root instanceof IFileElementType) {
					return ((GameParser) parser).game();
				}
				// let's hope it's an ID as needed by "rename function"
				return ((GameParser) parser).primary();
			}
		};
	}

	/**
	 * "Tokens of those types are automatically skipped by PsiBuilder."
	 */
	@NotNull
	public TokenSet getWhitespaceTokens() {
		return WHITESPACE;
	}

	@NotNull
	public TokenSet getCommentTokens() {
		return COMMENTS;
	}

	@NotNull
	public TokenSet getStringLiteralElements() {
		return STRING;
	}

	@NotNull
	public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
		return SpaceRequirements.MAY;
	}

	/**
	 * What is the IFileElementType of the root parse tree node? It
	 * is called from {@link #createFile(FileViewProvider)} at least.
	 */
	@NotNull
	@Override
	public IFileElementType getFileNodeType() {
		return FILE;
	}

	/**
	 * Create the root of your PSI tree (a PsiFile).
	 * <p>
	 * From IntelliJ IDEA Architectural Overview:
	 * "A PSI (Program Structure Interface) file is the root of a structure
	 * representing the contents of a file as a hierarchy of elements
	 * in a particular programming language."
	 * <p>
	 * PsiFile is to be distinguished from a FileASTNode, which is a parse
	 * tree node that eventually becomes a PsiFile. From PsiFile, we can get
	 * it back via: {@link PsiFile#getNode}.
	 */
	@NotNull
	@Override
	public PsiFile createFile(@NotNull FileViewProvider viewProvider) {
		return new GDescPSIFileRoot(viewProvider);
	}

	/**
	 * Convert from *NON-LEAF* parse node (AST they call it)
	 * to PSI node. Leaves are created in the AST factory.
	 * Rename re-factoring can cause this to be
	 * called on a TokenIElementType since we want to rename ID nodes.
	 * In that case, this method is called to create the root node
	 * but with ID type. Kind of strange, but we can simply create a
	 * ASTWrapperPsiElement to make everything work correctly.
	 * <p>
	 * RuleIElementType.  Ah! It's that ID is the root
	 * IElementType requested to parse, which means that the root
	 * node returned from parsetree->PSI conversion.  But, it
	 * must be a CompositeElement! The adaptor calls
	 * rootMarker.done(root) to finish off the PSI conversion.
	 * See {@link ANTLRParserAdaptor#parse(IElementType root,
	 * PsiBuilder)}
	 * <p>
	 * If you don't care to distinguish PSI nodes by type, it is
	 * sufficient to create a {@link ANTLRPsiNode} around
	 * the parse tree node
	 */
	@NotNull
	public PsiElement createElement(ASTNode node) {
		IElementType elType = node.getElementType();
		if (elType instanceof TokenIElementType) {
			return new ANTLRPsiNode(node);
		}
		if (!(elType instanceof RuleIElementType ruleElType)) {
			return new ANTLRPsiNode(node);
		}
		return switch (ruleElType.getRuleIndex()) {
//			case GameParser.RULE_directive -> new DirectiveSubtree(node, elType);
//			case GameParser.RULE_stateClause -> new StateClauseSubtree(node, elType);
//			case GameParser.RULE_flagClause -> new FlagClauseSubtree(node, elType);
//			case GameParser.RULE_variableDeclarator ->  new VariableDeclaratorSubtree(node, elType);
			case GameParser.RULE_block -> new BlockSubtree(node);
			case GameParser.RULE_functionInvocation -> new CallSubtree(node);
			default -> new ANTLRPsiNode(node);
		};
	}
}
