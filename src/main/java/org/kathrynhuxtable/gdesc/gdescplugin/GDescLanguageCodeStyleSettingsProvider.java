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

import java.io.IOException;
import java.io.InputStream;

import com.intellij.application.options.IndentOptionsEditor;
import com.intellij.application.options.SmartIndentOptionsEditor;
import com.intellij.lang.Language;
import com.intellij.openapi.util.io.StreamUtil;
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable;
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizableOptions;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings.IndentOptions;
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider;
import org.jetbrains.annotations.NotNull;

final class GDescLanguageCodeStyleSettingsProvider extends LanguageCodeStyleSettingsProvider {

	private String indentsSample;
	private String spacingSample;
	private String blankLinesSample;
	private String wrappingAndBracesSample;
	private String commentsSample;

	@NotNull
	@Override
	public Language getLanguage() {
		return GDescLanguage.INSTANCE;
	}

	@Override
	public void customizeSettings(@NotNull CodeStyleSettingsCustomizable consumer, @NotNull SettingsType settingsType) {
		if (settingsType == SettingsType.SPACING_SETTINGS) {
			consumer.showStandardOptions(
					"SPACE_AROUND_ASSIGNMENT_OPERATORS",
					"SPACE_AROUND_LOGICAL_OPERATORS",
					"SPACE_AROUND_EQUALITY_OPERATORS",
					"SPACE_AROUND_RELATIONAL_OPERATORS",
					"SPACE_AROUND_ADDITIVE_OPERATORS",
					"SPACE_AROUND_MULTIPLICATIVE_OPERATORS",
					"SPACE_AROUND_UNARY_OPERATOR",
					"SPACE_AFTER_COMMA",
					"SPACE_BEFORE_COMMA",
					"SPACE_BEFORE_COLON",
					"SPACE_AFTER_COLON"
			);
			consumer.renameStandardOption(
					"SPACE_AROUND_ASSIGNMENT_OPERATORS",
					"Space around assigment operators (=, =>)"
			);
			consumer.renameStandardOption(
					"SPACE_AROUND_LOGICAL_OPERATORS",
					"Logical operators (.or., .and.)"
			);
			consumer.renameStandardOption(
					"SPACE_AROUND_EQUALITY_OPERATORS",
					"Equality operators (==, .eq., ...)"
			);
			consumer.renameStandardOption(
					"SPACE_AROUND_RELATIONAL_OPERATORS",
					"Relational operators (<, .lt., ...)"
			);
			consumer.renameStandardOption(
					"SPACE_AROUND_MULTIPLICATIVE_OPERATORS",
					"Multiplicative operators (*, /)"
			);
			consumer.renameStandardOption(
					"SPACE_AROUND_UNARY_OPERATOR",
					"Unary operators (+, -)"
			);

			CodeStyleSettingsCustomizableOptions codeStyleSettingsCustomizable = CodeStyleSettingsCustomizableOptions.getInstance();
			consumer.showCustomOption(
					GDescCodeStyleSettings.class,
					"SPACE_AROUND_NOT_OPERATOR",
					"Not operator (.not.)",
					codeStyleSettingsCustomizable.SPACES_AROUND_OPERATORS
			);
			consumer.showCustomOption(
					GDescCodeStyleSettings.class,
					"SPACE_AROUND_POWER_OPERATOR",
					"Power operator (**)",
					codeStyleSettingsCustomizable.SPACES_AROUND_OPERATORS
			);
			consumer.showCustomOption(
					GDescCodeStyleSettings.class,
					"SPACE_AROUND_EQUIVALENCE_OPERATOR",
					"Equivalence operators (.eqv., .neqv.)",
					codeStyleSettingsCustomizable.SPACES_AROUND_OPERATORS
			);
			consumer.showCustomOption(
					GDescCodeStyleSettings.class,
					"SPACE_AROUND_CONCAT_OPERATOR",
					"Concatenation operator (//)",
					codeStyleSettingsCustomizable.SPACES_AROUND_OPERATORS
			);
			consumer.showCustomOption(
					GDescCodeStyleSettings.class,
					"SPACE_AROUND_DEFINED_OPERATOR",
					"Defined operators",
					codeStyleSettingsCustomizable.SPACES_AROUND_OPERATORS
			);

			consumer.moveStandardOption(
					"SPACE_BEFORE_COLON",
					codeStyleSettingsCustomizable.SPACES_OTHER
			);
			consumer.moveStandardOption(
					"SPACE_AFTER_COLON",
					codeStyleSettingsCustomizable.SPACES_OTHER
			);

			consumer.showCustomOption(
					GDescCodeStyleSettings.class,
					"SPACE_BEFORE_DOUBLE_COLON",
					"Space before double-colon",
					codeStyleSettingsCustomizable.SPACES_OTHER
			);
			consumer.showCustomOption(
					GDescCodeStyleSettings.class,
					"SPACE_AFTER_DOUBLE_COLON",
					"Space after double-colon",
					codeStyleSettingsCustomizable.SPACES_OTHER
			);
			consumer.showCustomOption(
					GDescCodeStyleSettings.class,
					"SPACE_BEFORE_DOUBLE_COLON",
					"Space before double-colon",
					CodeStyleSettingsCustomizableOptions.getInstance().SPACES_OTHER
			);
			consumer.showCustomOption(
					GDescCodeStyleSettings.class,
					"SPACE_AFTER_DOUBLE_COLON",
					"Space after double-colon",
					CodeStyleSettingsCustomizableOptions.getInstance().SPACES_OTHER
			);
		} else if (settingsType == SettingsType.BLANK_LINES_SETTINGS) {
			consumer.showStandardOptions(
					"KEEP_BLANK_LINES_IN_CODE",
					"KEEP_BLANK_LINES_IN_DECLARATIONS"
			);
			consumer.renameStandardOption(
					"KEEP_BLANK_LINES_IN_DECLARATIONS",
					"Between directives"
			);
		}
	}

