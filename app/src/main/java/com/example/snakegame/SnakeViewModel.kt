package com.example.snakegame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SnakeViewModel: ViewModel() {
    val body = MutableLiveData<List<Position>>()
    val apple = MutableLiveData<Position>()
    val score = MutableLiveData<Int>()
    fun start(){

    }
    fun move(dir: Direction){

    }
    fun reset(){

    }
}

data class Position(val x: Int, val y: Int)

enum class Direction{
    UP,DOWN,LEFT,RIGHT
}