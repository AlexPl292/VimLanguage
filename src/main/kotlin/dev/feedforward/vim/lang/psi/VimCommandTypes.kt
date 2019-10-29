package dev.feedforward.vim.lang.psi

object VimCommandTypes {
    val commands = mapOf(
            VimTypes.ECHO_COMMAND to "echo",
            VimTypes.ASCII_COMMAND to "ascii",
            VimTypes.COMCLEAR_COMMAND to "comclear",
            VimTypes.ONLY_COMMAND to "only",
            VimTypes.QUIT_COMMAND to "quit",
            VimTypes.COPY_COMMAND to "copy"
    )
}
