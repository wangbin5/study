package wang.study.algorithm.lesson25;

public class Solution {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }


    public ListNode reverseKGroup(ListNode head, int k) {
        if(k<=1){
            return head;
        }
        ListNode first = null;
        ListNode start = null;
        ListNode prev = null;
        ListNode prevStart = null;
        ListNode current = head;
        int left = 0;
        while(current != null ){
            ListNode next = current.next;
            if(left ==1){
                if(first == null){
                    first = current;
                }
                if(prevStart!=null){
                    prevStart.next = current;
                }

            }
            if(left == 0){
                left = k;
                prevStart = start;
                start = current;
            }
            left --;
            current.next = prev;
            prev = current;
            current = next;
        }

        if(left> 0){
            ListNode reversePrev = reverse(prev,left,k);

            if(prevStart!=null){
                prevStart.next = reversePrev;
            }
            else{
                if(first == null){
                    first = reversePrev;
                }
                else{
                    start.next = reversePrev;
                }

            }
        }
        else{
            start.next = null;
        }
        return first;
    }

    private ListNode reverse(ListNode prev, int left, int k) {
        System.out.println("prev is "+prev.val+",left "+left +",k = "+k);
        ListNode node = prev;
        ListNode last = null;
        for(int i=left;i<k;i++){
            ListNode next = node.next;
            node.next = last;
            last = node;
            node = next;
        }
        return last == null ? node : last;
    }


}
