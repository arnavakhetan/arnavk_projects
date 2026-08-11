// Program calculates the surface area and volume for 3 spheres based on their respective radius.
// Has 2 classes. One has various methods for each variable. Other is responsible for creating the objects and printing the output.
class Sphere {
    double diameter; 
    void setDiameter(double diameter) {
        this.diameter = diameter;
    }
    double radius () {
        return diameter / 2.0; 
    }
    double diameter () {
        return diameter;
    }
    double surfaceArea () {
        double radius = radius();
        return 4 * Math.PI * radius * radius; 
    }
    double volume() {
        double radius = diameter() / 2.0;
        return (4.0 / 3.0) * Math.PI * radius * radius * radius; 
    }
}
class SphereInfo {
    public static void main(String[] args) {
        Sphere sphere1 = new Sphere(); 

        Sphere sphere2 = new Sphere(); 

        Sphere sphere3 = new Sphere(); 

        sphere1.setDiameter(0.0); 
        

        sphere2.setDiameter(1.0); 
        

        sphere3.setDiameter(7.5); 
        

        System.out.println("A sphere of radius " + sphere1.radius() + " has surface area " + sphere1.surfaceArea() + " and volume " + sphere1.volume());

        System.out.println("A sphere of radius " + sphere2.radius() + " has surface area " + sphere2.surfaceArea() + " and volume " + sphere2.volume());

        System.out.println("A sphere of radius " + sphere3.radius() + " has surface area " + sphere3.surfaceArea() + " and volume " + sphere3.volume());
    }
}
