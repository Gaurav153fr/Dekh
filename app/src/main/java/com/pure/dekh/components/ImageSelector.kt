package com.pure.dekh.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pure.dekh.R
import com.pure.dekh.SharedPreferenceHelper

@Composable
fun ImageSelectorContainer(setMeme: (MemeItem) -> Unit) {
    // Use Kotlin's List and populate it with some data, for example, 2 items
    val context = LocalContext.current
    val curr_meme:Int = SharedPreferenceHelper.getImageId(context)
    var selected by remember { mutableIntStateOf(curr_meme) }
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

    // LazyVerticalGrid with two columns

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(items.size) { item ->
            // Your ImageItem composable here
            ImageItem(items[item],setMeme,selected = items[item].id==selected,onClick = {selected=it})
        }
    }
}

@Composable
fun ImageItem(memeItem: MemeItem,setMeme: (MemeItem) -> Unit,context: Context = LocalContext.current,selected:Boolean=false,onClick:(id:Int)->Unit) {
    val selected_modifier = if (selected) Modifier.background(color = MaterialTheme.colorScheme.primaryContainer, shape = MaterialTheme.shapes.medium) else Modifier
    Box(
        modifier = selected_modifier
            .fillMaxWidth()
            .aspectRatio(1.0f)
            .padding(10.dp)
            .clip(RoundedCornerShape(16.dp)) // Clip image inside a rounded shape
            .border(width = 2.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center,

    ) {
        Image(
            painter = painterResource(id = memeItem.id),
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxSize()
                .clickable { setMeme(memeItem);SharedPreferenceHelper.saveImageId(context = context,imageId = memeItem.id);SharedPreferenceHelper.saveTextId(context=context, memeItem.text) ;onClick(memeItem.id)}
            ,
            alignment = Alignment.BottomCenter
        )
    }
}




data class MemeItem(val id: Int, val text: String)


@Preview
@Composable
fun ImageContainerPreview() {
    ImageSelectorContainer(setMeme = {})
}
