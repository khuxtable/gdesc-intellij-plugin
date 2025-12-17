package org.kathrynhuxtable.gdesc.gdescplugin.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import org.kathrynhuxtable.gdesc.gdescplugin.GDesc;

public class GDescTokenType extends IElementType {

  public GDescTokenType(@NotNull @NonNls String debugName) {
    super(debugName, GDesc.INSTANCE);
  }

  @Override
  public String toString() {
    return "GDescTokenType." + super.toString();
  }

}
