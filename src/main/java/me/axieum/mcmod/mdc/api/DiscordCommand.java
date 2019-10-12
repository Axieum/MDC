package me.axieum.mcmod.mdc.api;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public interface DiscordCommand
{
    /**
     * Get command names to respond to.
     *
     * @return command names
     */
    List<String> getNames();

    /**
     * Check whether this command should provide a response.
     *
     * @return true if a Discord message should be returned
     */
    boolean isQuiet();

    /**
     * Authorise the given executor to execute this command.
     *
     * @param executor Discord member whom is executing
     * @param channel  Discord text channel command was issued from
     * @return true if the member can use this command
     */
    boolean isAuthorised(Member executor, TextChannel channel);

    /**
     * Handle execution of the command.
     *
     * @param executor Discord member whom is executing
     * @param channel  Discord text channel command was issued from
     * @param args     command arguments (i.e. split on space)
     */
    void execute(Member executor, TextChannel channel, List<String> args);
}
