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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.window.Dialog

import  androidx.compose.material3.Button
import com.pure.dekh.SharedPreferenceHelper

@Composable
fun TextSelectorContainer(setMeme: (MemeItem) -> Unit) {
    // Use Kotlin's List and populate it with some data, for example, 2 items
    val context = LocalContext.current
    val currTxt:String = SharedPreferenceHelper.getTextId(context)
    var selected by remember { mutableStateOf(currTxt) }
    val items = listOf("ha bhai fine shyt padhle","nalle padhle")
var isDialogueOpen = remember { mutableStateOf(false) }
    var customText = remember { mutableStateOf("") }
    // LazyVerticalGrid with two columns
Column {
    Button(onClick = { isDialogueOpen.value = true }) {
        Text("Show Custom Text Dialog")
    }
CustomTextDialogue(isDialogueOpen.value,customText.value,{isDialogueOpen.value=false},{customText.value=it},{SharedPreferenceHelper.saveTextId(context,customText.value)})
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {

    items(items.size) { item ->
            // Your ImageItem composable here

            TextItem(items[item],selected = items[item]==selected,onClick = {selected=it})
        }
    }
}

}
@Composable
fun TextItem(text: String, context: Context = LocalContext.current, selected:Boolean=false, onClick:(text:String)->Unit) {
    val selectedModifier = if (selected) Modifier.background(color = MaterialTheme.colorScheme.primaryContainer, shape = MaterialTheme.shapes.medium) else Modifier
    Box(
        modifier = selectedModifier
            .fillMaxWidth()


            // Clip image inside a rounded shape
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center,

        ) {
      Text(text = text)
    }
}


@Composable
fun CustomTextDialogue(
    isDialogueOpen: Boolean,
    customText: String,
    onDismissRequest: () -> Unit,
    onTextChange: (String) -> Unit,
    onDoneClick: () -> Unit
) {
    if (isDialogueOpen) {
        Dialog(onDismissRequest = onDismissRequest) {
            // A Column or Box is usually used inside the Dialog
            // to contain the content
            Column(
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text(text = "Enter Custom Text")
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = customText,
                    onValueChange = onTextChange,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { onDismissRequest();onDoneClick() },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Done")
                }
            }
        }
    }
}

data class TextSetting(val text: String, val color: Color, val fontSize: Float)


@Preview
@Composable
fun TextContainerPreview() {
    TextSelectorContainer (setMeme = {})
}
