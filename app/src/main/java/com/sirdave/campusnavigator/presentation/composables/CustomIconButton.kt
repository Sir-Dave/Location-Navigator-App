package com.sirdave.campusnavigator.presentation.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sirdave.campusnavigator.R

@Composable
fun CustomIconButton(
    onClick: () -> Unit,
    text: String,
    @DrawableRes drawable: Int,
    buttonColor: ButtonColors = ButtonDefaults.outlinedButtonColors(
        contentColor = Color.Black
    ),
    modifier: Modifier = Modifier
){
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = buttonColor,
        modifier = modifier
    ) {
        Row {
            Icon(
                painter = painterResource(id = drawable),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                fontSize = 15.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IconButtonPreview() {
    CustomIconButton(
        onClick = { },
        text = "8 min",
        drawable = R.drawable.baseline_directions_car
    )
}