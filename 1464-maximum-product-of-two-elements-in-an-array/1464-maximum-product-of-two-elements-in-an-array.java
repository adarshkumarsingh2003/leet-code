class Solution {
    public int maxProduct(int[] nums) {
       Arrays.sort(nums);
       int i = nums[nums.length-1], j = nums[nums.length-2];
       return (i-1)*(j-1);
    }
}