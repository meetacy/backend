package app.meetacy.discord.webhook.embed

@JvmInline
value class DiscordEmbeds(val list: List<DiscordEmbed>) {
    init {
        require(list.size <= 10) { "Discord embeds size must be less than 10" }
    }
}
