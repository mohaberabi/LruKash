package com.mohaberabi.lrukache

data class Node<K, V>(
    val key: K,
    var data: V,
    var next: Node<K, V>? = null,
    var previous: Node<K, V>? = null
)
