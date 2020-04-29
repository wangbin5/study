package wang.study.algorithm.lesson32;


import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class SolutionTest {
    Solution solution = new Solution();

    @Test
    public void testCommon(){
        assertEquals("最大数量",2,solution.longestValidParentheses("()"));
        assertEquals("最大数量",4,solution.longestValidParentheses("()())"));
        assertEquals("最大数量",6,solution.longestValidParentheses("(()())"));
    }

    @Test
    public void testRightNOtEnough(){
        assertEquals("最大数量",4,solution.longestValidParentheses("((()((())("));
        assertEquals("最大数量",4,solution.longestValidParentheses("()((()((())("));
        assertEquals("最大数量",4,solution.longestValidParentheses(")()())"));


    }

    @Test
    public void testOther(){
        assertEquals("最大数量",2,solution.longestValidParentheses(")))))(((()("));
    }
}