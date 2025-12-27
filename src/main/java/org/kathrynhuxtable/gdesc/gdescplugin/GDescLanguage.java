package org.kathrynhuxtable.gdesc.gdescplugin;

import com.intellij.lang.Language;

public class GDescLanguage extends Language {

  public static final GDescLanguage INSTANCE = new GDescLanguage();

  private GDescLanguage() {
    super("GDesc");
  }

}
