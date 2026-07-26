class Solution {
    public int maximumProduct(int[] nums) {
        int max = 1;
        Arrays.sort(nums);
        int result = nums[nums.length-1]*nums[nums.length-2]*nums[nums.length-3];
        if(nums[0]*nums[1]*nums[nums.length-1]>result){
            return nums[0]*nums[1]*nums[nums.length-1];
        }
        else
        return result;
    }
}