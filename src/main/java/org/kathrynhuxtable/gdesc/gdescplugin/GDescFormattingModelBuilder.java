package org.kathrynhuxtable.gdesc.gdescplugin;

import com.intellij.formatting.*;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import org.jetbrains.annotations.NotNull;

final class GDescFormattingModelBuilder implements FormattingModelBuilder {

	private static SpacingBuilder createSpaceBuilder(CodeStyleSettings settings) {
		return null;
//		new SpacingBuilder(settings, GDescLanguage.INSTANCE)
//				.around(GDescParserDefinition.OPERATOR)
//				.spaceIf(settings.getCommonSettings(GDescLanguage.INSTANCE.getID()).SPACE_AROUND_ASSIGNMENT_OPERATORS)
//				.before(GDescParserDefinition.STRING).none();
//		;
	}

	@Override
	public @NotNull FormattingModel createModel(@NotNull FormattingContext formattingContext) {
		final CodeStyleSettings codeStyleSettings = formattingContext.getCodeStyleSettings();
		return FormattingModelProvider
				.createFormattingModelForPsiFile(formattingContext.getContainingFile(),
						new GDescBlock(formattingContext.getNode(),
								Wrap.createWrap(WrapType.NONE, false),
								Alignment.createAlignment(),
								createSpaceBuilder(codeStyleSettings)),
						codeStyleSettings);
	}

}
