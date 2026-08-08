class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        int[] last = new int[m];
        Arrays.fill(last, -1);

        int p = n - 1;
        for (int j = m - 1; j >= 0; j--) {
            while (p >= 0 && word1.charAt(p) != word2.charAt(j)) {
                p--;
            }
            last[j] = p;
            if (p >= 0) {
                p--; 
            }
        }

        int[] result = new int[m];
        boolean changed = false; 
        int i = 0;
        int j = 0; 

        while (i < n && j < m) {
            boolean isMatch = (word1.charAt(i) == word2.charAt(j));
            if (isMatch || (!changed && (j + 1 == m || i + 1 <= last[j + 1]))) {
                if (!isMatch) {
                    changed = true; 
                }
                result[j] = i;
                j++; 
            }
            i++; 
        }
        return j == m ? result : new int[0];
    }
}