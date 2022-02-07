package pl.wolny.kwadratowamasakraskull.configuatuion

import net.dzikoysk.cdn.entity.Description

class PluginConfig {
    var skullPenalty = 100
    var skullReward = 100
    @Description("Skull duration (In hours)")
    var skullTime = 24
    @Description("Executed when player balance is bellow minimum balance")
    var minimumBalanceCommand = "tempban #USER# 12h You are to poor to kill other players!"
    @Description("Message sent when player kills guilty player")
    var guiltyMessage = "&aGracz &f#USER-1# &azabija winnego gracza &f#USER-2#&a. &aotrzymując &f#REWARD#&a$"
    @Description("Message sent when player kills non guilty player")
    var nonGuiltyMessage = "&cGracz &f#USER-1# &czabija niewinnego gracza &f#USER-2#&c. &cZabójca traci &f#PENALTY#&c$"
}