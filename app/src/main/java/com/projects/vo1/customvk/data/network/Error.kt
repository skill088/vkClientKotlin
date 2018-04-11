package com.projects.vo1.customvk.data.network

class Error {

    class WrongGetException : RuntimeException() {
        override val message: String?
            get() = "Wrong GET parameters"
    }

    class OldTSException : RuntimeException() {
        override val message: String?
            get() = "Need to refresh timestamp"
        var ts: Long? = null
    }
    class ObsoletedKeyException : RuntimeException() {
        override val message: String?
            get() = "Need to refresh key"
    }
}