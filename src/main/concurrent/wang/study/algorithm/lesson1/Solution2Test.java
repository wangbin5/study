package wang.study.algorithm.lesson1;


import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class Solution2Test {
    @Test
    public void test(){
        int[] result = new Solution2().twoSum(new int[]{2, 7, 11, 15},9);
        assertEquals("first",0,result[0]);
        assertEquals("first",1,result[1]);
    }
}