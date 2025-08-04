package com.pure.dekh.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pure.dekh.R
import com.pure.dekh.getDateStringFromMillis
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun WidgetViewer(selectedMillis:Long,showSeconds:Boolean,memeItem:MemeItem,){

    val todayMillis = System.currentTimeMillis()

    var remainingMillis by remember {
        mutableLongStateOf(selectedMillis-todayMillis)
    }
    var remainingDaysString by remember {
        mutableStateOf(getDateStringFromMillis(remainingMillis))}

    LaunchedEffect(selectedMillis,showSeconds) {  // Re-run when selectedDate changes
        while (true) {
            val now = System.currentTimeMillis()
            remainingMillis = selectedMillis - now  // Calculate the remaining time correctly
            remainingDaysString = getDateStringFromMillis(remainingMillis) // Update string

            delay(1.seconds) // Wait for 1 second before recalculating
        }
    }
    Row(modifier = Modifier
        .height(160.dp)

        .padding(8.dp),
        ){
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(start = 8.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth(1/2f).fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
//                Text(text = remainingDaysString, fontSize = 20.sp)
                Column( horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center) {
                        Text(text = remainingDaysString.days.toString(), fontSize = 40.sp)
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(text = "D")
                    }

                    Row {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(text = remainingDaysString.hours.toString(), fontSize = 30.sp)
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(text = "h")
                        }
                        Spacer(modifier = Modifier.width(2.dp))
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(text = remainingDaysString.minutes.toString(), fontSize = 30.sp)
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(text = "m")
                        }
                        if (showSeconds){
                            Spacer(modifier = Modifier.width(2.dp))
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(text = remainingDaysString.seconds.toString(), fontSize = 30.sp)
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(text = "s")
                        }}

                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = memeItem.text)
            }
Box(modifier =Modifier.fillMaxHeight().fillMaxWidth(2/3f) ) {
    Image(painter = painterResource(id = memeItem.id), contentDescription = "meme image", modifier = Modifier.fillMaxSize())
}
        }

    }
}



@Preview(showBackground = true)
@Composable
fun WidgetViewerPreview(){
    WidgetViewer(selectedMillis = 99999999, showSeconds = true, memeItem = MemeItem(R.drawable.img_2,"Padhle bsdk"))
}