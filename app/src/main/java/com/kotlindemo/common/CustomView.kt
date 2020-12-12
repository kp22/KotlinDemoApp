package com.kotlindemo.common

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.kotlindemo.main.R
import java.lang.Math.*

class CustomView : View {


    private var radius = 0.0f                   // Radius of the circle.
    private var fanSpeed = FanSpeed.OFF         // The active selection.

    // position variable which will be used to draw label and indicator circle position
    private val pointPosition: PointF = PointF(0.0f, 0.0f)
    private val RADIUS_OFFSET_LABEL = 30
    private val RADIUS_OFFSET_INDICATOR = -35

    constructor(context: Context?) : super(context) {
        if (context != null) {
            this.context = context as Nothing?
        }
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var context = null

    private val paintLine = Paint()
    var pading = 10
    var color = resources.getColor(R.color.black)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width
        val h = height
        val radius1 = w / 2 - pading
        val centerX = w / 2
        val centerY = h / 2

        // Log.e("TAG", "onDraw: w = " + w + " | h = " + h);
        paintLine.style = Paint.Style.STROKE
        paintLine.color = Color.TRANSPARENT
        paintLine.strokeWidth = 1f
        paintLine.color = color


        //fan view start------
        // Set dial background color to green if selection not off.
        paint.color = if (fanSpeed == FanSpeed.OFF) Color.GRAY else Color.GREEN
        // Draw the dial.
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)
        // Draw the indicator circle.
        val markerRadius = radius + RADIUS_OFFSET_INDICATOR
        pointPosition.computeXYForSpeed(fanSpeed, markerRadius)
        paint.color = Color.BLACK
        canvas.drawCircle(pointPosition.x, pointPosition.y, radius / 12, paint)
        // Draw the text labels.
        val labelRadius = radius + RADIUS_OFFSET_LABEL
        for (i in FanSpeed.values()) {
            pointPosition.computeXYForSpeed(i, labelRadius)
            val label = resources.getString(i.label)
            canvas.drawText(label, pointPosition.x, pointPosition.y, paint)
        }

        //horizontal line
        canvas.drawLine(
            pading.toFloat(), centerY.toFloat(), w - pading.toFloat(),
            centerY.toFloat(), paintLine
        )
        //vertical line
        canvas.drawLine(
            centerX.toFloat(),
            centerY - radius1.toFloat(),
            centerX.toFloat(),
            centerY + radius1.toFloat(),
            paintLine
        )
    }

    @JvmName("setColor1")
    fun setColor(color: Int) {
        this.color = color
        invalidate()
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        radius = (min(width, height) / 2.0 * 0.8).toFloat()
    }

    private fun PointF.computeXYForSpeed(pos: FanSpeed, radius: Float) {
        // Angles are in radians.
        val startAngle = Math.PI * (9 / 8.0)
        val angle = startAngle + pos.ordinal * (Math.PI / 4)
        x = (radius * cos(angle)).toFloat() + width / 2
        y = (radius * sin(angle)).toFloat() + height / 2
    }

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    enum class FanSpeed(val label: Int) {
        OFF(R.string.fan_off),
        LOW(R.string.fan_low),
        MEDIUM(R.string.fan_medium),
        HIGH(R.string.fan_high);

        fun next() = when (this) {
            OFF -> LOW
            LOW -> MEDIUM
            MEDIUM -> HIGH
            HIGH -> OFF
        }
    }

    init {
        isClickable = true
    }

    override fun performClick(): Boolean {
        if (super.performClick()) return true

        fanSpeed = fanSpeed.next()
        contentDescription = resources.getString(fanSpeed.label)

        invalidate()
        return true
    }

}