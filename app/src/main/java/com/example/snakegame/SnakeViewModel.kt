package com.example.snakegame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//遊戲設計
class SnakeViewModel: ViewModel() {
    //MutableLiveData將ViewModel資料包在裡面
    val body = MutableLiveData<List<Position>>()
    val apple = MutableLiveData<Position>()
    val score = MutableLiveData<Int>()
    val snakeBody = mutableListOf<Position>()
    fun start(){
        snakeBody.apply {
            //初始位置設定
            add(Position(10, 10))
            add(Position(11, 10))
            add(Position(12, 10))
            add(Position(13, 10))
        }.also {
            //將蛇身體的位置存至body裏頭，傳至Main中
            body.value = it
        }
    }
    fun move(dir: Direction){

    }
    fun reset(){

    }
}
//儲存位置資料
data class Position(val x: Int, val y: Int)

//做方向集合
enum class Direction{
    UP,DOWN,LEFT,RIGHT
}