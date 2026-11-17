package com.example.appkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform