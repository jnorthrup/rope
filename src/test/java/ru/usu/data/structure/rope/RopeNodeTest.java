package ru.usu.data.structure.rope;

import junit.framework.Assert;

import org.junit.Test;
import ru.usu.data.structure.rope.RopeHelper;
import ru.usu.data.structure.rope.RopeNode;
import ru.usu.data.structure.rope.RopeNodeFactory;

/**
 * Test of rope's node operations
 * @author astarovoyt
 *
 */
public class RopeNodeTest
{
    private static final RopeNodeFactory factory = new RopeNodeFactory();

    @Test
    public void ropeNodeNormalizeTest()
    {
        RopeNode root = generateNonNormalizeRopeNode();
        RopeHelper.normalize(root);

        Assert.assertEquals(true, root.left.isLeaf());
        Assert.assertEquals(true, root.right.isLeaf());
        Assert.assertEquals(1, root.deep);
        Assert.assertEquals(RopeTest.SIMPLE_ROPE_STRING_EXPECTED, root.left.value);

    }

    private static RopeNode generateNonNormalizeRopeNode()
    {
        RopeNode leafLeft = RopeNodeFactory.createLeafNode(RopeTest.SIMPLE_ROPE_STRING_EXPECTED);
        RopeNode leafRight = RopeNodeFactory.createLeafNode(RopeTest.SIMPLE_ROPE_STRING_EXPECTED);

        RopeNode parent = RopeNodeFactory.createParentNode(leafLeft, null);
        RopeNode parentOfparent = RopeNodeFactory.createParentNode(null, parent);
        RopeNode parentOfparentOfparent = RopeNodeFactory.createParentNode(null, parentOfparent);

        return RopeNodeFactory.createParentNode(parentOfparentOfparent, leafRight);
    }
}
