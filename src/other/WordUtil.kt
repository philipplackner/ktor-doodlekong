package com.plcoding.other

import com.plcoding.other.Constants.USE_LOCALHOST
import com.plcoding.other.Constants.WORDLIST_PATH_LOCALHOST
import com.plcoding.other.Constants.WORDLIST_PATH_UBUNTU
import java.io.File

val words = readWordList(
    if(USE_LOCALHOST) WORDLIST_PATH_LOCALHOST
    else WORDLIST_PATH_UBUNTU
)

fun readWordList(fileName: String): List<String> {
    val inputStream = File(fileName).inputStream()
    val words = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { words.add(it) }
    return words
}

fun getRandomWords(amount: Int): List<String> {
    var curAmount = 0
    val result = mutableListOf<String>()
    while(curAmount < amount) {
        val word = words.random()
        if(!result.contains(word)) {
            result.add(word)
            curAmount++
        }
    }
    return result
}

fun String.transformToUnderscores() =
    toCharArray().map {
        if(it != ' ') '_' else ' '
    }.joinToString(" ")