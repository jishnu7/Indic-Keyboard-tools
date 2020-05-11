package org.smc.inputmethod.docs

import org.xmlpull.v1.XmlPullParser

class XPPWrapper(xpp: XmlPullParser) : XmlPullParser by xpp {
    fun getAttributes(): Map<String, String> {
        val attributes = HashMap<String, String>()
        for (x in 0 until this.attributeCount) {
            attributes[this.getAttributeName(x)] = this.getAttributeValue(x)
        }
        return attributes
    }
}