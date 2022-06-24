package com.ltbth.singleobservable

class Note(val id: Int, val note: String) {
    override fun toString(): String {
        return "${id}_${note}"
    }
}