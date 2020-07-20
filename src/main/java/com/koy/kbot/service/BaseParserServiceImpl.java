package com.koy.kbot.service;

import com.koy.kbot.configuration.core.CommandContext;
import com.koy.kbot.configuration.core.Plugin;
import com.koy.kbot.plugins.IPlugin;
import com.koy.kbot.plugins.help.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/06
 */
@Component
public class BaseParserServiceImpl implements IParserService {


    @Autowired
    private List<IPlugin> plugins;

    @Autowired
    private Helper helper;


    @Override
    public void parser(String[] args) {


        // get command
        String command = args[1].toUpperCase();

        // send to matched plugins, if not, send to help plugin
        IPlugin plugin = CommandContext.getCallsIplugin(command);
        if (plugin != null){
            plugin.handle(args);
        }

        plugin = CommandContext.getFastComands(command);

        if (plugin != null){
            plugin.handle(args);
            return;
        }

        // Backward compatibility, remove it soon, just give the command to helper by default.
        plugins
                .stream()
                .filter(p -> p.command().toUpperCase().equals(command))
                .findFirst()
                .orElseGet(() -> helper)
                .handle(args);



    }

}
