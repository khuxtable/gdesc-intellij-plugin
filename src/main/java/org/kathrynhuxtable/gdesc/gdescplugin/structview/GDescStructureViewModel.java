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
package org.kathrynhuxtable.gdesc.gdescplugin.structview;

import java.util.Comparator;

import com.intellij.icons.AllIcons.ObjectBrowser;
import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.ActionPresentation;
import com.intellij.ide.util.treeView.smartTree.ActionPresentationData;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import org.kathrynhuxtable.gdesc.gdescplugin.psi.*;

public class GDescStructureViewModel extends StructureViewModelBase implements StructureViewModel.ElementInfoProvider {

	Sorter TYPE_SORTER = new Sorter() {
		public Comparator<?> getComparator() {
			return (o1, o2) -> {
				GDescStructureViewElement e1 = (GDescStructureViewElement)o1;
				GDescStructureViewElement e2 = (GDescStructureViewElement)o2;
				String s1 = e1.getTypeSortKey();
				String s2 = e2.getTypeSortKey();
				return s1.compareToIgnoreCase(s2);
			};
		}

		public boolean isVisible() {
			return true;
		}

		public @NonNls String toString() {
			return this.getName();
		}

		public @NotNull ActionPresentation getPresentation() {
//			return new ActionPresentationData(PlatformEditorBundle.message("action.sort.alphabetically", new Object[0]), (String)null, ObjectBrowser.Sorted);
			return new ActionPresentationData("Type", (String) null, ObjectBrowser.Sorted);
		}

		public @NotNull String getName() {
			return "TYPE_COMPARATOR";
		}
	};

	public GDescStructureViewModel(GDescPSIFileRoot root) {
		super(root, new GDescStructureViewRootElement(root));
	}

	public Sorter @NotNull [] getSorters() {
		return new Sorter[]{Sorter.ALPHA_SORTER, TYPE_SORTER};
	}

	@Override
	public boolean isAlwaysLeaf(StructureViewTreeElement element) {
		return !isAlwaysShowsPlus(element);
	}

	@Override
	public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
		Object value = element.getValue();
		return value instanceof GDescPSIFileRoot;
	}

	@Override
	protected Class<?> @NotNull [] getSuitableClasses() {
		return new Class[]{
				IncludePragmaSubtree.class,
				InfoPragmaSubtree.class,
				FlagDirectiveSubtree.class,
				StateDirectiveSubtree.class,
				NoiseDirectiveSubtree.class,
				VerbDirectiveSubtree.class,
				VariableDirectiveSubtree.class,
				TextDirectiveSubtree.class,
				PlaceDirectiveSubtree.class,
				ObjectDirectiveSubtree.class,
				ActionDirectiveSubtree.class,
				ProcDirectiveSubtree.class,
				MainBlockSubtree.class
		};
	}
}
