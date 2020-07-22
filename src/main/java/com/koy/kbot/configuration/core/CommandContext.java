package com.koy.kbot.configuration.core;

import com.koy.kbot.plugins.IPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiFunction;

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

    public void initPluginsCommand() {

        BiFunction<Object, Object, Object> duplicate = (v1, v2) -> {
            // throw exception directly
            //TODO: more clearly details about duplicate
            throw new IllegalArgumentException("can not have duplicate name of call or fast command !");
        };
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(Plugin.class);
        Assert.notEmpty(beans, "no plugins can be found !");

        beans.entrySet().stream().parallel().forEach(e -> {
            Object beanInstance = e.getValue();
            Plugin pluginAnnotation = beanInstance.getClass().getAnnotation(Plugin.class);
            String call = pluginAnnotation.call();
            String[] fastCommand = pluginAnnotation.fastCommand();
            Arrays.stream(fastCommand).parallel().forEach(el -> fastCommands.putIfExist(el.toUpperCase(), beanInstance, duplicate));
            calls.put(call.toUpperCase(), beanInstance);
        });

    }


    public static IPlugin getCallsIplugin(String callName) {
        return (IPlugin) calls.get(callName.toUpperCase());
    }

    public static IPlugin getFastComands(String fastCommand) {
        return (IPlugin) fastCommands.get(fastCommand.toUpperCase());
    }

    // if necessary to use skip list
    private static class CommandMap<K, V> extends ConcurrentSkipListMap<K, V> {
        // TODO: effective stuff,  thoughts: forkJoinPool, lite lock
        synchronized void putIfExist(K key, V value, BiFunction<? super V, ? super V, ? extends V> applyIfExist) {
            boolean containsKey = this.containsKey(key);
            if (!containsKey) {
                this.put(key, value);
                return;
            }

            V preValue = this.get(key);
            V newV = applyIfExist.apply(preValue, value);
            this.put(key, newV);
        }
    }
}
