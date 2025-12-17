package org.kathrynhuxtable.gdesc.gdescplugin.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

import org.kathrynhuxtable.gdesc.gdescplugin.GDesc;
import org.kathrynhuxtable.gdesc.gdescplugin.GDescFileType;

public class GDescFile extends PsiFileBase {

  public GDescFile(@NotNull FileViewProvider viewProvider) {
    super(viewProvider, GDesc.INSTANCE);
  }

  @NotNull
  @Override
  public FileType getFileType() {
    return GDescFileType.INSTANCE;
  }

  @Override
  public String toString() {
    return "GDesc File";
  }

}
