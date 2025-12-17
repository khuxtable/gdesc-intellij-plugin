package org.kathrynhuxtable.gdesc.gdescplugin.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import org.kathrynhuxtable.gdesc.gdescplugin.GDesc;

public class GDescElementType extends IElementType {

  public GDescElementType(@NotNull @NonNls String debugName) {
    super(debugName, GDesc.INSTANCE);
  }

}
