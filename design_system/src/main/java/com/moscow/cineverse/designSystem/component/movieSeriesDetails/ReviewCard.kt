package com.moscow.cineverse.designSystem.component.movieSeriesDetails

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.component.CircleImage
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun MovieReviewCard(
    name: String,
    username: String,
    reviewText: String,
    rating: Int,
    date: String,
    avatar: Painter?,
    modifier: Modifier = Modifier
) {

    Column(modifier
        .fillMaxWidth()
        .background(
            color = Theme.colors.background.card,
            shape = RoundedCornerShape(Theme.radius.large)
        )
        .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {

        UserInfo(
             avatar,
             name,
             username
        )

        ExpandableText(
            text = reviewText
        )



        Row (Modifier.fillMaxWidth()){

            MovieRatingBar(rating,{})

            Spacer(Modifier.weight(1f))

            Text(
                text = date,
                style = Theme.textStyle.body.small.regular,
                color = Theme.colors.shade.secondary,
            )
        }


    }
}

@Composable
private fun UserInfo(
    userImage: Painter?,
    userName:String,
    userEmail:String,
    modifier: Modifier = Modifier) {
     Row(
         modifier,
         verticalAlignment = Alignment.CenterVertically,
         horizontalArrangement = Arrangement.spacedBy(8.dp)
     ){
         CircleImage(
             isLoading = false,
             image = userImage,
             modifier = Modifier.size(40.dp)
         )
         Column {

             Text(
                 text = userName,
                 style = Theme.textStyle.body.medium.medium,
                 color = Theme.colors.shade.primary,
                 maxLines = 1,
                 overflow = TextOverflow.Ellipsis
             )

             Text(
                 text = userEmail,
                 style = Theme.textStyle.body.small.regular,
                 color = Theme.colors.shade.secondary,
                 maxLines = 1,
                 overflow = TextOverflow.Ellipsis
             )
         }

     }

}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, backgroundColor = 1)
@Composable
private fun PreviewMovieReviewCard() {

    CineVerseTheme {
        MovieReviewCard(
            "Shrouk Mohamed",
            "@ShroukMohamed16",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            3,
            "Aug 15, 2025",
           painterResource(R.drawable.outline_user)
        )
    }
}