package pl.wolny.klockowamasakralifes.configuration

import net.dzikoysk.cdn.entity.Description

class PluginConfiguration {
    @Description("Number of lives that player recive when first join the server")
    var livesOnStart = 3

    @Description("Time of player ban (in minutes)")
    var banTime = 15

    @Description("Message shown on tablist when no live is available")
    var noLivesMessage = "&c&lUWAŻAJ! JAK ZGINIESZ TO DOSTANIESZ BANA!"

    @Description("Hearth symbol")
    var hearthSymbol = "❤"

    @Description("Hearth colour")
    var hearthColour = "&4"

    @Description("Kick message when no lives are available")
    @Description("This value uses MiniMessage (https://docs.adventure.kyori.net/minimessage/format)")
    @Description("Placeholder: #MINUTES# - shows ban minutes left")
    var deathKickMessage = "<gradient:#d7e02d:#e02d2d>Umarłeś! Nie możesz grać na serwerze! Odczekaj #MINUTES# minut!"

    @Description("Message displayed on tablist when more then 10 lives are available")
    @Description("Placeholder: #LIVES# - lives amount")
    @Description("Placeholder: #HEARTH_SYMBOL# - look at configuration above")
    @Description("Placeholder: #HEARTH_COLOUR# - look at configuration above")
    var livesOverflow = "&f#LIVES#&7x#HEARTH_COLOUR##HEARTH_SYMBOL#"

    @Description("Live potion colour (In rgb format)")
    var red = 227
    var green = 27
    var blue = 74

    @Description("Name for life potion")
    @Description("This value uses MiniMessage (https://docs.adventure.kyori.net/minimessage/format)")
    var potionName = "<#e31b4a>MIKSTURA ŻYCIA"

    @Description("Message send when you can not get life potion")
    @Description("This message uses MiniMessage")
    var canNotGetPotion = "<#e31b4a>Nie możesz zdobyć mikstury życia!"

    @Description("Message send when you successfully get life potion")
    @Description("This message uses MiniMessage")
    var potionRecived = "<green>Dodano miksturę życia!"

}