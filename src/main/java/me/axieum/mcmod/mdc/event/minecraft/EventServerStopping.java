package me.axieum.mcmod.mdc.event.minecraft;

import me.axieum.mcmod.mdc.Config;
import me.axieum.mcmod.mdc.DiscordClient;
import me.axieum.mcmod.mdc.util.DiscordUtils;
import me.axieum.mcmod.mdc.util.MessageFormatter;
import me.axieum.mcmod.mdc.util.ServerUtils;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

@Mod.EventBusSubscriber
public class EventServerStopping
{
    @SubscribeEvent
    public static void onServerStopping(FMLServerStoppingEvent event)
    {
        final DiscordClient discord = DiscordClient.getInstance();

        // Set the Bot status
        discord.setBotStatus(Config.BOT_STATUS_STOPPING.get());

        // Prepare formatter
        final MessageFormatter formatter = new MessageFormatter()
                .addDateTime("DATETIME")
                .addDuration("UPTIME", ServerUtils.getUptime());

        // Dispatch structured message
        DiscordUtils.sendMessagesFromMinecraft(formatter, "stopping", null);
    }
}
