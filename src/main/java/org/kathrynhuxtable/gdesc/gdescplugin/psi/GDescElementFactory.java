package org.kathrynhuxtable.gdesc.gdescplugin.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;

import org.kathrynhuxtable.gdesc.gdescplugin.GDescFileType;

public class GDescElementFactory {

	public static GDescProperty createProperty(Project project, String name) {
		GDescFile file = createFile(project, name);
		return (GDescProperty) file.getFirstChild();
	}

	public static GDescFile createFile(Project project, String text) {
		String name = "dummy.gdesc";
		return (GDescFile) PsiFileFactory.getInstance(project).
				createFileFromText(name, GDescFileType.INSTANCE, text);
	}

	public static GDescProperty createProperty(Project project, String name, String value) {
		final GDescFile file = createFile(project, name + " = " + value);
		return (GDescProperty) file.getFirstChild();
	}

	public static PsiElement createCRLF(Project project) {
		final GDescFile file = createFile(project, "\n");
		return file.getFirstChild();
	}
}
