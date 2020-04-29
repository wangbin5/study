package wang.study.algorithm.lesson1;


import org.testng.annotations.Test;

public class SolutionTest {


    @Test
    public void testSimple(){
        int[][] value= new int[][]{
                    new int[]{1,4,5},
                    new int[]{1,3,4},
                    new int[]{2,6}
        };
        runTest(value);

    }
    @Test
    public void testOne(){
        int[][] value= new int[][]{
                new int[]{0,2,5}
        };
        runTest(value);
    }
    @Test
    public void testEmpty(){
        int[][] value= new int[][]{
                new int[]{2},
                new int[]{},
                new int[]{-1}
        };
        runTest(value);
    }

    @Test
    public void testFour(){
        int[][] value= new int[][]{
                new int[]{-8,-7,-7,-5,1,1,3,4},
                new int[]{},
                new int[]{-2},
                new int[]{-10,-10,-7,0,1,3},
                new int[]{2}
        };
        runTest(value);
    }

    @Test
    public void testFive(){
        int[][] value= new int[][]{
                new int[]{-3,2,2},
                new int[]{-9},
                new int[]{-10,-5,-4,-2,-1,1,3,4},
                new int[]{-10,-9,-8,3,4},
                new int[]{-5,-3,3,4},
                new int[]{-9,-8,-5,-4,-2,-1,3},
        };
        runTest(value);
    }

    @Test
    public void testSix(){
        int[][] value= new int[][]{
                new int[]{-9,-7,-7},
                new int[]{-6,-4,-1,1},
                new int[]{-9,-8,-6,-5,-4,1,2,4},
                new int[]{-10},
                new int[]{-5,2,3}

        };
        runTest(value);
    }

    private void runTest(int[][] value) {
        Solution.ListNode[] nodes = this.createNodeList(value);
        Solution.Topk topk = new Solution.Topk(nodes);
        topk.handle();
        ;
        Solution.ListNode result = topk.getResult();
        while(result!=null){
            System.out.print(result.val+" -> ");
            result = result.next;
        }
    }

    private Solution.ListNode[] createNodeList(int[][] value) {
        Solution.ListNode[] nodes = new Solution.ListNode[value.length];
        for(int i=0;i<value.length;i++){
            nodes[i] = createNode(value[i]);
        }
        return nodes;
    }

    private Solution.ListNode createNode(int[] value) {
        if(value.length == 0 ){
            return null;
        }
        Solution.ListNode node = new Solution.ListNode(value[0]);
        Solution.ListNode last = node;
        for(int i=1;i<value.length;i++){
            Solution.ListNode next = new Solution.ListNode(value[i]);
            last.next = next;
            last = next;
        }
        return node;
    }


}