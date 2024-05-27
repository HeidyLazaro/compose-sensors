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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

        var topCount by remember { mutableStateOf(0) }
        var bottomCount by remember { mutableStateOf(0) }
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

            //ancho de la porteria y tamaño acorde a la pantalla
            val goalHeight = with(LocalDensity.current) { 80.dp.toPx() }
            val goalWidth = width / 5

            //posicion de las porterias
            val topGoalRect = Offset((width - goalWidth) / 2, 100f) to Offset((width + goalWidth) / 2, goalHeight)
            val bottomGoalRect =
                Offset((width - goalWidth) / 2, height - goalHeight) to Offset((width + goalWidth) / 2, height - 100)

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

            if (center.y <= topGoalRect.second.y && center.x in topGoalRect.first.x..topGoalRect.second.x) {
                topCount++
                center = Offset(width / 2, height / 2)
            } else if (center.y >= bottomGoalRect.first.y && center.x in bottomGoalRect.first.x..bottomGoalRect.second.x) {
                bottomCount++
                center = Offset(width / 2, height / 2)
            }

            Box(
                modifier = Modifier.fillMaxSize()
                    .paint(
                        painterResource(id = R.drawable.campo),
                        contentScale = ContentScale.FillBounds
                    )
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    //Porteria superior
                    drawRect(
                        color = Color.LightGray,
                        topLeft = topGoalRect.first,
                        size = androidx.compose.ui.geometry.Size(
                            topGoalRect.second.x - topGoalRect.first.x,
                            topGoalRect.second.y - topGoalRect.first.y
                        )
                    )
                    //Jugador cancha superior
                    drawCircle(
                        color = Color.Magenta,
                        radius = width / 17,
                        center = Offset(width / 7 * 2, height / 22 * 3),
                    )
                    //Jugador cancha superior
                    drawCircle(
                        color = Color.Red,
                        radius = width / 17,
                        center = Offset(width / 7 * 6, height / 11 * 2)
                    )
                    //Jugador cancha superior
                    drawCircle(
                        color = Color.Green,
                        radius = width / 17,
                        center = Offset(width / 7 * 5, height / 11 * 3)
                    )
                    //Jugador cancha superior
                    drawCircle(
                        color = Color.Yellow,
                        radius = width / 17,
                        center = Offset(width / 7 * 3, height / 11 * 4)
                    )
                    //Jugador cancha superior (rosa)
                    drawCircle(
                        color = Color.Cyan,
                        radius = width / 17,
                        center = Offset(width / 7 * 4, height / 11 * 5)
                    )

                    //Jugador usuario
                    drawCircle(
                        color = contentColor,
                        radius = radius,
                        center = center,
                    )

                    //Jugador cancha inferior
                    drawCircle(
                        color = Color.Magenta,
                        radius = width / 17,
                        center = Offset(width / 7 * 3, height / 11 * 7)
                    )
                    //Jugador cancha inferior
                    drawCircle(
                        color = Color.Blue,
                        radius = width / 17,
                        center = Offset(width / 7 * 1, height / 11 * 6)
                    )
                    //Jugador cancha inferior
                    drawCircle(
                        color = Color.Cyan,
                        radius = width / 17,
                        center = Offset(width / 7 * 6, height / 11 * 7)
                    )
                    //Jugador cancha inferior
                    drawCircle(
                        color = Color.Yellow,
                        radius = width / 17,
                        center = Offset(width / 7 * 5, height / 11 * 8)
                    )
                    //Jugador cancha inferior
                    drawCircle(
                        color = Color.Red,
                        radius = width / 17,
                        center = Offset(width / 7 * 2, height / 11 * 9)
                    )
                    //Porteria cancha inferior
                    drawRect(
                        color = Color.LightGray,
                        topLeft = bottomGoalRect.first,
                        size = androidx.compose.ui.geometry.Size(
                            bottomGoalRect.second.x - bottomGoalRect.first.x,
                            bottomGoalRect.second.y - bottomGoalRect.first.y
                        )
                    )
                }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(16.dp)
                            .align(Alignment.CenterStart),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "$bottomCount",
                            color = Color.Red,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.graphicsLayer {
                                rotationZ = 90f
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "$topCount",
                            color = Color.Cyan,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.graphicsLayer {
                                rotationZ = 90f
                            }
                        )
                    }
                }
            }

    } else {
        NotAvailableDemo(demo = Demo.ACCELEROMETER)
    }
}


