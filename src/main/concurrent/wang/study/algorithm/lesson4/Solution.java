package wang.study.algorithm.lesson4;

public class Solution {


    static class Node{
        int start;
        int end;

        int[] values;

        Node(int[] values){
            this.values = values;
            this.start = 0;
            this.end = values.length-1;
        }

        public int findMiddle(int needMove) {
            if(start+needMove < (end+start)/2){
                return values[start+needMove];
            }
            return values[(end+start)/2];
        }

        public int moveMiddle(int needMove) {
            if(start+needMove < (end+start)/2){
                start = start+needMove;
                return needMove;
            }
            int move = (end-start)/2;
            if(move == 0){
                move = 1;
            }
            start = start + move;
            return move;
        }

        public boolean empty() {
            return start > end;
        }
    }


    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        boolean one = (nums1.length+nums2.length) % 2 == 1;
        Node node1 = new Node(nums1);
        Node node2 = new Node(nums2);



        int needMove = (nums1.length+nums2.length)%2 == 1 ? (nums1.length+nums2.length)/2 : (nums1.length+nums2.length)/2-1;
        while(needMove > 1 && !node1.empty() && !node2.empty()){
            int maxMove = needMove/2;
            if(node1.findMiddle(maxMove) < node2.findMiddle(maxMove)){
                int move = node1.moveMiddle(maxMove);
                needMove = needMove - move;
            }
            else if(node1.findMiddle(maxMove) > node2.findMiddle(maxMove)){
                int move = node2.moveMiddle(maxMove);
                needMove = needMove - move;
            }
            else{
                int move1 = node1.moveMiddle(maxMove);
                needMove = needMove - move1;
                int move2 = node2.moveMiddle(maxMove);
                needMove = needMove - move2;
            }
        }

        if(node1.empty()){
            return findFromOneArray(nums2,node2.start,needMove,one);
        }
        else if(node2.empty()){
            return findFromOneArray(nums1,node1.start,needMove,one);
        }





        int value11 = node1.values[node1.start];
        int value21 = node2.values[node2.start];

        int[] values = new int[3];
        if(value11 < value21){
            values[0] = value11;
            int value12 = node1.values.length> node1.start+1 ? node1.values[node1.start+1] : Integer.MAX_VALUE;
            if(value12 < value21){
                values[1] = value12;
                int value13 = node1.values.length> node1.start+2 ? node1.values[node1.start+2] : Integer.MAX_VALUE;
                values[2] = value13 < value21 ? value13 : value21;
            }
            else{
                values[1] = value21;
                int value22 = node2.values.length> node2.start+1 ? node2.values[node2.start+1] : Integer.MAX_VALUE;
                values[2] = value12 < value22 ? value12 : value22;
            }
        }
        else{
            values[0] = value21;
            int value22 = node2.values.length> node2.start+1 ? node2.values[node2.start+1] : Integer.MAX_VALUE;
            if(value22 < value11){
                values[1] = value22;
                int value23 = node2.values.length> node2.start+2 ? node2.values[node2.start+2] : Integer.MAX_VALUE;
                values[2] = value23 < value11 ? value23 : value11;
            }
            else{
                values[1] = value11;
                int value12 = node1.values.length> node1.start+1 ? node1.values[node1.start+1] : Integer.MAX_VALUE;
                values[2] = value12 < value22 ? value12 : value22;
            }
        }
        int startIndex = needMove;
        return one ? values[needMove] : (values[needMove]+values[needMove+1])/2.0;

    }

    private double findFromOneArray(int[] nums1,int start,int length,boolean one) {
        if(one){
            return nums1[start+length];
        }
        int startIndex =start+length;
        int endIndex = start+length+1;
        return (nums1[startIndex]+nums1[endIndex])/2.0;
    }

}
