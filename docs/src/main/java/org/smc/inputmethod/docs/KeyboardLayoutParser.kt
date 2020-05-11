package org.smc.inputmethod.docs

import mu.KotlinLogging
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.StringReader
import java.nio.file.Path

private val logger = KotlinLogging.logger {}

class KeyboardLayoutParser {
    private val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
    private lateinit var parentPath: Path
    private val debug = false

    @Throws(XmlPullParserException::class, IOException::class)
    fun readXML(xmlPath: Path): Sequence<KeyboardElement> = sequence {
        saveAsParentIfNecessary(xmlPath)
        val xpp = prepareXpp(xmlPath)
        yieldAll(loopRecursivelyThrough(xpp))
    }

    private fun saveAsParentIfNecessary(xmlPath: Path) {
        if (!this::parentPath.isInitialized) {
            parentPath = xmlPath
        }
    }

    private fun prepareXpp(xmlPath: Path): XPPWrapper {
        val xml = getFromPath(xmlPath)
        val xpp = XPPWrapper(factory.newPullParser())
        xpp.setInput(StringReader(xml))
        return xpp
    }

    private fun loopRecursivelyThrough(xpp: XPPWrapper): Sequence<KeyboardElement> = sequence {
        var eventType = xpp.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_DOCUMENT -> {
                    // document starts
                }
                XmlPullParser.START_TAG -> yieldAll(processTag(xpp))
                XmlPullParser.END_TAG -> {
                    // xpp.name tag ends
                }
                XmlPullParser.TEXT -> {
                    // xpp.text shows the text
                }
            }
            eventType = xpp.next()
        }
    }

    private fun processTag(xpp: XPPWrapper): Sequence<KeyboardElement> = sequence {
        when (xpp.name) {
            "include" -> yieldAll(include(xpp))
            "Key" -> yield(newKey(xpp))
            "Row" -> yield(newRow(xpp))
        }
    }

    private fun include(xpp: XPPWrapper): Sequence<KeyboardElement> = sequence {
        val fileLink = xpp.getAttributes()["latin:keyboardLayout"]
        val includeName = getFileLinkFrom(fileLink)
        includeName?.let { yieldAll(readSiblingXML(it)) }
    }

    private fun readSiblingXML(path: String): Sequence<KeyboardElement> {
        return readXML(getSiblingByName(path, parentPath))
    }

    private fun newKey(xpp: XPPWrapper): Key {
        val rawKeySpec: String? = xpp.getAttributeValue("", "latin:keySpec")
        val keySpec = rawKeySpec ?: ""
        logger.debug { "New Key: $keySpec" }
        logger.debug { xpp.getAttributes() }
        return Key(keySpec)
    }

    private fun newRow(xpp: XPPWrapper): Row {
        logger.debug { "New Row" }
        return Row()
    }
}