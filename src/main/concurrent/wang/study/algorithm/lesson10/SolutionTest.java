package wang.study.algorithm.lesson10;


import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;

public class SolutionTest {
    private Solution solution = new Solution();

    @Test
    public void testMatch1(){
        assertFalse("无法匹配",solution.isMatch("aa","a"));
        assertFalse("无法匹配",solution.isMatch("mississippi","mis*is*p*."));

        assertTrue("匹配",solution.isMatch("aa","a*"));


        assertTrue("匹配",solution.isMatch("ab",".*"));
        assertTrue("匹配",solution.isMatch("aab","c*a*b"));
    }

    @Test
    public void testMatch2(){
        assertTrue("匹配",solution.isMatch("a","ab*"));
        assertTrue("匹配",solution.isMatch("","a*b*"));
    }

    @Test
    public void testFilter(){
        assertEquals("规则","*b*a*c*",solution.filter("***b**a***********c*****"));
        assertEquals("规则","b*b*a*c*",solution.filter("b*b*a*c*"));
    }
}