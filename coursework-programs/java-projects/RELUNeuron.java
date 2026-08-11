// This program is for the RELUNeuron class.
// It is responsible for the testing of the neuron using 100 training data files with 500 values each.
// It uses weights and bias created randomly at first which are then altered later.
// The program alters the weights and bias using a combination of inputs, error and learningRate.
// These new weights are written to a file called weights.dbl to save them for testing.
// This program only has one class: RELUNeuron
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random; // Importing all required packages
public class RELUNeuron { // Class is public which allows it to be used in the TestNeuron.java program for testing
	
	private double[] weights;
	private double bias; // Creating the variables
	private Random random; // Random number generator for creating the initial weights and bias

	public RELUNeuron(int numInput) { // Constructor
		random = new Random(); 
		weights = new double[numInput];
		// In charge of taking in the inputs for the neuron and initalizing the weights and bias random values
			for (int i = 0; i < numInput; i++) {
				weights[i] = -1.0 + (2.0 * random.nextDouble());
			}
			bias = -1.0 + (2.0 * random.nextDouble());
	}

	private double activation(double x) { // Relu activation function
		x /= 20.0;
		return x > 0 ? x : 0.0; // Return x if positive, otherwise return 0
	}

	public double output(double[] inputs) {
		double weightedSum = 0.0;
		for (int i = 0; i < inputs.length; i++) {
			weightedSum = weightedSum + (inputs[i] * weights[i]); // Sum of inputs and weights
		}

		weightedSum = weightedSum + bias; // Adding bias to sum of inputs and weights
		return activation(weightedSum);
	}

	public void write(DataOutputStream dos) throws IOException {
		for (double weight : weights) {
			dos.writeDouble(weight); // Writing the weight to the file
		}
		dos.writeDouble(bias); // Writing the weight to the file
	}

	public void read(DataInputStream dis) throws IOException {
		for (int i = 0; i < weights.length; i++) {
			weights[i] = dis.readDouble(); // Reading each weight from the file
		}
		bias = dis.readDouble(); // Reading the bias from the file
	}

	public void train(String[] trainingFiles, double learningRate) throws IOException {
		for (String fileName : trainingFiles) { // Iterating over each training file
			try (DataInputStream dis = new DataInputStream(new FileInputStream(fileName))) { // Exception check
				double[] inputs = new double[500];
				
				for (int i = 0; i < 500; i++) {
					inputs[i] = dis.readDouble(); // Reading the 500 input values from the file
				}

				double expectedOutput = dis.readDouble();
				double neuronOutput = output(inputs);
				double error = expectedOutput - neuronOutput; // Calculating the error

				for (int i = 0; i < weights.length; i++) {
					weights[i] = weights[i] + learningRate * error * inputs[i];
				}
				bias = bias + learningRate * error;
			} catch (IOException ioe) { // Checking for an exception that can occur in the process of reading/writing files
				ioe.printStackTrace();
			}
		}
	}

	public static void main(String[] args) { // Doesn't print out anything to this file, just trains the neuron and writes the weights.dbl file
		try {
			RELUNeuron neuron = new RELUNeuron(500); // Creating the neuron with 500 inputs
			String basePath = "TrainingData/";
			// Can also run through String basePath = "C:/Users/arnav/Documents/USF/Coursework/2ndYear1stSemester/CS112_Java/CourseInfo/Day09/TrainingData/";
			String[] trainingFiles = new String[100];
			for (int i = 0; i < 100; i++) {
				trainingFiles[i] = String.format(basePath + "NNTrainData%04d.dbl", i); // Formatting for the file name to go to all 100 files
			}
			try {
				neuron.train(trainingFiles, 0.01); // Training the neuron
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("weights.dbl"))) {
				neuron.write(dos); // Saving the resulting weights and bias to a file
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
