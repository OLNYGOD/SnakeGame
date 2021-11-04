package com.example.snakegame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.fixedRateTimer
import kotlin.random.Random

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
    private var scorePoint: Int = 0
    private lateinit var applePosition: Position

    fun start(){
        score.postValue(scorePoint)
        snakeBody.apply {
            //蛇初始位置設定
            add(Position(10, 10))
            add(Position(11, 10))
            add(Position(12, 10))
            add(Position(13, 10))
        }.also {
            //將蛇身體的位置存至body裏頭，傳至Main中
            body.postValue(it)
        }//速率固定器
        fixedRateTimer("Timer", true, initialDelay.toLong(), period.toLong()){
            val fristPosition = snakeBody.first().copy().apply {
                if( x<0 || x>20 || y<0 || y>20){
                    gameStatus.postValue(GameStatus.GAMEOVER)
                    cancel()
                }
                when(direction){
                    Direction.UP -> y--
                    Direction.DOWN -> y++
                    Direction.LEFT -> x--
                    Direction.RIGHT -> x++
                }
            }
            if ( snakeBody.contains(fristPosition)){
                gameStatus.postValue(GameStatus.GAMEOVER)
                cancel()
            }
            body.postValue(snakeBody)
            snakeBody.add(0, fristPosition) //新增一個位置在snakeBody前面
            //如果沒吃到蘋果，移除snakeBody最後面位置
            if( fristPosition != applePosition){
                snakeBody.removeLast()
            }else{
                scorePoint += 100
                score.postValue(scorePoint)
                generateApple()
            }

        }
        generateApple()
    }
    fun move(dir: Direction){
        direction = dir
    }
    fun reset(){

    }

    fun generateApple(){
        //fix applePosition over snakeBody
        val allPonit = mutableListOf<Position>().apply {
            for ( i in 0..19){
                for (j in 0..19)
                    add(Position(i, j))
            }
        }
        allPonit.removeAll(snakeBody)
        allPonit.shuffle()  //隨機排列
        applePosition = allPonit[0]
        /*do {
            applePosition = Position( Random.nextInt(20), Random.nextInt(20))
        }while (snakeBody.contains(applePosition))*/
        apple.postValue(applePosition)
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