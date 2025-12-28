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

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor;
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory;
import org.antlr.intellij.adaptor.lexer.TokenIElementType;
import org.jetbrains.annotations.NotNull;

import org.kathrynhuxtable.gdesc.parser.GameLexer;
import org.kathrynhuxtable.gdesc.parser.GameParser;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;


public class GDescSyntaxHighlighter extends SyntaxHighlighterBase {
	private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];
	public static final TextAttributesKey ID =
			createTextAttributesKey("GDESC_ID", DefaultLanguageHighlighterColors.IDENTIFIER);
	public static final TextAttributesKey INTERNAL_FUNCTION =
			createTextAttributesKey("GDESC_INTERNAL_FUNCTION", DefaultLanguageHighlighterColors.INSTANCE_METHOD);
	public static final TextAttributesKey CONSTANT =
			createTextAttributesKey("GDESC_CONSTANT", DefaultLanguageHighlighterColors.NUMBER);
	public static final TextAttributesKey KEYWORD =
			createTextAttributesKey("GDESC_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
	public static final TextAttributesKey KEYWORD2 =
			createTextAttributesKey("GDESC_KEYWORD2", DefaultLanguageHighlighterColors.INSTANCE_FIELD);
	public static final TextAttributesKey STRING =
			createTextAttributesKey("GDESC_STRING", DefaultLanguageHighlighterColors.STRING);
	public static final TextAttributesKey LINE_COMMENT =
			createTextAttributesKey("GDESC_LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
	public static final TextAttributesKey BLOCK_COMMENT =
			createTextAttributesKey("GDESC_BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT);
	public static final TextAttributesKey BAD_CHARACTER =
			createTextAttributesKey("GDESC_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);

	static {
		PSIElementTypeFactory.defineLanguageIElementTypes(GDescLanguage.INSTANCE,
				GameParser.tokenNames,
				GameParser.ruleNames);
	}

	@NotNull
	@Override
	public Lexer getHighlightingLexer() {
		GameLexer lexer = new GameLexer(null);
		return new ANTLRLexerAdaptor(GDescLanguage.INSTANCE, lexer);
	}

	@Override
	public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
		if (!(tokenType instanceof TokenIElementType myType)) return EMPTY_KEYS;
		int ttype = myType.getANTLRTokenType();
		TextAttributesKey attrKey;
		switch (ttype) {
		case GameLexer.IDENTIFIER:
			attrKey = ID;
			break;
		case GameLexer.ANYOF:
		case GameLexer.APPEND:
		case GameLexer.APPORT:
		case GameLexer.ATPLACE:
		case GameLexer.CHANCE:
		case GameLexer.CLEARFLAG:
		case GameLexer.DESCRIBE_:
		case GameLexer.IDROP:
		case GameLexer.FLUSHINPUT:
		case GameLexer.IGET:
		case GameLexer.GOTO:
		case GameLexer.INPUT:
		case GameLexer.INRANGE:
		case GameLexer.ISAT:
		case GameLexer.ISFLAG:
		case GameLexer.ISHAVE:
		case GameLexer.ISHERE:
		case GameLexer.ISNEAR:
		case GameLexer.KEY:
		case GameLexer.MOVE_:
		case GameLexer.NEEDCMD:
		case GameLexer.GETQUERY:
		case GameLexer.QUIP:
		case GameLexer.RESPOND:
		case GameLexer.SAY_:
		case GameLexer.SETFLAG:
		case GameLexer.SMOVE:
		case GameLexer.STOP:
		case GameLexer.TIE:
		case GameLexer.USERTYPED:
		case GameLexer.VARIS:
		case GameLexer.VOCAB:
			attrKey = INTERNAL_FUNCTION;
			break;
		case GameLexer.INCLUDE:
		case GameLexer.NAME:
		case GameLexer.VERSION:
		case GameLexer.DATE:
		case GameLexer.AUTHOR:
		case GameLexer.FLAGS:
		case GameLexer.STATE:
		case GameLexer.TEXT:
		case GameLexer.FRAGMENT:
		case GameLexer.INCREMENT:
		case GameLexer.CYCLE:
		case GameLexer.RANDOM:
		case GameLexer.ASSIGNED:
		case GameLexer.PLACE:
		case GameLexer.OBJECT:
		case GameLexer.NOISE:
		case GameLexer.VERB:
		case GameLexer.ACTION:
		case GameLexer.VARIABLE:
		case GameLexer.ARRAY:
		case GameLexer.PROC:
		case GameLexer.INITIAL:
		case GameLexer.REPEAT:
			attrKey = KEYWORD;
			break;
		case GameLexer.BREAK:
		case GameLexer.CASE:
		case GameLexer.CONTINUE:
		case GameLexer.DEFAULT:
		case GameLexer.ELSE:
		case GameLexer.FOR:
		case GameLexer.IF:
		case GameLexer.INSTANCEOF:
		case GameLexer.REF:
		case GameLexer.RETURN:
		case GameLexer.SWITCH:
		case GameLexer.TO:
		case GameLexer.UNTIL:
		case GameLexer.VAR:
		case GameLexer.WHILE:
			attrKey = KEYWORD2;
			break;
		case GameLexer.CHAR_LITERAL:
		case GameLexer.STRING_LITERAL:
		case GameLexer.TEXT_BLOCK:
			attrKey = STRING;
			break;
		case GameLexer.BOOL_LITERAL:
		case GameLexer.NUM_LITERAL:
		case GameLexer.NULL_LITERAL:
			attrKey = CONSTANT;
			break;
		case GameLexer.COMMENT:
			attrKey = LINE_COMMENT;
			break;
		case GameLexer.LINE_COMMENT:
			attrKey = BLOCK_COMMENT;
			break;
		case GameLexer.ERRCHAR:
			attrKey = BAD_CHARACTER;
			break;
		default:
			return EMPTY_KEYS;
		}
		return new TextAttributesKey[]{attrKey};
	}
}
