import java.util.Arrays;

class Solution {
    // Helper class to track required prime factors (2, 3, 5, 7)
    static class Factors {
        int c2 = 0, c3 = 0, c5 = 0, c7 = 0;

        Factors() {}

        Factors(int c2, int c3, int c5, int c7) {
            this.c2 = c2;
            this.c3 = c3;
            this.c5 = c5;
            this.c7 = c7;
        }
    }

    // Extract prime factors of t
    private Factors getFactors(long n) {
        Factors f = new Factors();
        while (n % 2 == 0) { f.c2++; n /= 2; }
        while (n % 3 == 0) { f.c3++; n /= 3; }
        while (n % 5 == 0) { f.c5++; n /= 5; }
        while (n % 7 == 0) { f.c7++; n /= 7; }
        if (n > 1) return null; // Invalid prime factor > 7
        return f;
    }

    // Update factor counts based on single digit d (1-9)
    private void addDigitFactors(Factors f, int digit, int val) {
        if (digit == 2) f.c2 += val;
        else if (digit == 3) f.c3 += val;
        else if (digit == 4) f.c2 += 2 * val;
        else if (digit == 5) f.c5 += val;
        else if (digit == 6) { f.c2 += val; f.c3 += val; }
        else if (digit == 7) f.c7 += val;
        else if (digit == 8) f.c2 += 3 * val;
        else if (digit == 9) f.c3 += 2 * val;
    }

    // Minimum digits needed to cover remaining required factors
    private int minDigitsNeeded(Factors req) {
        int c2 = Math.max(0, req.c2);
        int c3 = Math.max(0, req.c3);
        int c5 = Math.max(0, req.c5);
        int c7 = Math.max(0, req.c7);

        int count = c5 + c7;

        // Pack 3s into 9s
        count += c3 / 2;
        c3 %= 2;

        // Pack 2s into 8s
        count += c2 / 3;
        c2 %= 3;

        // Handle remaining c2 in {0, 1, 2} and c3 in {0, 1}
        if (c2 == 0 && c3 == 0) {
            // No extra digits needed
        } else if (c2 == 2 && c3 == 1) {
            count += 2; // e.g., digits '2' and '6'
        } else {
            count += 1; // (0,1)->'3', (1,0)->'2', (2,0)->'4', (1,1)->'6'
        }

        return count;
    }

    // Constructs the smallest suffix string of given length satisfying factors
    private String fillSuffix(int len, Factors req) {
        StringBuilder sb = new StringBuilder();
        int c2 = Math.max(0, req.c2);
        int c3 = Math.max(0, req.c3);
        int c5 = Math.max(0, req.c5);
        int c7 = Math.max(0, req.c7);

        while (c7 > 0) { sb.append('7'); c7--; }
        while (c5 > 0) { sb.append('5'); c5--; }

        while (c3 >= 2) { sb.append('9'); c3 -= 2; }
        while (c2 >= 3) { sb.append('8'); c2 -= 3; }

        if (c2 == 2 && c3 == 1) { sb.append("26"); c2 = 0; c3 = 0; }
        else if (c2 == 1 && c3 == 1) { sb.append('6'); c2 = 0; c3 = 0; }
        else if (c2 == 2) { sb.append('4'); c2 = 0; }
        else if (c2 == 1) { sb.append('2'); c2 = 0; }
        else if (c3 == 1) { sb.append('3'); c3 = 0; }

        while (sb.length() < len) {
            sb.append('1');
        }

        char[] chars = sb.toString().toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public String smallestNumber(String num, long t) {
        Factors target = getFactors(t);
        if (target == null) return "-1"; // Prime factor > 7 exists

        int n = num.length();
        boolean hasZero = false;
        int zeroIdx = -1;
        Factors currentReq = new Factors(target.c2, target.c3, target.c5, target.c7);

        // Check if original num is valid
        for (int i = 0; i < n; i++) {
            if (num.charAt(i) == '0') {
                hasZero = true;
                zeroIdx = i;
                break;
            }
            addDigitFactors(currentReq, num.charAt(i) - '0', -1);
        }

        if (!hasZero && currentReq.c2 <= 0 && currentReq.c3 <= 0 &&
            currentReq.c5 <= 0 && currentReq.c7 <= 0) {
            return num;
        }

        // Try replacing a digit at index i with a larger digit d > num[i]
        int limit = hasZero ? zeroIdx : n - 1;
        Factors prefixReq = new Factors(target.c2, target.c3, target.c5, target.c7);
        for (int i = 0; i < limit; i++) {
            addDigitFactors(prefixReq, num.charAt(i) - '0', -1);
        }

        for (int i = limit; i >= 0; i--) {
            int startDigit = (num.charAt(i) - '0') + 1;
            for (int d = startDigit; d <= 9; d++) {
                Factors tempReq = new Factors(prefixReq.c2, prefixReq.c3, prefixReq.c5, prefixReq.c7);
                addDigitFactors(tempReq, d, -1);

                int remLen = n - 1 - i;
                if (minDigitsNeeded(tempReq) <= remLen) {
                    String prefix = num.substring(0, i) + d;
                    String suffix = fillSuffix(remLen, tempReq);
                    return prefix + suffix;
                }
            }
            if (i > 0) {
                addDigitFactors(prefixReq, num.charAt(i - 1) - '0', 1);
            }
        }

        // Expand length if same-length answer is impossible
        int newLen = n + 1;
        while (minDigitsNeeded(target) > newLen) {
            newLen++;
        }
        return fillSuffix(newLen, target);
    }
}