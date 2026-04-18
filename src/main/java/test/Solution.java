package test;

public class Solution {
    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        return quickSelect(nums,0,n-1,n-k+1);//第k个最大的元素对应的是第n-k+1个最小的元素
    }

    private int quickSelect(int[] nums, int l, int r, int k) {//返回nums数组下标l~r中第k个最小的元素
        int dummy = nums[l];
        int left = l;
        int right = r;
        while (left < right) { //会让哨兵到中间去
            while (left < right && nums[right] >= dummy)right--;
            if (left < right) {
                int temp = nums[right];
                nums[right] = nums[left];
                nums[left] = temp;
            }
            while (left < right && nums[left] <= dummy)left++;
            if (left < right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
            }
        }
        for(int i:nums){
            System.out.print(i+" ");
        }
        System.out.println();
        if(left==k-1){
            return nums[left];
        }
        else if(left>k-1){
            return quickSelect(nums,l,left-1,k);
        }
        else return quickSelect(nums,left+1,r,k);
    }
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = {3,2,1,5,6,4};
        System.out.println(solution.findKthLargest(nums, 2));
    }
}