package dev.feedforward.vim.lang

import com.intellij.lexer.FlexAdapter
import java.io.Reader

class VimLexerAdapter : FlexAdapter(VimLexer(null as Reader?))
