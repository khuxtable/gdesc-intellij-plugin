package org.kathrynhuxtable.gdesc.gdescplugin.psi.impl;

import javax.swing.*;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.Nullable;

import org.kathrynhuxtable.gdesc.gdescplugin.psi.GDescElementFactory;
import org.kathrynhuxtable.gdesc.gdescplugin.psi.GDescProperty;
import org.kathrynhuxtable.gdesc.gdescplugin.psi.GDescTypes;

public class GDescPsiImplUtil {

  public static String getKey(GDescProperty element) {
    ASTNode keyNode = element.getNode().findChildByType(GDescTypes.KEY);
    if (keyNode != null) {
      // IMPORTANT: Convert embedded escaped spaces to simple spaces
      return keyNode.getText().replaceAll("\\\\ ", " ");
    } else {
      return null;
    }
  }

  public static String getValue(GDescProperty element) {
    ASTNode valueNode = element.getNode().findChildByType(GDescTypes.VALUE);
    if (valueNode != null) {
      return valueNode.getText();
    } else {
      return null;
    }
  }
  public static String getName(GDescProperty element) {
    return getKey(element);
  }

  public static PsiElement setName(GDescProperty element, String newName) {
    ASTNode keyNode = element.getNode().findChildByType(GDescTypes.KEY);
    if (keyNode != null) {
      GDescProperty property =
              GDescElementFactory.createProperty(element.getProject(), newName);
      ASTNode newKeyNode = property.getFirstChild().getNode();
      element.getNode().replaceChild(keyNode, newKeyNode);
    }
    return element;
  }

  public static PsiElement getNameIdentifier(GDescProperty element) {
    ASTNode keyNode = element.getNode().findChildByType(GDescTypes.KEY);
    return keyNode != null ? keyNode.getPsi() : null;
  }

  public static ItemPresentation getPresentation(final GDescProperty element) {
    return new ItemPresentation() {
      @Nullable
      @Override
      public String getPresentableText() {
        return element.getKey();
      }

      @Nullable
      @Override
      public String getLocationString() {
        PsiFile containingFile = element.getContainingFile();
        return containingFile == null ? null : containingFile.getName();
      }

      @Override
      public Icon getIcon(boolean unused) {
        return element.getIcon(0);
      }
    };
  }
}
