package ru.usu.data.structure.rope;


/**
 * @author astarovoyt
 *
 */
public final class RopeBuilder
{
    public static Rope build(String value)
    {
        return new Rope(value);
    }
}
