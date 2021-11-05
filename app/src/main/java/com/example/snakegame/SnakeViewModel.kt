package com.example.snakegame

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.fixedRateTimer
import kotlin.random.Random

//遊戲設計
class SnakeViewModel: ViewModel() {
    private val TAG: String? = SnakeViewModel::class.java.simpleName

    //MutableLiveData將ViewModel資料包在裡面
    val body = MutableLiveData<List<Position>>()
    val apple = MutableLiveData<Position>()
    val score = MutableLiveData<Int>()
    val size = MutableLiveData<Float>()
    val gameStatus = MutableLiveData<GameStatus>()
    val snakeBody = mutableListOf<Position>()
    var initialDelay = 1000
    var period = 500
    var direction = Direction.LEFT
    private var scorePoint: Int = 0
    private lateinit var applePosition: Position
    var resetStatus = false

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
                if( x<0 || x>=20 || y<0 || y>=20){
                    cancel()
                    gameStatus.postValue(GameStatus.GAMEOVER)
                }
                when(direction){
                    Direction.UP -> y--
                    Direction.DOWN -> y++
                    Direction.LEFT -> x--
                    Direction.RIGHT -> x++
                }
            }
            if (snakeBody.contains(fristPosition)){
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
            if (resetStatus){
                cancel()
                resetStatus = false
            }
        }
        generateApple()
    }
    fun move(dir: Direction){
        direction = dir
    }
    fun reset(){
        resetStatus = true
        gameStatus.postValue(GameStatus.ONGOING)
        direction = Direction.LEFT
        snakeBody.removeAll(snakeBody)
        body.postValue(snakeBody)
        scorePoint = 0
        /*initialDelay = 0
        period = 100000000*/
        start()
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