package dev.feedforward.psi

import com.intellij.psi.tree.IElementType
import dev.feedforward.VimLanguage

class VimTokenType(debugName: String) : IElementType(debugName, VimLanguage) {
    override fun toString(): String {
        return "VimTokenType." + super.toString()
    }
}
