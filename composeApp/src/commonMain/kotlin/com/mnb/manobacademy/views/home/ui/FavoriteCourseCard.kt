package com.mnb.manobacademy.views.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mnb.manobacademy.models.FavoriteCourse
import com.mnb.manobacademy.ui.theme.dimens
import manobacademykmp.composeapp.generated.resources.Res
import manobacademykmp.composeapp.generated.resources.hero_1
import manobacademykmp.composeapp.generated.resources.home_class_icon_desc
import manobacademykmp.composeapp.generated.resources.home_enrolled
import manobacademykmp.composeapp.generated.resources.home_lectures
import manobacademykmp.composeapp.generated.resources.home_rating_desc
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun FavoriteCourseCard(
    course: FavoriteCourse,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dimens = MaterialTheme.dimens
    Card(
        modifier = modifier.fillMaxWidth().clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = dimens.cardElevation),
        shape = RoundedCornerShape(dimens.cardCornerRadiusLarge),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().height(dimens.favoriteClassImageHeight),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(Res.drawable.hero_1),
                contentDescription = stringResource(Res.string.home_class_icon_desc),
                contentScale = ContentScale.Crop
                // TODO: Replace with Image(painterResource(course.imageUrl)...)
            )
            Row(modifier = Modifier.padding(dimens.paddingMedium)) {
                Spacer(modifier = Modifier.width(dimens.spacingMedium))
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // TODO: Fix text background
                        Text(
                            text = stringResource(Res.string.home_lectures, course.lectures),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(dimens.paddingSmall))
                                .padding(dimens.paddingSmall).fillMaxWidth(0.25f),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = stringResource(Res.string.home_enrolled, course.enrolled),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                    Spacer(modifier = Modifier.height(dimens.spacingSmall))
                    Text(
                        text = course.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    Text(
                        text = "By ${course.instructor}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    Spacer(modifier = Modifier.height(dimens.spacingSmall))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = stringResource(Res.string.home_rating_desc, course.rating.toString()),
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(dimens.spacingSmall))
                            Text(
                                text = "%.1f".format(course.rating),
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }
/*                        TextButton(onClick = onClick, contentPadding = PaddingValues(0.dp)) {
                            Text(stringResource(Res.string.home_more))
                        }*/
                    }
                }
            }
        }

    }
}
