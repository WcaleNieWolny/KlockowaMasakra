package pl.wolny.klockowamasakratablist.model.configuration

import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.annotation.Comment
import eu.okaeri.configs.annotation.NameModifier
import eu.okaeri.configs.annotation.NameStrategy
import eu.okaeri.configs.annotation.Names

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
class TabListConfig: OkaeriConfig() {

    @Comment("FakePlayer ping")
    var ping = 999
    @Comment("Server brand")
    var serverBrand = "&a&lKLOCKOWA MASAKRA"
    @Comment("Tablist footer")
    var tabListFooter = "&aTwoje saldo wynosi: #MONEY#"
}