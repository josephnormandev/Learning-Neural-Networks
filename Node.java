import java.util.ArrayList;

public class Node
{
	private double value;
	private double bias;

	private ArrayList<Double> reception;

	public Node()
	{
		this.bias = 0;
		this.value = 0;

		reception = new ArrayList<Double>();
	}

	public void setValue(double value)
	{
		reception.clear();
		this.value = value;
	}

	public void calculateValue()
	{
		double sum = 0;

		for(int i = 0; i < reception.size(); i ++)
		{
			sum += reception.get(i);
		}
		reception.clear();

		sum += bias;
		value = 1 / (1 + Math.exp(-1 * sum));
	}

	public void receive(double value)
	{
		reception.add(value);
	}

	public double getValue()
	{
		return value;
	}

	public void setBias(double bias)
	{
		this.bias = bias;
	}

	public double getBias()
	{
		return bias;
	}
}