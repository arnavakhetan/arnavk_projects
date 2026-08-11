// Code checks for the maximum and minimum values allowed by byte, short and int.
// The output for int will take a few seconds to run.
class Limits {
    public static void main(String[] args) {
        byte var1 = 0;
        byte var2 = 0;
        while (var1 >= var2) {
            var2 = var1;
            var1 = (byte) (var1 + 1);
        }
        System.out.println("Maximum byte value is " + var2);

        var1 = 0;
        var2 = 0;
        while (var1 <= var2) {
            var2 = var1;
            var1 = (byte) (var1 - 1);
        }
        System.out.println("Minimum byte value is " + var2);

        short var3 = 0;
        short var4 = 0;
        while (var3 >= var4) {
            var4 = var3;
            var3 = (short) (var3 + 1);
        }
        System.out.println("Maximum short value is " + var4);

        var3 = 0;
        var4 = 0;
        while (var3 <= var4) {
            var4 = var3;
            var3 = (short) (var3 - 1);
        }
        System.out.println("Minimum short value is " + var4);

        int var5 = 0;
        int var6 = 0;
        while (var5 >= var6) {
            var6 = var5;
            var5 = var5 + 1;
        }
        System.out.println("Maximum int value is " + var6);

        var5 = 0;
        var6 = 0;
        while (var5 <= var6) {
            var6 = var5;
            var5 = var5 - 1;
        }
        System.out.print("Minimum int value is " + var6);
    }
}

