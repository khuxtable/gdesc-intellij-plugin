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

import org.kathrynhuxtable.gdesc.gdescplugin.psi.*;
import org.kathrynhuxtable.gdesc.gdescplugin.psi.GlobalDefSubtree.DefinitionType;
import org.kathrynhuxtable.gdesc.parser.GameLexer;
import org.kathrynhuxtable.gdesc.parser.GameParser;

public class GDescParserDefinition implements ParserDefinition {

	public static final IFileElementType FILE =
			new IFileElementType(GDescLanguage.INSTANCE);

	public static TokenIElementType ID;
	public static TokenIElementType LBRACE;
	public static TokenIElementType RBRACE;
	public static TokenIElementType LPAREN;
	public static TokenIElementType RPAREN;
	public static TokenIElementType LBRACK;
	public static TokenIElementType RBRACK;

	static {
		PSIElementTypeFactory.defineLanguageIElementTypes(GDescLanguage.INSTANCE,
				GameParser.tokenNames,
				GameParser.ruleNames);
		List<TokenIElementType> tokenIElementTypes =
				PSIElementTypeFactory.getTokenIElementTypes(GDescLanguage.INSTANCE);
		ID = tokenIElementTypes.get(GameLexer.IDENTIFIER);

		LBRACE = tokenIElementTypes.get(GameLexer.LBRACE);
		RBRACE = tokenIElementTypes.get(GameLexer.RBRACE);
		LPAREN = tokenIElementTypes.get(GameLexer.LPAREN);
		RPAREN = tokenIElementTypes.get(GameLexer.RPAREN);
		LBRACK = tokenIElementTypes.get(GameLexer.LBRACK);
		RBRACK = tokenIElementTypes.get(GameLexer.RBRACK);
	}

	public static final TokenSet IDENTIFIERS =
			PSIElementTypeFactory.createTokenSet(
					GDescLanguage.INSTANCE,
					GameLexer.IDENTIFIER);

	public static final TokenSet COMMENTS =
			PSIElementTypeFactory.createTokenSet(
					GDescLanguage.INSTANCE,
					GameLexer.COMMENT,
					GameLexer.LINE_COMMENT);

	public static final TokenSet WHITESPACE =
			PSIElementTypeFactory.createTokenSet(
					GDescLanguage.INSTANCE,
					GameLexer.WS);

	public static final TokenSet STRING =
			PSIElementTypeFactory.createTokenSet(
					GDescLanguage.INSTANCE,
					GameLexer.CHAR_LITERAL,
					GameLexer.STRING_LITERAL,
					GameLexer.TEXT_BLOCK);

	public static final TokenSet OPERATOR =
			PSIElementTypeFactory.createTokenSet(
					GDescLanguage.INSTANCE,
					GameLexer.ADD_ASSIGN,
					GameLexer.SUB_ASSIGN,
					GameLexer.MUL_ASSIGN,
					GameLexer.DIV_ASSIGN,
					GameLexer.AND_ASSIGN,
					GameLexer.OR_ASSIGN,
					GameLexer.XOR_ASSIGN,
					GameLexer.MOD_ASSIGN,
					GameLexer.LSHIFT_ASSIGN,
					GameLexer.RSHIFT_ASSIGN,
					GameLexer.URSHIFT_ASSIGN,
					GameLexer.EQUAL,
					GameLexer.LSHIFT,
					GameLexer.URSHIFT,
					GameLexer.RSHIFT,
					GameLexer.GT,
					GameLexer.LT,
					GameLexer.QUESTION,
					GameLexer.COLON,
					GameLexer.EQUALS,
					GameLexer.LE,
					GameLexer.GE,
					GameLexer.NOTEQUALS,
					GameLexer.AND,
					GameLexer.OR,
					GameLexer.ADD,
					GameLexer.SUB,
					GameLexer.MUL,
					GameLexer.DIV,
					GameLexer.BITAND,
					GameLexer.BITOR,
					GameLexer.CARET,
					GameLexer.MOD);

	@NotNull
	@Override
	public Lexer createLexer(Project project) {
		GameLexer lexer = new GameLexer(null);
		return new ANTLRLexerAdaptor(GDescLanguage.INSTANCE, lexer);
	}

	@NotNull
	@Override
	public PsiParser createParser(final Project project) {
		final GameParser parser = new GameParser(null);
		return new ANTLRParserAdaptor(GDescLanguage.INSTANCE, parser) {
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
	public SpaceRequirements spaceExistenceTypeBetweenTokens(ASTNode left, ASTNode right) {
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
			case GameParser.RULE_includePragma -> new PragmaSubtree(node);
			case GameParser.RULE_namePragma -> new PragmaSubtree(node);
			case GameParser.RULE_versionPragma -> new PragmaSubtree(node);
			case GameParser.RULE_datePragma -> new PragmaSubtree(node);
			case GameParser.RULE_authorPragma -> new PragmaSubtree(node);
			case GameParser.RULE_noiseDirective -> new NoiseSubtree(node);
			case GameParser.RULE_stateClause -> new GlobalDefSubtree(node, elType, DefinitionType.StateDef);
			case GameParser.RULE_flagClause -> new GlobalDefSubtree(node, elType, DefinitionType.FlagDef);
			case GameParser.RULE_verbDirective -> new GlobalDefSubtree(node, elType, DefinitionType.VerbDef);
			case GameParser.RULE_variableDirective -> new GlobalDefSubtree(node, elType, DefinitionType.VariableDef);
			case GameParser.RULE_arrayDirective -> new GlobalDefSubtree(node, elType, DefinitionType.ArrayDef);
			case GameParser.RULE_textDirective -> new GlobalDefSubtree(node, elType, DefinitionType.TextDef);
			case GameParser.RULE_fragmentDirective -> new GlobalDefSubtree(node, elType, DefinitionType.FragmentDef);
			case GameParser.RULE_placeDirective -> new GlobalDefSubtree(node, elType, DefinitionType.PlaceDef);
			case GameParser.RULE_objectDirective -> new GlobalDefSubtree(node, elType, DefinitionType.ObjectDef);
			case GameParser.RULE_procDirective -> new ProcSubtree(node, elType);
			case GameParser.RULE_initialDirective -> new MainBlockSubtree(node);
			case GameParser.RULE_repeatDirective -> new MainBlockSubtree(node);
			case GameParser.RULE_block -> new BlockSubtree(node);
			case GameParser.RULE_basicForStatement, GameParser.RULE_basicForStatementNoShortIf ->
					new BasicForSubtree(node);
			case GameParser.RULE_enhancedForStatement, GameParser.RULE_enhancedForStatementNoShortIf ->
					new EnhancedForSubtree(node);
			case GameParser.RULE_variableDeclarator -> new VardefSubtree(node, elType);
			case GameParser.RULE_functionInvocation -> new CallSubtree(node);
			default -> new ANTLRPsiNode(node);
		};
	}
}
