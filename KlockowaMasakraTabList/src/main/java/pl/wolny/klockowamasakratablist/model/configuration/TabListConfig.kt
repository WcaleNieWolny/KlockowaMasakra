package pl.wolny.klockowamasakratablist.model.configuration

import net.dzikoysk.cdn.entity.Description

class TabListConfig {

    @Description("FakePlayer ping")
    var ping = 999
    @Description("Server brand")
    var serverBrand = "&a&lKLOCKOWA MASAKRA"
    @Description("Tablist footer")
    var tabListFooter = "&aTwoje saldo wynosi: #MONEY#"
}