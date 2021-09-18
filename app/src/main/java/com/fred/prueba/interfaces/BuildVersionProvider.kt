package com.fred.prueba.interfaces

interface BuildVersionProvider {
    fun isMarshmallowAndAbove():Boolean
    fun isLollipopAndAbove():Boolean
    fun isAndroidRAndAbove():Boolean
    fun isAndroidNAndAbove():Boolean
}