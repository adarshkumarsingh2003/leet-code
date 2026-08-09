class Solution {

    int[][] dp;
    int[] suffix;
    int n;

    public int stoneGameII(int[] piles) {

        n = piles.length;

        suffix = new int[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }

        dp = new int[n][n + 1];

        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        return solve(0, 1);
    }

    private int solve(int i, int m) {

        if (i >= n) {
            return 0;
        }

        if (2 * m >= n - i) {
            return suffix[i];
        }

        if (dp[i][m] != -1) {
            return dp[i][m];
        }

        int ans = 0;

        for (int x = 1; x <= 2 * m; x++) {

            ans = Math.max(
                ans,
                suffix[i] - solve(i + x, Math.max(m, x))
            );
        }

        return dp[i][m] = ans;
    }
}