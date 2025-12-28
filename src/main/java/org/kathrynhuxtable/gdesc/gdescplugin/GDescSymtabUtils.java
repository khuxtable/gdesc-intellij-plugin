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
package org.kathrynhuxtable.gdesc.gdescplugin;

import com.intellij.lang.Language;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.PsiNamedElement;
import org.antlr.intellij.adaptor.xpath.XPath;
import org.antlr.intellij.adaptor.psi.ScopeNode;
import org.antlr.intellij.adaptor.psi.Trees;

import java.util.Collection;
import java.util.Objects;

/**
 * Modified from the Antlr adapter.
 */
public class GDescSymtabUtils {
	/**
	 * Return the root of a def subtree chosen from among the
	 * matches from xpathToIDNodes that matches namedElement's text.
	 * Assumption: ID nodes are direct children of def subtree roots.
	 */
	public static PsiElement resolve(ScopeNode scope,
	                                 Language language,
	                                 PsiNamedElement namedElement,
	                                 String... xpathsToIDNodes) {
		for (String xpathToIDNodes : xpathsToIDNodes) {
			Collection<? extends PsiElement> defIDNodes =
					XPath.findAll(language, scope, xpathToIDNodes);
			String id = Objects.requireNonNull(namedElement.getName()).toLowerCase();
			for (PsiElement defIDNode : defIDNodes) {
				if (defIDNode.getText().toLowerCase().equals(id)) {
					return defIDNode.getParent();
				}
			}
		}

		// If not found, ask the enclosing scope/context to resolve.
		// That might lead back to this method, but probably with a
		// different xpathToIDNodes (which is why I don't call this method
		// directly).
		ScopeNode context = scope.getContext();
		if (context != null) {
			return context.resolve(namedElement);
		}

		// must be top scope; no resolution for element
		return null;
	}
}
