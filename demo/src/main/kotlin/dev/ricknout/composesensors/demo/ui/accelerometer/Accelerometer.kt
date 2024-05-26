package dev.ricknout.composesensors.demo.ui.accelerometer

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Paint
import android.text.style.BackgroundColorSpan
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import dev.ricknout.composesensors.accelerometer.isAccelerometerSensorAvailable
import dev.ricknout.composesensors.accelerometer.rememberAccelerometerSensorValueAsState
import dev.ricknout.composesensors.demo.R
import dev.ricknout.composesensors.demo.model.Demo
import dev.ricknout.composesensors.demo.ui.Demo
import dev.ricknout.composesensors.demo.ui.NotAvailableDemo

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

            val goalHeight = with(LocalDensity.current) { 20.dp.toPx() }
            val goalWidth = width / 5

            //val topGoalRect = Offset((width - goalWidth) / 2, 0f) to Offset((width + goalWidth) / 2, goalHeight)
            val futTopRect = Offset(75F, 50F)  to Offset(250F, 100F)
            //val bottomGoalRect = Offset((width - goalWidth) / 2, height - goalHeight) to Offset((width + goalWidth) / 2, height)


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
            Box (modifier = Modifier.fillMaxSize()){
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(
                        color = contentColor,
                        radius = radius,
                        center = center,
                    )
                    drawRect(
                        color = Color.Cyan,
                        topLeft = futTopRect.first,
                        size = Size(futTopRect.second.x, futTopRect.first.y),

                        //size = androidx.compose.ui.geometry.Size(futTopRect.second.x - bottomGoalRect.first.x, bottomGoalRect.second.y - bottomGoalRect.first.y)
                    )
                    /* drawRect(
                         color = Color.Magenta,
                         topLeft = futTopRect.first,
                         size = androidx.compose.ui.geometry.Size(topGoalRect.second.x - topGoalRect.first.x, topGoalRect.second.y - topGoalRect.first.y)
                     )*/
                }
            }
        }
    } else {
        NotAvailableDemo(demo = Demo.ACCELEROMETER)
    }
}
