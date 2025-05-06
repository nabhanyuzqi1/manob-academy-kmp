package com.mnb.manobacademy.features.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.* // Import semua ikon filled untuk kemudahan
import androidx.compose.material.icons.outlined.* // Import ikon outlined jika perlu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.mnb.manobacademy.models.Category
import com.mnb.manobacademy.models.Course
import com.mnb.manobacademy.models.FavoriteCourse
import com.mnb.manobacademy.models.Instructor
import com.mnb.manobacademy.ui.theme.dimens // Import dimens
import com.mnb.manobacademy.views.home.component.HomeComponent
import manobacademykmp.composeapp.generated.resources.* // Import Res
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.StringResource // Import StringResource untuk tipe data model

// Data class dan list dummy (seharusnya diimpor dari model)
// Contoh data dummy (bisa dihapus nanti)
val dummyCategories = listOf(
    Category(Res.string.home_category_art, Icons.Default.Palette),
    Category(Res.string.home_category_photography, Icons.Default.PhotoCamera),
    Category(Res.string.home_category_design, Icons.Default.Draw),
    Category(Res.string.home_category_video, Icons.Default.Videocam)
)
val dummyFavorite = FavoriteCourse("fav1", "Fotografi Live Class", "Warhub Parmungkas", 4.8f, 25, 48, "")
val dummyCourses = listOf(
    Course("c1", "Kelas Pemula Fotografi", "Fotografi", 4.7f, "Rp 90.000", "Rp 44.900", ""),
    Course("c2", "Desain Grafis Dasar", "Desain", 4.9f, "Rp 120.000", "Rp 59.900", ""),
    Course("c3", "Editing Video Mobile", "Video", 4.6f, "Rp 80.000", "Rp 39.900", "")
)
val dummyInstructors = listOf(
    Instructor("i1", "Instruktur A", ""),
    Instructor("i2", "Instruktur B", ""),
    Instructor("i3", "Instruktur C", ""),
    Instructor("i4", "Instruktur D", "")
)

