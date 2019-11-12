package dev.feedforward.vim.lang

import dev.feedforward.vim.lang.CommonCommandFlags.BANG
import dev.feedforward.vim.lang.CommonCommandFlags.COUNT
import dev.feedforward.vim.lang.CommonCommandFlags.EXTRA
import dev.feedforward.vim.lang.CommonCommandFlags.NEEDARG
import dev.feedforward.vim.lang.CommonCommandFlags.NOTRLCOM
import dev.feedforward.vim.lang.CommonCommandFlags.REGSTR
import dev.feedforward.vim.lang.CommonCommandFlags.TRLBAR
import dev.feedforward.vim.lang.CommonCommandFlags.WORD1
import java.util.*

class VimLexerUtils {
    companion object {

        @JvmField
        var trlbar = false
        @JvmField
        var nocomment = false

        @JvmStatic
        fun isCommand(text: CharSequence): Int {
            trlbar = false
            nocomment = false

            val stringText = text.toString()
            val command = commonCommands
                    .find { it.name.startsWith(stringText) && stringText.length >= it.minLength }
                    ?: return -1

            if (TRLBAR in command.flags) trlbar = true
            if (NOTRLCOM in command.flags) nocomment = true
            if (waitForArg(command.flags)) return ARGUMENT
            return SIMPLE_COMMAND
        }

        const val SIMPLE_COMMAND = 0
        const val ARGUMENT = 1

        private fun waitForArg(flags: EnumSet<CommonCommandFlags>): Boolean {
            return BANG in flags ||
                    NEEDARG in flags ||
                    WORD1 in flags ||
                    EXTRA in flags ||
                    REGSTR in flags ||
                    COUNT in flags
        }
    }
}

