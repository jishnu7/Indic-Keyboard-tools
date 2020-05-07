package org.smc.inputmethod.docs

import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.nio.file.Path

internal object Main {
    @Throws(XmlPullParserException::class, IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val xmlFilePath = Path.of(args[0])
        val keyboardLayoutParser = KeyboardLayoutParser()
        keyboardLayoutParser.readXML(xmlFilePath)
    }
}