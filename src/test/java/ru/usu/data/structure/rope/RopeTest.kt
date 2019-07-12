package ru.usu.data.structure.rope

import junit.framework.Assert

import org.junit.Test

import ru.usu.data.structure.rope.*

/**
 * Tests for building rope
 *
 * @author astarovoyt
 */
class RopeTest {

    @Test
    fun ropeSimpleToStringTest() {
        val rope = RopeBuilder.build(SIMPLE_ROPE_STRING_EXPECTED)

        Assert.assertEquals(SIMPLE_ROPE_STRING_EXPECTED, rope.toString())
    }

    @Test
    fun ropeComplexToStringTest() {
        val rope = generateComplexRope()

        Assert.assertEquals(COMPLEX_ROPE_STRING_EXPECTED, rope.toString())
    }

    @Test
    fun ropeComplexToLengthTest() {
        val rope = generateComplexRope()

        Assert.assertEquals(COMPLEX_ROPE_STRING_EXPECTED.length, rope.length)
    }

    @Test
    fun ropeSimpleLengthTest() {
        val rope = RopeBuilder.build(SIMPLE_ROPE_STRING_EXPECTED)

        Assert.assertEquals(SIMPLE_ROPE_STRING_EXPECTED.length, rope.length)
    }

    @Test
    fun ropeSimpleCharAtTest() {
        val rope = RopeBuilder.build(SIMPLE_ROPE_STRING_EXPECTED)

        Assert.assertEquals(SIMPLE_ROPE_STRING_EXPECTED[1], rope[1])
    }

    @Test
    fun ropeComplexCharAtTest() {
        val rope = generateComplexRope()

        for (charIndex in 0 until COMPLEX_ROPE_STRING_EXPECTED.length) {
            Assert.assertEquals(COMPLEX_ROPE_STRING_EXPECTED[charIndex], rope[charIndex])
        }

    }

    @Test
    fun ropeSimpleSplitTest() {
        val builder = RopeBuilder
        val rope = RopeBuilder.build(SIMPLE_ROPE_STRING_EXPECTED)
        val index = 1
        val ropes = rope.split(index)

        val expectedLeft = SIMPLE_ROPE_STRING_EXPECTED.substring(0, index)
        val expectedRight = SIMPLE_ROPE_STRING_EXPECTED.substring(index)

        val leftRope = ropes[0]
        val rightRope = ropes[1]

        Assert.assertEquals(expectedLeft, leftRope.toString())
        Assert.assertEquals(expectedRight, rightRope.toString())
        Assert.assertEquals(0, leftRope.deep)
        Assert.assertEquals(0, rightRope.deep)
    }

    @Test
    fun ropeComplexSplitTest() {
        val rope = generateComplexRope()

        for (index in 0 until COMPLEX_ROPE_STRING_EXPECTED.length) {
            val ropes = rope.split(index)
            val expectedLeft = COMPLEX_ROPE_STRING_EXPECTED.substring(0, index)
            val expectedRight = COMPLEX_ROPE_STRING_EXPECTED.substring(index)

            Assert.assertEquals(expectedLeft, ropes[0].toString())
            Assert.assertEquals(expectedRight, ropes[1].toString())
        }
    }

    @Test
    fun ropeComplexSplitDeepTest() {
        val rope = generateComplexRope()
        val index = 3

        val ropes = rope.split(index)

        val leftRope = ropes[0]
        val rightRope = ropes[1]
        RopeHelper.printRope(leftRope as Rope)
        Assert.assertEquals(1, leftRope.deep)
        Assert.assertEquals(2, rightRope.deep)
    }

    @Test
    fun ropeSimpleDeepTest() {
        val builder = RopeBuilder
        val rope = RopeBuilder.build(SIMPLE_ROPE_STRING_EXPECTED)

        Assert.assertEquals(0, rope.deep)
    }

    @Test
    fun ropeComplexDeepTest() {
        val rope = generateComplexRope()

        Assert.assertEquals(2, rope.deep)
    }

    @Test
    fun ropeComplexPrintTest() {
        val rope = generateComplexRope()
        RopeHelper.printRope(rope)
    }

    @Test
    fun ropeSimpleAppendTest() {
        val builder = RopeBuilder
        val rope = RopeBuilder.build(SIMPLE_ROPE_STRING_EXPECTED)
        val ropeAppendix = RopeBuilder.build(SIMPLE_ROPE_STRING_EXPECTED)

        val result = rope.append(ropeAppendix)

        RopeHelper.printRope(result!!)
        Assert.assertEquals(SIMPLE_ROPE_STRING_EXPECTED + SIMPLE_ROPE_STRING_EXPECTED, result.toString())
    }

    @Test
    fun ropeComplexAppendTest() {
        val rope = generateComplexRope()
        val ropeAppendix = generateComplexRope()

        val result = rope.append(ropeAppendix)

        RopeHelper.printRope(result!!)
        Assert.assertEquals(COMPLEX_ROPE_STRING_EXPECTED + COMPLEX_ROPE_STRING_EXPECTED, result.toString())
    }

    @Test
    fun ropeComplexSubSequenceTest() {
        val rope = generateComplexRope()
        val ropeAppendix = generateComplexRope()

        val result = rope.append(ropeAppendix)

        val string = COMPLEX_ROPE_STRING_EXPECTED + COMPLEX_ROPE_STRING_EXPECTED

        for (i in 0 until string.length - 1) {
            for (j in i + 1 until string.length) {
                Assert.assertEquals("substr $i $j", string.subSequence(i, j), result!!.subSequence(i, j).toString())
            }
        }
    }

    @Test
    fun ropeComplexSubSequenceTest2() {
        val rope = generateComplexRope()
        val ropeAppendix = generateComplexRope()

        val result = rope.append(ropeAppendix)

        val string = COMPLEX_ROPE_STRING_EXPECTED + COMPLEX_ROPE_STRING_EXPECTED
        val i = 1
        val j = 5

        Assert.assertEquals(string.subSequence(i, j), result!!.subSequence(i, j).toString())
    }

    companion object {
        private val COMPLEX_ROPE_STRING_EXPECTED = "my new str temp"

        val SIMPLE_ROPE_STRING_EXPECTED = "abc"

        private val factory = RopeNodeFactory

        private fun generateComplexRope(): Rope {
            val parentLeft = RopeNodeFactory.createParentNode(RopeNodeFactory.createLeafNode("my "), RopeNodeFactory.createLeafNode("new "))
            val parentRight = RopeNodeFactory.createParentNode(RopeNodeFactory.createLeafNode("str "), RopeNodeFactory.createLeafNode("temp"))

            val root = RopeNodeFactory.createParentNode(parentLeft, parentRight)

            return Rope(root)
        }
    }
}