// Data untuk Bottom Navigation (seharusnya diimpor dari model)
sealed class BottomNavItem(val route: String, val titleRes: StringResource, val icon: ImageVector) {
    data object Home : BottomNavItem("home", Res.string.home_bottom_nav_home, Icons.Filled.Home)
    data object Classes : BottomNavItem("classes", Res.string.home_bottom_nav_classes, Icons.Filled.School) // Ganti ikon jika perlu
    data object Checkout : BottomNavItem("checkout", Res.string.home_bottom_nav_checkout, Icons.Filled.BookmarkBorder) // Ganti ikon jika perlu
    data object Dashboard : BottomNavItem("dashboard", Res.string.home_bottom_nav_dashboard, Icons.Filled.GridView) // Ganti ikon jika perlu
    data object Profile : BottomNavItem("profile", Res.string.home_bottom_nav_profile, Icons.Filled.Person)
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Classes,
    BottomNavItem.Checkout,
    BottomNavItem.Dashboard,
    BottomNavItem.Profile
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(component: HomeComponent) {
    val state by component.state.subscribeAsState()
    val dimens = MaterialTheme.dimens

    Scaffold(
        // Tidak ada TopAppBar di desain ini
        bottomBar = {
            HomeBottomNavigation(
                currentRoute = state.currentBottomNavRoute,
                onItemSelected = component::onBottomNavItemSelected
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Terapkan padding dari Scaffold (terutama untuk bottom bar)
                .verticalScroll(rememberScrollState()) // Buat seluruh halaman bisa discroll
        ) {
            // --- Bagian Atas (Greeting & Search) ---
            Column(modifier = Modifier.padding(horizontal = dimens.paddingLarge)) {
                Spacer(modifier = Modifier.height(dimens.paddingLarge)) // Jarak dari status bar (jika edge-to-edge)
                Text(
                    text = stringResource(Res.string.home_greeting, state.userName),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(dimens.spacingMedium))
                OutlinedTextField(
                    value = state.searchQuery,
                    onValueChange = component::onSearchQueryChanged,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(stringResource(Res.string.home_search_placeholder)) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = stringResource(Res.string.home_search_icon_desc)) },
                    shape = RoundedCornerShape(dimens.cardCornerRadiusLarge), // Sudut bulat
                    colors = OutlinedTextFieldDefaults.colors( // Warna custom (opsional)
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(dimens.spacingLarge))
            }

            // --- Kelas Favorit ---
            SectionTitle(titleRes = Res.string.home_favorite_class_title)
            state.favoriteCourse?.let { course ->
                FavoriteCourseCard(
                    course = course,
                    onClick = { component.onCourseClicked(course.id) },
                    modifier = Modifier.padding(horizontal = dimens.paddingLarge)
                )
            }
            Spacer(modifier = Modifier.height(dimens.spacingLarge))

            // --- Kategori ---
            LazyRow(
                contentPadding = PaddingValues(horizontal = dimens.paddingLarge),
                horizontalArrangement = Arrangement.spacedBy(dimens.spacingMedium)
            ) {
                items(state.categories) { category ->
                    CategoryChip(
                        category = category,
                        isSelected = state.selectedCategory == category,
                        onClick = { component.onCategorySelected(category) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimens.spacingLarge))

            // --- Daftar Kelas ---
            SectionTitle(
                titleRes = Res.string.home_bottom_nav_classes, // Judul bagian kelas
                actionTextRes = Res.string.home_view_all,
                onActionClick = { /* TODO: Navigasi ke halaman semua kelas */ }
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = dimens.paddingLarge),
                horizontalArrangement = Arrangement.spacedBy(dimens.spacingMedium)
            ) {
                items(state.courses) { course ->
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
                onActionClick = { /* TODO: Navigasi ke halaman semua instruktur */ }
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = dimens.paddingLarge),
                horizontalArrangement = Arrangement.spacedBy(dimens.spacingMedium)
            ) {
                items(state.instructors) { instructor ->
                    InstructorAvatar(
                        instructor = instructor,
                        onClick = { component.onInstructorClicked(instructor.id) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimens.paddingLarge)) // Spasi di akhir scroll

            // Tombol Logout (Contoh penempatan, bisa dipindah ke profil)
            Button(
                onClick = component::onLogoutClicked,
                modifier = Modifier.padding(horizontal = dimens.paddingLarge)
            ) {
                Text(stringResource(Res.string.home_logout_button))
            }
            Spacer(modifier = Modifier.height(dimens.paddingLarge))

        } // Akhir Column utama
    } // Akhir Scaffold
}

// --- Komponen Kecil untuk UI ---

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
            .padding(bottom = dimens.spacingSmall), // Jarak bawah judul
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
private fun FavoriteCourseCard(
    course: FavoriteCourse,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dimens = MaterialTheme.dimens
    Card(
        modifier = modifier.fillMaxWidth().clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = dimens.cardElevation),
        shape = RoundedCornerShape(dimens.cardCornerRadiusLarge),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant) // Warna kartu
    ) {
        Row(modifier = Modifier.padding(dimens.paddingMedium)) {
            // Gambar (Placeholder)
            Box(
                modifier = Modifier
                    .size(dimens.favoriteClassImageHeight)
                    .clip(RoundedCornerShape(dimens.cardCornerRadiusMedium))
                    .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Image, // Ganti dengan ikon yang sesuai atau Image
                    contentDescription = "Course Image Placeholder",
                    modifier = Modifier.size(dimens.paddingHuge),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                // TODO: Ganti dengan Image(painterResource(course.imageUrl)...) jika sudah ada
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
                            contentDescription = stringResource(Res.string.home_rating_desc, course.rating),
                            tint = Color(0xFFFFC107), // Warna bintang kuning
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

@OptIn(ExperimentalMaterial3Api::class) // Untuk FilterChip
@Composable
private fun CategoryChip(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dimens = MaterialTheme.dimens
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(dimens.cardCornerRadiusLarge), // Chip bulat
        label = { Text(stringResource(category.nameRes)) },
        leadingIcon = {
            Icon(
                imageVector = category.icon,
                contentDescription = null, // Nama kategori sudah cukup
                modifier = Modifier.size(FilterChipDefaults.IconSize)
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f) // Warna saat tidak terpilih
        )
    )
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface) // Warna dasar kartu
    ) {
        Column {
            // Gambar Kelas (Placeholder)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimens.classCardImageHeight)
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.PhotoCamera, // Ganti ikon sesuai kategori?
                    contentDescription = stringResource(Res.string.home_class_icon_desc),
                    modifier = Modifier.size(dimens.paddingHuge),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                // TODO: Ganti dengan Image(painterResource(course.imageUrl)...) jika sudah ada
            }
            Column(modifier = Modifier.padding(dimens.paddingMedium)) {
                Text(
                    text = course.title, // Gunakan judul dari data
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(dimens.spacingSmall))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = stringResource(Res.string.home_rating_desc, course.rating),
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
                    textDecoration = TextDecoration.LineThrough, // Harga coret
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
    // Gambar Avatar (Placeholder)
    Box(
        modifier = modifier
            .size(dimens.instructorAvatarSize)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            Icons.Default.Person,
            contentDescription = stringResource(Res.string.home_instructor_avatar_desc),
            tint = MaterialTheme.colorScheme.onTertiaryContainer
        )
        // TODO: Ganti dengan Image(painterResource(instructor.imageUrl)...) jika sudah ada
    }
}

@Composable
private fun HomeBottomNavigation(
    currentRoute: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface, // Warna dasar bottom nav
        tonalElevation = MaterialTheme.dimens.cardElevation // Beri sedikit elevasi
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = stringResource(item.titleRes)) },
                label = { Text(stringResource(item.titleRes), style = MaterialTheme.typography.labelSmall) }, // Style teks kecil
                selected = currentRoute == item.route,
                onClick = { onItemSelected(item.route) },
                colors = NavigationBarItemDefaults.colors( // Warna item nav
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.primary, // Warna teks terpilih
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer, // Warna indikator terpilih
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                alwaysShowLabel = true // Selalu tampilkan label
            )
        }
    }
}
