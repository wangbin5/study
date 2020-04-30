package wang.study.algorithm.lesson1;

import java.util.HashMap;
import java.util.Map;

public class Solution2 {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> cache = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            int differ = target - nums[i];
            if(cache.containsKey(differ)){
                return new int[]{cache.get(differ),i};
            }
            cache.put(nums[i],i);
        }
        return null;
    }
}
