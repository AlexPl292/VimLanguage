package dev.feedforward.vim.lang.psi

import com.intellij.psi.tree.IElementType
import dev.feedforward.vim.lang.VimLanguage


class VimElementType(debugName: String) : IElementType(debugName, VimLanguage)
