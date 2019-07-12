package ru.usu.data.structure.rope

import junit.framework.Assert

import org.junit.Test
import ru.usu.data.structure.rope.RopeHelper
import ru.usu.data.structure.rope.RopeNode
import ru.usu.data.structure.rope.RopeNodeFactory

/**
 * Test of rope's node operations
 * @author astarovoyt
 */
class RopeNodeTest {

    @Test
    fun ropeNodeNormalizeTest() {
        val root = generateNonNormalizeRopeNode()
        RopeHelper.normalize(root)

        Assert.assertEquals(true, root.left!!.isLeaf)
        Assert.assertEquals(true, root.right!!.isLeaf)
        Assert.assertEquals(1, root.deep)
        Assert.assertEquals(RopeTest.SIMPLE_ROPE_STRING_EXPECTED, root.left!!.value)

    }

    companion object {
        private val factory = RopeNodeFactory

        private fun generateNonNormalizeRopeNode(): RopeNode {
            val leafLeft = RopeNodeFactory.createLeafNode(RopeTest.SIMPLE_ROPE_STRING_EXPECTED)
            val leafRight = RopeNodeFactory.createLeafNode(RopeTest.SIMPLE_ROPE_STRING_EXPECTED)

            val parent = RopeNodeFactory.createParentNode(leafLeft, null)
            val parentOfparent = RopeNodeFactory.createParentNode(null, parent)
            val parentOfparentOfparent = RopeNodeFactory.createParentNode(null, parentOfparent)

            return RopeNodeFactory.createParentNode(parentOfparentOfparent, leafRight)
        }
    }
}
