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
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable;
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider;
import org.jetbrains.annotations.NotNull;

final class GDescLanguageCodeStyleSettingsProvider extends LanguageCodeStyleSettingsProvider {

	@NotNull
	@Override
	public Language getLanguage() {
		return GDescLanguage.INSTANCE;
	}

	@Override
	public void customizeSettings(@NotNull CodeStyleSettingsCustomizable consumer, @NotNull SettingsType settingsType) {
		if (settingsType == SettingsType.SPACING_SETTINGS) {
			consumer.showStandardOptions("SPACE_AROUND_ASSIGNMENT_OPERATORS");
			consumer.renameStandardOption("SPACE_AROUND_ASSIGNMENT_OPERATORS", "Operators");
		} else if (settingsType == SettingsType.BLANK_LINES_SETTINGS) {
			consumer.showStandardOptions("KEEP_BLANK_LINES_IN_CODE");
		}
	}

	@Override public String getCodeSample(@NotNull SettingsType settingsType) {
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
				            done = 4;
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

}
