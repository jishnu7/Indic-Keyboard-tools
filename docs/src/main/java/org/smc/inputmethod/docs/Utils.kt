package org.smc.inputmethod.docs

import java.nio.file.Files
import java.nio.file.Path

fun getFromPath(path: Path): String {
    return Files.readString(path)
}

fun getSiblingByName(name: String, child: Path): Path {
    return Path.of(child.parent.toString(), name)
}

fun getFileLinkFrom(attribute: String?): String? {
    val includeName = attribute?.split("/")?.getOrNull(1)
    return if (includeName != null && includeName.isNotEmpty()) {
        "$includeName.xml"
    } else {
        null
    }
}