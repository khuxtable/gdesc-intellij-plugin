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

import java.util.Map;
import javax.swing.*;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class GDescColorSettingsPage implements ColorSettingsPage {

	private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
//			new AttributesDescriptor("Operators//Plus", MySyntaxHighlighter.PLUS),
			new AttributesDescriptor("ID", GDescSyntaxHighlighter.ID),
			new AttributesDescriptor("Internal function", GDescSyntaxHighlighter.INTERNAL_FUNCTION),
			new AttributesDescriptor("Constant", GDescSyntaxHighlighter.CONSTANT),
			new AttributesDescriptor("Keyword", GDescSyntaxHighlighter.KEYWORD),
			new AttributesDescriptor("Keyword 2", GDescSyntaxHighlighter.KEYWORD2),
			new AttributesDescriptor("String", GDescSyntaxHighlighter.STRING),
			new AttributesDescriptor("Line comment", GDescSyntaxHighlighter.LINE_COMMENT),
			new AttributesDescriptor("Block comment", GDescSyntaxHighlighter.BLOCK_COMMENT)
	};

	@Override
	public Icon getIcon() {
		return GDescIcons.FILE;
	}

	@NotNull
	@Override
	public SyntaxHighlighter getHighlighter() {
		return new GDescSyntaxHighlighter();
	}

	@NotNull
	@Override
	public String getDemoText() {
		return """
				// You are reading the ".gdesc" entry.
				name "Sample Game";
				/*
				 * Multi-line comments exist.
				 */
				flags variable
				    moved;
				
				proc stuff {
					if (arg1 instanceof verb) {
						bail.out();
					}
				}

				proc bail.out stuff nonsense {
				    var qualifier;                  // Local variable initialised to zero
				    if (status == 1) {
				        if (arg1 instanceof verb) {  // If that word is an object name
				            if (!isnear(arg1)) {
				                quip("There is no # here!", arg1);
				            } else {
				                quip("You need to say what you want to do with the #.", arg1);
				            }
				        } else {
				            quip("You need to say what you want to #.", arg1);
				        }
				    }
				    quip("You can't do that!");     // Generic no can do
				}
				
				fragment you_do
				    "You #";
				text cannot_tell_directions
				    ""\"
				    You can't tell directions here.
				    Try Up, Down, Left, Right, Forward, Back
				    ""\";""";
	}

	@Nullable
	@Override
	public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
		return null;
	}

	@Override
	public AttributesDescriptor @NotNull [] getAttributeDescriptors() {
		return DESCRIPTORS;
	}

	@Override
	public ColorDescriptor @NotNull [] getColorDescriptors() {
		return ColorDescriptor.EMPTY_ARRAY;
	}

	@NotNull
	@Override
	public String getDisplayName() {
		return "GDesc";
	}

}
