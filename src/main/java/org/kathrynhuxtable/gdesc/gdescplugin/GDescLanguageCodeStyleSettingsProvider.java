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
