package dev.feedforward.vim.lang

import com.intellij.lang.parser.GeneratedParserUtilBase
import com.intellij.psi.PsiElement
import dev.feedforward.vim.lang.psi.VimCommand
import dev.feedforward.vim.lang.psi.VimCommandTypes

class VimPsiImplUtils : GeneratedParserUtilBase() {
    companion object {
        @JvmStatic
        fun getCommandName(command: VimCommand): String {
            var e: PsiElement? = command
            val endOffset = command.textRange.endOffset
            while (e != null && e.node.startOffset < endOffset) {
                val commandName = VimCommandTypes.commands[e.node.elementType]
                if (commandName != null) return commandName
                e = e.nextSibling
            }
            throw RuntimeException("Command name not found")
        }
    }
}
