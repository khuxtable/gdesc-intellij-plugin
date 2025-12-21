package org.kathrynhuxtable.gdesc.gdescplugin;

import javax.swing.*;

import com.intellij.ide.IconProvider;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


final class GDescPropertyIconProvider extends IconProvider {

  @Override
  public @Nullable Icon getIcon(@NotNull PsiElement element, int flags) {
    return null;//element instanceof GDescProperty ? GDescIcons.FILE : null;
  }

}
