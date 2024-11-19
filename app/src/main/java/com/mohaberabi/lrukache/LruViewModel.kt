package com.mohaberabi.lrukache

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LruViewModel(
    private val lru: LruKache<String, String> = LruKache(10)
) : ViewModel() {


    private val _channel = Channel<LRUResult>()

    val channel = _channel.receiveAsFlow()


    fun add(key: String, value: String) {
        val result = lru.add(key, value)
        viewModelScope.launch {
            _channel.send(result)
        }
    }
}