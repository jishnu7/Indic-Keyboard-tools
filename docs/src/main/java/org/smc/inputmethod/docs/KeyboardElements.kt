package org.smc.inputmethod.docs

interface KeyboardElement

data class Key(val key: String): KeyboardElement

class Row: KeyboardElement