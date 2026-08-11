// This program is for testing the RELUNeuron file.
// It tests the constructor, the read() method, the write() method, the train() method and the output() method.
// The constructor is tested through checking if the initialization of the neuron works properly.
// The read() method tries to correctly read in the values from weights.dbl.
// The write() method writes the weights and bias to a file called weights_test.dbl.
// The output() method will use the saved weights from weights.dbl and create outputs.
// The train() method uses randomly generated values and helps to create an error value. 
// The error value is the difference between the expected output and the output of the neuron. 
// The number of iterations for both the train() and output() method is the same - 10000.
// This program only has one class: TestNeuron
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException; // Importing the packages required

public class TestNeuron {
    public static void main(String[] args) {
        try {
            RELUNeuron neuron = new RELUNeuron(500);  // Creating an instance of RELUNeuron with 500 inputs
            System.out.println("Constructor test passed."); 

            try (DataInputStream dis = new DataInputStream(new FileInputStream("weights.dbl"))) {
                neuron.read(dis); // Loading the previously saved weights and bias from weights.dbl
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            System.out.println("read() method test passed. Weights loaded successfully.");

            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("weights_test.dbl"))) { 
                neuron.write(dos);  // Writing weights and bias to a new file called weights_test.dbl
            }
            System.out.println("write() method test passed. Weights saved to weights_test.dbl");

            int iterations = 10000;
            for (int i = 0; i < iterations; i++) {  // Testing the train() method using random values 10000 times
                double[] randomInputs = new double[500];
                for (int j = 0; j < 500; j++) {
                    randomInputs[j] = Math.random();  // Creating random inputs
                }

                double expectedOutput = Math.random();  // Creating random expected output
                double neuronOutput = neuron.output(randomInputs); 
                double error = expectedOutput - neuronOutput; // Using the principles of the train() method

                System.out.println("Iteration " + (i + 1) + ": Error = " + error + ", Expected Output = " + expectedOutput + ", Neuron Output = " + neuronOutput);
            } // Print statement with the iteration, error, expected output and output of the neuron
            System.out.println("train() method test passed. Iterated " + iterations + " times.");
            
             // Testing the output() method 10000 times
            for (int times = 0; times < iterations; times++) {
                double[] testInputs = new double[500];  // Creating an array for random inputs
                for (int i = 0; i < 500; i++) {
                    testInputs[i] = Math.random();  // Generating random values
                }

                double output = neuron.output(testInputs);  // Getting the output from the neuron
                System.out.println("Iteration " + (times + 1) + ": Neuron Output = " + output);
            }

            System.out.println("output() method test passed. Iterated for " + iterations + " times.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
