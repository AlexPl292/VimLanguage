package dev.feedforward.vim.lang

import dev.feedforward.vim.lang.CommonCommandFlags.BANG
import dev.feedforward.vim.lang.CommonCommandFlags.COUNT
import dev.feedforward.vim.lang.CommonCommandFlags.EXTRA
import dev.feedforward.vim.lang.CommonCommandFlags.NEEDARG
import dev.feedforward.vim.lang.CommonCommandFlags.REGSTR
import dev.feedforward.vim.lang.CommonCommandFlags.TRLBAR
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

            if (TRLBAR in command.flags) return ARGUMENT_UNTIL_BAR
            if (waitForArg(command.flags)) return WAIT_FOR_ARGUMENT
            return SIMPLE_COMMAND
        }

        const val SIMPLE_COMMAND = 0
        const val WAIT_FOR_ARGUMENT = 1
        const val ARGUMENT_UNTIL_BAR = 2

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

val commonCommands = arrayOf(
        VimCommonCommand("ascii", 2, TRLBAR),
        VimCommonCommand("only", 2, BANG, TRLBAR),
        VimCommonCommand("quit", 1, BANG, TRLBAR),
        VimCommonCommand("comclear", 4, TRLBAR),
        VimCommonCommand("command", 3, BANG, EXTRA),
        VimCommonCommand("delcommand", 4, NEEDARG, WORD1, TRLBAR),
        VimCommonCommand("copy", 2, TRLBAR),
        VimCommonCommand("delete", 1, REGSTR, COUNT, TRLBAR)
)

class VimCommonCommand(
        val name: String,
        val minLength: Int,
        vararg flags: CommonCommandFlags
) {
    val flags: EnumSet<CommonCommandFlags> = EnumSet.copyOf(flags.toSet())
}

enum class CommonCommandFlags {
    BANG,
    NEEDARG,
    WORD1,
    EXTRA,
    REGSTR,
    COUNT,
    TRLBAR
}

