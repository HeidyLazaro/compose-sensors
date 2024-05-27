package dev.ricknout.composesensors.demo.ui.accelerometer

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.BackgroundColorSpan
import android.view.View
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.ricknout.composesensors.accelerometer.isAccelerometerSensorAvailable
import dev.ricknout.composesensors.accelerometer.rememberAccelerometerSensorValueAsState
import dev.ricknout.composesensors.demo.R
import dev.ricknout.composesensors.demo.model.Demo
import dev.ricknout.composesensors.demo.ui.Demo
import dev.ricknout.composesensors.demo.ui.NotAvailableDemo
import kotlin.random.Random

@SuppressLint("ResourceAsColor")
@Composable
fun AccelerometerDemo() {
    if (isAccelerometerSensorAvailable()) {
        val sensorValue by rememberAccelerometerSensorValueAsState()
        val (x, y, z) = sensorValue.value
        Demo(
            demo = Demo.ACCELEROMETER,
            value = "X: $x m/s^2\nY: $y m/s^2\nZ: $z m/s^2",
        ) {
            val width = constraints.maxWidth.toFloat()
            val height = constraints.maxHeight.toFloat()
            var center by remember { mutableStateOf(Offset(width / 2, height / 2)) }
            val orientation = LocalConfiguration.current.orientation
            val contentColor = LocalContentColor.current
            val radius = with(LocalDensity.current) { 10.dp.toPx() }

            center = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Offset(
                    x = (center.x - x).coerceIn(radius, width - radius),
                    y = (center.y + y).coerceIn(radius, height - radius),
                )
            } else {
                Offset(
                    x = (center.x + y).coerceIn(radius, width - radius),
                    y = (center.y + x).coerceIn(radius, height - radius),
                )
            }
            Box (modifier = Modifier.fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.campo),
                    contentScale = ContentScale.FillBounds
                )
            ){
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(
                        color = contentColor,
                        radius = radius,
                        center = center,
                    )
                    //Great
                    drawCircle(
                        color = Color.Magenta,
                        radius = width/17,
                        center = Offset(200f,250f)
                    )
                    //Great
                    drawCircle(
                        color = Color.Red,
                        radius = width/17,
                        center = Offset(width/8f,height/3f)
                    )
                    //Great
                    drawCircle(
                        color = Color.Blue,
                        radius = width/17,
                        center = Offset(700f,500f)
                    )
                    //Great
                    drawCircle(
                        color = Color.DarkGray,
                        radius = width/17,
                        center = Offset(700f,900f)
                    )
                    //Great
                    drawCircle(
                        color = Color.Green,
                        radius = width/17,
                        center = Offset(800f,1100f)
                    )
                    //Great
                    drawCircle(
                        color = Color.Cyan,
                        radius = width/17,
                        center = Offset(400f,1200f)
                    )
                    //Great
                    drawCircle(
                        color = Color.Yellow,
                        radius = width/17,
                        center = Offset(600f,1500f)
                    )
                    //Great
                    drawCircle(
                        color = Color.LightGray,
                        radius = width/17,
                        center = Offset(200f,1500f)
                    )
                    //Great
                    drawCircle(
                        color = Color.Yellow,
                        radius = width/17,
                        center = Offset(800f,300f)
                    )
                }
            }
        }
    } else {
        NotAvailableDemo(demo = Demo.ACCELEROMETER)
    }
}


