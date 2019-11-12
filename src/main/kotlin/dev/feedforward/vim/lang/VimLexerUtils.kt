package dev.feedforward.vim.lang

import dev.feedforward.vim.lang.CommonCommandFlags.BANG
import dev.feedforward.vim.lang.CommonCommandFlags.NEEDARG
import dev.feedforward.vim.lang.CommonCommandFlags.WORD1
import java.util.*

class VimLexerUtils {
    companion object {
        @JvmStatic
        fun isCommand(text: CharSequence): Int {
            val stringText = text.toString()
            val command = commonCommands
                    .find { it.name.startsWith(stringText) && stringText.length >= it.minLength }
                    ?: return -1

            if (waitForArg(command.flags)) return WAIT_FOR_ARGUMENT
            return SIMPLE_COMMAND
        }

        const val SIMPLE_COMMAND = 0
        const val WAIT_FOR_ARGUMENT = 1

        private fun waitForArg(flags: EnumSet<CommonCommandFlags>): Boolean {
            return BANG in flags ||
                    NEEDARG in flags ||
                    WORD1 in flags
        }
    }
}

val commonCommands = arrayOf(
        VimCommonCommand("ascii", 2),
        VimCommonCommand("only", 2, EnumSet.of(BANG)),
        VimCommonCommand("quit", 1, EnumSet.of(BANG)),
        VimCommonCommand("comclear", 4),
        VimCommonCommand("delcommand", 4, EnumSet.of(NEEDARG, WORD1)),
        VimCommonCommand("copy", 2)
)

data class VimCommonCommand(
        val name: String,
        val minLength: Int,
        val flags: EnumSet<CommonCommandFlags> = EnumSet.noneOf(CommonCommandFlags::class.java)
)

enum class CommonCommandFlags {
    BANG,
    NEEDARG,
    WORD1
}

