package dev.feedforward.vim.lang

import dev.feedforward.vim.lang.CommonCommandFlags.BANG
import dev.feedforward.vim.lang.CommonCommandFlags.CMDWIN
import dev.feedforward.vim.lang.CommonCommandFlags.COUNT
import dev.feedforward.vim.lang.CommonCommandFlags.EXTRA
import dev.feedforward.vim.lang.CommonCommandFlags.MODIFY
import dev.feedforward.vim.lang.CommonCommandFlags.NEEDARG
import dev.feedforward.vim.lang.CommonCommandFlags.NOTRLCOM
import dev.feedforward.vim.lang.CommonCommandFlags.RANGE
import dev.feedforward.vim.lang.CommonCommandFlags.REGSTR
import dev.feedforward.vim.lang.CommonCommandFlags.SBOXOK
import dev.feedforward.vim.lang.CommonCommandFlags.TRLBAR
import dev.feedforward.vim.lang.CommonCommandFlags.USECTRLV
import dev.feedforward.vim.lang.CommonCommandFlags.WHOLEFOLD
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
    RANGE,           /* allow a linespecs */
    BANG,            /* allow a ! after the command name */
    EXTRA,           /* allow extra args after command name */
    XFILE,           /* expand wildcards in extra part */
    NOSPC,           /* no spaces allowed in the extra part */
    DFLALL,          /* default file range is 1,$ */
    WHOLEFOLD,       /* extend range to include whole fold also
                                   when less than two numbers given */
    NEEDARG,         /* argument required */
    TRLBAR,          /* check for trailing vertical bar */
    REGSTR,          /* allow "x for register designation */
    COUNT,           /* allow count in argument, after command */
    NOTRLCOM,        /* no trailing comment allowed */
    ZEROR,           /* zero line number allowed */
    USECTRLV,        /* do not remove CTRL-V from argument */
    NOTADR,          /* number before command is not an address */
    EDITCMD,         /* allow "+command" argument */
    BUFNAME,         /* accepts buffer name */
    BUFUNL,          /* accepts unlisted buffer too */
    ARGOPT,          /* allow "++opt=val" argument */
    SBOXOK,          /* allowed in the sandbox */
    CMDWIN,          /* allowed in cmdline window; when missing
                         disallows editing another buffer when
                         curbuf_lock is set */
    MODIFY,          /* forbidden in non-'modifiable' buffer */
    EXFLAGS,         /* allow flags after count in argument */
    FILES,           /* (XFILE | EXTRA) multiple extra files allowed */
    WORD1,           /* (EXTRA | NOSPC)  one extra word allowed */
    FILE1            /* (FILES | NOSPC)  1 file allowed, defaults to current file */
}

val commonCommands = arrayOf(
        VimCommonCommand("ascii", 2, TRLBAR, SBOXOK, CMDWIN),
        VimCommonCommand("only", 2, BANG, TRLBAR),
        VimCommonCommand("quit", 1, BANG, TRLBAR, CMDWIN),
        VimCommonCommand("comclear", 4, TRLBAR, CMDWIN),
        VimCommonCommand("command", 3, EXTRA, BANG, NOTRLCOM, USECTRLV, CMDWIN),
        VimCommonCommand("delcommand", 4, NEEDARG, WORD1, TRLBAR, CMDWIN),
        VimCommonCommand("copy", 2, RANGE, WHOLEFOLD, EXTRA, TRLBAR, CMDWIN, MODIFY),
        VimCommonCommand("delete", 1, RANGE, WHOLEFOLD, REGSTR, COUNT, TRLBAR, CMDWIN, MODIFY),
        VimCommonCommand("delmarks", 4, BANG, EXTRA, TRLBAR, CMDWIN),
        VimCommonCommand("digraphs", 3, EXTRA, TRLBAR, CMDWIN),
        VimCommonCommand("browse", 3, NEEDARG, EXTRA, NOTRLCOM, CMDWIN),
        VimCommonCommand("qall", 2, BANG, TRLBAR, CMDWIN)
)
