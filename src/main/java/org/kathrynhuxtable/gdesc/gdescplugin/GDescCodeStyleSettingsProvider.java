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
		return GDesc.INSTANCE;
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
			super(GDesc.INSTANCE, currentSettings, settings);
		}

	}

}
