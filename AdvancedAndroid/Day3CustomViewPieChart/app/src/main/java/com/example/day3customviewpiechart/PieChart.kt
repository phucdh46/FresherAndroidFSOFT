package com.example.day3customviewpiechart

import android.content.Context
import android.graphics.*
import android.graphics.Color.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlin.math.min
import kotlin.math.roundToInt


class PieChart(
    context: Context, attrs: AttributeSet
) : View(context, attrs) {
    private var sumPie: Int = 0
    private var arrPie: ArrayList<Int> = ArrayList()
    private var viewCenterX = 0f
    private var viewCenterY = 0f
    private var viewRadius = 0f
    private var oval = RectF()
    private var mShowText: Boolean
    private val pieStartAngle = -90f
    private var sliceStartPoint = pieStartAngle

    // array color
    private val sliceColors = arrayListOf(
        CYAN,
        YELLOW,
        GREEN,
        RED,
        GRAY,
        MAGENTA,
        BLUE,
        DKGRAY
    )

    // text paint
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = BLACK
        textSize = 30f
    }

    //pie paint
    private val piePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL

    }

    init {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.PieChart, 0, 0
        ).apply {
            try {
                mShowText = getBoolean(R.styleable.PieChart_showText, false)
            } finally {
                recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var size = min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas?) {
        var colorIndex = 0
        //draw pie from array
        arrPie.indices.forEach { i ->
            //draw color
            if (colorIndex >= sliceColors.size) colorIndex = 0
            piePaint.color = sliceColors[colorIndex]
            colorIndex++
            canvas?.drawArc(
                oval,
                sliceStartPoint,
                countSweepAngle(arrPie[i]),
                true,
                piePaint
            )

            val midAngle = sliceStartPoint + countSweepAngle(arrPie[i])
            //find x text
            val sliceCenterX = if (arrPie.size == 1) viewCenterX
            else findSliceCenterX(
                cX = viewCenterX,
                midAngle = (midAngle + sliceStartPoint) / 2,
                radius = viewRadius.roundToInt()
            )
            //find y text
            val sliceCenterY = if (arrPie.size == 1) viewCenterY
            else findSliceCenterY(
                cY = viewCenterY,
                midAngle = (midAngle + sliceStartPoint) / 2,
                radius = viewRadius.roundToInt()
            )
            //draw text
            if (isShowText()) {
                drawSliceText(
                    canvas = canvas,
                    text = countSlicePercent(arrPie[i]),
                    sliceX = sliceCenterX,
                    sliceY = sliceCenterY,
                    textPaint = textPaint
                )
            }
            sliceStartPoint += countSweepAngle(arrPie[i])
        }
    }

    //calculate slide pie percent
    private fun countSlicePercent(value: Int): String {
        return "${value * 100 / sumPie}%"
    }

    //calculate SweepAngle
    private fun countSweepAngle(value: Int): Float {
        return (value / sumPie.toFloat() * 360)
    }

    //find x text
    private fun findSliceCenterX(cX: Float, midAngle: Float, radius: Int): Float {
        val rX = cX + radius * (kotlin.math.cos(Math.toRadians(midAngle.toDouble())))
        return ((rX + cX) / 2).toFloat()
    }

    //find y text
    private fun findSliceCenterY(cY: Float, midAngle: Float, radius: Int): Float {
        val rY = cY + radius * (kotlin.math.sin(Math.toRadians(midAngle.toDouble())))
        return ((rY + cY) / 2).toFloat()
    }

    //draw text
    private fun drawSliceText(
        canvas: Canvas?,
        text: String,
        sliceX: Float,
        sliceY: Float,
        textPaint: Paint
    ) {
        val textBounds = Rect()
        textPaint.getTextBounds(text, 0, text.length, textBounds)
        val correctedX = sliceX - textBounds.width() / 2
        val correctedY = sliceY + textBounds.height() / 2
        canvas?.drawText(text, correctedX, correctedY, textPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        viewCenterX = (width / 2).toFloat()
        viewCenterY = (height / 2).toFloat()
        viewRadius = (width / 2).toFloat()
        oval = RectF(0f, 0f, width.toFloat(), height.toFloat())
    }

    fun isShowText(): Boolean {
        return mShowText
    }

    fun setShowText(showText: Boolean) {
        mShowText = showText
        invalidate()
        requestLayout()
    }

    //set array int
    fun setData(arr: ArrayList<Int>) {
        if (arrPie.size > 0) {
            arrPie.clear()
        }
        arrPie.addAll(arr)
        sumPie = arrPie.sum()
    }

    //touch event
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val color =
            Bitmap.createBitmap(event.getX().toInt(), event.getY().toInt(), Bitmap.Config.RGB_565)
        Toast.makeText(context, color.toString(), Toast.LENGTH_SHORT).show()
        return super.onTouchEvent(event)
    }
}