package wang.study.algorithm.lesson32;

public class Solution {

    private static final char LEFT = '(';
    private static final char RIGHT = ')';

    public int longestValidParentheses(String s) {
        Boolean[] valid = new Boolean[s.length()];
        int depth = 0;
        for(int i=0; i< s.length();i++ ){
            char c = s.charAt(i);
            if(c == LEFT ){
                depth++;
            }
            else{
               if(depth ==0 ){
                   valid[i] = false;
               }
               if(depth>0){
                   depth -- ;
               }
               for(int j=i-1;j>= 0;j--){
                   if(valid[j] == null){
                       valid[i] = true;
                       valid[j]= true;
                       break;
                   }else if(!valid[j]){
                       break;
                   }
               }

            }
        }
        int maxLength = 0;
        int currentLength = 0;
        for(int i=0;i<valid.length;i++){
            System.out.println(i+":"+valid[i]);
            if(valid[i]!=null && valid[i]){
                currentLength++;
            }
            else{
                maxLength = maxLength < currentLength ? currentLength : maxLength;
                currentLength = 0;
            }
        }
        return maxLength < currentLength ? currentLength : maxLength;
    }
}
