// Code checks for the limits of precision of the float and double data types.
// The answer for both sums should be 1.
// The number of numbers after the decimal point in each answer help to show that the double is more precise than the float.
// The double has more decimal places after the "." but is still not precise enough to get the answer as 1.0.
class SmallSums {
    public static void main(String[] args) {
        float FLOAT_CONS = 0.000001F;
        float float_sum = 0.0F;
        for (int i = 0; i < 1000000; i++) {
            float_sum = float_sum + FLOAT_CONS;
        }
        System.out.println(float_sum);

        double DOUBLE_CONS = 0.000001;
        double double_sum = 0.0;
        for (int j = 0; j < 1000000; j++) {
            double_sum = double_sum + DOUBLE_CONS;
        }
        System.out.print(double_sum);
    }
}
