package wang.study.algorithm.lesson25;


import org.testng.annotations.Test;

public class SolutionTest {
    private Solution solution = new Solution();

    @Test
    public void testK1(){
        Solution.ListNode head = create(10);
        Solution.ListNode result =solution.reverseKGroup(head,1);
        print(result);
    }

    @Test
    public void testK2(){
        Solution.ListNode head = create(3);
        Solution.ListNode result = solution.reverseKGroup(head,2);
        print(result);
    }

    @Test
    public void testK3(){
        Solution.ListNode head = create(10);
        Solution.ListNode result = solution.reverseKGroup(head,3);
        print(result);
    }

    @Test
    public void testK6(){
        Solution.ListNode head = create(10);
        Solution.ListNode result = solution.reverseKGroup(head,6);
        print(result);
    }

    @Test
    public void testK5(){
        Solution.ListNode head = create(3);
        Solution.ListNode result = solution.reverseKGroup(head,5);
        print(result);
    }

    private void print(Solution.ListNode node){
        while(node!=null){
            System.out.print(node.val +" -> ");
            node = node.next;
        }
    }

    private Solution.ListNode create(int length) {
        Solution.ListNode first = new Solution.ListNode(1);
        Solution.ListNode current = first;
        for(int i=0;i<length;i++){
            current.next = new Solution.ListNode(2+i);
            current = current.next;
        }
        return first;
    }
}