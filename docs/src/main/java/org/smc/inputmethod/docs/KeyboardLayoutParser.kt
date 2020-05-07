package org.smc.inputmethod.docs

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.StringReader

class KeyboardLayoutParser {
    private val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()

    @Throws(XmlPullParserException::class, IOException::class)
    fun readXML(xml: String?) {
        val xpp = factory.newPullParser()
        xpp.setInput(StringReader(xml))
        var eventType = xpp.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {
                println("Start document")
            } else if (eventType == XmlPullParser.START_TAG) {
                println("Start tag " + xpp.name)
            } else if (eventType == XmlPullParser.END_TAG) {
                println("End tag " + xpp.name)
            } else if (eventType == XmlPullParser.TEXT) {
                println("Text " + xpp.text)
            }
            eventType = xpp.next()
        }
    }

}