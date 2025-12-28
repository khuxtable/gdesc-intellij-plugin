/*
 * Copyright © 2025, 2026 Kathryn A Huxtable
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kathrynhuxtable.gdesc.gdescplugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.psi.IdentifierDefSubtree;
import org.antlr.intellij.adaptor.psi.ScopeNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.kathrynhuxtable.gdesc.gdescplugin.GDescLanguage;
import org.kathrynhuxtable.gdesc.gdescplugin.GDescSymtabUtils;

/**
 * A subtree associated with a function definition.
 * Its scope is the set of arguments.
 */
public class ProcSubtree extends IdentifierDefSubtree implements ScopeNode, GDescDirectiveElement {
	public ProcSubtree(@NotNull ASTNode node, @NotNull IElementType idElementType) {
		super(node, idElementType);
	}

	@Nullable
	@Override
	public PsiElement resolve(PsiNamedElement element) {
		System.out.println(getClass().getSimpleName() +
				".resolve(" + getNode() +
				" at " + Integer.toHexString(getNode().hashCode()) + ")");

		return GDescSymtabUtils.resolve(this, GDescLanguage.INSTANCE,
				element, "/procDirective/IDENTIFIER");
	}
}
