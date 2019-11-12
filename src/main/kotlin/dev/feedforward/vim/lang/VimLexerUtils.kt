package dev.feedforward.vim.lang

import java.util.*

class VimLexerUtils {
    companion object {
        @JvmStatic
        fun isCommand(text: CharSequence): Int {
            val stringText = text.toString()
            val command = commonCommands
                    .find { it.name.startsWith(stringText) && stringText.length >= it.minLength }
                    ?: return -1

            if (command.flags.contains(CommonCommandFlags.BANG)) return WAIT_FOR_ARGUMENT
            return SIMPLE_COMMAND
        }

        const val SIMPLE_COMMAND = 0
        const val WAIT_FOR_ARGUMENT = 1
    }
}

val commonCommands = arrayOf(
        VimCommonCommand("ascii", 2, emptySet()),
        VimCommonCommand("only", 2, EnumSet.of(CommonCommandFlags.BANG)),
        VimCommonCommand("quit", 1, EnumSet.of(CommonCommandFlags.BANG)),
        VimCommonCommand("comclear", 4, emptySet())
)

data class VimCommonCommand(
        val name: String,
        val minLength: Int,
        val flags: Set<CommonCommandFlags>
)

enum class CommonCommandFlags {
    BANG
}

