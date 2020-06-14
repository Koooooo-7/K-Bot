package com.koy.kbot.common;

import com.koy.kbot.holder.GuildMessageReceivedEventHolder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/07
 */
@Component
public class MessageSender {

    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            2, 5, 0L, TimeUnit.NANOSECONDS,
            new ArrayBlockingQueue<>(2000),

            new ThreadFactory() {
                AtomicInteger counter = new AtomicInteger(0);

                @Override
                public Thread newThread(@NotNull Runnable r) {
                    String name = "messageSender thread number ---" + counter.incrementAndGet();
                    return new Thread(r, name);
                }
            });


    public void setString(MessageChannel channel, String content) {
        threadPoolExecutor.execute(new sendStringMsgWorker(content, channel));
    }

    ;

    public void setEmbed(MessageChannel channel, MessageEmbed content) {
        threadPoolExecutor.execute(new sendEmbedMsgWorker(content, channel));
    }

    ;

    private class sendStringMsgWorker implements Runnable {

        String content;
        MessageChannel channel;

        sendStringMsgWorker(String content, MessageChannel messageChannel) {
            this.content = content;
            this.channel = messageChannel;
        }

        @Override
        public void run() {
            channel.sendMessage(content).queue();
        }
    }

    private class sendEmbedMsgWorker implements Runnable {

        MessageEmbed content;
        MessageChannel channel;

        sendEmbedMsgWorker(MessageEmbed content, MessageChannel messageChannel) {
            this.content = content;
            this.channel = messageChannel;
        }

        @Override
        public void run() {
            channel.sendMessage(content).queue();
        }
    }
}
