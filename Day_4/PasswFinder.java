public class PasswFinder {

    private final int LOWER_LIMIT = 264360;
    private final int UPPER_LIMIT = 746325;

    private final int DIGITS = 6;

    public static void main(String[] args) {
        PasswFinder passwFinder = new PasswFinder();
        System.out.println(passwFinder.getNumOfPossiblePassw());
    }

    private int getNumOfPossiblePassw() {
        int possiblePassw = 0;

        for (int currentTry = LOWER_LIMIT; currentTry <= UPPER_LIMIT; currentTry++) {
            int[] digits = getDigits(currentTry);
            int adjacentCount = 0;
            boolean adjacent = false;
            boolean decrease = false;
            for (int digitIndex = 1; digitIndex < DIGITS; digitIndex++) {
                if (digits[digitIndex] == digits[digitIndex - 1]) {
                    adjacentCount++;
                } else if (digits[digitIndex] < digits[digitIndex - 1]) {
                    decrease = true;
                    break;
                } else {
                    if (adjacentCount == 1) {
                        adjacent = true;
                    }
                    adjacentCount = 0;
                }
            }
            if (adjacentCount == 1) {
                adjacent = true;
            }

            if (!decrease && adjacent) {
                possiblePassw++;
            }
        }

        return possiblePassw;
    }

    private int[] getDigits(int number) {
        int length = DIGITS;
        int[] digits = new int[length];
        for (int i = length; i > 0; i--) {
            digits[i - 1] = number % (int) Math.pow(10, 1);
            number = number / (int) Math.pow(10, 1);
        }

        return digits;
    }

}
