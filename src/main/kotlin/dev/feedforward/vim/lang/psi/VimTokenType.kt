package dev.feedforward.vim.lang.psi

import com.intellij.psi.tree.IElementType
import dev.feedforward.vim.lang.VimLanguage

class VimTokenType(debugName: String) : IElementType(debugName, VimLanguage) {
    override fun toString(): String {
        return "VimTokenType." + super.toString()
    }
}
