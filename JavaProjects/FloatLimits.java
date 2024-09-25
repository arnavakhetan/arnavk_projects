// Code checks the smallest positive value for the float and double data types.
class FloatLimits {
    public static void main(String[] args) {
        float var1 = 1.0F;
        float var3 = 0.0F;
        while (var1 > 0) {
            var1 = (float) (var1 / 2);
            if (var1 > 0) {
                var3 = var1;
            }
        }
        System.out.println("Smallest positive float is " + var3);

        double var2 = 1.0;
        double var4 = 0.0;
        while (var2 > 0) {
            var2 = var2 / 2;
            if (var2 > 0) {
                var4 = var2;
            }
        }
        System.out.print("Smallest positive double is " + var4);
    }
}
