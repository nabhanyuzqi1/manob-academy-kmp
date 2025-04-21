package com.mnb.manobacademy

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform