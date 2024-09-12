// Program calculates the surface area and volume for 3 spheres based on their respective radius.
// Has 2 classes. One has various methods for each variable. Other is responsible for creating the objects and printing the output.
class Sphere {
    double diameter; // member variable created
    void setDiameter(double diameter) {
        this.diameter = diameter;
    }
    double radius () {
        return diameter / 2.0; // Radius is half of the diameter
    }
    double diameter () {
        return diameter;
    }
    double surfaceArea () {
        double radius = radius();
        return 4 * Math.PI * radius * radius; // 4*PI*(R square) is the surface area formula for a sphere
    }
    double volume() {
        double radius = diameter() / 2.0;
        return (4.0 / 3.0) * Math.PI * radius * radius * radius; // 4/3*PI*(R cube) is the volume formula for a sphere
    }
}
class SphereInfo {
    public static void main(String[] args) {
        Sphere sphere1 = new Sphere(); // 1st object

        Sphere sphere2 = new Sphere(); // 2nd object

        Sphere sphere3 = new Sphere(); // 3rd object

        sphere1.setDiameter(0.0); // 0.0 radius
        // Sets the radius for the 1st object

        sphere2.setDiameter(1.0); // 0.5 radius
        // Sets the radius for the 2nd object

        sphere3.setDiameter(7.5); // 3.75 radius
        // Sets the radius for the 3rd object

        // Prints out the 3 statements using System.out.println()
        System.out.println("A sphere of radius " + sphere1.radius() + " has surface area " + sphere1.surfaceArea() + " and volume " + sphere1.volume());

        System.out.println("A sphere of radius " + sphere2.radius() + " has surface area " + sphere2.surfaceArea() + " and volume " + sphere2.volume());

        System.out.println("A sphere of radius " + sphere3.radius() + " has surface area " + sphere3.surfaceArea() + " and volume " + sphere3.volume());
    }
}
