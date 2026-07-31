class Solution {
    public int minimumPushes(String word) {
        int[] freq = new int[26];
        for (char ch : word.toCharArray()) {
            freq[ch - 'a']++;
        }
        Arrays.sort(freq);
        int ans = 0;
        for (int i = 0; i < 26; i++) {
            ans += (i / 8 + 1) * freq[25 - i];
        }
        return ans;
    }
}