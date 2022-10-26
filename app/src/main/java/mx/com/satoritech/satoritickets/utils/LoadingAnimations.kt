package mx.com.satoritech.satoritickets.utils

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.*
import mx.com.satoritech.satoritickets.R
import mx.com.satoritech.satoritickets.ui.ui.Avenir
import mx.com.satoritech.satoritickets.ui.ui.pictonBlue

@Composable
fun DialogLoading(
    cornerRadius: Dp = 16.dp,
    paddingStart: Dp = 56.dp,
    paddingEnd: Dp = 56.dp,
    paddingTop: Dp = 32.dp,
    paddingBottom: Dp = 32.dp,
    progressIndicatorColor: Color = pictonBlue,
    progressIndicatorSize: Dp = 80.dp
) {
    val isLottiePlaying by remember {
        mutableStateOf(true)
    }

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loading_circle)
    )

    val lottieAnimation by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isLottiePlaying
    )

    Dialog( onDismissRequest = {}) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 10.dp,
            shape = RoundedCornerShape(cornerRadius)
        ) {
            Column(
                modifier = Modifier
                    //
                    .padding(horizontal = 30.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CompositionAnimation(composition, lottieAnimation, 200)
//                ProgressIndicatorLoading(
//                    progressIndicatorSize = progressIndicatorSize,
//                    progressIndicatorColor = progressIndicatorColor
//                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Cargando...",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontFamily = Avenir,
                )
            }
        }
    }
}

@Composable
fun ProgressIndicatorLoading(progressIndicatorSize: Dp, progressIndicatorColor: Color) {

    val infiniteTransition = rememberInfiniteTransition()

    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = keyframes { durationMillis = 600 }
        )
    )

    CircularProgressIndicator(
        progress = 1f,
        modifier = Modifier
            .size(progressIndicatorSize)
            .rotate(angle)
            .border(
                12.dp,
                brush = Brush.sweepGradient(
                    listOf(
                        Color.White, // add background color first
                        progressIndicatorColor.copy(alpha = 0.1f),
                        progressIndicatorColor
                    )
                ),
                shape = CircleShape
            ),
        strokeWidth = 1.dp,
        color = Color.White // Set background color
    )
}

@Composable
fun PacmanDialogLoading() {
    val infiniteTransition: InfiniteTransition = rememberInfiniteTransition()
    Dialog(onDismissRequest = {}) {
        Surface(
            elevation = 4.dp,
            shape = RoundedCornerShape(15.dp)
        ) {

            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val mouthAnimation by infiniteTransition.animateFloat(
                    initialValue = 360F,
                    targetValue = 280F,
                    animationSpec = infiniteRepeatable(
                        animation = tween(800, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )

                val antiMouthAnimation by infiniteTransition.animateFloat(
                    initialValue = 0F,
                    targetValue = 40F,
                    animationSpec = infiniteRepeatable(
                        animation = tween(800, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )

                Canvas(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(100.dp)

                ) {
                    drawArc(
                        color = Color(0xFFFFd301),
                        startAngle = antiMouthAnimation,
                        sweepAngle = mouthAnimation,
                        useCenter = true,
                    )

                    drawCircle(
                        color = Color.Black,
                        radius = 15f,
                        center = Offset(x = this.center.x + 15f, y = this.center.y - 85f)
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Waka... waka...",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    fontFamily = Avenir,
                )
            }
        }
    }
}

@Composable
fun LottieLoadingCircle(){
    val isLottiePlaying by remember {
        mutableStateOf(true)
    }

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loading_circle)
    )

    val lottieAnimation by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isLottiePlaying
    )

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CompositionAnimation(
            composition,
            lottieAnimation,
            200
        )
    }
}

@Composable
fun LottieLoadingDots(){
    val isLottiePlaying by remember {
        mutableStateOf(true)
    }

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loading_dots)
    )

    val lottieAnimation by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isLottiePlaying
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CompositionAnimation(
            composition,
            lottieAnimation,
            80
        )
    }
}

@Composable()
fun CompositionAnimation(
    composition: LottieComposition?,
    lottieAnimation: Float,
    size: Int
){
    LottieAnimation(
        composition,
        lottieAnimation,
        Modifier.size(size.dp)
    )
}