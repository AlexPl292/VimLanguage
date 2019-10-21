package dev.feedforward.vim.lang;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import dev.feedforward.vim.lang.psi.VimTypes;
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
SINGLE_QUOTED_STRING_LITERAL=\'(\\.|[^\\\'])*\'

HEX_NUMBER = "0" [xX] [0-9a-fA-F]+
INT_NUMBER = [:digit:]+
FLOAT_NUMBER = [:digit:]+\.[:digit:]+
SCIENTIFIC_NUMBER = [:digit:]+\.[:digit:]+[eE]([+-]?[:digit:]+)

IDENTIFIER = [[:jletterdigit:]_]+

%%

<YYINITIAL> {END_OF_LINE_COMMENT}                           { yybegin(YYINITIAL); return VimTypes.COMMENT; }

<YYINITIAL> {
      "ec"|"ech"|"echo"                                       { return VimTypes.ECHO; }
      "as"|"asc"|"asci"|"ascii"                               { return VimTypes.ASCII; }
      "comc"|"comcl"|"comcle"|"comclea"|"comclear"            { return VimTypes.COMCLEAR; }

      {STRING_LITERAL}                                        { return VimTypes.STRING_LITERAL; }
      {SINGLE_QUOTED_STRING_LITERAL}                          { return VimTypes.STRING_LITERAL; }

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
      "<?"                                                    { return VimTypes.LTCI; }
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
      "+"                                                     { return VimTypes.PLUS; }
      "-"                                                     { return VimTypes.MINUS; }
      "."                                                     { return VimTypes.DOT; }
      "*"                                                     { return VimTypes.STAR; }
      "/"                                                     { return VimTypes.SLASH; }
      "%"                                                     { return VimTypes.PERCENT; }
      "!"                                                     { return VimTypes.NOT; }

      ":"                                                     { return VimTypes.COLON; }
      "["                                                     { return VimTypes.SQOPEN; }
      "]"                                                     { return VimTypes.SQCLOSE; }
      "("                                                     { return VimTypes.POPEN; }
      ")"                                                     { return VimTypes.PCLOSE; }
      "?"                                                     { return VimTypes.QUESTION; }

      {HEX_NUMBER}                                            { return VimTypes.HEX_NUMBER; }
      {INT_NUMBER}                                            { return VimTypes.INT_NUMBER; }
      {FLOAT_NUMBER}                                          { return VimTypes.FLOAT_NUMBER; }
      {SCIENTIFIC_NUMBER}                                     { return VimTypes.SCIENTIFIC_NUMBER; }

      {IDENTIFIER}                                            { return VimTypes.IDENTIFIER; }
}

({CRLF}|{WHITE_SPACE})+                                     { return TokenType.WHITE_SPACE; }

[^]                                                         { return TokenType.BAD_CHARACTER; }
