package com.koy.kbot.configuration.core;

import com.koy.kbot.plugins.IPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Supplier;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/07/19
 */
@Component
public class CommandContext {

    @Autowired
    ApplicationContext applicationContext;


    // collect the calls
    private static final CommandMap<String, Object> calls = new CommandMap<>();
    // collect the fast commands
    private static final CommandMap<String, Object> fastCommands = new CommandMap<>();
    // collect all the commands
    private static final List<String> commands = new ArrayList<>();

    public void initPluginsCommand() {

        PutIfExistFunction<Object, String, Object, Object> duplicate = (k, v1, v2) -> {
            // throw exception directly
            Plugin pluginAnnotation = v1.getClass().getAnnotation(Plugin.class);
            String beanName = v1.getClass().getName();
            String pluginName = pluginAnnotation.name();
            String name = StringUtils.isEmpty(pluginName) ? beanName : pluginName;
            throw new IllegalArgumentException("can not have duplicate name of call or fast command ! the command "
                    + k + " already in plugin:" + name);
        };

        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(Plugin.class);
        Assert.notEmpty(beans, "no plugins can be found !");

        beans.entrySet().stream().parallel().forEach(e -> {
            Object beanInstance = e.getValue();
            Plugin pluginAnnotation = beanInstance.getClass().getAnnotation(Plugin.class);
            String call = pluginAnnotation.call();
            commands.add(call);
            String[] fastCommand = pluginAnnotation.fastCommand();
            Arrays.stream(fastCommand).parallel()
                    .forEach(cmd -> {
                                fastCommands.putIfExist(cmd.toUpperCase(), beanInstance, duplicate);
                                commands.add(cmd);
                            }
                    );
            calls.put(call.toUpperCase(), beanInstance);
        });

    }

    public static Supplier<IPlugin> matchedPlugin(String callName) {
        IPlugin plugin = getCallOnPlugin(callName);
        return () -> Objects.nonNull(plugin) ? plugin : getFastCommands(callName);
    }

    private static IPlugin getCallOnPlugin(String callName) {
        return (IPlugin) calls.get(callName.toUpperCase());
    }

    private static IPlugin getFastCommands(String fastCommand) {
        return (IPlugin) fastCommands.get(fastCommand.toUpperCase());
    }

    public static List<String> getCommands() {
        return commands;
    }

    // if necessary to use skip list
    private static class CommandMap<K, V> extends ConcurrentSkipListMap<K, V> {
        // TODO: effective stuff,  thoughts: forkJoinPool, lite lock
        synchronized void putIfExist(K key, V value, PutIfExistFunction<? extends V, K, V, V> applyIfExist) {
            boolean containsKey = this.containsKey(key);
            if (!containsKey) {
                this.put(key, value);
                return;
            }

            V preValue = this.get(key);
            V newV = applyIfExist.apply(key, preValue, value);

            this.put(key, newV);
        }
    }
}
