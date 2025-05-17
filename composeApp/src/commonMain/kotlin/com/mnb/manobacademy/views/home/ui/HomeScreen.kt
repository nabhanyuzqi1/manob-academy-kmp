package com.mnb.manobacademy.views.home.ui // Ensure this matches your project structure

import androidx.compose.foundation.background
// import androidx.compose.foundation.border // Not used, can be removed
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items // Ensure this is the correct import for LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications // Specifically import for notification icon
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
// import androidx.compose.ui.layout.ContentScale // Not used, can be removed
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.mnb.manobacademy.models.Category
import com.mnb.manobacademy.models.Course
import com.mnb.manobacademy.models.FavoriteCourse
import com.mnb.manobacademy.models.Instructor
import com.mnb.manobacademy.models.NewsItem
import com.mnb.manobacademy.models.dummyCategories
import com.mnb.manobacademy.models.dummyCourses
import com.mnb.manobacademy.models.dummyInstructors
import com.mnb.manobacademy.models.dummyNewsItems
import com.mnb.manobacademy.ui.theme.dimens // Assuming AppDimens.current is used via MaterialTheme.dimens
import com.mnb.manobacademy.views.home.component.HomeComponent // Ensure this path is correct
import manobacademykmp.composeapp.generated.resources.*
// import org.jetbrains.compose.resources.painterResource // Not used directly with placeholders, but keep if you load images
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.StringResource



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(component: HomeComponent) {
    // Accessing state provided by the HomeComponent
    val state by component.state.subscribeAsState()
    // Accessing dimensions, assuming they are provided via MaterialTheme
    val dimens = MaterialTheme.dimens

    Scaffold(
        bottomBar = {
            HomeBottomNavigation(
                currentRoute = state.currentBottomNavRoute, // state.value.currentBottomNavRoute if state is Value<HomeComponent.State>
                onItemSelected = component::onBottomNavItemSelected
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // --- Bagian Atas (Greeting & Search) ---
            Column(modifier = Modifier.padding(horizontal = dimens.paddingLarge)) {
                Spacer(modifier = Modifier.height(dimens.paddingLarge))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column() {
                        Text(
                            text = stringResource(Res.string.home_greeting_short),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = state.userName, // state.value.userName if state is Value<HomeComponent.State>
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row{
                        IconButton(onClick = { component.onNotificationClicked() }) {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = stringResource(Res.string.home_notification_icon_desc)
                            )
                        }
                        IconButton(onClick = { component.onLogoutClicked() }) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = stringResource(Res.string.home_search_icon_desc)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(dimens.spacingExtraLarge))
            }


            // --- Kelas Favorit ---
            //SectionTitle(titleRes = Res.string.home_favorite_class_title)
            state.favoriteCourse?.let { course -> // state.value.favoriteCourse if state is Value<HomeComponent.State>
                FavoriteCourseCard(
                    course = course,
                    onClick = { component.onCourseClicked(course.id) },
                    modifier = Modifier.padding(horizontal = dimens.paddingLarge)
                )
            } ?: Box( // Placeholder if no favorite course
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimens.paddingLarge)
                    .height(dimens.favoriteClassImageHeight)
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(dimens.cardCornerRadiusLarge))
                    .padding(dimens.paddingMedium),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.home_no_favorite_course_placeholder),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(dimens.spacingExtraLarge))

            // --- Kategori ---
            SectionTitle(titleRes = Res.string.home_category_title)
            LazyRow(
                contentPadding = PaddingValues(horizontal = dimens.paddingLarge),
                horizontalArrangement = Arrangement.spacedBy(dimens.spacingMedium)
            ) {
                // Use state.value.categories if state is Value<HomeComponent.State>
                val categoriesToShow = state.categories.ifEmpty { dummyCategories }
                items(categoriesToShow) { category ->
                    CategoryChip(
                        category = category,
                        // Use state.value.selectedCategory if state is Value<HomeComponent.State>
                        isSelected = state.selectedCategory == category,
                        onClick = { component.onCategorySelected(category) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimens.spacingLarge))

            // --- Daftar Kelas ---

            LazyRow(
                contentPadding = PaddingValues(horizontal = dimens.paddingLarge),
                horizontalArrangement = Arrangement.spacedBy(dimens.spacingMedium)
            ) {
                // Use state.value.courses if state is Value<HomeComponent.State>
                val coursesToShow = state.courses.ifEmpty { dummyCourses }
                items(coursesToShow) { course ->
                    CourseCard(
                        course = course,
                        onClick = { component.onCourseClicked(course.id) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimens.spacingLarge))

            // --- Instruktur ---
            SectionTitle(
                titleRes = Res.string.home_instructor_title,
                actionTextRes = Res.string.home_view_all,
                onActionClick = { component.onViewAllInstructorsClicked() }
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = dimens.paddingLarge),
                horizontalArrangement = Arrangement.spacedBy(dimens.spacingMedium)
            ) {
                // Use state.value.instructors if state is Value<HomeComponent.State>
                val instructorsToShow = state.instructors.ifEmpty { dummyInstructors }
                items(instructorsToShow) { instructor ->
                    InstructorAvatar(
                        instructor = instructor,
                        onClick = { component.onInstructorClicked(instructor.id) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimens.spacingLarge))

            // --- Berita (News) ---
            SectionTitle(
                titleRes = Res.string.home_news_title,
                actionTextRes = Res.string.home_view_all,
                onActionClick = { component.onViewAllNewsClicked() }
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = dimens.paddingLarge),
                horizontalArrangement = Arrangement.spacedBy(dimens.spacingMedium)
            ) {
                // Corrected: Access newsItems from state.value if state is Value<HomeComponent.State>
                // Assuming state itself is HomeComponent.State based on `val state by component.state.subscribeAsState()`
                // If `component.state` is `Value<HomeComponent.State>`, then it should be `state.value.newsItems`
                // However, the error log for MainActivity implies `HomeComponent.State` is directly what `state` variable holds.
                // Let's assume `state` is directly `HomeComponent.State` for now as per the original code.
                // If `state.newsItems` is not found, it means `newsItems` is not part of `HomeComponent.State`.
                // Based on the provided HomeComponent, State *does* include newsItems.
                val newsItemsList = state.newsItems // This should be List<NewsItem>
                items(items = newsItemsList.ifEmpty { dummyNewsItems }) { newsItem -> // Explicitly pass to items
                    NewsCard(
                        newsItem = newsItem,
                        onClick = { component.onNewsItemClicked(newsItem.id) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimens.spacingLarge))

            // Tombol Logout
            Button(
                onClick = component::onLogoutClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimens.paddingLarge)
            ) {
                Text(stringResource(Res.string.home_logout_button))
            }
            Spacer(modifier = Modifier.height(dimens.paddingLarge))

        }
    }
}

@Composable
private fun SectionTitle(
    titleRes: StringResource,
    modifier: Modifier = Modifier,
    actionTextRes: StringResource? = null,
    onActionClick: (() -> Unit)? = null
) {
    val dimens = MaterialTheme.dimens
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimens.paddingLarge)
            .padding(bottom = dimens.spacingSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(titleRes),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        actionTextRes?.let {
            TextButton(onClick = { onActionClick?.invoke() }) {
                Text(stringResource(it))
            }
        }
    }
}


@Composable
private fun CourseCard(
    course: Course,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dimens = MaterialTheme.dimens
    Card(
        modifier = modifier.width(dimens.classCardWidth).clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = dimens.cardElevation),
        shape = RoundedCornerShape(dimens.cardCornerRadiusMedium),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimens.classCardImageHeight)
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.PhotoCamera,
                    contentDescription = stringResource(Res.string.home_class_icon_desc),
                    modifier = Modifier.size(dimens.paddingHuge),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                // TODO: Replace with Image(painterResource(course.imageUrl)...)
            }
            Column(modifier = Modifier.padding(dimens.paddingMedium)) {
                Text(
                    text = course.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(dimens.spacingExtraSmall))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = stringResource(Res.string.home_rating_desc, course.rating.toString()),
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(dimens.spacingSmall))
                    Text(
                        text = "%.1f".format(course.rating),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(dimens.spacingSmall))
                Text(
                    text = course.originalPrice,
                    style = MaterialTheme.typography.labelSmall,
                    textDecoration = TextDecoration.LineThrough,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = course.discountedPrice,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun InstructorAvatar(
    instructor: Instructor,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dimens = MaterialTheme.dimens
    Box(
        modifier = modifier
            .size(dimens.instructorAvatarSize)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .clickable(onClick = onClick)
            .padding(dimens.spacingSmall),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            Icons.Default.Person,
            contentDescription = stringResource(Res.string.home_instructor_avatar_desc),
            modifier = Modifier.fillMaxSize(0.8f),
            tint = MaterialTheme.colorScheme.onTertiaryContainer
        )
        // TODO: Replace with Image(painterResource(instructor.imageUrl)...)
    }
}

@Composable
private fun NewsCard(
    newsItem: NewsItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dimens = MaterialTheme.dimens
    Card(
        modifier = modifier
            .width(dimens.classCardWidth * 1.3f) // Example: Make news cards slightly wider
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = dimens.cardElevation),
        shape = RoundedCornerShape(dimens.cardCornerRadiusMedium),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimens.classCardImageHeight * 0.7f) // Adjust height as needed
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Article, // News-related icon
                    contentDescription = stringResource(Res.string.home_news_image_desc),
                    modifier = Modifier.size(dimens.paddingHuge),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                // TODO: Replace with Image(painterResource(newsItem.imageUrl)...)
            }
            Column(modifier = Modifier.padding(dimens.paddingMedium)) {
                Text(
                    text = newsItem.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(dimens.spacingExtraSmall))
                Text(
                    text = newsItem.source,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(dimens.spacingExtraSmall))
                Text(
                    text = newsItem.date,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

