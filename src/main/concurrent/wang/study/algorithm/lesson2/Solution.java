package wang.study.algorithm.lesson2;

import java.util.HashSet;
import java.util.Set;

public class Solution {
    public boolean isHappy(int n) {
        Set<Integer> cache = new HashSet<>();
        int s = caculateHappy(n);
        while(s!=1){
            cache.add(s);
            s = caculateHappy(s);
            if(cache.contains(s)){
                return false;
            }
        }
        return true;
    }

    public int caculateHappy(int n){
        int summary = 0;
        while(n> 0){
            summary += (n%10)*(n%10);
            n = n/10;
        }
        return summary;
    }
}
