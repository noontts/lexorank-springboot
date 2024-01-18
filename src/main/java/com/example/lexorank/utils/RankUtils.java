package com.example.lexorank.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class RankUtils {
    public static String convertBase10to36(long decimal) {
        return Long.toString(decimal, 36);
    }

    private static long convertBase36to10(String base36) {
        return Long.parseLong(base36, 36);
    }

    public static ArrayList<String> generatedRank(long maxSize, long rankSizeRequest) {
        long rankSize = rankSizeRequest + 1;
        long rankingStep = maxSize / rankSize;
        long current = 0;

        ArrayList<String> ranks = new ArrayList<>();

        for (long i = 0; i < rankSize; i++) {
            current += rankingStep;
            System.out.println("Current " + i + " : " + current);
            String value = "0|" + convertBase10to36(current);
            ranks.add(value);
        }

        System.out.println("Generated Ranks: " + ranks);
        return ranks;
    }

    public static String findBetween(String firstValueReq, String secondValueReq) {

        long firstValue = convertBase36to10(removePrefix(firstValueReq, "0|"));
        long secondValue = convertBase36to10(removePrefix(secondValueReq, "0|"));

        if (firstValue > secondValue) {
            throw new RuntimeException("Request rank isn't correct.");
        }

        long difference = secondValue - firstValue;

        if (difference < 2) {
            System.out.println("No Space Between");
            log.info("No Space Between");
            return "Trigger ReBalancing";
        } else {
            long middleRank = firstValue + difference / 2;
            System.out.println("MiddleRank : " + convertBase10to36(middleRank));
            return "0|" + convertBase10to36(middleRank);
        }
    }

    private static String removePrefix(String input, String prefix) {
        if (input.startsWith(prefix)) {
            return input.substring(prefix.length());
        } else {
            return input;
        }
    }

}
