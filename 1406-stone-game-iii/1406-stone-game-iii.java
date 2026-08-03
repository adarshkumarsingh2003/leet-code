class Solution {
    public String stoneGameIII(int[] stoneValue) {
        int n = stoneValue.length;
        int[] dp = new int[n + 1];

        for (int i = 0; i < n; i++) {
            dp[i] = Integer.MIN_VALUE;
        }

        dp[n] = 0;

        for (int i = n - 1; i >= 0; i--) {
            int sum = 0;

            for (int k = 0; k < 3 && i + k < n; k++) {
                sum += stoneValue[i + k];
                dp[i] = Math.max(dp[i], sum - dp[i + k + 1]);
            }
        }

        if (dp[0] > 0)
            return "Alice";
        else if (dp[0] < 0)
            return "Bob";
        else
            return "Tie";
    }
}
