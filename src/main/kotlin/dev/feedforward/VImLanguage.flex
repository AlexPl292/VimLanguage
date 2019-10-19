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
      "=="                                                    { return VimTypes.EQEQ; }
      "==?"                                                   { return VimTypes.EQEQCI; }
      "==#"                                                   { return VimTypes.EQEQCS; }
      "!="                                                    { return VimTypes.NEQ; }
      "!=?"                                                   { return VimTypes.NEQCI; }
      "!=#"                                                   { return VimTypes.NEQCS; }
      ">"                                                     { return VimTypes.GT; }
      ">?"                                                    { return VimTypes.GTCI; }
      ">#"                                                    { return VimTypes.GTCS; }
      ">="                                                    { return VimTypes.GTEQ; }
      ">=?"                                                   { return VimTypes.GTEQCI; }
      ">=#"                                                   { return VimTypes.GTEQCS; }
      "<"                                                     { return VimTypes.LT; }
      "<?"        ￿                                            { return VimTypes.LTCI; }
      "<#"                                                    { return VimTypes.LTCS; }
      "<="                                                    { return VimTypes.LTEQ; }
      "<=?"                                                   { return VimTypes.LTEQCI; }
      "<=#"                                                   { return VimTypes.LTEQCS; }
      "=~"                                                    { return VimTypes.MATCH; }
      "=~?"                                                   { return VimTypes.MATCHCI; }
      "=~#"                                                   { return VimTypes.MATCHCS; }
      "!~"                                                    { return VimTypes.NOMATCH; }
      "!~?"                                                   { return VimTypes.NOMATCHCI; }
      "!~#"                                                   { return VimTypes.NOMATCHCS; }
      "is"                                                    { return VimTypes.IS; }
      "is?"                                                   { return VimTypes.ISCI; }
      "is#"                                                   { return VimTypes.ISCS; }
      "isnot"                                                 { return VimTypes.ISNOT; }
      "isnot?"                                                { return VimTypes.ISNOTCI; }
      "isnot#"                                                { return VimTypes.ISNOTCS; }
}

({CRLF}|{WHITE_SPACE})+                                     { return TokenType.WHITE_SPACE; }

[^]                                                         { return TokenType.BAD_CHARACTER; }
