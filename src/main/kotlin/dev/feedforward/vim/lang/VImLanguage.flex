package dev.feedforward.vim.lang;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import dev.feedforward.vim.lang.psi.VimTypes;
import com.intellij.psi.TokenType;
import java.util.ArrayDeque;
import java.util.Queue;
import dev.feedforward.vim.lang.VimLexerUtils;

%%

%public
%class VimLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%public
%{
    private final Queue<Integer> queue = new ArrayDeque<>();

    private void trace(int... states) {
      for (int state: states) {
          queue.add(state);
      }
      yybegin(queue.poll());
    }

    private void continueQueue() {
      if (queue.isEmpty()) {
        yybegin(YYINITIAL);
      } else {
        yybegin(queue.poll());
      }
    }
%}

END_OF_LINE_COMMENT=("#")[^\r\n]*

CRLF=\R
WHITE_SPACE=[\ \n\t\f]

STRING_LITERAL=\"(\\.|[^\\\"])*\"
SINGLE_QUOTED_STRING_LITERAL=\'(\\.|[^\\\'])*\'

HEX_NUMBER = "0" [xX] [0-9a-fA-F]+
INT_NUMBER = [:digit:]+
FLOAT_NUMBER = [:digit:]+\.[:digit:]+
SCIENTIFIC_NUMBER = [:digit:]+\.[:digit:]+[eE]([+-]?[:digit:]+)

WRITABLE_REGISTERS = [0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ\-\*\+\_\/\"]

QUOTE_WITH_ANYTHING = \'.

WORD = [:letter:]+

%state C_BANG C_ARGUMENT C_REGISTER C_COUNT
%xstate COMMON_COMMAND_ARGUMENT

%%

<YYINITIAL> {END_OF_LINE_COMMENT}                           { yybegin(YYINITIAL); return VimTypes.COMMENT; }

<YYINITIAL> {
      "ec"|"ech"|"echo"                                       { return VimTypes.C_ECHO; }
      "comc"|"comcl"|"comcle"|"comclea"|"comclear"            { return VimTypes.C_COMCLEAR; }
      "q"|"qu"|"qui"|"quit"                                   { trace(C_BANG); return VimTypes.C_QUIT; }
      "co"|"cop"|"copy"                                       { return VimTypes.C_COPY; }
      "com"|"comm"|"comma"|"comman"|"command"                 {
              trace(C_BANG, C_ARGUMENT);
              return VimTypes.C_COMMAND;
            }
      "delc"|"delco"|"delcom"|"delcomm"|"delcomma"|"delcomman"|"delcommand" { return VimTypes.C_DELCOMMAND; }
      "d"|"de"|"del"|"dele"|"delet"|"delete"                  {
        trace(C_REGISTER, C_COUNT);
        return VimTypes.C_DELETE;
      }

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
      "!"                                                     { return VimTypes.BANG; }
      "$"                                                     { return VimTypes.DOLLAR; }
      "'"                                                     { return VimTypes.SINGLE_QUOTE; }
      ","                                                     { return VimTypes.COMMA; }
      ";"                                                     { return VimTypes.SEMICOLON; }

      ":"                                                     { return VimTypes.COLON; }
      "["                                                     { return VimTypes.SQOPEN; }
      "]"                                                     { return VimTypes.SQCLOSE; }
      "("                                                     { return VimTypes.POPEN; }
      ")"                                                     { return VimTypes.PCLOSE; }
      "?"                                                     { return VimTypes.QUESTION; }

      \\&                                                     { return VimTypes.ESCAPED_AMPERSAND; }
      \\\?                                                    { return VimTypes.ESCAPED_QUESTION; }
      \\\/                                                    { return VimTypes.ESCAPED_SLASH; }

      {HEX_NUMBER}                                            { return VimTypes.HEX_NUMBER; }
      {INT_NUMBER}                                            { return VimTypes.INT_NUMBER; }
      {FLOAT_NUMBER}                                          { return VimTypes.FLOAT_NUMBER; }
      {SCIENTIFIC_NUMBER}                                     { return VimTypes.SCIENTIFIC_NUMBER; }

      {WORD}                                                  {
        final int command = VimLexerUtils.isCommand(yytext());
        if (command < 0) {
          return VimTypes.WORD;
        }

        if (command == VimLexerUtils.WAIT_FOR_ARGUMENT) {
          yybegin(COMMON_COMMAND_ARGUMENT);
        }
        return VimTypes.COMMON_COMMAND_NAME;
      }

      {QUOTE_WITH_ANYTHING}                                   { return VimTypes.QUOTE_WITH_ANYTHING; }
}

<COMMON_COMMAND_ARGUMENT> {
    [^\r\n]+                                                  { yybegin(YYINITIAL); return VimTypes.COMMON_COMMAND_ARGUMENT; }
    [\r\n]                                                    { yypushback(1); yybegin(YYINITIAL); }
}

<C_BANG> {
    "!"                                                         { continueQueue(); return VimTypes.BANG; }
    [^!]                                                        { yypushback(1); continueQueue(); }
}

<C_ARGUMENT> {
    [^\R]+                                                      { return VimTypes.COMMAND_ARGUMENT; }
    ({CRLF}|{WHITE_SPACE})+                                     { continueQueue(); return TokenType.WHITE_SPACE; }
}

<C_REGISTER> {
      {WRITABLE_REGISTERS}                                    { continueQueue(); return VimTypes.WRITABLE_REGISTER; }
      ({CRLF}|{WHITE_SPACE})+                                 { return TokenType.WHITE_SPACE; }
      [^]                                                     { yypushback(1); continueQueue(); }
}

<C_COUNT> {
    {INT_NUMBER}                                              { continueQueue(); return VimTypes.INT_NUMBER; }
    ({CRLF}|{WHITE_SPACE})+                                   { return TokenType.WHITE_SPACE; }
    [^]                                                       { yypushback(1); continueQueue(); }
}

({CRLF}|{WHITE_SPACE})+                                     { return TokenType.WHITE_SPACE; }

[^]                                                         { return TokenType.BAD_CHARACTER; }
