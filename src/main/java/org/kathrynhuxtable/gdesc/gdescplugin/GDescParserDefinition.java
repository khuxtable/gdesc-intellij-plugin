/*
 * Copyright © 2025, 2026 Kathryn A Huxtable
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
	public static TokenIElementType QUESTION;
	public static TokenIElementType COLON;
	public static TokenIElementType COMMENT;
	public static TokenIElementType LINE_COMMENT;

	public static RuleIElementType INCLUDE_PRAGMA;
	public static RuleIElementType INFO_PRAGMA;
	public static RuleIElementType FLAG_DIRECTIVE;
	public static RuleIElementType STATE_DIRECTIVE;
	public static RuleIElementType NOISE_DIRECTIVE;
	public static RuleIElementType VERB_DIRECTIVE;
	public static RuleIElementType VARIABLE_DIRECTIVE;
	public static RuleIElementType TEXT_DIRECTIVE;
	public static RuleIElementType FRAGMENT_DIRECTIVE;
	public static RuleIElementType PLACE_DIRECTIVE;
	public static RuleIElementType OBJECT_DIRECTIVE;
	public static RuleIElementType ACTION_DIRECTIVE;
	public static RuleIElementType PROC_DIRECTIVE;
	public static RuleIElementType INITIAL_DIRECTIVE;
	public static RuleIElementType REPEAT_DIRECTIVE;

	public static RuleIElementType FUNC_REF;
	public static RuleIElementType TERNARY_EXPRESSION;

	public static RuleIElementType GAME;
	public static RuleIElementType DIRECTIVE;

	public static RuleIElementType BLOCK;
	public static RuleIElementType EMPTY_STATEMENT;
	public static RuleIElementType LOCAL_VARIABLE_DECLARATION_STATEMENT;
	public static RuleIElementType EXPRESSION_STATEMENT;
	public static RuleIElementType BREAK_STATEMENT;
	public static RuleIElementType CONTINUE_STATEMENT;
	public static RuleIElementType RETURN_STATEMENT;
	public static RuleIElementType IF_STATEMENT;
	public static RuleIElementType IF_THEN_ELSE_STATEMENT;
	public static RuleIElementType WHILE_STATEMENT;
	public static RuleIElementType REPEAT_STATEMENT;
	public static RuleIElementType BASIC_FOR_STATEMENT;
	public static RuleIElementType ENHANCED_FOR_STATEMENT;
	public static RuleIElementType CONDITIONAL_OR_EXPRESSION;
	public static RuleIElementType CONDITIONAL_AND_EXPRESSION;
	public static RuleIElementType INCLUSIVE_OR_EXPRESSION;
	public static RuleIElementType EXCLUSIVE_OR_EXPRESSION;
	public static RuleIElementType AND_EXPRESSION;
	public static RuleIElementType RELATIONAL_EXPRESSION;
	public static RuleIElementType SHIFT_EXPRESSION;
	public static RuleIElementType ADDITIVE_EXPRESSION;
	public static RuleIElementType MULTIPLICATIVE_EXPRESSION;
	public static RuleIElementType OPTIONAL_EXPRESSION_LIST;

	static {
		PSIElementTypeFactory.defineLanguageIElementTypes(GDescLanguage.INSTANCE,
				GameParser.tokenNames,
				GameParser.ruleNames);
		List<TokenIElementType> tokenIElementTypes =
				PSIElementTypeFactory.getTokenIElementTypes(GDescLanguage.INSTANCE);
		List<RuleIElementType> ruleIElementTypes =
				PSIElementTypeFactory.getRuleIElementTypes(GDescLanguage.INSTANCE);

		ID = tokenIElementTypes.get(GameLexer.IDENTIFIER);

		LBRACE = tokenIElementTypes.get(GameLexer.LBRACE);
		RBRACE = tokenIElementTypes.get(GameLexer.RBRACE);
		LPAREN = tokenIElementTypes.get(GameLexer.LPAREN);
		RPAREN = tokenIElementTypes.get(GameLexer.RPAREN);
		LBRACK = tokenIElementTypes.get(GameLexer.LBRACK);
		RBRACK = tokenIElementTypes.get(GameLexer.RBRACK);
		QUESTION = tokenIElementTypes.get(GameLexer.QUESTION);
		COLON = tokenIElementTypes.get(GameLexer.COLON);
		COMMENT = tokenIElementTypes.get(GameLexer.COMMENT);
		LINE_COMMENT = tokenIElementTypes.get(GameLexer.LINE_COMMENT);

		GAME = ruleIElementTypes.get(GameParser.RULE_game);
		DIRECTIVE = ruleIElementTypes.get(GameParser.RULE_directive);

		INCLUDE_PRAGMA = ruleIElementTypes.get(GameParser.RULE_includePragma);
		INFO_PRAGMA = ruleIElementTypes.get(GameParser.RULE_infoPragma);
		FLAG_DIRECTIVE = ruleIElementTypes.get(GameParser.RULE_flagDirective);
		STATE_DIRECTIVE = ruleIElementTypes.get(GameParser.RULE_stateDirective);
		NOISE_DIRECTIVE = ruleIElementTypes.get(GameParser.RULE_noiseDirective);
		VERB_DIRECTIVE = ruleIElementTypes.get(GameParser.RULE_verbDirective);
		VARIABLE_DIRECTIVE = ruleIElementTypes.get(GameParser.RULE_variableDirective);
		TEXT_DIRECTIVE = ruleIElementTypes.get(GameParser.RULE_textDirective);
		FRAGMENT_DIRECTIVE = ruleIElementTypes.get(GameParser.RULE_fragmentDirective);
		PLACE_DIRECTIVE = ruleIElementTypes.get(GameParser.RULE_placeDirective);
		OBJECT_DIRECTIVE = ruleIElementTypes.get(GameParser.RULE_objectDirective);
		ACTION_DIRECTIVE = ruleIElementTypes.get(GameParser.RULE_actionDirective);
		PROC_DIRECTIVE = ruleIElementTypes.get(GameParser.RULE_procDirective);
		INITIAL_DIRECTIVE = ruleIElementTypes.get(GameParser.RULE_initialDirective);
		REPEAT_DIRECTIVE = ruleIElementTypes.get(GameParser.RULE_repeatDirective);

		FUNC_REF = ruleIElementTypes.get(GameParser.RULE_functionInvocation);
		TERNARY_EXPRESSION = ruleIElementTypes.get(GameParser.RULE_queryExpression);

		BLOCK = ruleIElementTypes.get(GameParser.RULE_block);
		EMPTY_STATEMENT = ruleIElementTypes.get(GameParser.RULE_emptyStatement);
		LOCAL_VARIABLE_DECLARATION_STATEMENT = ruleIElementTypes.get(GameParser.RULE_localVariableDeclarationStatement);
		EXPRESSION_STATEMENT = ruleIElementTypes.get(GameParser.RULE_expressionStatement);
		BREAK_STATEMENT = ruleIElementTypes.get(GameParser.RULE_breakStatement);
		CONTINUE_STATEMENT = ruleIElementTypes.get(GameParser.RULE_continueStatement);
		RETURN_STATEMENT = ruleIElementTypes.get(GameParser.RULE_returnStatement);
		IF_STATEMENT = ruleIElementTypes.get(GameParser.RULE_ifStatement);
		WHILE_STATEMENT = ruleIElementTypes.get(GameParser.RULE_whileStatement);
		REPEAT_STATEMENT = ruleIElementTypes.get(GameParser.RULE_repeatStatement);
		BASIC_FOR_STATEMENT = ruleIElementTypes.get(GameParser.RULE_basicForStatement);
		ENHANCED_FOR_STATEMENT = ruleIElementTypes.get(GameParser.RULE_enhancedForStatement);

		CONDITIONAL_OR_EXPRESSION = ruleIElementTypes.get(GameParser.RULE_conditionalOrExpression);
		CONDITIONAL_AND_EXPRESSION = ruleIElementTypes.get(GameParser.RULE_conditionalAndExpression);
		INCLUSIVE_OR_EXPRESSION = ruleIElementTypes.get(GameParser.RULE_inclusiveOrExpression);
		EXCLUSIVE_OR_EXPRESSION = ruleIElementTypes.get(GameParser.RULE_exclusiveOrExpression);
		AND_EXPRESSION = ruleIElementTypes.get(GameParser.RULE_andExpression);
		RELATIONAL_EXPRESSION = ruleIElementTypes.get(GameParser.RULE_relationalExpression);
		SHIFT_EXPRESSION = ruleIElementTypes.get(GameParser.RULE_shiftExpression);
		ADDITIVE_EXPRESSION = ruleIElementTypes.get(GameParser.RULE_additiveExpression);
		MULTIPLICATIVE_EXPRESSION = ruleIElementTypes.get(GameParser.RULE_multiplicativeExpression);

		OPTIONAL_EXPRESSION_LIST = ruleIElementTypes.get(GameParser.RULE_optionalExpressionList);
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

	public static final TokenSet ONE_LINE_STRING =
			PSIElementTypeFactory.createTokenSet(
					GDescLanguage.INSTANCE,
					GameLexer.CHAR_LITERAL,
					GameLexer.STRING_LITERAL);

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
			// Main directives
			case GameParser.RULE_includePragma -> new IncludePragmaSubtree(node);
			case GameParser.RULE_infoPragma -> new InfoPragmaSubtree(node);
			case GameParser.RULE_flagDirective -> new FlagDirectiveSubtree(node);
			case GameParser.RULE_stateDirective -> new StateDirectiveSubtree(node);
			case GameParser.RULE_noiseDirective -> new NoiseDirectiveSubtree(node);
			case GameParser.RULE_verbDirective -> new VerbDirectiveSubtree(node, elType);
			case GameParser.RULE_variableDirective -> new VariableDirectiveSubtree(node, elType);
			case GameParser.RULE_textDirective -> new TextDirectiveSubtree(node, elType, false);
			case GameParser.RULE_fragmentDirective -> new TextDirectiveSubtree(node, elType, true);
			case GameParser.RULE_placeDirective -> new PlaceDirectiveSubtree(node, elType);
			case GameParser.RULE_objectDirective -> new ObjectDirectiveSubtree(node, elType);
			case GameParser.RULE_actionDirective -> new ActionDirectiveSubtree(node);
			case GameParser.RULE_procDirective -> new ProcDirectiveSubtree(node, elType);
			case GameParser.RULE_initialDirective -> new MainBlockSubtree(node, true);
			case GameParser.RULE_repeatDirective -> new MainBlockSubtree(node, false);
			// Directive Clauses
			case GameParser.RULE_flagClause -> new FlagClauseSubtree(node, elType);
			case GameParser.RULE_stateClause -> new StateClauseSubtree(node, elType);
			// Block and Statements
			case GameParser.RULE_block -> new BlockSubtree(node);
			case GameParser.RULE_emptyStatement ->  new EmptyStatementSubtree(node);
			case GameParser.RULE_localVariableDeclarationStatement ->   new LocalVariableDeclarationStatementSubtree(node);
			case GameParser.RULE_expressionStatement ->  new ExpressionStatementSubtree(node);
			case GameParser.RULE_breakStatement ->  new BreakStatementSubtree(node);
			case GameParser.RULE_continueStatement ->  new ContinueStatementSubtree(node);
			case GameParser.RULE_returnStatement ->  new ReturnStatementSubtree(node);
			case GameParser.RULE_ifStatement ->   new IfStatementSubtree(node);
			case GameParser.RULE_whileStatement ->  new WhileStatementSubtree(node);
			case GameParser.RULE_repeatStatement -> new RepeatStatementSubtree(node);
			case GameParser.RULE_basicForStatement -> new BasicForSubtree(node);
			case GameParser.RULE_enhancedForStatement -> new EnhancedForSubtree(node);
			// Statement Clauses
			case GameParser.RULE_variableDeclarator -> new VariableDeclaratorSubtree(node, elType);
			case GameParser.RULE_optionalLabel -> new LabelClauseSubtree(node, elType);
			// Expressions
			case GameParser.RULE_assignment -> new AssignmentExpressionSubtree(node, elType);
			case GameParser.RULE_arrayAccess -> new ArrayReferenceSubtree(node, elType);
			case GameParser.RULE_queryExpression ->  new QueryExpressionSubtree(node, elType);
			case GameParser.RULE_conditionalOrExpression, GameParser.RULE_conditionalAndExpression,
			     GameParser.RULE_inclusiveOrExpression, GameParser.RULE_exclusiveOrExpression,
			     GameParser.RULE_andExpression, GameParser.RULE_relationalExpression,
			     GameParser.RULE_shiftExpression, GameParser.RULE_additiveExpression,
			     GameParser.RULE_multiplicativeExpression -> {
				if (node.getChildren(TokenSet.ANY).length > 1) {
					yield new BinaryExpressionSubtree(node, elType);
				} else {
					yield new ANTLRPsiNode(node);
				}
			}
			case GameParser.RULE_unaryExpression, GameParser.RULE_preIncrementOrDecrementExpression,
			     GameParser.RULE_unaryExpressionNotPlusMinus -> new UnaryPreExpressionSubtree(node, elType);
			case GameParser.RULE_postIncrementOrDecrementExpression ->  new UnaryPostExpressionSubtree(node, elType);
			case GameParser.RULE_parenthesizedExpression ->  new ParenthesizedExpressionSubtree(node, elType);
			case GameParser.RULE_functionInvocation -> new FunctionInvocationSubtree(node);
			case GameParser.RULE_optionalExpressionList -> new OptionalExpressionListSubtree(node, elType);
			case GameParser.RULE_refExpression ->  new RefExpressionSubtree(node, elType);
			case GameParser.RULE_instanceofExpression -> new InstanceOfExpressionSubtree(node, elType);
			default -> new ANTLRPsiNode(node);
		};
	}
}
