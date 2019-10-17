package dev.feedforward

import com.intellij.lexer.FlexAdapter
import java.io.Reader

class VimLexerAdapter : FlexAdapter(VimLexer(null as Reader?))
