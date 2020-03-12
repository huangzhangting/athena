package org.tin.athena.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 发红包测试
 * */
public class RedEnvelopeTest {
    private static final BigDecimal minAmount = new BigDecimal("0.01");

    public static BigDecimal[] generateRedEnvelope(long amount, int num){
        //参数校验

        //最小金额校验
        BigDecimal totalAmount = new BigDecimal(amount);
        BigDecimal totalMin = minAmount.multiply(new BigDecimal(num));
        if(totalAmount.compareTo(totalMin) < 0){
            throw new RuntimeException("非法参数");
        }

        BigDecimal[] arr = new BigDecimal[num];
        if(num == 1){
            arr[0] = totalAmount;
            return arr;
        }

        for(int i=1; i<num; i++){
            //计算每轮的最大金额
            BigDecimal max = totalAmount.subtract(minAmount.multiply(new BigDecimal(num - i)));
            max = max.subtract(minAmount); //避免取随机数时出现0

            //根据最大金额，取随机值
            BigDecimal cur = new BigDecimal(Math.random()).multiply(max).setScale(2, RoundingMode.DOWN);
            cur = cur.add(minAmount); //避免取随机数时出现0

            arr[i] = cur;

            totalAmount = totalAmount.subtract(cur);
        }

        //最后一个
        arr[0] = totalAmount;

        return arr;
    }


    public static void main(String[] args) {
        BigDecimal[] amounts = generateRedEnvelope(100, 10);
        BigDecimal total = BigDecimal.ZERO;
        for(BigDecimal b : amounts){
            System.out.println(b.toString());
            total = total.add(b);
        }
        System.out.println("总金额：" + total);
    }

}
