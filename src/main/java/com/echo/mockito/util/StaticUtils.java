package com.echo.mockito.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author echo
 * @version 1.0
 * @date 2022/3/2 7:21 PM
 */
public class StaticUtils {

    private StaticUtils() {}

    //返回指定区间的 Integer List
    public static List<Integer> range(int start, int end) {
        return IntStream.range(start, end).boxed().collect(Collectors.toList());
    }

    //返回 Echo 字符串
    public static String name() {
        return "Echo";
    }
}
