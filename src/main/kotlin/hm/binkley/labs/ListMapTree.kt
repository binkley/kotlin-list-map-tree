package hm.binkley.labs

class ListMapTree private constructor(val name: String, val depth: Int) {
    fun newChild(name: String) = ListMapTree(name, depth + 1)

    companion object {
        fun newRoot(name: String) = ListMapTree(name, 0)
    }
}
