package algo;

import java.util.HashMap;
import java.util.Map;

/**
 * 最大子数组算法
 *
 * @author 微信公众号：微观技术
 */

public class MaxSubArray {


    /**
     * 描述：
     * <p>
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     */
    static Map<String, Integer> maxSubArray(Integer[] nums) {

        if (nums.length <= 0) {
            return null;
        }
        int max = 0, cur = 0;
        int startPositon = -1, endPosition = -1;
        for (int i = 0; i < nums.length; i++) {
            if (cur < 0) {
                cur = nums[i]; //如果前面加起来的和小于0，抛弃前面的
                startPositon = i;
                endPosition = i;
            } else {
                cur += nums[i];
            }

            if (cur > max) {  // 当前的 i 为正的，有效
                max = cur;
                endPosition = i;
            }

        }

        Map<String, Integer> result = new HashMap<>();
        result.put("startPositon", startPositon);
        result.put("endPosition", endPosition);
        result.put("max", max);
        return result;

    }

    public static void main(String[] args) {

        Integer[] nums_1 = {1, -2, 3, 10, -4, 7, 2, -5};
        System.out.println("nums_1运行结果：" + maxSubArray(nums_1));

        Integer[] nums_2 = {-1, 1};
        System.out.println("nums_2运行结果：" + maxSubArray(nums_2));

        Integer[] nums_3 = {-1};
        System.out.println("nums_3运行结果：" + maxSubArray(nums_3));

        Integer[] nums_4 = {1, -2, 4};
        System.out.println("nums_4运行结果：" + maxSubArray(nums_4));

        Integer[] nums_5 = {1, -2, 4, 5, 3, -2, 1, 1};
        System.out.println("nums_5运行结果：" + maxSubArray(nums_5));


    }
}
