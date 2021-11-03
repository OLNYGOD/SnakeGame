package com.example.snakegame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.fixedRateTimer

//遊戲設計
class SnakeViewModel: ViewModel() {
    //MutableLiveData將ViewModel資料包在裡面
    val body = MutableLiveData<List<Position>>()
    val apple = MutableLiveData<Position>()
    val score = MutableLiveData<Int>()
    val size = MutableLiveData<Float>()
    val gameStatus = MutableLiveData<GameStatus>()
    val snakeBody = mutableListOf<Position>()
    val initialDelay = 1000
    val period = 500
    var direction = Direction.LEFT
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
        }//速率固定器
        fixedRateTimer("Timer", true, initialDelay.toLong(), period.toLong()){
            val fristPosition = snakeBody.first().copy().apply {
                when(direction){
                    Direction.UP -> y--
                    Direction.DOWN -> y++
                    Direction.LEFT -> x--
                    Direction.RIGHT -> x++
                }
                if( x<0 || x>20 || y<0 || y>20){
                    cancel()
                    gameStatus.postValue(GameStatus.GAMEOVER)
                }
            }
            snakeBody.add(0, fristPosition) //新增一個位置在snakeBody前面
            snakeBody.removeLast()               //移除一個位置在snakeBody後面
            body.postValue(snakeBody)
        }
    }
    fun move(dir: Direction){
        direction = dir
    }
    fun reset(){

    }
}
//儲存位置資料
data class Position(var x: Int, var y: Int)

//做方向集合
enum class Direction{
    UP,DOWN,LEFT,RIGHT
}

enum class GameStatus{
    ONGOING, GAMEOVER
}