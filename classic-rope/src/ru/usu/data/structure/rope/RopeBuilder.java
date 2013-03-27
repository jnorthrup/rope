package ru.usu.data.structure.rope;

/**
 * @author astarovoyt
 *
 */
public class RopeBuilder 
{
	public Rope build(String value)
	{
		return new RopeImpl(value);
	}
}
