package com.justinkirkey.pushups

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform