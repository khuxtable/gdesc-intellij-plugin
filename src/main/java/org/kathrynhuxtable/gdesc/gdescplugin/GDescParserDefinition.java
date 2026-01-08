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

	public static final IFileElementType FILE = GDescElementTypeService.FILE;

	public static TokenIElementType ID = GDescElementTypeService.ID;
	public static TokenIElementType LBRACE = GDescElementTypeService.LBRACE;
	public static TokenIElementType RBRACE = GDescElementTypeService.RBRACE;
	public static TokenIElementType LPAREN = GDescElementTypeService.LPAREN;
	public static TokenIElementType RPAREN = GDescElementTypeService.RPAREN;
	public static TokenIElementType LBRACK = GDescElementTypeService.LBRACK;
	public static TokenIElementType RBRACK = GDescElementTypeService.RBRACK;
	public static TokenIElementType QUESTION = GDescElementTypeService.QUESTION;
	public static TokenIElementType COLON = GDescElementTypeService.COLON;
	public static TokenIElementType COMMENT = GDescElementTypeService.COMMENT;
	public static TokenIElementType LINE_COMMENT = GDescElementTypeService.LINE_COMMENT;

	public static RuleIElementType INCLUDE_PRAGMA = GDescElementTypeService.INCLUDE_PRAGMA;
	public static RuleIElementType INFO_PRAGMA = GDescElementTypeService.INFO_PRAGMA;
	public static RuleIElementType FLAG_DIRECTIVE = GDescElementTypeService.FLAG_DIRECTIVE;
	public static RuleIElementType STATE_DIRECTIVE = GDescElementTypeService.STATE_DIRECTIVE;
	public static RuleIElementType NOISE_DIRECTIVE = GDescElementTypeService.NOISE_DIRECTIVE;
	public static RuleIElementType VERB_DIRECTIVE = GDescElementTypeService.VERB_DIRECTIVE;
	public static RuleIElementType VARIABLE_DIRECTIVE = GDescElementTypeService.VARIABLE_DIRECTIVE;
	public static RuleIElementType TEXT_DIRECTIVE = GDescElementTypeService.TEXT_DIRECTIVE;
	public static RuleIElementType FRAGMENT_DIRECTIVE = GDescElementTypeService.FRAGMENT_DIRECTIVE;
	public static RuleIElementType PLACE_DIRECTIVE = GDescElementTypeService.PLACE_DIRECTIVE;
	public static RuleIElementType OBJECT_DIRECTIVE = GDescElementTypeService.OBJECT_DIRECTIVE;
	public static RuleIElementType ACTION_DIRECTIVE = GDescElementTypeService.ACTION_DIRECTIVE;
	public static RuleIElementType PROC_DIRECTIVE = GDescElementTypeService.PROC_DIRECTIVE;
	public static RuleIElementType INITIAL_DIRECTIVE = GDescElementTypeService.INITIAL_DIRECTIVE;
	public static RuleIElementType REPEAT_DIRECTIVE = GDescElementTypeService.REPEAT_DIRECTIVE;

	public static RuleIElementType FUNC_REF = GDescElementTypeService.FUNC_REF;
	public static RuleIElementType TERNARY_EXPRESSION = GDescElementTypeService.TERNARY_EXPRESSION;

	public static RuleIElementType GAME = GDescElementTypeService.GAME;
	public static RuleIElementType DIRECTIVE = GDescElementTypeService.DIRECTIVE;

	public static RuleIElementType BLOCK = GDescElementTypeService.BLOCK;
	public static RuleIElementType STATEMENT = GDescElementTypeService.STATEMENT;
	public static RuleIElementType EMPTY_STATEMENT = GDescElementTypeService.EMPTY_STATEMENT;
	public static RuleIElementType LOCAL_VARIABLE_DECLARATION_STATEMENT = GDescElementTypeService.LOCAL_VARIABLE_DECLARATION_STATEMENT;
	public static RuleIElementType EXPRESSION_STATEMENT = GDescElementTypeService.EXPRESSION_STATEMENT;
	public static RuleIElementType BREAK_STATEMENT = GDescElementTypeService.BREAK_STATEMENT;
	public static RuleIElementType CONTINUE_STATEMENT = GDescElementTypeService.CONTINUE_STATEMENT;
	public static RuleIElementType RETURN_STATEMENT = GDescElementTypeService.RETURN_STATEMENT;
	public static RuleIElementType IF_STATEMENT = GDescElementTypeService.IF_STATEMENT;
	public static RuleIElementType WHILE_STATEMENT = GDescElementTypeService.WHILE_STATEMENT;
	public static RuleIElementType REPEAT_STATEMENT = GDescElementTypeService.REPEAT_STATEMENT;
	public static RuleIElementType BASIC_FOR_STATEMENT = GDescElementTypeService.BASIC_FOR_STATEMENT;
	public static RuleIElementType ENHANCED_FOR_STATEMENT = GDescElementTypeService.ENHANCED_FOR_STATEMENT;
	public static RuleIElementType CONDITIONAL_OR_EXPRESSION = GDescElementTypeService.CONDITIONAL_OR_EXPRESSION;
	public static RuleIElementType CONDITIONAL_AND_EXPRESSION = GDescElementTypeService.CONDITIONAL_AND_EXPRESSION;
	public static RuleIElementType INCLUSIVE_OR_EXPRESSION = GDescElementTypeService.INCLUSIVE_OR_EXPRESSION;
	public static RuleIElementType EXCLUSIVE_OR_EXPRESSION = GDescElementTypeService.EXCLUSIVE_OR_EXPRESSION;
	public static RuleIElementType AND_EXPRESSION = GDescElementTypeService.AND_EXPRESSION;
	public static RuleIElementType RELATIONAL_EXPRESSION = GDescElementTypeService.RELATIONAL_EXPRESSION;
	public static RuleIElementType SHIFT_EXPRESSION = GDescElementTypeService.SHIFT_EXPRESSION;
	public static RuleIElementType ADDITIVE_EXPRESSION = GDescElementTypeService.ADDITIVE_EXPRESSION;
	public static RuleIElementType MULTIPLICATIVE_EXPRESSION = GDescElementTypeService.MULTIPLICATIVE_EXPRESSION;
	public static RuleIElementType OPTIONAL_EXPRESSION_LIST = GDescElementTypeService.OPTIONAL_EXPRESSION_LIST;

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
		return GDescElementTypeService.WHITESPACE;
	}

	@NotNull
	public TokenSet getCommentTokens() {
		return GDescElementTypeService.COMMENTS;
	}

	@NotNull
	public TokenSet getStringLiteralElements() {
		return GDescElementTypeService.STRING;
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
			case GameParser.RULE_emptyStatement -> new EmptyStatementSubtree(node);
			case GameParser.RULE_localVariableDeclarationStatement ->
					new LocalVariableDeclarationStatementSubtree(node);
			case GameParser.RULE_expressionStatement -> new ExpressionStatementSubtree(node);
			case GameParser.RULE_breakStatement -> new BreakStatementSubtree(node);
			case GameParser.RULE_continueStatement -> new ContinueStatementSubtree(node);
			case GameParser.RULE_returnStatement -> new ReturnStatementSubtree(node);
			case GameParser.RULE_ifStatement -> new IfStatementSubtree(node);
			case GameParser.RULE_whileStatement -> new WhileStatementSubtree(node);
			case GameParser.RULE_repeatStatement -> new RepeatStatementSubtree(node);
			case GameParser.RULE_basicForStatement -> new BasicForSubtree(node);
			case GameParser.RULE_enhancedForStatement -> new EnhancedForSubtree(node);
			// Statement Clauses
			case GameParser.RULE_variableDeclarator -> new VariableDeclaratorSubtree(node, elType);
			case GameParser.RULE_optionalLabel -> new LabelClauseSubtree(node, elType);
			// Expressions
			case GameParser.RULE_assignment -> new AssignmentExpressionSubtree(node, elType);
			case GameParser.RULE_arrayAccess -> new ArrayReferenceSubtree(node, elType);
			case GameParser.RULE_queryExpression -> new QueryExpressionSubtree(node, elType);
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
			case GameParser.RULE_postIncrementOrDecrementExpression -> new UnaryPostExpressionSubtree(node, elType);
			case GameParser.RULE_parenthesizedExpression -> new ParenthesizedExpressionSubtree(node, elType);
			case GameParser.RULE_functionInvocation -> new FunctionInvocationSubtree(node);
			case GameParser.RULE_optionalExpressionList -> new OptionalExpressionListSubtree(node, elType);
			case GameParser.RULE_refExpression -> new RefExpressionSubtree(node, elType);
			case GameParser.RULE_instanceofExpression -> new InstanceOfExpressionSubtree(node, elType);
			default -> new ANTLRPsiNode(node);
		};
	}
}
