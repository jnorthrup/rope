package ru.usu.data.structure.rope

import java.util.ArrayList

/**
 * @author astarovoyt
 */
internal class RopeBalancer(private val rope: Rope) {


    private val leafNodes: List<RopeNode>
        get() {
            val leafs = ArrayList<RopeNode>()
            RopeBalancer.putLeafs(this.rope.root, leafs)
            return leafs
        }

    fun balanceIfNessesary(): Rope {
        if (RopeBalancer.MAX_ROPE_DEPTH < this.rope.deep) {
            this.balance()
        }

        return this.rope
    }

    private fun balance() {
        this.rope.root = RopeBalancer.merge(this.leafNodes)
    }

    override fun toString(): String {
        return "RopeBalancer{" +
                "rope=" + this.rope +
                '}'.toString()
    }

    companion object {
        private val factory = RopeNodeFactory
        private val MAX_ROPE_DEPTH: Short = 96

        private fun putLeafs(node: RopeNode?, leafs: MutableList<RopeNode>) {
            if (null == node) {
                return
            }

            if (node.isLeaf) {
                leafs.add(node)
                return
            }

            RopeBalancer.putLeafs(node.left, leafs)
            RopeBalancer.putLeafs(node.right, leafs)
        }

        private fun merge(leafNodes: List<RopeNode>): RopeNode {
            return RopeBalancer.merge(leafNodes, 0, leafNodes.size)
        }

        private fun merge(leafNodes: List<RopeNode>, start: Int, end: Int): RopeNode {
            val range = end - start
            if (1 == range) {
                return leafNodes[start]
            } else if (2 == range) {
                return RopeNodeFactory.createParentNode(leafNodes[start], leafNodes[start + 1])
            }
            val middle = start + range / 2
            return RopeNodeFactory.createParentNode(RopeBalancer.merge(leafNodes, start, middle), RopeBalancer.merge(leafNodes, middle, end))
        }
    }
}
