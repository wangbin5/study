package wang.study.algorithm.lesson4;


import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class SolutionTest {

    private Solution solution = new Solution();

    @Test
    public void testEmpty(){
        double result = solution.findMedianSortedArrays(new int[]{1,2,3},new int[0]);
        assertEquals("结果",2.0,result,0.01);
    }

    @Test
    public void testEmpty2(){
        double result = solution.findMedianSortedArrays(new int[]{1,2,3,4},new int[0]);
        assertEquals("结果",2.5,result,0.01);
    }


    @Test
    public void testArray1(){
        double result = solution.findMedianSortedArrays(new int[]{1,2,3,4},new int[]{2});
        assertEquals("结果",2,result,0.01);
    }

    @Test
    public void testArray2(){
        double result = solution.findMedianSortedArrays(new int[]{1,2,3,4},new int[]{7});
        assertEquals("结果",3,result,0.01);
    }

    @Test
    public void testArray3(){
        double result = solution.findMedianSortedArrays(new int[]{1,2,3,4},new int[]{2,5});
        assertEquals("结果",2.5,result,0.01);
    }

    @Test
    public void testArray4(){
        double result = solution.findMedianSortedArrays(new int[]{1,2,3,4},new int[]{2,2,2,2});
        assertEquals("结果",2.0,result,0.01);
    }

    @Test
    public void testArray5(){
        double result = solution.findMedianSortedArrays(new int[]{1,2},new int[]{1,2,3});
        assertEquals("结果",2.0,result,0.01);
    }
    @Test
    public void testArray6(){
        double result = solution.findMedianSortedArrays(new int[]{1},new int[]{2,3,4});
        assertEquals("结果",2.5,result,0.01);
    }

    @Test
    public void testArray7(){
        double result = solution.findMedianSortedArrays(new int[]{2},new int[]{1,3,4});
        assertEquals("结果",2.5,result,0.01);
    }
    @Test
    public void testArray8(){
        double result = solution.findMedianSortedArrays(new int[]{3},new int[]{1,2,4});
        assertEquals("结果",2.5,result,0.01);
    }

}