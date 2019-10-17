package dev.feedforward

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class VimFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, VimLanguage) {
    override fun getFileType(): FileType {
        return VimFileType
    }

    override fun toString(): String {
        return "Vim File"
    }
}
