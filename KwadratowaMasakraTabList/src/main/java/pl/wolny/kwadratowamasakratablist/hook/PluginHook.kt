package pl.wolny.kwadratowamasakratablist.hook

interface PluginHook<T> {
    fun isAvailable(): Boolean
    fun setup()
    fun provider(): T
}