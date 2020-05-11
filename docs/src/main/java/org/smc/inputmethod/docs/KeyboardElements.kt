package org.smc.inputmethod.docs

interface KeyboardElement

data class Key(val keySpec: String?, val keyStyle: String?, val moreKeys: String?): KeyboardElement

class Row: KeyboardElement

class ParsedKeyboard {

    class ParsedRow {
        val keys = ArrayList<Key>()

        override fun toString(): String {
            return "ParsedRow(keys=$keys)"
        }
    }

    val rows = ArrayList<ParsedRow>()

    override fun toString(): String {
        return "ParsedKeyboard(rows=$rows)"
    }

}