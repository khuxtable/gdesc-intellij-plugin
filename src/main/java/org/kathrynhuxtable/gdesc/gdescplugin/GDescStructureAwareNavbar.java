package org.kathrynhuxtable.gdesc.gdescplugin;

import javax.swing.*;

import com.intellij.ide.navigationToolbar.StructureAwareNavBarModelExtension;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.psi.tree.TokenSet;
import org.antlr.intellij.adaptor.lexer.RuleIElementType;
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.kathrynhuxtable.gdesc.gdescplugin.psi.GDescPSIFileRoot;
import org.kathrynhuxtable.gdesc.parser.GameParser;

final class GDescStructureAwareNavbar extends StructureAwareNavBarModelExtension {

	@NotNull
	@Override
	protected Language getLanguage() {
		return GDescLanguage.INSTANCE;
	}

	@Override
	public @Nullable String getPresentableText(Object object) {
		if (object instanceof GDescPSIFileRoot) {
			return ((GDescPSIFileRoot) object).getVirtualFile().getNameWithoutExtension();
		} else if (object instanceof ANTLRPsiNode antlrPsiNode) {
			ASTNode node = antlrPsiNode.getNode();
			RuleIElementType elementType = (RuleIElementType) node.getElementType();
			return switch (elementType.getRuleIndex()) {
				case GameParser.RULE_includePragma -> {
					ASTNode[] children = node.getChildren(TokenSet.ANY);
					yield children[2].getText() + " [include]";
				}
				case GameParser.RULE_namePragma -> {
					ASTNode[] children = node.getChildren(TokenSet.ANY);
					yield children[2].getText() + " [name]";
				}
				case GameParser.RULE_versionPragma -> {
					ASTNode[] children = node.getChildren(TokenSet.ANY);
					yield children[2].getText() + " [version]";
				}
				case GameParser.RULE_datePragma -> {
					ASTNode[] children = node.getChildren(TokenSet.ANY);
					yield children[2].getText() + " [date]";
				}
				case GameParser.RULE_authorPragma -> {
					ASTNode[] children = node.getChildren(TokenSet.ANY);
					yield children[2].getText() + " [author]";
				}
				case GameParser.RULE_flagDirective -> {
					ASTNode[] children = node.getChildren(TokenSet.ANY);
					yield children[2].getText() + " [flag]";
				}
				case GameParser.RULE_stateDirective -> {
					ASTNode[] children = node.getChildren(TokenSet.ANY);
					yield children[2].getText() + " [state]";
				}
				case GameParser.RULE_noiseDirective -> "noise";
				case GameParser.RULE_verbDirective -> {
					ASTNode[] children = node.getChildren(TokenSet.ANY);
					yield children[2].getText() + " [verb]";
				}
				case GameParser.RULE_variableDirective -> {
					ASTNode[] children = node.getChildren(TokenSet.ANY);
					yield children[2].getText() + " [variable]";
				}
				case GameParser.RULE_arrayDirective -> {
					ASTNode[] children = node.getChildren(TokenSet.ANY);
					yield children[2].getText() + " [array]";
				}
				case GameParser.RULE_textDirective -> {
					ASTNode[] children = node.getChildren(TokenSet.ANY);
					yield children[2].getText() + " [text]";
				}
				case GameParser.RULE_fragmentDirective -> {
					ASTNode[] children = node.getChildren(TokenSet.ANY);
					yield children[2].getText() + " [fragment]";
				}
				case GameParser.RULE_placeDirective -> {
					ASTNode[] children = node.getChildren(TokenSet.ANY);
					yield children[2].getText() + " [place]";
				}
				case GameParser.RULE_objectDirective -> {
					ASTNode[] children = node.getChildren(TokenSet.ANY);
					yield children[2].getText() + " [object]";
				}
				case GameParser.RULE_actionDirective -> {
					ASTNode[] children = node.getChildren(TokenSet.ANY);
					yield children[2].getText() + " [action]";
				}
				case GameParser.RULE_procDirective -> {
					ASTNode[] children = node.getChildren(TokenSet.ANY);
					yield children[2].getText() + " [proc]";
				}
				case GameParser.RULE_initialDirective -> "initial [initial]";
				case GameParser.RULE_repeatDirective -> "repeat [repeat]";
				default -> "directive:" + elementType.getRuleIndex() + " [directive]";
			};
		} else {
			return null;
		}
	}

	@Override
	@Nullable
	public Icon getIcon(Object object) {
		if (object instanceof GDescPSIFileRoot) {
			return GDescIcons.FILE;
		} else if (object instanceof ANTLRPsiNode) {
			return GDescIcons.FUNC_ICON;
		} else {
			return null;
		}
	}
}
