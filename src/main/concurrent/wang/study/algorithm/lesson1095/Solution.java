package wang.study.algorithm.lesson1095;

public class Solution {
    static class MountainArray{
        private int[] items;
        public int get(int index) {
            return items[index];
        }
        public int length() {
            return items.length;
        }

        public MountainArray(int[] items){
            this.items = items;
        }
    }

    static class PreHandler{
        int maxIndex;
        int maxValue;

        public PreHandler(int maxIndex, int maxValue) {
            this.maxValue = maxValue;
            this.maxIndex = maxIndex;
        }
    }

    public int findInMountainArray(int target, MountainArray mountainArr) {
        PreHandler max = findMax(mountainArr);
        if(max.maxValue == target){
            return max.maxIndex;
        }
        int index = findValue(mountainArr,target,0,max.maxIndex-1,true);
        return index == -1 ? findValue(mountainArr,target,max.maxIndex+1,mountainArr.length()-1,false) : index;
    }

    private int findValue(MountainArray mountainArr, int target, int start, int end, boolean asc) {
        if( end - start <=1){
            if(mountainArr.get(start) == target){
                return start;
            }
            if(start!=end && mountainArr.get(end) == target){
                return end;
            }

            return -1;
        }
        int middle = (start+end)/2;
        int value = mountainArr.get(middle);
        if(target == value){
            return middle;
        }
        return compare(target,value,asc) ? findValue(mountainArr,target,start,middle-1,asc)
                : findValue(mountainArr,target,middle+1,end,asc);
    }

    private boolean compare(int target, int value, boolean asc) {
        return asc  ? target < value : target > value;
    }

    protected PreHandler findMax(MountainArray mountainArr){
        int start = 0;
        int end = mountainArr.length()-1;
        int middleIndex = (start+end)/2;
        int middleValue = mountainArr.get(middleIndex);
        PreHandler max = findCMaxIndexAndValue(mountainArr,start,end,middleIndex,middleValue);
        return max;
    }

    // 1 2 3 7 8 6 5 4 3 2 1
    protected PreHandler findCMaxIndexAndValue(MountainArray mountainArr,int start,int end,int middleIndex,int middleValue) {
        if(end-start<3){
            int startValue = mountainArr.get(start);
            int endValue = mountainArr.get(end);
            if(end == start+1){
                return startValue > endValue ? new PreHandler(start,startValue): new PreHandler(end,endValue);
            }
            if(startValue > middleValue){
                return new PreHandler(start,startValue);
            }else if(middleValue > endValue){
                return new PreHandler(middleIndex,middleValue);
            }
            return new PreHandler(end,endValue);
        }
        int firstQuarter= (start+middleIndex)/2;
        int thirdQuarter= (end+middleIndex)/2;

        int firstQuarterValue = mountainArr.get(firstQuarter);
        if(firstQuarterValue >= middleValue ){
            if(firstQuarterValue == middleValue){
                // 1/4 ~1/2
                int index = (firstQuarter+middleIndex)/2;
                return findCMaxIndexAndValue(mountainArr,firstQuarter,middleIndex,index,mountainArr.get(index));
            }
            else{
                // 0 ~ 1/2
                return findCMaxIndexAndValue(mountainArr,0,middleIndex,firstQuarter,firstQuarterValue);
            }
        }
        else{
            int thirdQuarterValue = mountainArr.get(thirdQuarter);
            if(middleValue > thirdQuarterValue){
                // 1/4 ~3/4
                return findCMaxIndexAndValue(mountainArr,firstQuarter,thirdQuarter,middleIndex,middleValue);
            }
            else if(middleValue == thirdQuarterValue){
                // 1/2 ~ 3/4
                int index = (thirdQuarter+middleIndex)/2;
                return findCMaxIndexAndValue(mountainArr,middleIndex,thirdQuarter,index,mountainArr.get(index));
            }
            else{
                //1/2 ~ 1
                return findCMaxIndexAndValue(mountainArr,middleIndex,end,thirdQuarter,thirdQuarterValue);
            }
        }
    }


}
