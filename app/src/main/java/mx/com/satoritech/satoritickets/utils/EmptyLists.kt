package mx.com.satoritech.satoritickets.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import mx.com.satoritech.satoritickets.ui.ui.Avenir

@Composable
fun EmpyData() {
    Column(
        modifier = Modifier.fillMaxSize().padding(top = 20.dp),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Datos no disponibles",
            fontFamily = Avenir,
            fontWeight = FontWeight.SemiBold,
            fontSize = 25.sp,
        )
    }
}


@Composable
fun NoSearchResults() {
    Column(
        modifier = Modifier.fillMaxSize().padding(top = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            "Sin coincidencias",
            fontFamily = Avenir,
            fontWeight = FontWeight.SemiBold,
            fontSize = 25.sp,
        )
    }
}