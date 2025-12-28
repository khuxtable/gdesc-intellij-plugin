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

import com.intellij.application.options.CodeStyleAbstractConfigurable;
import com.intellij.application.options.CodeStyleAbstractPanel;
import com.intellij.application.options.TabbedLanguageCodeStylePanel;
import com.intellij.lang.Language;
import com.intellij.psi.codeStyle.CodeStyleConfigurable;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider;
import com.intellij.psi.codeStyle.CustomCodeStyleSettings;
import org.jetbrains.annotations.NotNull;

final class GDescCodeStyleSettingsProvider extends CodeStyleSettingsProvider {

	@Override
	public CustomCodeStyleSettings createCustomSettings(@NotNull CodeStyleSettings settings) {
		return new GDescCodeStyleSettings(settings);
	}

	@Override
	public Language getLanguage() {
		return GDescLanguage.INSTANCE;
	}

	@Override
	public String getConfigurableDisplayName() {
		return "GDesc";
	}

	@NotNull
	public CodeStyleConfigurable createConfigurable(@NotNull CodeStyleSettings settings,
	                                                @NotNull CodeStyleSettings modelSettings) {
		return new CodeStyleAbstractConfigurable(settings, modelSettings, this.getConfigurableDisplayName()) {
			@Override
			protected @NotNull CodeStyleAbstractPanel createPanel(@NotNull CodeStyleSettings settings) {
				return new GDescCodeStyleMainPanel(getCurrentSettings(), settings);
			}
		};
	}

	private static class GDescCodeStyleMainPanel extends TabbedLanguageCodeStylePanel {

		public GDescCodeStyleMainPanel(CodeStyleSettings currentSettings, CodeStyleSettings settings) {
			super(GDescLanguage.INSTANCE, currentSettings, settings);
		}

	}

}
