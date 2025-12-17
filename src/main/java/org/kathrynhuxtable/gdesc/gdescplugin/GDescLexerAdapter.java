package org.kathrynhuxtable.gdesc.gdescplugin;

import com.intellij.lexer.FlexAdapter;

public class GDescLexerAdapter extends FlexAdapter {

  public GDescLexerAdapter() {
    super(new GDescLexer(null));
  }

}
