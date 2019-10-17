package dev.feedforward

import com.intellij.lexer.Lexer
import com.intellij.testFramework.LexerTestCase
import java.io.File

class VimLexerTest : LexerTestCase() {
    override fun getDirPath(): String = this.javaClass.classLoader.getResource("testData")?.path
            ?: throw RuntimeException("Test data missing")

    override fun createLexer(): Lexer = VimLexerAdapter()

    override fun getPathToTestDataFile(extension: String?): String {
        return dirPath + File.separator + "lexer" + File.separator + getTestName(true) + extension
    }

    fun testStringCaseOne() = doTest()

    private fun doTest(): Unit = doFileTest("vim")
}
