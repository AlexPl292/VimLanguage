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
    TRLBAR,
    NOTRLCOM
}

val commonCommands = arrayOf(
        VimCommonCommand("ascii", 2, TRLBAR),
        VimCommonCommand("only", 2, BANG, TRLBAR),
        VimCommonCommand("quit", 1, BANG, TRLBAR),
        VimCommonCommand("comclear", 4, TRLBAR),
        VimCommonCommand("command", 3, BANG, EXTRA, NOTRLCOM),
        VimCommonCommand("delcommand", 4, NEEDARG, WORD1, TRLBAR),
        VimCommonCommand("copy", 2, TRLBAR),
        VimCommonCommand("delete", 1, REGSTR, COUNT, TRLBAR),
        VimCommonCommand("delmarks", 4, BANG, EXTRA, TRLBAR),
        VimCommonCommand("digraphs", 3, EXTRA, TRLBAR),
        VimCommonCommand("browse", 3, NEEDARG, EXTRA, NOTRLCOM),
        VimCommonCommand("qall", 2, BANG, TRLBAR)
)
