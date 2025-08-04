package com.pure.dekh.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDismiss: () -> Unit,
    onSelected: (Long) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),

        )

    DatePickerDialog(
        modifier = Modifier.padding(32.dp),
        onDismissRequest = {onDismiss()},

        confirmButton = {
            TextButton(onClick = {
                val selectedDateMillis = datePickerState.selectedDateMillis

                if (selectedDateMillis != null) {
                    onSelected(selectedDateMillis)
                }
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        Box(
            modifier = Modifier.padding(start =16.dp,end = 16.dp)
        ) {
            DatePicker(state = datePickerState)
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DatePickerModalPreview() {
    DatePickerModal(onDismiss = {}, onSelected = {})

}