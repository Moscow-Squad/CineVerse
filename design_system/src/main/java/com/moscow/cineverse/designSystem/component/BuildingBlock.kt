package com.moscow.cineverse.designSystem.component

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R

@Composable
fun BuildingBlock(
    isSelected: Boolean,
    onClick: () -> Unit,
    @DrawableRes iconRes: Int,
    title: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    selectedTint: Color = Theme.colors.brand.primary,
    unselectedTint: Color = Theme.colors.shade.tertiary,
) {
    val animatedTint by animateColorAsState(
        targetValue = if (isSelected) selectedTint else unselectedTint,
        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
        label = "iconTint"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            tint = animatedTint,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = title,
            style = Theme.textStyle.label.medium.semiBold,
            color = animatedTint,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BuildingBlockPreview() {
    var selected by remember { mutableStateOf(false) }

    BuildingBlock(
        isSelected = selected,
        onClick = { selected = !selected },
        iconRes = R.drawable.outline_home,
        title = "Home"
    )
}