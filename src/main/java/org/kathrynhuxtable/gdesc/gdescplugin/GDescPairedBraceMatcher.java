package org.kathrynhuxtable.gdesc.gdescplugin;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GDescPairedBraceMatcher implements PairedBraceMatcher {

	private static final BracePair[] PAIRS = new BracePair[]{
			new BracePair(GDescParserDefinition.LBRACE, GDescParserDefinition.RBRACE, true),
			new BracePair(GDescParserDefinition.LPAREN, GDescParserDefinition.RPAREN, false),
			new BracePair(GDescParserDefinition.LBRACK, GDescParserDefinition.RBRACK, false),
	};

	@Override
	public BracePair @NotNull [] getPairs() {
		return PAIRS;
	}

	@Override
	public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
		return true;
	}

	@Override
	public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
		return openingBraceOffset;
	}
}
