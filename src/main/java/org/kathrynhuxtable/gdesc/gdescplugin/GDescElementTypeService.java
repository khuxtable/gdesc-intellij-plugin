/*
 * Copyright © ${YEAR} Kathryn A Huxtable
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

import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory;
import org.antlr.intellij.adaptor.lexer.RuleIElementType;
import org.antlr.intellij.adaptor.lexer.TokenIElementType;

import org.kathrynhuxtable.gdesc.parser.GameLexer;
import org.kathrynhuxtable.gdesc.parser.GameParser;

public class GDescElementTypeService {

	public static final IFileElementType FILE = new IFileElementType(GDescLanguage.INSTANCE);

	public static TokenIElementType ID;
	public static TokenIElementType LBRACE;
	public static TokenIElementType RBRACE;
	public static TokenIElementType LPAREN;
	public static TokenIElementType RPAREN;
	public static TokenIElementType LBRACK;
	public static TokenIElementType RBRACK;
	public static TokenIElementType QUESTION;
	public static TokenIElementType EQUAL;
	public static TokenIElementType COLON;
	public static TokenIElementType SEMICOLON;
	public static TokenIElementType SUB;
	public static TokenIElementType COMMA;
	public static TokenIElementType COMMENT;
	public static TokenIElementType LINE_COMMENT;

	public static RuleIElementType GAME;
	public static RuleIElementType DIRECTIVE;

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

	public static RuleIElementType VARIABLE_DECLARATOR;

	public static RuleIElementType FUNC_REF;
	public static RuleIElementType TERNARY_EXPRESSION;

	public static RuleIElementType BLOCK;
	public static RuleIElementType STATEMENT;
	public static RuleIElementType EMPTY_STATEMENT;
	public static RuleIElementType LOCAL_VARIABLE_DECLARATION_STATEMENT;
	public static RuleIElementType EXPRESSION_STATEMENT;
	public static RuleIElementType BREAK_STATEMENT;
	public static RuleIElementType CONTINUE_STATEMENT;
	public static RuleIElementType RETURN_STATEMENT;
	public static RuleIElementType IF_STATEMENT;
	public static RuleIElementType WHILE_STATEMENT;
	public static RuleIElementType REPEAT_STATEMENT;
	public static RuleIElementType BASIC_FOR_STATEMENT;
	public static RuleIElementType ENHANCED_FOR_STATEMENT;

	public static RuleIElementType ASSIGNMENT;
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
		List<TokenIElementType> tokenIElementTypes = PSIElementTypeFactory.getTokenIElementTypes(GDescLanguage.INSTANCE);
		List<RuleIElementType> ruleIElementTypes = PSIElementTypeFactory.getRuleIElementTypes(GDescLanguage.INSTANCE);

		ID = tokenIElementTypes.get(GameLexer.IDENTIFIER);

		LBRACE = tokenIElementTypes.get(GameLexer.LBRACE);
		RBRACE = tokenIElementTypes.get(GameLexer.RBRACE);
		LPAREN = tokenIElementTypes.get(GameLexer.LPAREN);
		RPAREN = tokenIElementTypes.get(GameLexer.RPAREN);
		LBRACK = tokenIElementTypes.get(GameLexer.LBRACK);
		RBRACK = tokenIElementTypes.get(GameLexer.RBRACK);
		QUESTION = tokenIElementTypes.get(GameLexer.QUESTION);
		EQUAL = tokenIElementTypes.get(GameLexer.EQUAL);
		COLON = tokenIElementTypes.get(GameLexer.COLON);
		SEMICOLON = tokenIElementTypes.get(GameLexer.SEMI);
		SUB = tokenIElementTypes.get(GameLexer.SUB);
		COMMA = tokenIElementTypes.get(GameLexer.COMMA);
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

		VARIABLE_DECLARATOR = ruleIElementTypes.get(GameParser.RULE_variableDeclarator);

		FUNC_REF = ruleIElementTypes.get(GameParser.RULE_functionInvocation);
		TERNARY_EXPRESSION = ruleIElementTypes.get(GameParser.RULE_queryExpression);

		BLOCK = ruleIElementTypes.get(GameParser.RULE_block);
		STATEMENT = ruleIElementTypes.get(GameParser.RULE_statement);
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

		ASSIGNMENT = ruleIElementTypes.get(GameParser.RULE_assignment);
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
}
