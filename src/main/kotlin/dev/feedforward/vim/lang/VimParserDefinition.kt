package dev.feedforward.vim.lang

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import dev.feedforward.vim.lang.parser.VimParser
import dev.feedforward.vim.lang.psi.VimExtraTokens
import dev.feedforward.vim.lang.psi.VimFile
import dev.feedforward.vim.lang.psi.VimTypes
import javax.swing.Icon

class VimParserDefinition : ParserDefinition {
    override fun createParser(project: Project?): PsiParser = VimParser()

    override fun createFile(viewProvider: FileViewProvider): PsiFile = VimFile(viewProvider)

    override fun getStringLiteralElements(): TokenSet = TokenSet.EMPTY

    override fun getFileNodeType(): IFileElementType = FILE

    override fun createLexer(project: Project?): Lexer = VimLexerAdapter()

    override fun createElement(node: ASTNode): PsiElement {
        return VimTypes.Factory.createElement(node)
    }

    override fun getCommentTokens(): TokenSet {
        return COMMENTS
    }

    override fun getWhitespaceTokens(): TokenSet {
        return WHITE_SPACES
    }

    companion object {
        val WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE, VimExtraTokens.IGNORABLE_WHITESPACE)
        val COMMENTS = TokenSet.create(VimTypes.FULL_LINE_COMMENT, VimTypes.COMMENT)

        val FILE = IFileElementType(VimLanguage)
    }
}

object VimFileType : LanguageFileType(VimLanguage) {
    override fun getIcon(): Icon? {
        return null
    }

    override fun getName(): String {
        return "Vim file"
    }

    override fun getDefaultExtension(): String {
        return "vim"
    }

    override fun getDescription(): String {
        return "Vim Language"
    }
}
