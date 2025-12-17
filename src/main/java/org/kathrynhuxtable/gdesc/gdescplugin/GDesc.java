package org.kathrynhuxtable.gdesc.gdescplugin;

import com.intellij.lang.Language;

public class GDesc extends Language {

  public static final GDesc INSTANCE = new GDesc();

  private GDesc() {
    super("GDesc");
  }

}
