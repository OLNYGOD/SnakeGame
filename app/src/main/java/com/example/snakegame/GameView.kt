package com.example.snakegame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

//遊戲畫面顯示
class GameView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    val TAG = GameView::class.java.simpleName
    val gap = 3
    var snakeBody : List<Position>? = null
    var apple: Position? = null
    var size = 0  //每一格寬度
    val paint = Paint().apply {color = Color.BLACK } //使用黑色畫筆
    val paintApple = Paint().apply {color = Color.RED } //使用黑色畫筆

    //畫畫面
    override fun onDraw(canvas: Canvas?) {
        Log.d(TAG, "GameView" + 1)
        super.onDraw(canvas)
        canvas?.run {
            apple?.run {
                drawRect((x*size+gap).toFloat(),
                    (y*size+gap).toFloat(),
                    ((x+1)*size-gap).toFloat(),
                    ((y+1)*size-gap).toFloat(),paintApple)
            }
            snakeBody?.forEach {
                //劃出snakebody每格位置
                /*Log.d(TAG, "GameView" + snakeBody)
                Log.d(TAG, "GameView" + size)*/
                drawRect((it.x*size+gap).toFloat(),
                    ((it.y)*size+gap).toFloat(),
                    ((it.x+1)*size-gap).toFloat(),
                    ((it.y+1)*size-gap).toFloat(),paint)
            }
        }
    }
    //寬度搭配螢幕自動改變
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        //Log.d(TAG, "GameView" + width)
        size = width / 20 //表示共20格
    }
}