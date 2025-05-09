package com.mnb.manobacademy.views.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mnb.manobacademy.models.FavoriteCourse
import com.mnb.manobacademy.ui.theme.dimens
import manobacademykmp.composeapp.generated.resources.Res
import manobacademykmp.composeapp.generated.resources.bg_guide_1
import manobacademykmp.composeapp.generated.resources.home_class_icon_desc
import manobacademykmp.composeapp.generated.resources.home_enrolled
import manobacademykmp.composeapp.generated.resources.home_lectures
import manobacademykmp.composeapp.generated.resources.home_more
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
                painter = painterResource(Res.drawable.bg_guide_1),
                contentDescription = stringResource(Res.string.home_class_icon_desc)
            )
            Row(modifier = Modifier.padding(dimens.paddingMedium)) {
                Box(
                    modifier = Modifier
                        .size(dimens.favoriteClassImageHeight)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(dimens.cardCornerRadiusMedium))
                        .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Image,
                        contentDescription = stringResource(Res.string.home_class_icon_desc),
                        modifier = Modifier.size(dimens.paddingHuge),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    // TODO: Replace with Image(painterResource(course.imageUrl)...)
                }
                Spacer(modifier = Modifier.width(dimens.spacingMedium))
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(Res.string.home_lectures, course.lectures),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = stringResource(Res.string.home_enrolled, course.enrolled),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.height(dimens.spacingSmall))
                    Text(
                        text = course.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "By ${course.instructor}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
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
                                fontWeight = FontWeight.Bold
                            )
                        }
                        TextButton(onClick = onClick, contentPadding = PaddingValues(0.dp)) {
                            Text(stringResource(Res.string.home_more))
                        }
                    }
                }
            }
        }

    }
}
