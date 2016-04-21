package com.example.gotoh.mygame

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.main.*
import java.util.*

class GameActivity : AppCompatActivity() {


    var bmp: Bitmap? = null
    var bmpWidth: Int = 0
    var bmpHeight: Int = 0
    var widthOffset: Double = 0.0
    var heightOffset: Double = 0.0
    var totalDegree: Double = 0.0
    var prevTotaldegree: Double = 0.0
    var prevX: Double = 0.0
    var prevY: Double = 0.0
    var matrix: Matrix = Matrix()
    var prevTriangleDegree: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        r.setOnTouchListener { view, event -> touchImage(view, event) }
    }


    private fun touchImage(view: View, event: MotionEvent): Boolean {
        // Log.v("************",">>touchImage")
        when (event.action) {
            //ドラッグ開始
            MotionEvent.ACTION_DOWN -> {
                // Log.v("************",">>ACTION_DOWN")

                widthOffset = r.width.toFloat() / 2.0
                heightOffset = r.height / 2.0

                totalDegree = 0.0
                prevTotaldegree = 0.0
                prevTriangleDegree = 0.0

                prevX = event.x.toDouble() - widthOffset
                prevY = event.y.toDouble() - heightOffset

                bmp = BitmapFactory.decodeResource(getResources(),
                        R.drawable.r);

            }
            //ドラッグ終了
            MotionEvent.ACTION_UP -> r.setImageBitmap(bmp)

            //ドラッグ中
            MotionEvent.ACTION_MOVE -> {
                val x: Double = event.x.toDouble() - widthOffset
                val y: Double = event.y.toDouble() - heightOffset

                val a: Double = x * prevX + y * prevY
                val b: Double = x * prevY - y * prevX

                var triangleDegree: Double = Math.toDegrees(Math.atan2(b, a)) * -1.0;
                if (triangleDegree < 0) {
                    triangleDegree += 360.0
                }

                matrix.postRotate((triangleDegree - prevTriangleDegree).toFloat())

                var ansBmp: Bitmap = Bitmap.createBitmap(bmp, 0, 0,
                        bmp!!.width, bmp!!.height, matrix, true)

                val trimX: Int = (ansBmp!!.width - bmp!!.width) / 2
                val trimY: Int = (ansBmp!!.height - bmp!!.height) / 2

                if (trimX < 0 || trimY < 0) return true

                val trimBmp: Bitmap = Bitmap.createBitmap(ansBmp, trimX, trimY,
                        bmp!!.width, bmp!!.height);
                r.setImageBitmap(trimBmp)


                prevTriangleDegree = triangleDegree


            }
            else -> {
            }

        }

        return true
    }

}
/**
 *
 */
//    private fun rotate() {
//        val numberCalc = if (totalDegree <= 18.0f ) {
//            1
//        } else if (totalDegree <= 54.0f ) {
//            2
//        } else if (totalDegree <= 90.0f ) {
//            3
//        } else if (totalDegree <= 126.0f ) {
//            4
//        } else if (totalDegree <= 162.0f ) {
//            5
//        } else if (totalDegree <= 198.0f ) {
//            6
//        } else if (totalDegree <= 234.0f ) {
//            7
//        } else if (totalDegree <= 270.0f ) {
//            8
//        } else if (totalDegree <= 306.0f ) {
//            9
//        } else if (totalDegree <= 342.0f ) {
//            10
//        } else {
//            1
//        }
//
//        i += 1
//        degree -= 0.1f * i.toFloat()
//
//        totalDegree += degree
//        if (totalDegree > 360.0f) {
//            totalDegree -= 360.0f
//        }
//        Log.v("*******************", "totalDegree:"+totalDegree)
//        ans.text = numberCalc.toString()
//
//        matrix.postRotate(degree, bmp!!.width / 2.0f, bmp!!.height / 2.0f)
//       // r.imageMatrix = matrix
//        val ansBmp = Bitmap.createBitmap(bmp, 0, 0, bmp!!.width, bmp!!.height, matrix, true)
//
//        val cutX = (ansBmp.width - bmp!!.width) / 2;
//        val cutY = (ansBmp.height - bmp!!.height) / 2;
//        val ansBmp2 = Bitmap.createBitmap(ansBmp, cutX, cutY,
//        bmp!!.width, bmp!!.height);
//
//
//        r.setImageBitmap(ansBmp2)
//        r.invalidate()
//
//
//
//        interval += Random().nextInt((interval / 10.0).toInt())
//
//        if (1500 > interval && degree > 0) {
//            Handler().postDelayed(Runnable { rotate() },interval)
//        }
//    }





