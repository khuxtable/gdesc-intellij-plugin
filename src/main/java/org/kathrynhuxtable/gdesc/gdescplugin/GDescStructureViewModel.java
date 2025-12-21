package org.kathrynhuxtable.gdesc.gdescplugin;

import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GDescStructureViewModel extends StructureViewModelBase implements
    StructureViewModel.ElementInfoProvider {

  public GDescStructureViewModel(@Nullable Editor editor, PsiFile psiFile) {
    super(psiFile, editor, new GDescStructureViewElement(psiFile));
  }

  @NotNull
  public Sorter @NotNull [] getSorters() {
    return new Sorter[]{Sorter.ALPHA_SORTER};
  }


  @Override
  public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
    return false;
  }

  @Override
  public boolean isAlwaysLeaf(StructureViewTreeElement element) {
    return false;//element.getValue() instanceof GDescProperty;
  }

  @Override
  protected Class<?> @NotNull [] getSuitableClasses() {
    return null;//new Class[]{GDescProperty.class};
  }

}
