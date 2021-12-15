package me.onur.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.util.Collections;

public class Bot extends ListenerAdapter {

    public Bot() {}

    public static void main(String[] args) throws LoginException {
        String token = "token";
        //JDA jda = JDABuilder.createDefault(token).addEventListeners(new Bot()).build();
        JDABuilder.createLight(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(new Bot())
                .setActivity(Activity.streaming("openduct.net" ,"https://twitch.tv/o"))
                .build();
    }

    @Override
    public void onReady(ReadyEvent event) {
        System.out.println("Ready");

    }


    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        Message msg = event.getMessage();
        if (msg.getContentRaw().equals("ping"))
        {
            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            channel.sendMessage("Pong!") /* => RestAction<Message> */
                    .queue(response /* => Message */ -> {
                        response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
                    });
        }

    }
    @Override
    public void onGuildJoin(GuildJoinEvent event)
    {
        JDA api = event.getJDA();
        User user = api.getUserById("497674151251804160"); // Acquire a reference to the User instance through the id
        user.openPrivateChannel().queue((channel) ->
        {
            // Send a private message to the user
            channel.sendMessageFormat("New server: **%s**", event.getGuild().getName()).queue();
        });
    }

}