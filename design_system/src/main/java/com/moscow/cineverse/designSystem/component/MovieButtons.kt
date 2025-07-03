package com.moscow.cineverse.designSystem.component

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.designSystem.utils.applyIf

@Composable
fun MovieButton(
    buttonText:String,
    textColor: Color,
    textStyle: TextStyle,
    onClick:()->Unit,
    modifier: Modifier = Modifier,
    buttonColor: Color?=null,
    cornerRadius: Dp = Theme.radius.large,
    enable:Boolean = true,
    isLoading: Boolean = false
) {

    Row(
        modifier = Modifier
            .applyIf(buttonColor != null){
                val backgroundColor by animateColorAsState(
                    if(enable) buttonColor!! else Theme.colors.button.disabled
                )
                this.background(color = backgroundColor, shape = RoundedCornerShape(cornerRadius))
            }
            .clickable{ if (enable) onClick()}
            .then(modifier)

    )
    {

        val color by animateColorAsState(if(!enable) Theme.colors.button.onDisabled else textColor)


        AnimatedContent(isLoading) { state ->
            if(state == false )
                Text(
                    text = buttonText,
                    color =color,
                    style = textStyle
                    )

                // TODO:should show circular progress bar when isLoading is true
        }
    }
}

@Preview
@Composable
private fun PreviewButton() {
    var isLoading by remember { mutableStateOf(false) }
    var isEnabled by remember { mutableStateOf(true) }

    MovieButton(
        buttonText = "Login",
        textColor = Color.Blue,
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily.Monospace ,
            fontWeight = FontWeight.W500
        ),
        isLoading = isLoading,
        enable = isEnabled,
        onClick = {
            isEnabled = !isEnabled

        },
        buttonColor = Theme.colors.button.primary,
        modifier = Modifier
            .padding(horizontal = 24.dp , vertical = 16.dp)
    )
}