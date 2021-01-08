package com.koy.kbot.configuration.core;

import com.google.common.collect.Collections2;
import com.koy.kbot.listener.IListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

/**
 * @Description wrapper JDABuilder for the listeners register as list
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/21
 */
@SuppressWarnings("deprecation")
public class JDABuilderWrapper extends JDABuilder {

    private JDABuilderWrapper(@Nullable String token, int intents) {
        this.token = token;
        this.intents = 1 | intents;
    }

    private JDABuilderWrapper applyDefault() {
        return (JDABuilderWrapper) this.setMemberCachePolicy(MemberCachePolicy.DEFAULT)
                .setChunkingFilter(ChunkingFilter.NONE)
                .disableCache(CacheFlag.CLIENT_STATUS, CacheFlag.ACTIVITY)
                .setLargeThreshold(250);
    }

    @Nonnull
    @CheckReturnValue
    public static JDABuilderWrapper createDefault(@Nullable String token) {
        return new JDABuilderWrapper(token, GatewayIntent.DEFAULT).applyDefault();
    }

    @Nonnull
    public JDABuilder addEventListeners(@Nonnull Collection<IListener> listeners) {
        net.dv8tion.jda.internal.utils.Checks.noneNull(listeners, "listeners");
        this.listeners.addAll(listeners);
        return this;
    }
}
