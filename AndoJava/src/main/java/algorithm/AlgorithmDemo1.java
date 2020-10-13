package algorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AlgorithmDemo1 {

    public static void main(String[] args) {
        s2_2();
    }

    /*
    模块一 代码效率优化方法论
    01 | 复杂度：如何衡量程序运行的效率？
    https://kaiwu.lagou.com/course/courseInfo.htm?courseId=185#/detail/pc?id=3339
     */

    // 将给定数组倒序输出 1 , 通过一个 for 循环，从左到右将 a 数组的元素，从右到左地赋值到 b 数组中，最后输出数组 b
    // 时间复杂度 O(n)
    private static void s1_1() {
        int[] a = {1, 2, 3, 4, 5};
        int[] b = new int[5];
        for (int i = 0; i < a.length; i++) {
            b[i] = a[a.length - 1 - i];
        }
        System.out.println(Arrays.toString(b));
    }

    // 将给定数组倒序输出 2 , 交换首尾对应的元素, 最后打印数组 a
    // 时间复杂度 O(n)
    private static void s1_2() {
        int[] a = {1, 2, 3, 4, 5};
        int temp = -1;
        for (int i = 0; i < (a.length / 2); i++) {
            temp = a[i];
            a[i] = a[a.length - 1 - i];
            a[a.length - 1 - i] = temp;
        }
        System.out.println(Arrays.toString(a));
    }

    // 查找数组 a 中的最大值
    // 时间复杂度 O(n)
    private static void s1_3() {
        final int a[] = {1, 4, 3};
        int max_val = -1;
        int max_index = -1;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > max_val) {
                max_val = a[i];
                max_index = i;
            }
        }
        System.out.println("max_val = " + max_val + "  max_index = " + max_index);
    }

    // 查找数组中出现次数最多的数字
    // 时间复杂度 O(n*n)
    private static void s1_4() {
        int[] a = {1, 3, 4, 3, 4, 1, 3, 3};
        int max_val = -1;
        int max_count = 0;
        int temp_count = 0;
        for (int i = 0; i < a.length; i++) {
            temp_count = 0;
            for (int j = 0; j < a.length; j++) {
                if (a[i] == a[j]) {
                    temp_count += 1;
                }
                if (temp_count > max_count) {
                    max_count = temp_count;
                    max_val = a[i];
                }
            }
        }
        System.out.println("max_val = " + max_val + " max_count = " + max_count);
    }

    /*
    结论:
        1. 一个顺序结构的代码，时间复杂度是 O(1)。
        2. 二分查找，或者更通用地说是采用分而治之的二分策略，时间复杂度都是 O(logn)。见后续。
        3. 一个简单的 for 循环，时间复杂度是 O(n)。
        4. 两个顺序执行的 for 循环，时间复杂度是 O(n)+O(n)=O(2n)，其实也是 O(n)。
        5. 两个嵌套的 for 循环，时间复杂度是 O(n²)。

    总结:
        一、复杂度通常包括时间复杂度和空间复杂度。在具体计算复杂度时需要注意以下几点:
            1. 它与具体的常系数无关，O(n) 和 O(2n) 表示的是同样的复杂度。
            2. 复杂度相加的时候，选择高者作为结果，也就是说 O(n²)+O(n) 和 O(n²) 表示的是同样的复杂度。
            3. O(1) 也是表示一个特殊复杂度，即任务与算例个数 n 无关。
            
        二、复杂度细分为时间复杂度和空间复杂度，其中时间复杂度与代码的结构设计高度相关；空间复杂度与代码中数据结构的选择高度相关。
     */


    /*
    02 | 数据结构：将“昂贵”的时间复杂度转换成“廉价”的空间复杂度
    https://kaiwu.lagou.com/course/courseInfo.htm?courseId=185#/detail/pc?id=3340
     */

    // 假设有任意多张面额为 2 元、3 元、7 元的货币，现要用它们凑出 100 元，求总共有多少种可能性。
    // 数学模型 : 7x + 3y + 2z = 100
    // 时间复杂度 O(n*n)
    private static void s2_1() {
        int total = 100;
        int count = 0;
        for (int i = 0; i <= (total / 7); i++) {
            for (int j = 0; j <= (total / 3); j++) {
                //O(n*n*n)
//                for (int k = 0; k <= (100 / 2); k++) {
//                    if (i * 7 + j * 3 + k * 2 == 100) {
//                        count += 1;
//                    }
//                }

                //O(n*n)
                if ((total - i * 7 - j * 3 >= 0) && ((total - 7 * i + 3 * j) % 2 == 0)) {
                    count += 1;
                }
            }
        }
        System.out.println("count = " + count);
    }


    // 查找出现最多的数值 1
    // 时间复杂度 O(n*n)
    private static void s2_3() {
        int[] a = {1, 2, 3, 4, 5, 5, 5, 6};
        int max_val = -1;
        int max_count = -1;
        int temp_count = 0;
        for (int i = 0; i < a.length; i++) {
            temp_count = 0;

            for (int j = 0; j < a.length; j++) {
                if (a[i] == a[j]) {
                    temp_count += 1;
                }
                if (max_count < temp_count) {
                    max_val = a[i];
                    max_count = temp_count;
                }
            }
        }
        System.out.println("max_val = " + max_val + "   max_count = " + max_count);
    }

    // 查找出现最多的数值 2 , 从1到2展示了从时间复杂度向空间复杂度的转移
    // 时间复杂度 O(n) , 空间复杂度增加为 O(n)
    // 定义一个 k-v 结构的字典，用来存放元素-出现次数的 k-v 关系
    private static void s2_4() {
        int[] a = {1, 2, 3, 4, 5, 5, 5, 6};
        int max_val = -1;
        int max_count = -1;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < a.length; i++) {
            if (map.containsKey(a[i])) {
                int value = map.get(a[i]) + 1;
                map.put(a[i], value);
                if (max_count < value) {
                    max_val = a[i];
                    max_count = value;
                }
            } else {
                map.put(a[i], 1);
            }
        }
        System.out.println("max_val = " + max_val + "   max_count = " + max_count);
    }

    /*
    总结:
        第一步，暴力解法。在没有任何时间、空间约束下，完成代码任务的开发。
        第二步，无效操作处理。将代码中的无效计算、无效存储剔除，降低时间或空间复杂度。
        第三步，时空转换。设计合理数据结构，完成时间复杂度向空间复杂度的转移。
     */

    // 假设有任意多张面额为 2 元、3 元、7 元的货币，现要用它们凑出 100 元，求总共有多少种可能性。
    // 数学模型 : 7x + 3y + 2z = 100
    // s2_1 时间复杂度向空间复杂度的转移
    //todo 2020年10月13日 16:50:22  https://kaiwu.lagou.com/course/courseInfo.htm?courseId=185#/detail/pc?id=3340
    private static void s2_2() {
        int count = 0;
        int num_7 = 0;
        for (int i = 0; i <= (100 / 7); i++) {
            num_7 = i;
        }
        for (int j = 0; j <= ((100 - 7 * num_7) / 3); j++) {
            if ((100 - num_7 * 7 - j * 3 >= 0) && ((100 - num_7 * 7 - j * 3) % 2 == 0)) {
                count += 1;
            }
        }
        System.out.println(count);
    }

}
