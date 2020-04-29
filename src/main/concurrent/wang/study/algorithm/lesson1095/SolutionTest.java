package wang.study.algorithm.lesson1095;


import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class SolutionTest {
    private Solution solution = new Solution();

    @Test
    public void testGetMaxValue1(){
        Solution.PreHandler max = solution.findMax(createValue(new int[]{1,3,2}));
        assertEquals("index",1,max.maxIndex);
        assertEquals("value",3,max.maxValue);


    }

    @Test
    public void testGetMaxValue2(){
        Solution.PreHandler max = solution.findMax(createValue(new int[]{1,3,2,1}));
        assertEquals("index",1,max.maxIndex);
        assertEquals("value",3,max.maxValue);
    }

    @Test
    public void testGetMaxValue3(){
        Solution.PreHandler max = solution.findMax(createValue(new int[]{1,2,3,4,5,100,2,1}));
        assertEquals("index",5,max.maxIndex);
        assertEquals("value",100,max.maxValue);
    }

    @Test
    public void testGetMaxValue4(){
        Solution.PreHandler max = solution.findMax(createValue(new int[]{1,2}));
        assertEquals("index",1,max.maxIndex);
        assertEquals("value",2,max.maxValue);
    }

    @Test
    public void testGetMaxValue5(){
        Solution.PreHandler max = solution.findMax(createValue(new int[]{1,2,3,4,100,99,98,5,4,2,1}));
        assertEquals("index",4,max.maxIndex);
        assertEquals("value",100,max.maxValue);
    }

    @Test
    public void testGetMaxValue6(){
        Solution.PreHandler max = solution.findMax(createValue(new int[]{1,30,100,30,20,10,1}));
        assertEquals("index",2,max.maxIndex);
        assertEquals("value",100,max.maxValue);
    }

    @Test
    public void testFindInMountainArray1(){
        assertEquals("坐标",0,solution.findInMountainArray(1,createValue(new int[]{1,3,2})));
        assertEquals("坐标",1,solution.findInMountainArray(3,createValue(new int[]{1,3,2})));
        assertEquals("坐标",2,solution.findInMountainArray(2,createValue(new int[]{1,3,2})));
        assertEquals("坐标",-1,solution.findInMountainArray(0,createValue(new int[]{1,3,2})));
        assertEquals("坐标",-1,solution.findInMountainArray(4,createValue(new int[]{1,3,2})));
    }

    @Test
    public void testFindInMountainArray2(){
        assertEquals("坐标",0,solution.findInMountainArray(1,createValue(new int[]{1,3,2,1})));
        assertEquals("坐标",1,solution.findInMountainArray(3,createValue(new int[]{1,3,2,1})));
        assertEquals("坐标",2,solution.findInMountainArray(2,createValue(new int[]{1,3,2,1})));
    }

    @Test
    public void testFindInMountainArray3(){
        assertEquals("坐标",0,solution.findInMountainArray(1,createValue(new int[]{1,2,3,4,5,100,2,1})));
        assertEquals("坐标",1,solution.findInMountainArray(2,createValue(new int[]{1,2,3,4,5,100,2,1})));
        assertEquals("坐标",2,solution.findInMountainArray(3,createValue(new int[]{1,2,3,4,5,100,2,1})));
        assertEquals("坐标",3,solution.findInMountainArray(4,createValue(new int[]{1,2,3,4,5,100,2,1})));
        assertEquals("坐标",4,solution.findInMountainArray(5,createValue(new int[]{1,2,3,4,5,100,2,1})));
        assertEquals("坐标",5,solution.findInMountainArray(100,createValue(new int[]{1,2,3,4,5,100,2,1})));
    }

    @Test
    public void testFindInMountainArray4() {
        assertEquals("坐标", -1, solution.findInMountainArray(0, createValue(new int[]{1, 5,2})));
    }


    private Solution.MountainArray createValue(int[] values) {
        return new Solution.MountainArray(values);
    }


}