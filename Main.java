import java.util.Arrays;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;


public class Main
{
	public static void main(String args[]) throws FileNotFoundException
	{
		File data = new File("./semeion.data");
		Scanner fileIn = new Scanner(data);

		double[][] drawings = new double[1593][16 * 16];
		int[] answers = new int[1593];

		int testing_i = 1500;

		int i = 0;
		while(fileIn.hasNextLine())
		{
			String line = fileIn.nextLine();
			String[] spaced = line.split("\\s+");

			for(int j = 0; j < spaced.length - 1; j ++)
			{
				drawings[i][j] = Float.parseFloat(spaced[j]);
			}
			answers[i] = Integer.parseInt(spaced[spaced.length - 1]);
			i ++;
		}

		System.out.println("Let's train the algorithm training set");

		int generations = 100;
		double bestAccuracy = 0;

		Network network = new Network(16 * 16, 16, 10);		
		Network bestNetwork = null;

		for(int generation = 0; generation < generations; generation ++)
		{
			double sumCorrect = 0;
			double accuracy = 0;
			network.generateRandomValues();

			for(i = 0; i < testing_i; i ++)
			{
				int answer = answers[i];
				int guess = 0;

				double[] networkAnswer = network.calculate(drawings[i]);
				double cost = Network.calculateCost(networkAnswer, answer);

				for(int j = 0; j < networkAnswer.length; j ++)
				{
					guess = networkAnswer[j] > networkAnswer[guess] ? j : guess;
				}

				if(answer == guess)
					sumCorrect ++;
			}

			accuracy = sumCorrect / drawings.length;

			if(accuracy > bestAccuracy)
			{
				bestAccuracy = accuracy;
				bestNetwork = network;
				network = new Network(16 * 16, 16, 10);
			}
			double progress = Math.floor((generation + 1) * 100 / generations);
			System.out.println("Progress: " + (int)progress + "%, " + accuracy);
		}
		System.out.println("Best Accuracy: " + bestAccuracy);
		System.out.println();
		System.out.println("Great, let's try some random predictions with the testing set");

		for(i = testing_i; i < drawings.length; i ++)
		{
			int answer = answers[i];
			int guess = 0;
			double[] networkAnswer = bestNetwork.calculate(drawings[i]);

			for(int j = 0; j < networkAnswer.length; j ++)
			{
				guess = networkAnswer[j] > networkAnswer[guess] ? j : guess;
			}

			System.out.println("Answer: " + answer + ", Guessed: " + guess);
		}
	}
}