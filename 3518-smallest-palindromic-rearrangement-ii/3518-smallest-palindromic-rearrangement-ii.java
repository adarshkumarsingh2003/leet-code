class Solution {
    private static final long MAX_K = 1_000_001L; 

    public String smallestPalindrome(String s, int k) {
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        int[] halfCount = new int[26];
        String midChar = "";

        for (int i = 0; i < 26; i++) {
            halfCount[i] = count[i] / 2;
            if (count[i] % 2 != 0) {
                midChar = String.valueOf((char) ('a' + i));
            }
        }

        long totalPermutations = countArrangements(halfCount);
        if (totalPermutations < k) {
            return "";
        }

        int halfLen = 0;
        for (int c : halfCount) {
            halfLen += c;
        }

        StringBuilder leftHalf = new StringBuilder();
        long currentK = k;

        for (int pos = 0; pos < halfLen; pos++) {
            for (int i = 0; i < 26; i++) {
                if (halfCount[i] == 0) continue;

                halfCount[i]--;
                long ways = countArrangements(halfCount);

                if (currentK <= ways) {
                    leftHalf.append((char) ('a' + i));
                    break;
                } else {
                    currentK -= ways;
                    halfCount[i]++; 
                }
            }
        }

        String leftStr = leftHalf.toString();
        String rightStr = leftHalf.reverse().toString();

        return leftStr + midChar + rightStr;
    }

    private long countArrangements(int[] freq) {
        int total = 0;
        for (int f : freq) total += f;

        long res = 1;
        for (int f : freq) {
            if (f == 0) continue;
            res = res * nCk(total, f);
            if (res >= MAX_K) return MAX_K;
            total -= f;
        }
        return res;
    }

    private long nCk(int n, int k) {
        k = Math.min(k, n - k);
        long res = 1;
        for (int i = 1; i <= k; i++) {
            res = res * (n - i + 1) / i;
            if (res >= MAX_K) return MAX_K;
        }
        return res;
    }
}