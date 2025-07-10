package com.moscow.cineverse.designSystem.component.movieSeriesDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.CircleImage

@Composable
fun MovieReviewCard(modifier: Modifier = Modifier) {

    Column(modifier) {


    }
}

@Composable
private fun UserInfo(
    userImage:Painter?,
    modifier: Modifier = Modifier) {
     Row(
         modifier,
         verticalAlignment = Alignment.CenterVertically,
         horizontalArrangement = Arrangement.spacedBy(8.dp)
     ){
         CircleImage(
             isLoading = false,
             image = userImage
         )
         Column {
             Text("Shrouk Mohamed")
             Text("shrookmohaned 200")
         }

     }

}
@Preview
@Composable
private fun PreviewMovieReviewCard() {

}