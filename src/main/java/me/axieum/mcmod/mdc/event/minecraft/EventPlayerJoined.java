package me.axieum.mcmod.mdc.event.minecraft;

import me.axieum.mcmod.mdc.Config;
import me.axieum.mcmod.mdc.DiscordClient;
import me.axieum.mcmod.mdc.api.ChannelsConfig.ChannelConfig;
import me.axieum.mcmod.mdc.util.MessageFormatter;
import me.axieum.mcmod.mdc.util.PlayerUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventPlayerJoined
{
    @SubscribeEvent
    public static void onPlayerJoined(PlayerEvent.PlayerLoggedInEvent event)
    {
        // Fetch useful event information
        final PlayerEntity player = event.getPlayer();

        final String name = player.getName().getFormattedText();
        final double x = player.prevPosX, y = player.prevPosY, z = player.prevPosZ;
        final String dimension = PlayerUtils.getDimensionName(player);

        // Prepare formatter
        final MessageFormatter formatter = new MessageFormatter()
                .withDateTime("DATE")
                .add("PLAYER", name)
                .add("DIMENSION", dimension)
                .add("X", String.valueOf((int) x))
                .add("Y", String.valueOf((int) y))
                .add("Z", String.valueOf((int) z));

        // Format and send messages
        final DiscordClient discord = DiscordClient.getInstance();
        for (ChannelConfig channel : Config.getChannels()) {
            // Fetch the message format
            String message = channel.getMessages().join;
            if (message == null || message.isEmpty()) continue;

            // Send message
            discord.sendMessage(formatter.format(message), channel.id);
        }

        // Cache their login time (NB: for computing play time)
        PlayerUtils.loginTimes.put(player, System.currentTimeMillis());
    }
}