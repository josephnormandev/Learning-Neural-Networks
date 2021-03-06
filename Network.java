import java.util.ArrayList;

import java.lang.Math;

public class Network
{
	public int inputs;
	public int middles;
	public int outputs;

	private ArrayList<Node> nodes;
	private ArrayList<Node> nodes_1;
	private ArrayList<Node> nodes_2;
	private ArrayList<Node> nodes_3;
	private ArrayList<Node> nodes_4;

	private ArrayList<Pushing> pushings;
	private ArrayList<Pushing> pushings_1;
	private ArrayList<Pushing> pushings_2;
	private ArrayList<Pushing> pushings_3;

	public Network(int inputs, int middles, int outputs)
	{
		this.inputs = inputs;
		this.middles = middles;
		this.outputs = outputs;

		nodes = new ArrayList<Node>();
		nodes_1 = new ArrayList<Node>();
		nodes_2 = new ArrayList<Node>();
		nodes_3 = new ArrayList<Node>();
		nodes_4 = new ArrayList<Node>();

		pushings = new ArrayList<Pushing>();
		pushings_1 = new ArrayList<Pushing>();
		pushings_2 = new ArrayList<Pushing>();
		pushings_3 = new ArrayList<Pushing>();

		generateNodeStructure();
	}

	public double[] calculate(double[] input)
	{
		for(int i = 0; i < nodes_1.size(); i ++)
		{
			nodes_1.get(i).setValue(input[i]);
		}

		// now push the first column of pushings...
		for(int i = 0; i < pushings_1.size(); i ++)
		{
			pushings_1.get(i).push();
		}

		// then calculate the second column of nodes...
		for(int i = 0; i < nodes_2.size(); i ++)
		{
			nodes_2.get(i).calculateValue();
		}

		// now push the second column of pushings...
		for(int i = 0; i < pushings_2.size(); i ++)
		{
			pushings_2.get(i).push();
		}

		// then calculate the third column of nodes...
		for(int i = 0; i < nodes_3.size(); i ++)
		{
			nodes_3.get(i).calculateValue();
		}

		// now push the third column of pushings...
		for(int i = 0; i < pushings_3.size(); i ++)
		{
			pushings_3.get(i).push();
		}

		// then calculate the output column of nodes...
		double[] outputArray = new double[outputs];
		int j = 0;
		for(int i = 0; i < nodes_4.size(); i ++)
		{
			nodes.get(i).calculateValue();
			outputArray[j] = nodes_4.get(i).getValue();
			j ++;
		}
		return outputArray;
	}

	public void generateNodeStructure()
	{
		for(int i = 0; i < inputs; i ++)
		{
			Node newNode = new Node();
			nodes_1.add(newNode);
			nodes.add(newNode);
		}

		// then calculate the second column of nodes...
		for(int i = inputs; i < inputs + middles; i ++)
		{
			Node newNode = new Node();
			nodes_2.add(newNode);
			nodes.add(newNode);
		}

		// then calculate the third column of nodes...
		for(int i = inputs + middles; i < inputs + middles * 2; i ++)
		{
			Node newNode = new Node();
			nodes_3.add(newNode);
			nodes.add(newNode);
		}
		// then calculate the output column of nodes...
		for(int i = inputs + middles * 2; i < inputs + middles * 2 + outputs; i ++)
		{
			Node newNode = new Node();
			nodes_4.add(newNode);
			nodes.add(newNode);
		}

		// create pushings from input_layer to layer_1
		for(int i = 0; i < nodes_1.size(); i ++)
		{
			for(int j = 0; j < nodes_2.size(); j ++)
			{
				Pushing newPushing = new Pushing(nodes_1.get(i), nodes_2.get(j));
				pushings_1.add(newPushing);
				pushings.add(newPushing);
			}
		}

		for(int i = 0; i < nodes_2.size(); i ++)
		{
			for(int j = 0; j < nodes_3.size(); j ++)
			{
				Pushing newPushing = new Pushing(nodes_2.get(i), nodes_3.get(j));
				pushings_2.add(newPushing);
				pushings.add(newPushing);
			}
		}

		for(int i = 0; i < nodes_3.size(); i ++)
		{
			for(int j = 0; j < nodes_4.size(); j ++)
			{
				Pushing newPushing = new Pushing(nodes_3.get(i), nodes_4.get(j));
				pushings_3.add(newPushing);
				pushings.add(newPushing);
			}
		}
	}

	public void generateRandomValues()
	{
		for(int i = 0; i < nodes.size(); i ++)
		{
			double randomBias = Math.random() * 20 - 10;

			nodes.get(i).setBias(randomBias);
		}

		for(int i = 0; i < pushings.size(); i ++)
		{
			double randomWeight = Math.random() * 10 - 5;

			pushings.get(i).setWeight(randomWeight);
		}
	}

	public int getNodeCount()
	{
		return inputs + middles * 2 + outputs;
	}

	public int getPushingsCount()
	{
		return pushings.size();
	}

	public static double calculateCost(double[] guess, int answer)
	{
		double[] answerArray = new double[10];
		answerArray[answer] = 1;

		double cost = 0;

		for(int i = 0; i < guess.length; i ++)
		{
			cost += Math.pow(guess[i] - answerArray[i], 2);
		}
		return cost;
	}
}