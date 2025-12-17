package org.kathrynhuxtable.gdesc.gdescplugin.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

import org.kathrynhuxtable.gdesc.gdescplugin.psi.GDescNamedElement;

public abstract class GDescNamedElementImpl extends ASTWrapperPsiElement implements GDescNamedElement {

  public GDescNamedElementImpl(@NotNull ASTNode node) {
    super(node);
  }

}
