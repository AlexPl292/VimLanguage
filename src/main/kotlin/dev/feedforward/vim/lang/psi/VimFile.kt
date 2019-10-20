package dev.feedforward.vim.lang.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import dev.feedforward.vim.lang.VimFileType
import dev.feedforward.vim.lang.VimLanguage

class VimFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, VimLanguage) {
    override fun getFileType(): FileType = VimFileType

    override fun toString(): String ="Vim File"
}
