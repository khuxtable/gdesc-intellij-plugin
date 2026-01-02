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
import com.intellij.psi.tree.IElementType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.antlr.intellij.adaptor.psi.IdentifierDefSubtree;
import org.jetbrains.annotations.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlaceDirectiveSubtree extends IdentifierDefSubtree implements GDescDirectiveElement {

	public PlaceDirectiveSubtree(@NotNull ASTNode node, @NotNull IElementType idElementTyp) {
		super(node, idElementTyp);
	}

//	@Override
//	public @Nullable PsiElement resolve(PsiNamedElement element) {
//		System.out.println(getClass().getSimpleName() + ":" + definitionType +
//				".resolve(" + getNode() +
//				" at " + Integer.toHexString(getNode().hashCode()) + ")");
//		switch (definitionType) {
//		case ArrayDef:
//			break;
//		case FlagDef:
//			break;
//		case StateDef:
//			break;
//		case VariableDef:
//			break;
//		case VerbDef:
//			return GDescSymtabUtils.resolve(this, GDescLanguage.INSTANCE,
//					element, "/game/directive/verbDirective/IDENTIFIER");
//		case TextDef:
//			return GDescSymtabUtils.resolve(this, GDescLanguage.INSTANCE,
//					element, "/game/directive/textDirective/IDENTIFIER");
//		case FragmentDef:
//			break;
//		case PlaceDef:
//			break;
//		case ObjectDef:
//			break;
//		}
//		return null;
//	}
}
