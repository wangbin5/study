package wang.study.algorithm.lesson1;

public class Solution {

    static class ListNode{
        ListNode next;
        int val;

        public ListNode(int val) {
            this.val = val;
        }
        public ListNode(){

        }
    }

    static class Tree{
        private ListNode[] root;

        private int lastIndex;

        public Tree(ListNode[] params){
            ListNode[] node = filter(params);
            this.root = new ListNode[node.length];
            for(int i=0;i<node.length;i++){
                root[i] = node[i];
            }
            this.lastIndex = root.length;
            reset();
            this.printNode(this.root);
        }
        private void reset(){
            if(root.length == 0){
                return;
            }
            for(int i=root.length/2; i>=0 ;i--){
                resetPositionFromTop(i);
            }
        }

        public ListNode[] filter(ListNode[] nodes){
            int length = 0;
            for(int i = 0;i<nodes.length;i++){
                if(nodes[i] != null){
                    length ++;
                }
            }
            ListNode[] result = new ListNode[length];
            int start = 0;
            for(int i = 0;i<nodes.length;i++){
                if(nodes[i] != null){
                    result[start] = nodes[i];
                    start++;
                }
            }
            return result;
        }



        private void resetPosition(int resetPosition){
            if(resetPosition == 0){
                return;
            }

            int leftIndex = resetPosition%2 == 1 ? resetPosition : resetPosition-1;
            int rightIndex = resetPosition%2 == 1 ? resetPosition+1: resetPosition;
            int topIndex = resetPosition%2 == 1 ? resetPosition/2 : resetPosition/2 - 1;
            ListNode left = this.lastIndex> leftIndex ? this.root[leftIndex] : null;
            ListNode right = this.lastIndex> rightIndex ? this.root[rightIndex] : null;
            ListNode top = this.root[topIndex];

            if(right == null || left.val <= right.val){
                if(left.val < top.val){
                    this.root[topIndex] = left;
                    this.root[leftIndex] = top;
                    resetPosition(topIndex);
                }
                return;
            }
            else{
                if(right.val < top.val){
                    this.root[topIndex] = right;
                    this.root[rightIndex] = top;
                    resetPosition(topIndex);
                }
                return;
            }
        }

        private void resetPositionFromTop(int topIndex){

            int leftIndex = topIndex*2+1;
            int rightIndex = topIndex*2+2;
            ListNode top = this.root[topIndex];

            ListNode left = this.lastIndex> leftIndex ? this.root[leftIndex] : null;
            ListNode right = this.lastIndex> rightIndex ? this.root[rightIndex] : null;
            if(left == null){
                return;
            }
            if(right == null || left.val <= right.val){
                if(left.val < top.val){
                    this.root[topIndex] = left;
                    this.root[leftIndex] = top;
                    resetPositionFromTop(leftIndex);
                }
                return;
            }
            else {
                if(right.val < top.val){
                    this.root[topIndex] = right;
                    this.root[rightIndex] = top;
                    resetPositionFromTop(rightIndex);
                }
                return;
            }
        }


        public void addOne(ListNode node){
            this.root[lastIndex] = node;
            lastIndex++;
            int startIndex = lastIndex%2 == 1 ? lastIndex -1 : lastIndex;
            this.resetPosition(startIndex);
        }

        private void printNode(ListNode[] root) {
            for(ListNode node : root){
                if(node!=null){
                    System.out.print(node.val +",");
                }
            }
            System.out.println("");
        }


        public ListNode findAndRemoveTop(){
            if(lastIndex <= 0){
                return null;
            }
            ListNode first = root[0];
            root[0] = root[lastIndex -1];
            root[lastIndex -1] = null;
            lastIndex--;
            resetPositionFromTop(0);
            return first;
        }
    }


    static class Topk{

        private ListNode result;

        private ListNode last;

        private Tree tree;


        public Topk(ListNode[] lists){
            this.tree = new Tree(lists);
        }

        public void handle(){
            ListNode top = tree.findAndRemoveTop();
            handleNext(top);

        }

        public void handleNext(ListNode node){
            if(node!=null && node.next!=null){
                tree.addOne(node.next);
            }
            if(node == null){
                node = tree.findAndRemoveTop();
            }
            if(node == null){
                return;
            }
            if(this.result == null){
                this.result = new ListNode(node.val);
                this.last = this.result;

            }
            else{
                this.last.next = new ListNode(node.val);
                this.last = this.last.next;

            }
            ListNode top = tree.findAndRemoveTop();
            handleNext(top);
        }

        public ListNode getResult(){
            return this.result;
        }

    }

    public ListNode mergeKLists(ListNode[] lists) {
        Topk sort = new Topk(lists);
        sort.handle();
        ListNode result = sort.getResult();
        return result;
    }
}
