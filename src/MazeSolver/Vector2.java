package MazeSolver;

public class Vector2<E, T> implements Comparable<Vector2<E, T>>
{
	E firstVal;
	T secondVal;

	// sets the values for the first and second value of the vector
	public Vector2(E e, T t)
	{
		this.firstVal = e;
		this.secondVal = t;
	}

	// simple compareTo of the first values of each vector
	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(Vector2<E, T> vec)
	{
		return ((Comparable<E>) (this.firstVal)).compareTo((E) vec.firstVal);
	}
}
