package com.pure.dekh.screens

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.annotation.RestrictTo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pure.dekh.R
import com.pure.dekh.SharedPreferenceHelper
import com.pure.dekh.components.DatePickerModal
import com.pure.dekh.components.ImageSelectorContainer
import com.pure.dekh.components.MemeItem
import com.pure.dekh.components.TextSelectorContainer
import com.pure.dekh.components.WidgetViewer
import com.pure.dekh.getDateStringFromMillis
import com.pure.dekh.ui.theme.DekhTheme
import com.pure.dekh.widget.WidgetReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


import java.text.DateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.seconds


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun WidgetScreen(paddingValues: PaddingValues){
    var dateOpenDialog by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
   val defaultDate = SharedPreferenceHelper.getCountdownMillis(context)
val defaultRandom = SharedPreferenceHelper.getRandom(context)
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(TabItem.ImageTab, TabItem.TextTab)

    var selectedMillis by remember {
        mutableLongStateOf(defaultDate)
    }

    var isRandom by remember {
        mutableStateOf(defaultRandom)
    }
var showSeconds by remember {
    mutableStateOf(false)
}
    val scope = CoroutineScope(Dispatchers.Main)

var memeItem by remember {
    mutableStateOf<MemeItem>(MemeItem(R.drawable.img,""))
}
    LaunchedEffect(selectedMillis) {
        // This block will run whenever selectedMillis changes
        SharedPreferenceHelper.saveCountdownMillis(context, selectedMillis)
    }
     CoroutineScope(Dispatchers.Main).launch {
         WidgetReceiver().updateAllWidgets(context = context)
     }
    Column(modifier = Modifier.padding(  paddingValues),) {
        Column(modifier = Modifier.padding(8.dp)) {


        WidgetViewer(selectedMillis = selectedMillis, showSeconds =showSeconds,memeItem=memeItem )

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {




                TextButton(onClick = { dateOpenDialog = true },modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .background(color=MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(16.dp)) ) {
Text(text = "Exam Date: ${DateFormat.getDateInstance().format(selectedMillis)}")



            }


            if (dateOpenDialog) {

            DatePickerModal(onDismiss = {dateOpenDialog=false   }, onSelected = {  selectedMillis=it} )}
        }


//        Row(modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween) {
//            Text(text = "Show seconds")
//            Switch(checked = showSeconds, onCheckedChange ={showSeconds=it} )
//        }
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Random")
                Switch(checked = isRandom, onCheckedChange ={SharedPreferenceHelper.saveRandom(context,it);isRandom=it} )
            }

            Column() {
                Column(modifier = Modifier.padding(8.dp)) {

                    PrimaryTabRow(
                        selectedTabIndex = selectedTabIndex,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        tabs.forEachIndexed { index, tab ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = { Text(text = tab.title) },

                            )
                        }
                    }

                    // Display content based on the selected tab
                    when (selectedTabIndex) {
                        0 -> ImageSelectorContainer { memeItem = it }
                        1 -> TextSelectorContainer { memeItem = it }
                    }
                }
            }
    }}
}



@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun WidgetScreenPreview() {
    DekhTheme {
        Surface {
            WidgetScreen(paddingValues = PaddingValues(0.dp))
        }
    }
}

sealed class TabItem(val title: String, val icon: ImageVector) {
    object ImageTab : TabItem("Image", Icons.Default.Call)
    object TextTab : TabItem("Text", Icons.Default.Add)
}
