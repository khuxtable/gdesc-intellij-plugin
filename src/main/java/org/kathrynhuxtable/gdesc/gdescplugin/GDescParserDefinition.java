package org.kathrynhuxtable.gdesc.gdescplugin;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

import org.kathrynhuxtable.gdesc.gdescplugin.parser.GDescParser;
import org.kathrynhuxtable.gdesc.gdescplugin.psi.GDescFile;
import org.kathrynhuxtable.gdesc.gdescplugin.psi.GDescTokenSets;
import org.kathrynhuxtable.gdesc.gdescplugin.psi.GDescTypes;

final class GDescParserDefinition implements ParserDefinition {

  public static final IFileElementType FILE = new IFileElementType(GDesc.INSTANCE);

  @NotNull
  @Override
  public Lexer createLexer(Project project) {
    return new GDescLexerAdapter();
  }

  @NotNull
  @Override
  public TokenSet getCommentTokens() {
    return GDescTokenSets.COMMENTS;
  }

  @NotNull
  @Override
  public TokenSet getStringLiteralElements() {
    return TokenSet.EMPTY;
  }

  @NotNull
  @Override
  public PsiParser createParser(final Project project) {
    return new GDescParser();
  }

  @NotNull
  @Override
  public IFileElementType getFileNodeType() {
    return FILE;
  }

  @NotNull
  @Override
  public PsiFile createFile(@NotNull FileViewProvider viewProvider) {
    return new GDescFile(viewProvider);
  }

  @NotNull
  @Override
  public PsiElement createElement(ASTNode node) {
    return GDescTypes.Factory.createElement(node);
  }

}
