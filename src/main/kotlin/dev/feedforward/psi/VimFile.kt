package dev.feedforward.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import dev.feedforward.VimFileType
import dev.feedforward.VimLanguage

class VimFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, VimLanguage) {
    override fun getFileType(): FileType {
        return VimFileType
    }

    override fun toString(): String {
        return "Vim File"
    }
}
