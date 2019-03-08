package com.zbin.test.netty;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by TDW on 2019/3/5.
 */
public class test {
    public static void main(String[] args) {

        BigDecimal decimal = new BigDecimal(1000000.2341213);
        BigDecimal decimal2 = BigDecimal.valueOf(1000000.2341213);
        System.out.println(decimal);
        System.out.println(decimal2);
    }
    /**
     * 交换
     * @param a
     * @param b
     */
    private static void sawp(Integer a, Integer b) {
        int temp = b;
    }

}
