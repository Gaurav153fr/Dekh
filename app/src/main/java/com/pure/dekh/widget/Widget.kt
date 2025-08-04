package com.pure.dekh.widget

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.BackgroundModifier
import androidx.glance.Button
import androidx.glance.ButtonColors
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.LocalGlanceId
import androidx.glance.appwidget.GlanceAppWidget

import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontStyle
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.pure.dekh.R
import com.pure.dekh.SharedPreferenceHelper
import com.pure.dekh.components.MemeItem
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

/**
 * The GlanceAppWidget for displaying a countdown.
 */
class Widget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                CountdownWidgetContent()
            }
        }
    }

    /**
     * Composable function for the widget's UI content.
     */
    @Composable
    private fun CountdownWidgetContent() {
        // Get the context from the Glance composition local
        val context = LocalContext.current
val random = SharedPreferenceHelper.getRandom(context)
        // State to hold the formatted countdown text and a key to trigger refresh
        var countdownText by remember { mutableStateOf("Calculating...") }
        var refreshKey by remember { mutableStateOf(0) }
        var days by remember { mutableStateOf(0L) }
        var hours by remember { mutableStateOf(0L) }
        var minutes by remember { mutableStateOf(0L) }
        var seconds by remember { mutableStateOf(0L) }

        // Get the target countdown date from SharedPreferences
        val targetMillis = SharedPreferenceHelper.getCountdownMillis(context)
var image_id = SharedPreferenceHelper.getImageId(context)
        var text_id = SharedPreferenceHelper.getTextId(context)
        // LaunchedEffect runs a coroutine tied to the composable lifecycle.
        // It's used here to update the countdown every second.
        // The `refreshKey` is a dependency that forces the effect to restart.

        if (random){
            val items = listOf(
                MemeItem(id = R.drawable.img, text = "Padhle Bsdk"),
                MemeItem(id = R.drawable.img_1, text = "padh rha hai na"),
                MemeItem(id = R.drawable.middle_finger, text = "F*ck you"),
                MemeItem(id=R.drawable.badmoshi,text = "Badmoshi nahi"),
                MemeItem(id = R.drawable.img_2, text = "Padhai kab?"),
                MemeItem(id = R.drawable.img_3, text = "padhle nahi to yahi hal hoga?"),
                MemeItem(id = R.drawable.img_4, text = "time waste mat kar DSA kar"),
                MemeItem(id = R.drawable.img_5, text = "paper hai padhle bkl"),


                )
            val random_meme = items.random()
            image_id = random_meme.id;
            text_id=    random_meme.text;
        }
        LaunchedEffect(key1 = targetMillis, key2 = refreshKey) {
            while (true) {
                val currentMillis = System.currentTimeMillis()
                val remainingMillis = targetMillis - currentMillis

                if (remainingMillis <= 0) {
                    countdownText = "Happy Event Day!"
                    break
                }

                // Calculate and format the remaining time
                days = TimeUnit.MILLISECONDS.toDays(remainingMillis)
                 hours = TimeUnit.MILLISECONDS.toHours(remainingMillis) % 24
                minutes = TimeUnit.MILLISECONDS.toMinutes(remainingMillis) % 60
                seconds = TimeUnit.MILLISECONDS.toSeconds(remainingMillis) % 60

                countdownText = "$days D, $hours H, $minutes M, $seconds S"

                // Wait for a second before recalculating
                delay(1000)
            }
        }
Box(modifier = GlanceModifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
Row(modifier = GlanceModifier.background(GlanceTheme.colors.primaryContainer).fillMaxSize(), horizontalAlignment = Alignment.End, verticalAlignment = Alignment.Vertical.Bottom) {
    Spacer(modifier = GlanceModifier.width(70.dp))
    Image(
        provider = ImageProvider(image_id),
        contentDescription = "A descriptive text for the image",
modifier = GlanceModifier.fillMaxWidth()


        )}
        // The widget layout
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Row(modifier = GlanceModifier.defaultWeight(), ) {
            // Countdown text
      Column(modifier = GlanceModifier.defaultWeight().background(imageProvider = ImageProvider(R.drawable.gradient), colorFilter = ColorFilter.tint(GlanceTheme.colors.primaryContainer)),horizontalAlignment = Alignment.Start, verticalAlignment = Alignment.CenterVertically)  {

                    Row(verticalAlignment = Alignment.Bottom, horizontalAlignment = Alignment.CenterHorizontally, modifier = GlanceModifier.fillMaxWidth()){
                        Text(
                            text = days.toString(),
                            style = TextStyle(fontSize = 48.sp,
                                fontWeight = FontWeight.Bold,
                                color = GlanceTheme.colors.onPrimaryContainer
                                )

                        )
                       Spacer(modifier = GlanceModifier.width(5.dp))
                       Text(text = "d",style = TextStyle(
                           color = GlanceTheme.colors.onPrimaryContainer
                       ))
                    }
          Row(verticalAlignment = Alignment.Bottom){
              Text(
                  text = hours.toString(),
                  style = TextStyle(fontSize = 24.sp,
                      fontWeight = FontWeight.Bold,
                      color = GlanceTheme.colors.onPrimaryContainer
                  )

              )
              Spacer(modifier = GlanceModifier.width(2.dp))
              Text(text = "h",style = TextStyle(
                  color = GlanceTheme.colors.onPrimaryContainer
              ))
              Spacer(modifier = GlanceModifier.width(10.dp))
              Row{
                  Text(
                      text = minutes.toString(),
                      style = TextStyle(fontSize = 24.sp,
                          fontWeight = FontWeight.Bold,
                          color = GlanceTheme.colors.onPrimaryContainer
                      )

                  )
                  Spacer(modifier = GlanceModifier.width(2.dp))
                  Text(text = "m",style = TextStyle(
                      color = GlanceTheme.colors.onPrimaryContainer
                  ))
              }
          }
          Spacer(modifier = GlanceModifier.height(5.dp))

          Row(verticalAlignment = Alignment.Bottom, horizontalAlignment = Alignment.CenterHorizontally, modifier = GlanceModifier.fillMaxWidth()){
          Text(
                  text = seconds.toString(),
                  style = TextStyle(fontSize = 14.sp,
                      fontWeight = FontWeight.Bold,
                      color = GlanceTheme.colors.onPrimaryContainer
                  )

              )
              Spacer(modifier = GlanceModifier.width(5.dp))
              Text(text = "s",style = TextStyle(
                  color = GlanceTheme.colors.onPrimaryContainer
              ))
          }
                }


            }

            Row (modifier = GlanceModifier.fillMaxWidth()){
                Text(
                    modifier = GlanceModifier.defaultWeight().background(imageProvider = ImageProvider(R.drawable.gradient), colorFilter = ColorFilter.tint(GlanceTheme.colors.primaryContainer)),
                    text = text_id,

                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Italic,
                        fontSize = 16.sp,
                        color = GlanceTheme.colors.onPrimaryContainer
                    )
                )

                // Refresh button
                Button(
                       text="⟳",

                    onClick = {
                        // Update the refresh key to trigger a recomposition and restart the countdown
                        refreshKey++
                    }
                )
            }
        }}
    }
}
