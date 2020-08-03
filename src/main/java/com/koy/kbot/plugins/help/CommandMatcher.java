package com.koy.kbot.plugins.help;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.koy.kbot.configuration.core.CommandContext;
import com.koy.kbot.exception.KBotException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/08/01
 */
@Component
public class CommandMatcher {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(3);

    // the default threshold of score, when the min distance < 3, there will have recommend command
    private static final int DEFAULT_THRESHOLD = 2;


    Collection<String> getRecommendCommand(String inputString) {

        // TODO: thought, if it can use CommandContext#CommandMap also
        final Multimap<Integer, String> matchResult = Multimaps.synchronizedListMultimap(ArrayListMultimap.create());

        Collection<String> recommend = new ArrayList<>();

        try {

            List<String> commands = CommandContext.getCommands();
            // it is not a good idea to use CyclicBarrier, so need reset the countDownLatch
            CountDownLatch countDownLatch = new CountDownLatch(commands.size());

            for (String cmd : commands) {
                executorService.execute(new Worker(cmd.toUpperCase(), inputString.toUpperCase(), countDownLatch, matchResult));
            }

            countDownLatch.await(10L, TimeUnit.SECONDS);
            List<Integer> scores = matchResult.keySet().stream().sorted().collect(Collectors.toList());

            if (scores.isEmpty() || scores.get(0) > DEFAULT_THRESHOLD) {
                return recommend;
            }

            return matchResult.get(scores.get(0));

        } catch (Exception e) {
            throw new KBotException("help cant get the help");
        }
    }

    /**
     * usring Levenshtein Distance (Edit Distance) to be the command match score.
     * <p>
     * Levenshtein Distance (BK tree) :
     * <p>
     * word1, word2
     * using 3 operations to make word1 = word2
     * 3 operations:
     * - replace
     * - insert
     * - remove
     * <p>
     * normally we will know that
     * d(x,y) = 0 <-> x = y
     * d(x,y) = d(y,x)
     * d(x,y) + d(y,z) >= d(x,z)
     * <p>
     * and we can know how to get the min edit distance
     * <p>
     * if word1[i] = word2[j] , that op[i][j] = op[i-1][j-1]
     * else op[i][j] = 1 + min(op[i][j-1], op[i-1][j], op[i-1][j-1])
     *
     * @param inputString input string
     * @param command     the command of the commands
     * @return the min distance
     */
    private static int minDistance(String inputString, String command) {


        int inputStringLength = inputString.length();
        int commandLength = command.length();
        int[][] dp = new int[inputStringLength + 1][commandLength + 1];

        // base case: command = ""
        for (int i = 0; i < inputStringLength + 1; i++) {
            dp[i][0] = i;
        }

        // base case: inputString = ""
        for (int i = 0; i < commandLength + 1; i++) {
            dp[0][i] = i;
        }

        // dp
        for (int i = 1; i < inputStringLength + 1; i++) {
            for (int j = 1; j < commandLength + 1; j++) {

                // the last char is the same
                if (inputString.charAt(i - 1) == command.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // find the previous operation
                    dp[i][j] = Math.min(dp[i][j - 1], Math.min(dp[i - 1][j], dp[i - 1][j - 1])) + 1;
                }
            }
        }

        return dp[inputStringLength][commandLength];
    }


    private class Worker implements Runnable {

        private String cmd;
        private String inputString;
        private CountDownLatch countDownLatch;
        private Multimap<Integer, String> matchResult;

        Worker(String cmd, String inputString, CountDownLatch countDownLatch, Multimap<Integer, String> mr) {
            this.cmd = cmd;
            this.inputString = inputString;
            this.countDownLatch = countDownLatch;
            matchResult = mr;
        }

        @Override
        public void run() {
            int score = minDistance(inputString, cmd);
            matchResult.put(score, cmd);
            countDownLatch.countDown();
        }
    }

}
