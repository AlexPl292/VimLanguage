package dev.feedforward;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import dev.feedforward.psi.VimTypes;
import com.intellij.psi.TokenType;

%%

%public
%class VimLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%public
%eof{  return;
%eof}

END_OF_LINE_COMMENT=("#")[^\r\n]*

CRLF=\R
WHITE_SPACE=[\ \n\t\f]

QUOTE                    = \"

%state WAITING_VALUE
%state STRING_EXPRESSION

%%

<YYINITIAL> {END_OF_LINE_COMMENT}                           { yybegin(YYINITIAL); return VimTypes.COMMENT; }

<YYINITIAL> {
      "echo"                                                  { return VimTypes.ECHO; }
      {QUOTE}                                                 { yybegin(STRING_EXPRESSION); return VimTypes.OPEN_QUOTE; }
}

<STRING_EXPRESSION> {
      {QUOTE}                                                 { yybegin(YYINITIAL); return VimTypes.CLOSE_QUOTE; }
      [^\"]+                                                  { return VimTypes.STRING_CONTENT; }
}

({CRLF}|{WHITE_SPACE})+                                     { return TokenType.WHITE_SPACE; }

[^]                                                         { return TokenType.BAD_CHARACTER; }
