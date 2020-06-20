package com.koy.kbot.service;

import com.koy.kbot.plugins.IPlugin;
import com.koy.kbot.plugins.help.HelpPlugin;
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
    List<IPlugin> plugins;

    @Autowired
    HelpPlugin helpPlugin;


    @Override
    public void parser(String[] args) {


        // get command
        String command = args[1].toUpperCase();

        // send to matched plugins, if not, send to help plugin
        plugins
                .stream()
                .filter(p -> p.command().toUpperCase().equals(command))
                .findFirst()
                .orElseGet(() -> helpPlugin)
                .handle(args);

    }

}
