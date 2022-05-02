package qqte

import java.util.*

class InfoCardDto {
    lateinit var value: String
    lateinit var name: String

    var refreshUrl: String? = null
    var contentId: String = UUID.randomUUID().toString()
    var icon: String = "nc-sound-wave"
}
