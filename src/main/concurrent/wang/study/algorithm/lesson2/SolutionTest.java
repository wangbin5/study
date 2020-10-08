package wang.study.algorithm.lesson2;


import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

public class SolutionTest {
    Solution solution = new Solution();

    @Test
    public void test(){
        assertTrue("正确",solution.isHappy(82));
    }
}