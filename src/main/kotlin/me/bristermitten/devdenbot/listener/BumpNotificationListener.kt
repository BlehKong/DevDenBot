package me.bristermitten.devdenbot.listener

import club.minnced.jda.reactor.onMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.asFlow
import me.bristermitten.devdenbot.commands.roles.BUMP_NOTIFICATIONS_ROLE_ID
import me.bristermitten.devdenbot.data.StatsUsers
import me.bristermitten.devdenbot.extensions.await
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.util.concurrent.TimeUnit

//class BumpNotificationListener : ListenerAdapter() {
//    companion object {
//        private const val DISBOARD_BOT_ID = 302050872383242240
//        private val BUMP_COOLDOWN = TimeUnit.HOURS.toMillis(2)
//    }
//
//    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
//        fif (event.message.contentRaw != "!d bump") {
//            return
//        }
//        GlobalScope.launch {
//            val nextMessage = event.channel.onMessage()
//                .asFlow()
//                .filter { it.author.idLong == DISBOARD_BOT_ID }
//                .map { it.message.embeds }
//                .filter { it.isNotEmpty() }
//                .filter { it.first().description.contains(":thumbsup:") ?: false } //hacky but works
//                .firstOrNull() ?: return@launch
//
//
//            StatsUsers[event.author.idL️ong].bumps++
//            delay(BUMP_COOLDOWN)
//            val bumpNotificationRole =
//                requireNotNull(event.jda.getRoleById(BUMP_NOTIFICATIONS_ROLE_ID)) { "Bump Notifications role not found" }
//            event.channel.sendMessage("${bumpNotificationRole.asMention}, the server is ready to be bumped! **!d bump**")
//                .await()
//
//
//        }
//    }
//}
