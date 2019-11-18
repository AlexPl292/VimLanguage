package dev.feedforward.vim.lang

import com.intellij.psi.PsiFileFactory
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import dev.feedforward.vim.lang.psi.VimCommand
import junit.framework.TestCase

class VimTest : BasePlatformTestCase() {
    fun `test parser`() {
        val elements = PsiFileFactory.getInstance(project).createFileFromText("x.vim", VimFileType, """
            ascii
            only
        """.trimIndent())
        val child = elements.firstChild
    }
}
