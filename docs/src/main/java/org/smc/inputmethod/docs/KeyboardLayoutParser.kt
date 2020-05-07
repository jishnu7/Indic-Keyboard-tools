package org.smc.inputmethod.docs

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.StringReader
import java.nio.file.Path

class KeyboardLayoutParser {
    private val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
    private lateinit var parentPath: Path

    @Throws(XmlPullParserException::class, IOException::class)
    fun readXML(xmlPath: Path) {
        if (!this::parentPath.isInitialized) {
            parentPath = xmlPath
        }
        val xml = getFromPath(xmlPath)
        val xpp = factory.newPullParser()
        xpp.setInput(StringReader(xml))
        var eventType = xpp.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_DOCUMENT -> println("Start document")
                XmlPullParser.START_TAG -> processTag(xpp)
                XmlPullParser.END_TAG -> println("End tag ${xpp.name}")
                XmlPullParser.TEXT -> println("Text ${xpp.text}")
            }
            eventType = xpp.next()
        }
    }
    private fun processTag(xpp: XmlPullParser) {
        println("Start tag ${xpp.name}")
        var fileLink = ""
        if (xpp.attributeCount > 0) {
            for (x in 0 until xpp.attributeCount) {
                println("${xpp.getAttributeName(x)}: ${xpp.getAttributeValue(x)}")
                if (xpp.getAttributeName(x) == "latin:keyboardLayout") {
                    fileLink = xpp.getAttributeValue(x)
                    assert(fileLink == xpp.getAttributeValue("", "latin:keyboardLayout"))
                }
            }
        }
        if (xpp.name == "include") {
            val includename = "${fileLink.split("/")[1]}.xml"
            readXML(getSiblingByName(includename, parentPath))
        }
    }
}