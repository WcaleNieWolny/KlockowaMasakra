package pl.wolny.klockowamasakratablist.hook

interface PluginHook<T> {
    fun isAvailable(): Boolean
    fun setup()
    fun provider(): T
}