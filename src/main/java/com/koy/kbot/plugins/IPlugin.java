package com.koy.kbot.plugins;

import java.io.IOException;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/06
 */
public interface IPlugin {

    void handle(String[] args) throws IOException;
}
