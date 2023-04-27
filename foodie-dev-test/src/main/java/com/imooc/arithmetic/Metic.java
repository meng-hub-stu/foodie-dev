package com.imooc.arithmetic;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 常见的算法
 *
 * @author Mengdl
 * @date 2022/04/19
 */
public class Metic {
    /**
     * 冒泡算法
     */
    public static void bubbling() {
        int a[] = {22, 3, 6, 54, 86, 21, 35, 1, 65, 4};
        int b[] = {1, 45, 23, 100};
        int[] c = ArrayUtils.addAll(a, b);
        for (int i = 0; i <= c.length - 1; i++) {
            for (int j = i; j <= c.length - 1; j++) {
                if (c[i] > c[j]) {
                    //交换位置
                    int tem = c[i];
                    c[i] = c[j];
                    c[j] = tem;
                }
            }
        }
        System.out.println("排好序：");
        for (int aa : c) {
            System.out.print(aa + " ");
        }
    }

    public static void main(String[] args) {
        bubbling();
    }

}
