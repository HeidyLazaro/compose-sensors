package dev.ricknout.composesensors.demo.ui.accelerometer

import android.annotation.SuppressLint
import android.content.res.Configuration
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.ricknout.composesensors.accelerometer.isAccelerometerSensorAvailable
import dev.ricknout.composesensors.accelerometer.rememberAccelerometerSensorValueAsState
import dev.ricknout.composesensors.demo.R
import dev.ricknout.composesensors.demo.model.Demo
import dev.ricknout.composesensors.demo.ui.Demo
import dev.ricknout.composesensors.demo.ui.NotAvailableDemo

@SuppressLint("ResourceAsColor")
@Composable
fun AccelerometerDemo() {
    if (isAccelerometerSensorAvailable()) {
        val sensorValue by rememberAccelerometerSensorValueAsState()
        var (x, y, z) = sensorValue.value

        var topCount by remember { mutableStateOf(0) }
        var bottomCount by remember { mutableStateOf(0) }
        Demo(
            demo = Demo.ACCELEROMETER,
            value = "X: $x m/s^2\nY: $y m/s^2\nZ: $z m/s^2",
        ) {
            val width = constraints.maxWidth.toFloat()
            val height = constraints.maxHeight.toFloat()
            val radious = width / 17
            var center by remember { mutableStateOf(Offset(width / 2, height / 2)) }
            val orientation = LocalConfiguration.current.orientation
            val contentColor1 = LocalContentColor.provides(Color.Black)
            val contentColor2 = LocalContentColor.current
            val userRadius = with(LocalDensity.current) { 10.dp.toPx() }

            //Ancho de la porteria y tamaño acorde a la pantalla
            val goalHeight = with(LocalDensity.current) { 80.dp.toPx() }
            val goalWidth = width / 5

            //Posicion de las porterias
            val topGoalRect = Offset((width - goalWidth) / 2, 100f) to Offset((width + goalWidth) / 2, goalHeight)
            val bottomGoalRect =
                Offset((width - goalWidth) / 2, height - goalHeight) to Offset((width + goalWidth) / 2, height - 100)

            //Origen de los obstaculos
            val origen1 = Offset(width / 7 * 2, height / 22 * 3)
            val origen2 = Offset(width / 7 * 6, height / 11 * 2)
            val origen3 = Offset(width / 7 * 5, height / 11 * 3)
            val origen4 = Offset(width / 7 * 3, height / 11 * 4)
            val origen5 = Offset(width / 7 * 4, height / 11 * 5)
            val origen6 = Offset(width / 7 * 3, height / 11 * 7)
            val origen7 = Offset(width / 7 * 1, height / 11 * 6)
            val origen8 = Offset(width / 7 * 6, height / 11 * 7)
            val origen9 = Offset(width / 7 * 5, height / 11 * 8)
            val origen10 = Offset(width / 7 * 2, height / 11 * 9)

            //Cajas de colisiones de obstaculos
            val collision1 = center.x in origen1.x-radious..origen1.x+radious && center.y in origen1.y-radious..origen1.y+radious
            val collision2 = center.x in origen2.x-radious..origen2.x+radious && center.y in origen2.y-radious..origen2.y+radious
            val collision3 = center.x in origen3.x-radious..origen3.x+radious && center.y in origen3.y-radious..origen3.y+radious
            val collision4 = center.x in origen4.x-radious..origen4.x+radious && center.y in origen4.y-radious..origen4.y+radious
            val collision5 = center.x in origen5.x-radious..origen5.x+radious && center.y in origen5.y-radious..origen5.y+radious
            val collision6 = center.x in origen6.x-radious..origen6.x+radious && center.y in origen6.y-radious..origen6.y+radious
            val collision7 = center.x in origen7.x-radious..origen7.x+radious && center.y in origen7.y-radious..origen7.y+radious
            val collision8 = center.x in origen8.x-radious..origen8.x+radious && center.y in origen8.y-radious..origen8.y+radious
            val collision9 = center.x in origen9.x-radious..origen9.x+radious && center.y in origen9.y-radious..origen9.y+radious
            val collision10 = center.x in origen10.x-radious..origen10.x+radious && center.y in origen10.y-radious..origen10.y+radious

// Verifica si la pelota colisiona con los bloques de obstáculos
            if (collision1 || collision2 || collision3 || collision4 || collision5 || collision6 || collision7 || collision8 || collision9 || collision10) {
                // Cambia la dirección de la pelota
                x = -x*17
                y = -y*17
            }

                center = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Offset(
                    x = (center.x - x).coerceIn(userRadius, width - userRadius),
                    y = (center.y + y).coerceIn(userRadius, height - userRadius),
                )
            } else {
                Offset(
                    x = (center.x + y).coerceIn(userRadius, width - userRadius),
                    y = (center.y + x).coerceIn(userRadius, height - userRadius),
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
                    //Tabla del contador
                    drawRect(
                        color = Color.Black,
                        topLeft = Offset(25f, height/2-60f),
                        size = androidx.compose.ui.geometry.Size(
                            35f, 120f
                        )
                    )

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
                        radius = radious,
                        center = origen1
                    )
                    //Jugador cancha superior
                    drawCircle(
                        color = Color.Red,
                        radius = radious,
                        center = origen2
                    )
                    //Jugador cancha superior
                    drawCircle(
                        color = Color.Green,
                        radius = radious,
                        center = origen3
                    )
                    //Jugador cancha superior
                    drawCircle(
                        color = Color.Yellow,
                        radius = radious,
                        center = origen4
                    )
                    //Jugador cancha superior (rosa)
                    drawCircle(
                        color = Color.Cyan,
                        radius = radious,
                        center = origen5
                    )

                    //Jugador usuario
                    drawCircle(
                        color = contentColor2,
                        radius = userRadius,
                        center = center,
                    )

                    //Jugador cancha inferior
                    drawCircle(
                        color = Color.Magenta,
                        radius = radious,
                        center = origen6
                    )
                    //Jugador cancha inferior
                    drawCircle(
                        color = Color.Blue,
                        radius = radious,
                        center = origen7
                    )
                    //Jugador cancha inferior
                    drawCircle(
                        color = Color.Cyan,
                        radius = radious,
                        center = origen8
                    )
                    //Jugador cancha inferior
                    drawCircle(
                        color = Color.Yellow,
                        radius = radious,
                        center = origen9
                    )
                    //Jugador cancha inferior
                    drawCircle(
                        color = Color.Red,
                        radius = radious,
                        center = origen10
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
                            color = Color.Green,
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


