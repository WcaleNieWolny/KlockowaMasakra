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

    @Description("Hearth colour minimessage")
    var hearthColourMiniMessage = "<dark_red>"

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

    @Description("Message send when you can not use a command")
    @Description("This message uses MiniMessage")
    var canNotUseThisCommand = "<#e31b4a>Nie możesz użyć tej komendy!"

    @Description("Message send when you use /lives command")
    @Description("This message uses MiniMessage")
    @Description("Placeholder: #LIVES# - number of player lifes")
    @Description("Placeholder: #HEARTH_SYMBOL# - look at configuration above")
    @Description("Placeholder: #HEARTH_COLOUR_MINIMESSAGE# - look at configuration above")
    var livesCommand = "<green>Posiadasz: <white>#LIVES#<gray>x#HEARTH_COLOUR_MINIMESSAGE##HEARTH_SYMBOL#"

    @Description("Message send when you successfully use /livepardon PLAYER command")
    @Description("This message uses MiniMessage")
    @Description("Placeholder: #PLAYER# - player name")
    var pardonCommand = "<green>Przebaczasz #PLAYER#!"

    @Description("Message send when you successfully use /livepardon * command")
    @Description("This message uses MiniMessage")
    var pardonAllCommand = "<green> Ożywiasz wszystkich zmarłych graczy na serwerze!"

    @Description("Message send when a player is not live banned")
    @Description("This message uses MiniMessage")
    var pardonNoPlayerFound = "<#e31b4a> Podany gracz nie jest zbanowany za brak żyć!"

    @Description("Message send when you use a command with invalid arguments")
    @Description("This message uses MiniMessage")
    @Description("Placeholder: #USAGE# - command usage")
    var invalidUsage = "<#e31b4a> Podano złe argumenty! Poprawne użycie: #USAGE#"

    @Description("Message send when you successfully use /givelives PLAYER AMMOUNT command")
    @Description("This message uses MiniMessage")
    @Description("Placeholder: #PLAYER# - player name")
    @Description("Placeholder: #AMMOUNT# - player name")
    var giveLivesCommand = "<green>Dodajesz #AMMOUNT# żyć dla #PLAYER#!"

    @Description("Message send when you use /givelives on offline player")
    @Description("This message uses MiniMessage")
    var playerNotOnline = "<#e31b4a> Ten gracz nie jest online!"
}