package org.kathrynhuxtable.gdesc.gdescplugin;

import java.util.ArrayList;
import java.util.List;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import org.kathrynhuxtable.gdesc.gdescplugin.psi.GDescPSIFileRoot;

public class GDescStructureViewElement implements StructureViewTreeElement, SortableTreeElement {

  private final NavigatablePsiElement myElement;

  public GDescStructureViewElement(NavigatablePsiElement element) {
    this.myElement = element;
  }

  @Override
  public Object getValue() {
    return myElement;
  }

  @Override
  public void navigate(boolean requestFocus) {
    myElement.navigate(requestFocus);
  }

  @Override
  public boolean canNavigate() {
    return myElement.canNavigate();
  }

  @Override
  public boolean canNavigateToSource() {
    return myElement.canNavigateToSource();
  }

  @NotNull
  @Override
  public String getAlphaSortKey() {
    String name = myElement.getName();
    return name != null ? name : "";
  }

  @NotNull
  @Override
  public ItemPresentation getPresentation() {
    ItemPresentation presentation = myElement.getPresentation();
    return presentation != null ? presentation : new PresentationData();
  }

  @Override
  public TreeElement @NotNull [] getChildren() {
//    if (myElement instanceof GDescPSIFileRoot) {
//      List<GDescProperty> properties = PsiTreeUtil.getChildrenOfTypeAsList(myElement, GDescProperty.class);
//      List<TreeElement> treeElements = new ArrayList<>(properties.size());
//      for (GDescProperty property : properties) {
//        treeElements.add(new GDescStructureViewElement((GDescPropertyImpl) property));
//      }
//      return treeElements.toArray(new TreeElement[0]);
//    }
    return EMPTY_ARRAY;
  }

}
