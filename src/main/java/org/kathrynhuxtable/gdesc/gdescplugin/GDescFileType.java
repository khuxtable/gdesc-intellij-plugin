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

import javax.swing.*;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;

public final class GDescFileType extends LanguageFileType {

  public static final GDescFileType INSTANCE = new GDescFileType();

  private GDescFileType() {
    super(GDescLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public String getName() {
    return "GDesc";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "Game Description file";
  }

  @NotNull
  @Override
  public String getDefaultExtension() {
    return "gdesc";
  }

  @Override
  public Icon getIcon() {
    return GDescIcons.FILE;
  }

}
