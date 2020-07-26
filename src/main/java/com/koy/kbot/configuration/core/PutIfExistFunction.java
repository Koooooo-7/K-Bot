package com.koy.kbot.configuration.core;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/07/26
 */
@FunctionalInterface
public interface PutIfExistFunction<R, K, V1, V2> {
    /**
     * @param k  key
     * @param v1 the value already in the map
     * @param v2 the value wanna put in
     * @return
     */
    R apply(K k, V1 v1, V2 v2);
}
