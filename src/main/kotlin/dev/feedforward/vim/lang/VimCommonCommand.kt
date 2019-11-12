package dev.feedforward.vim.lang

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

