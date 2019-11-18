package dev.feedforward.vim.lang.psi

import com.intellij.psi.tree.IElementType

interface VimExtraTokens {
    companion object {
        @JvmField
        val IGNORABLE_WHITESPACE: IElementType = VimTokenType("IGNORABLE_WHITESPACE")
    }
}
