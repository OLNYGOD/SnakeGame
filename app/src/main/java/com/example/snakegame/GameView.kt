package com.example.snakegame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

//遊戲畫面顯示
class GameView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    var snakebody : List<Position>? = null
    var size = 0  //方格大小
    //畫畫面
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.run {
            snakebody?.forEach {
                var paint = Paint().apply {
                    color = Color.BLACK
                }//劃出snakebody每格位置
                drawRect((it.x * size).toFloat(),
                    (it.y*size).toFloat(),
                    (it.x+1*size).toFloat(),
                    (it.y+1*size).toFloat(),paint)
            }
        }
    }
    //寬度搭配螢幕自動改變
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        size = width / 200
    }
}

