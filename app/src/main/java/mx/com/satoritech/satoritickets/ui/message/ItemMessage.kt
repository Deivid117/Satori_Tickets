package mx.com.satoritech.satoritickets.ui.message

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.com.satoritech.satoritickets.ui.ui.Avenir
import mx.com.satoritech.satoritickets.ui.ui.messageItem
import mx.com.satoritech.satoritickets.ui.ui.pictonBlue

// Medían 50 dp
@Composable
fun ItemRigth(message: String, hour: String){
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 50.dp, end = 10.dp),
        horizontalAlignment = Alignment.End
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Box(
            Modifier
                .background(
                    color = pictonBlue,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 20.dp)
                    //shape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp, bottomStart = 5.dp)
                )
                .padding(vertical = 8.dp, horizontal = 12.dp)
                //.padding(5.dp)
        ) {
            Column() {
                Text(
                    text = message,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Avenir,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Box(Modifier.align(Alignment.End)) {
                    Text(
                        text = hour,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Avenir,
                    )
                }
            }
        }
        /*Box(){
            Canvas(modifier = Modifier.size(5.dp), onDraw = {
                val trianglePath = Path().apply {
                    //Punta superior izquierda
                    moveTo(-40f, 0f)
                    //Punta superior derecha
                    lineTo(10f, 0f)
                    //Punta inferior derecha
                    lineTo(10f, 30f)
                }
                drawPath(
                    color = pictonBlue,
                    path = trianglePath
                )
            })
        }*/
        Spacer(modifier = Modifier.height(5.dp))
    }
}

@Composable
fun ItemLeft(message: String, hour: String){
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 50.dp),
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Box(
            Modifier
                .background(
                    color = messageItem,
                    shape = RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp, bottomStart = 20.dp)
                )
                .padding(vertical = 8.dp, horizontal = 12.dp)
        ) {
            Column() {
                Text(
                    text = message,
                    fontSize = 15.sp,
                    fontFamily = Avenir,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Box(Modifier.align(Alignment.End)) {
                    Text(
                        text = hour,
                        fontSize = 12.sp,
                        fontFamily = Avenir,
                    )
                }
            }
        }
        /*Box(){
            Canvas(modifier = Modifier.size(5.dp), onDraw = {
                val trianglePath = Path().apply {
                    //Punta superior derecha
                    moveTo(60f, 0f)
                    //Punta superior izquierda
                    lineTo(0f, 0f)
                    //Punta inferior izquierda
                    lineTo(0f, 30f)
                }
                drawPath(
                    color = messageItem,
                    path = trianglePath
                )
            })
        }*/
        Spacer(modifier = Modifier.height(5.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    ItemRigth("Jelou there", "12:00")
    Spacer(modifier = Modifier.height(20.dp))
    ItemLeft("Jelou there", "12:00")
}