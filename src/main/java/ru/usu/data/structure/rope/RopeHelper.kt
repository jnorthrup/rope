package ru.usu.data.structure.rope

import java.io.PrintStream

internal object RopeHelper {


    private val FIBONACCI = longArrayOf(0L, 1L, 1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L, 55L, 89L, 144L, 233L, 377L, 610L, 987L, 1597L, 2584L, 4181L, 6765L, 10946L, 17711L, 28657L, 46368L, 75025L, 121393L, 196418L, 317811L, 514229L, 832040L, 1346269L, 2178309L, 3524578L, 5702887L, 9227465L, 14930352L, 24157817L, 39088169L, 63245986L, 102334155L, 165580141L, 267914296L, 433494437L, 701408733L, 1134903170L, 1836311903L, 2971215073L, 4807526976L, 7778742049L, 12586269025L, 20365011074L, 32951280099L, 53316291173L, 86267571272L, 139583862445L, 225851433717L, 365435296162L, 591286729879L, 956722026041L, 1548008755920L, 2504730781961L, 4052739537881L, 6557470319842L, 10610209857723L, 17167680177565L, 27777890035288L, 44945570212853L, 72723460248141L, 117669030460994L, 190392490709135L, 308061521170129L, 498454011879264L, 806515533049393L, 1304969544928657L, 2111485077978050L, 3416454622906707L, 5527939700884757L, 8944394323791464L, 14472334024676221L, 23416728348467685L, 37889062373143906L, 61305790721611591L, 99194853094755497L, 160500643816367088L, 259695496911122585L, 420196140727489673L, 679891637638612258L, 1100087778366101931L, 1779979416004714189L, 2880067194370816120L, 4660046610375530309L, 7540113804746346429L)
    private val SPACES = ("                        "
            + "                                                " + "                                                "
            + "                                                " + "                                ")

    private val MIN_LENGTH = 17

    private val factory = RopeNodeFactory

    fun concatenate(left: Rope?, right: Rope?): Rope? {
        if (null == left || null == right) {
            return left ?: right
        }

        checkArgs(left, right)

        if (MIN_LENGTH > left.length + right.length) {
            return balanceIfNessesary(Rope(left.toString() + right))
        }

        if (left.isFlat && !right.isFlat) {
            val rootNode = right.root
            if (MIN_LENGTH > left.length + rootNode.left!!.influence) {
                val node = RopeNodeFactory.createParentNode(
                        RopeNodeFactory.createLeafNode(left.toString() + rootNode.left!!.value!!),
                        rootNode.right!!)
                return balanceIfNessesary(Rope(node))
            }
        }

        if (right.isFlat && !left.isFlat) {
            val rootNode = left.root
            if (MIN_LENGTH > rootNode.right!!.influence + right.length) {
                val node = RopeNodeFactory.createParentNode(rootNode.left!!,
                        RopeNodeFactory.createLeafNode(rootNode.right!!.value!! + right))
                return balanceIfNessesary(Rope(node))
            }
        }

        return balanceIfNessesary(Rope(RopeNodeFactory.createParentNode(left.root, right.root)))
    }

    private fun balanceIfNessesary(result: Rope): Rope {
        return RopeBalancer(result).balanceIfNessesary()
    }

    private fun checkArgs(left: Rope, right: Rope) {
        if (Integer.MAX_VALUE < left.length.toLong() + right.length) {
            throw IllegalArgumentException("Max rope length can be " + Integer.MAX_VALUE)
        }
    }

    fun isBalanced(r: Rope): Boolean {
        val depth = r.deep
        return if (depth >= FIBONACCI.size - 2) false else FIBONACCI[depth + 2] <= r.length
    }
    fun normalize(node: RopeNode?) {
        if (null == node || node.isLeaf) {
            return
        }

        if (node.isHalf) {
            val child = node.half
            normalize(child)
            copyNodePropeties(node, child!!)
        } else {
            normalize(node.left)
            normalize(node.right)
            node.deep = getIncDeep(node)
        }

        if (null != node.right && node.right!!.isEmpty) {
            node.right = null
        }

        if (null != node.left && node.left!!.isEmpty) {
            node.left = null
        }
    }

    private fun copyNodePropeties(dest: RopeNode, source: RopeNode) {
        dest.left = source.left
        dest.right = source.right
        dest.value = source.value
        dest.deep = source.deep
    }

    fun getInfluence(node: RopeNode?): Int {
        return node?.influence ?: 0
    }

    private fun getDeep(node: RopeNode?): Int {
        return node?.deep ?: 0
    }

    fun getIncDeep(node: RopeNode): Int {
        return Math.max(RopeHelper.getDeep(node.left), RopeHelper.getDeep(node.right)) + 1
    }

    fun printRope(rope: Rope) {
        printRopeNode(rope.root)
    }

    private fun printRopeNode(rope: RopeNode) {
        visualize(rope, System.out, 0)
    }

    private fun visualize(node: RopeNode?, out: PrintStream, depth: Int) {
        if (null == node) {
            return
        }

        if (0 == node.deep) {
            out.print(SPACES.substring(0, depth * 2))
            out.println("\"" + node + "\"")

            return
        }

        out.print(SPACES.substring(0, depth * 2))
        out.println("concat[left]")
        visualize(node.left, out, depth + 1)
        out.print(SPACES.substring(0, depth * 2))
        out.println("concat[right]")
        visualize(node.right, out, depth + 1)
    }
}
