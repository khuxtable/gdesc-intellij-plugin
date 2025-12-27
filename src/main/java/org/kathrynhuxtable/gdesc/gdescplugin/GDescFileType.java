package org.kathrynhuxtable.gdesc.gdescplugin;

import javax.swing.*;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;

public final class GDescFileType extends LanguageFileType {

  public static final GDescFileType INSTANCE = new GDescFileType();

  private GDescFileType() {
    super(GDescLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public String getName() {
    return "GDesc File";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "Game Description file";
  }

  @NotNull
  @Override
  public String getDefaultExtension() {
    return "gdesc";
  }

  @Override
  public Icon getIcon() {
    return GDescIcons.FILE;
  }

}
