package org.kathrynhuxtable.gdesc.gdescplugin.psi;

import com.intellij.psi.tree.TokenSet;

public interface GDescTokenSets {

  TokenSet IDENTIFIERS = TokenSet.create(GDescTypes.KEY);

  TokenSet COMMENTS = TokenSet.create(GDescTypes.COMMENT);

}
