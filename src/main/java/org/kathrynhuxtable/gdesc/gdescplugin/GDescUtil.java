package org.kathrynhuxtable.gdesc.gdescplugin;

import java.util.*;

import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import org.kathrynhuxtable.gdesc.gdescplugin.psi.GDescFile;
import org.kathrynhuxtable.gdesc.gdescplugin.psi.GDescProperty;

public class GDescUtil {

  /**
   * Searches the entire project for GDesc language files with instances of the GDesc property with the given key.
   *
   * @param project current project
   * @param key     to check
   * @return matching properties
   */
  public static List<GDescProperty> findProperties(Project project, String key) {
    List<GDescProperty> result = new ArrayList<>();
    Collection<VirtualFile> virtualFiles =
        FileTypeIndex.getFiles(GDescFileType.INSTANCE, GlobalSearchScope.allScope(project));
    for (VirtualFile virtualFile : virtualFiles) {
      GDescFile gdescFile = (GDescFile) PsiManager.getInstance(project).findFile(virtualFile);
      if (gdescFile != null) {
        GDescProperty[] properties = PsiTreeUtil.getChildrenOfType(gdescFile, GDescProperty.class);
        if (properties != null) {
          for (GDescProperty property : properties) {
            if (key.equals(property.getKey())) {
              result.add(property);
            }
          }
        }
      }
    }
    return result;
  }

  public static List<GDescProperty> findProperties(Project project) {
    List<GDescProperty> result = new ArrayList<>();
    Collection<VirtualFile> virtualFiles =
        FileTypeIndex.getFiles(GDescFileType.INSTANCE, GlobalSearchScope.allScope(project));
    for (VirtualFile virtualFile : virtualFiles) {
      GDescFile GDescFile = (GDescFile) PsiManager.getInstance(project).findFile(virtualFile);
      if (GDescFile != null) {
        GDescProperty[] properties = PsiTreeUtil.getChildrenOfType(GDescFile, GDescProperty.class);
        if (properties != null) {
          Collections.addAll(result, properties);
        }
      }
    }
    return result;
  }

  /**
   * Attempts to collect any comment elements above the GDesc key/value pair.
   */
  public static @NotNull String findDocumentationComment(GDescProperty property) {
    List<String> result = new LinkedList<>();
    PsiElement element = property.getPrevSibling();
    while (element instanceof PsiComment || element instanceof PsiWhiteSpace) {
      if (element instanceof PsiComment) {
        String commentText = element.getText().replaceFirst("[!# ]+", "");
        result.add(commentText);
      }
      element = element.getPrevSibling();
    }
    return StringUtil.join(Lists.reverse(result), "\n ");
  }

}
