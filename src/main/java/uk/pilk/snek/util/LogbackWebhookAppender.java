package uk.pilk.snek.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.Encoder;
import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogbackWebhookAppender extends AppenderBase<ILoggingEvent> {

    @Getter
    private String webhookUrl;
    @Getter
    @Setter
    private Encoder<ILoggingEvent> encoder;

    private WebhookClient client;

    private static final int PADDING_LEN = "```ansi\n```".length();
    private static final Pattern filter = Pattern.compile("""
            ^\\s+at (java\\.base/)?\
            (org\\.springframework\\.aop\\.\
            |java\\.lang\\.reflect\\.Method\\.\
            |jdk\\.internal\\.reflect\\.\
            |org\\.springframework\\.beans\\.factory\\.)\
            .*+$""");
    private static final Pattern discordURLPattern = Pattern.compile("""
             \\bhttps://discord\\.com/channels/\\d+/\\d+/\\d+\\b\
             """);
    private static final Pattern discordMemberPattern = Pattern.compile("<@\\d+>");

    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
        if (webhookUrl == null)
            return;
        // Using the builder
        client = WebhookClient.withUrl(webhookUrl);
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        if (webhookUrl == null || webhookUrl.isEmpty() || client == null) {
            return;
        }
        if (eventObject.getLoggerName().equalsIgnoreCase("club.minnced.discord.webhook.WebhookClient")){
            return;
        }
        try {

            byte[] bytes = encoder.encode(eventObject);
            String data =  filter(new String(bytes));
            if (data.length() > (4096 - PADDING_LEN)){
                for (String split : splitMessage(data)){
                    sendHook(eventObject, split);
                }
            } else {
                sendHook(eventObject, "```ansi\n" + data + "```");
            }
        } catch (Exception e) {
            //emergency "logging" that won't try to send its own error to discord
            e.printStackTrace();
        }
    }

    private static String filter(String input){
        StringBuilder out = new StringBuilder();
        for (String line : input.split("\n")){
            Matcher matcher = filter.matcher(line);
            if (!matcher.matches()){
                out.append(line).append("\n");
            }
        }
        return out.toString();
    }

    private void sendHook(ILoggingEvent eventObject, String message){
        WebhookEmbed embed = new WebhookEmbed(OffsetDateTime.from(Instant.ofEpochMilli(eventObject.getTimeStamp()).atOffset(ZoneOffset.from(ZonedDateTime.now().getOffset()))),
                getColourForLevel(eventObject.getLevel().toInteger()),
                message,
                null,
                null,
                null,
                new WebhookEmbed.EmbedTitle(eventObject.getLevel().toString() + " at " + eventObject.getLoggerName(), null),
                null,
                List.of()
        );
        var builder = new WebhookMessageBuilder();
        builder.addEmbeds(embed);
        StringBuilder content = new StringBuilder();
        Matcher m = discordURLPattern.matcher(message);
        if (m.find()){
            content.append(m.group());
        }
        m = discordMemberPattern.matcher(message);
        if (m.find()){
            content.append("\n");
            content.append(m.group());
        }
        builder.setContent(content.toString());
        client.send(builder.build()).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });
    }

    private int getColourForLevel(int level) {
        return switch (level) {
            case 40000 -> 0xff0000; //ERROR
            case 30000 -> 0xd530d0; //WARN
            case 20000 -> 0x00ff00; //INFO
            case 10000 -> 0x0044ff; //DEBUG
            case 5000  -> 0x505050; //TRACE
            default -> 0xffffff;
        };
    }

    private static ArrayList<String> splitMessage(String stringtoSend) {
        ArrayList<String> msgs = new ArrayList<>();
        int target = 4096 - PADDING_LEN;
        if (stringtoSend != null) {
            while (stringtoSend.length() > target) {
                int leeway = target - (stringtoSend.length() % target);
                int index = stringtoSend.lastIndexOf("\n", target);
                if (index < leeway)
                    index = stringtoSend.lastIndexOf(" ", target);
                if (index < leeway)
                    index = target;
                String temp = stringtoSend.substring(0, index).trim();
                if (!temp.isEmpty())
                    msgs.add("```ansi\n" + temp + "```");
                stringtoSend = stringtoSend.substring(index).trim();
            }
            if (!stringtoSend.isEmpty())
                msgs.add("```ansi\n" + stringtoSend + "```");
        }
        return msgs;
    }
}
