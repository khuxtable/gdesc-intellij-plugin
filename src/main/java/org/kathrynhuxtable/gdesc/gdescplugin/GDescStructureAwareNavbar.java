package org.kathrynhuxtable.gdesc.gdescplugin;

import javax.swing.*;

import com.intellij.icons.AllIcons;
import com.intellij.ide.navigationToolbar.StructureAwareNavBarModelExtension;
import com.intellij.lang.Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.kathrynhuxtable.gdesc.gdescplugin.psi.GDescFile;
import org.kathrynhuxtable.gdesc.gdescplugin.psi.GDescProperty;

final class GDescStructureAwareNavbar extends StructureAwareNavBarModelExtension {

  @NotNull
  @Override
  protected Language getLanguage() {
    return GDesc.INSTANCE;
  }

  @Override
  public @Nullable String getPresentableText(Object object) {
    if (object instanceof GDescFile) {
      return ((GDescFile) object).getName();
    }
    if (object instanceof GDescProperty) {
      return ((GDescProperty) object).getName();
    }

    return null;
  }

  @Override
  @Nullable
  public Icon getIcon(Object object) {
    if (object instanceof GDescProperty) {
      return AllIcons.Nodes.Property;
    }

    return null;
  }

}
