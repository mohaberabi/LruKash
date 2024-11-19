package com.mohaberabi.lrukache


class LruKache<K, V>(
    private val maxSize: Long
) {


    private val cache = mutableMapOf<K, Node<K, V>>()

    private var mostRecentUsed: Node<K, V>? = null

    private var leastRecentUsed: Node<K, V>? = null


    fun add(k: K, v: V): LRUResult {
        val res = if (cache.containsKey(k)) {
            val mru = cache[k]
            mru?.data = v
            removeNode(mru!!)
            addHead(mru)
            LRUResult.HIT
        } else {
            val added = Node(k, v)
            addHead(added)
            cache[k] = added
            LRUResult.MISS
        }
        if (cache.size > maxSize) {
            removeLestRecentUsed()
        }
        return res
    }


    private fun addHead(node: Node<K, V>) {
        node.next = mostRecentUsed
        mostRecentUsed?.previous = node
        mostRecentUsed = node
        if (leastRecentUsed == null) {
            leastRecentUsed = mostRecentUsed
        }
    }

    private fun removeNode(node: Node<K, V>) {
        if (node.previous != null) {
            node.previous?.next = node.next
        } else {
            mostRecentUsed = node.next
        }
        if (node.next != null) {
            node.next?.previous = node.previous
        } else {
            leastRecentUsed = node.previous
        }
    }

    fun get(k: K): V? {
        val node = cache[k] ?: return null
        removeNode(node)
        addHead(node)
        return node.data
    }


    private fun removeLestRecentUsed() {
        leastRecentUsed?.let { lru ->
            removeNode(lru)
            cache.remove(lru.key)
            if (lru.previous == null) {
                mostRecentUsed = null
            }
            leastRecentUsed = lru.previous
            leastRecentUsed?.next = null
        }
    }
}