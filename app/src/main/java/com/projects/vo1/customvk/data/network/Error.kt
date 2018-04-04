package com.projects.vo1.customvk.data.network

import java.lang.Exception

/**
 * Created by Admin on 28.03.2018.
 */
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