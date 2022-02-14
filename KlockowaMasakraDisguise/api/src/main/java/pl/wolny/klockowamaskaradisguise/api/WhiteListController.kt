package pl.wolny.klockowamaskaradisguise.api

interface WhiteListController {
    fun isWhitelisted(name: String): Boolean
}