class Solution {
    public int[] intersect(int[] nums1, int[] nums2) {

        int n = nums1.length;
        int m = nums2.length;

        int[] result = new int[Math.min(n, m)];
        boolean[] used = new boolean[m];

        int k = 0;

        for (int i = 0; i < n; i++) {

            for (int j = 0; j < m; j++) {

                if (!used[j] && nums1[i] == nums2[j]) {

                    result[k] = nums1[i];
                    k++;

                    used[j] = true;

                    break;
                }
            }
        }

        return Arrays.copyOf(result, k);
    }
}