package mx.com.satoritech.satoritickets.ui.ticket

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.*
import mx.com.satoritech.satoritickets.ui.ui.Avenir
import mx.com.satoritech.satoritickets.R

@Composable
fun AddTicketDialog(
    onDemissR: () -> Unit,
){
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.checked_done)
    )

    Dialog(onDismissRequest = { onDemissR() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 10.dp,
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .height(180.dp)
                    .padding(horizontal = 30.dp)
                    .padding(bottom = 30.dp),
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                LottieAnimation(
                    composition,
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(90.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        text = stringResource(id = R.string.addTicketSuccessful),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        fontFamily = Avenir,
                    )
                }
            }
        }
    }
}