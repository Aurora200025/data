package test;

public class testOffer03 {

    public int findRepeatNumber(int[] nums) {
        for (int i=0; i<nums.length; i++) {
            for (int j=1; j<nums.length; j++) {
                if (nums[i] == nums[j]) {
                    return nums[i];
                }
            }
        }
        return -1;
    }
}
