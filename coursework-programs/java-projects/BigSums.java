// This code also tries to find the limits of precision of the float and double data types like how SmallSums.java does.
// The output represents the number of times you can multiply the first value by itself before an error occurs which stops you from getting 1.0 in the subtraction.
// The number being bigger in double than in float again shows that double is more precise than float.
class BigSums {
    public static void main(String[] args) {
        double d1 = 1024; 
        double d2 = d1;
        double d3 = d2 + 1.0;
        int doubleCounter = 0;
        while (d3 - d2 == 1.0) {
            doubleCounter = doubleCounter + 1;
            d2 *= d1;
            d3 = d2 + 1.0;
        }
        System.out.println(doubleCounter);

        float f1 = 1024.0F;
        float f2 = f1;
        float f3 = f2 + 1.0F;
        int floatCounter = 0;
        while (f3 - f2 == 1.0F) {
            floatCounter = floatCounter + 1;
            f2 *= f1;
            f3 = f2 + 1.0F;
        }
        System.out.print(floatCounter);
    }
}
