public class Pushing
{
	public Node source;
	public Node destination; 

	private double weight;

	public Pushing(Node source, Node destination)
	{
		this.source = source;
		this.destination = destination;
	}

	public void push()
	{
		double weightedValue = weight * source.getValue();
		destination.receive(weightedValue);
	}

	public void setWeight(double weight)
	{
		this.weight = weight;
	}

	public double getWeight()
	{
		return weight;
	}
}