public class Test {

    public int countGoodTriplets(int[] arr, int a, int b, int c) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                for (int k = j + 1; k < arr.length; k++) {
                    if (Math.abs(arr[i] - arr[j]) <= a &&
                            Math.abs(arr[j] - arr[k]) <= b &&
                            Math.abs(arr[i] - arr[k]) <= c) {
                        ans++;
                    }
                }
            }

        }
        return ans;
    }

    public static int addDigits(int num) {
        if (num < 10) {
            return num;
        }
        int r = 0;
        while (num / 10 != 0) {
            r += num % 10;
            num /= 10;
        }
        r += num;
        return addDigits(r);
    }

    public int subtractProductAndSum(int n) {
        int m = 1;
        int sum = 0;
        while (n / 10 != 0) {
            sum += n % 10;
            m *= n % 10;
            n /= 10;
        }
        m *= n;
        sum += n;
        return m - sum;
    }

    public static boolean isPowerOfTwo(int n) {
        // 一直除以2，中间不能有余数，到1结束
        while (n % 2 == 0 && n != 0) {
            n /= 2;
            if (n == 1) {
                return true;
            }
        }
        return n == 1;
    }


    public static int[] shuffle(int[] nums, int n) {
        // 1,2,3,4,5,6
        int[] ints = new int[nums.length];
        for (int i = 0, j = i + n, k = 0; i < n; i++, j++) {
            ints[k] = nums[i];
            ints[k + 1] = nums[j];
            k += 2;
        }
        return ints;
    }

    /**
     * 行变成熟
     *
     * @param matrix
     * @return
     */
    public int[][] transpose(int[][] matrix) {
        int[][] transposed = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }

    public static int maxScore(String s) {
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            int count = 0;
            for (int j = 0; j < s.length(); j++) {
                if (j <= i && j != s.length() - 1 && s.charAt(j) == '0') {
                    count++;
                }
                if (j > i && s.charAt(j) == '1') {
                    count++;
                }
            }
            ans = Math.max(ans, count);
        }
        return ans;
    }

    public static int vowelStrings(String[] words, int left, int right) {
        int ans = 0;
        Character[] vowels = new Character[]{'a', 'e', 'i', 'o', 'u'};
        for (int i = left; i < right; i++) {
            String word = words[i];
            int isVowel = 0;
            for (Character vowel : vowels) {
                if (vowel == word.charAt(0)) {
                    isVowel++;
                }
                if (vowel == word.charAt(word.length() - 1)) {
                    isVowel++;
                }
            }
            if (isVowel == 2) {
                ans++;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        String[] str = {"are", "amy", "u"};
        int i = vowelStrings(str, 0, 2);
        System.out.println(i);


    }

    public int peakIndexInMountainArray(int[] arr) {
        for (int l = 0, m = l + 1, r = m + 1; r < arr.length; l++, m++, r++) {
            if (arr[l] < arr[m] && arr[m] > arr[r]) {
                return m;
            }
        }
        return -1;
    }




}
