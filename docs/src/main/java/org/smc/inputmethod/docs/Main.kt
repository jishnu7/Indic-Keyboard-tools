package org.smc.inputmethod.docs

import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

internal object Main {
    @Throws(XmlPullParserException::class, IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val xmlFilePath = args.getOrNull(0)
        val outputFilePath = args.getOrNull(1)
        if (xmlFilePath == null) {
            println("Please pass path to XML file to parse as first argument")
            println("Example: ./gradlew run --args=\"../../java/res/xml/kbd_malayalam_inscript.xml\"")
            throw IllegalArgumentException("Path to XML missing")
        }
        val keyboardLayoutParser = KeyboardLayoutParser()
        val keyboard = keyboardLayoutParser.readXML(xmlFilePath)
        val keyboardParsed = ElementSaver(keyboard).save()
        val keyboardJSON = convertToJSON(keyboardParsed)
        if (outputFilePath == null) {
            println(keyboardJSON)
            println("Pass output file path as second argument to write the above to it")
        } else {
            writeToFile(outputFilePath, keyboardJSON)
        }
    }
}