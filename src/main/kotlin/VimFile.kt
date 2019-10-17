package dev.feedforward

import javax.swing.Icon
import com.intellij.psi.FileViewProvider
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType

class VimFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, VimLanguage) {
    override fun getFileType(): FileType {
        return VimFileType
    }

    override fun toString(): String {
        return "VIm File"
    }

    override fun getIcon(flags: Int): Icon? {
        return super.getIcon(flags)
    }
}
