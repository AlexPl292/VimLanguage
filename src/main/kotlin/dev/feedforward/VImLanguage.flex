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

STRING_LITERAL=\"(\\.|[^\\\"])*\"

%state WAITING_VALUE

%%

<YYINITIAL> {END_OF_LINE_COMMENT}                           { yybegin(YYINITIAL); return VimTypes.COMMENT; }

<YYINITIAL> {
      "echo"                                                  { return VimTypes.ECHO; }
      {STRING_LITERAL}                                        { return VimTypes.STRING_LITERAL; }

      "||"                                                    { return VimTypes.OROR; }
      "&&"                                                    { return VimTypes.ANDAND; }
}

({CRLF}|{WHITE_SPACE})+                                     { return TokenType.WHITE_SPACE; }

[^]                                                         { return TokenType.BAD_CHARACTER; }