	@Override
	public void customizeDefaults(CommonCodeStyleSettings commonSettings, @NotNull IndentOptions indentOptions) {
		commonSettings.KEEP_BLANK_LINES_IN_CODE = 1;
	}

	@Override
	public IndentOptionsEditor getIndentOptionsEditor() {
		return new SmartIndentOptionsEditor();
	}

	@Override
	public String getCodeSample(@NotNull SettingsType settingsType) {
		return switch (settingsType) {
			case INDENT_SETTINGS -> getIndentsSample();
			case BLANK_LINES_SETTINGS -> getBlankLinesSample();
			case SPACING_SETTINGS -> getSpacingSample();
			case WRAPPING_AND_BRACES_SETTINGS ->  getWrappingAndBracesSample();
			case COMMENTER_SETTINGS -> getCommentsSample();
			case LANGUAGE_SPECIFIC -> "No language specific sample available";
		};
	}

	private @NotNull String getIndentsSample() {
		if (indentsSample == null) {
			indentsSample = loadCodeSampleResource("indents.gdesc");
		}
		return indentsSample;
	}

	private @NotNull String getSpacingSample() {
		if (spacingSample == null) {
			spacingSample = loadCodeSampleResource("spacing.gdesc");
		}
		return spacingSample;
	}

	private @NotNull String getWrappingAndBracesSample() {
		if (wrappingAndBracesSample == null) {
			wrappingAndBracesSample = loadCodeSampleResource("wrapping.gdesc");
		}
		return wrappingAndBracesSample;
	}

	private @NotNull String getBlankLinesSample() {
		if (blankLinesSample == null) {
			blankLinesSample = loadCodeSampleResource("blanklines.gdesc");
		}
		return blankLinesSample;
	}

	private @NotNull String getCommentsSample() {
		if (commentsSample == null) {
			commentsSample = loadCodeSampleResource("comments.gdesc");
		}
		return commentsSample;
	}

	private String loadCodeSampleResource(String resource) {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("codesamples/" + resource)) {
			assert inputStream != null;
			return StreamUtil.convertSeparators(new String(inputStream.readAllBytes()));
		} catch (IOException e) {
			return "Can't find resource " + resource;
		}
	}

}
