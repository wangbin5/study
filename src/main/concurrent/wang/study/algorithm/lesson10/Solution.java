package wang.study.algorithm.lesson10;

import java.util.HashMap;
import java.util.Map;

public class Solution {

    private static final char ANY_ONE = '.';

    private static final char ANY_MANY = '*';


    public boolean isMatch(String s, String p) {
        String formatP = filter(p);
        Handler handler = new Handler();
        return handler.isMatchUsingCache(s,0,formatP,0);
    }

    protected String filter(String p) {
        if(p.length() == 0){
            return p;
        }
        StringBuilder result = new StringBuilder();
        char last = p.charAt(0);
        result.append(last);
        for(int i=1;i<p.length();i++){
            char current = p.charAt(i);
            if(current == ANY_MANY && last == ANY_MANY){

            }
            else{
                last = current;
                result.append(current);
            }
        }
        return result.toString();
    }

    static class Handler{
        private Map<String,Boolean> cache = new HashMap<>();

        protected boolean isMatchUsingCache(String s, int sIndex, String p, int eIndex){
            String key = sIndex +"_"+ eIndex;
            if(this.cache.containsKey(key)){
                return this.cache.get(key);
            }
            boolean result = isMatch(s,sIndex,p,eIndex);
            this.cache.put(key,result);
            return result;
        }
        private boolean isMatch(String s, int sIndex, String p, int eIndex) {
            int length = s.length();
            int pLength = p.length();
            while(sIndex < length){
                if(eIndex>=pLength){
                    return false;
                }
                char sChar = s.charAt(sIndex);
                char pChar = p.charAt(eIndex);
                boolean nextIsMany = eIndex+1 >= pLength ? false : p.charAt(eIndex+1) == ANY_MANY;
                if(!nextIsMany && match(sChar,pChar)){
                    sIndex++;
                    eIndex++;
                }
                else if(nextIsMany){
                    boolean charMatch = match(sChar,pChar);
                    if(!charMatch){
                        return isMatchUsingCache(s,sIndex,p,eIndex+2);
                    }
                    return isMatchUsingCache(s,sIndex+1,p,eIndex) || isMatchUsingCache(s,sIndex+1,p,eIndex+2)|| isMatchUsingCache(s,sIndex,p,eIndex+2);
                }
                else{
                    return false;
                }
            }

            return checkMatch(p,eIndex);
        }

        private boolean checkMatch(String p, int eIndex) {
            if(p.length() == eIndex){
                return true;
            }
            for(int i=eIndex;i<p.length();i++){
                char current = p.charAt(i);
                if(current!=ANY_MANY && p.length() <= i+1){
                    return false;
                }
                if(current == ANY_MANY){
                    continue;
                }
                char next = p.charAt(i+1);
                if(next == ANY_MANY){
                    i++;
                }
                else{
                    return false;
                }
            }
            return true;
        }

        private boolean match(char s,char p){
            return s == p || p == ANY_ONE;
        }
    }




}
