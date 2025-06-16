package com.mnb.manobacademy.views.auth.ui // Atau package onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mnb.manobacademy.models.store.onboardingPages
import com.mnb.manobacademy.ui.components.PrimaryActionButton
import com.mnb.manobacademy.ui.theme.dimens
import com.mnb.manobacademy.util.getScreenHeightDp
import kotlinx.coroutines.launch
import manobacademykmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GuideScreen(
    onGetStarted: () -> Unit
) {
    val dimens = MaterialTheme.dimens
    val pageCount = onboardingPages.size
    val pagerState = rememberPagerState { pageCount }
    val scope = rememberCoroutineScope()

    val screenHeight = getScreenHeightDp()
    val tallScreenThreshold = 700.dp
    val extraTopPadding = if (screenHeight > tallScreenThreshold) 32.dp else 0.dp

    val gradientColorStart = Color.Transparent
    val gradientColorMid = MaterialTheme.colorScheme.background.copy(alpha = 0.75f)
    val gradientColorEnd = MaterialTheme.colorScheme.background.copy(alpha = 1.0f)

    val isDark = isSystemInDarkTheme()
    val textColor = if (isDark) Color.White else Color.DarkGray

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { pageIndex ->
                val pageData = onboardingPages[pageIndex]
                Image(
                    painter = painterResource(pageData.imageRes),
                    contentDescription = stringResource(pageData.imageContentDescRes),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(gradientColorStart, gradientColorStart, gradientColorMid, gradientColorEnd),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding().padding(bottom = dimens.paddingHuge)
                    .padding(horizontal = dimens.paddingHuge)
                    .padding(bottom = dimens.paddingLarge),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(extraTopPadding))

                val logoResource: DrawableResource = if (isDark) {
                    Res.drawable.logo_manob_academy_dark
                } else {
                    Res.drawable.logo_manob_academy_light
                }
                Image(
                    painter = painterResource(logoResource),
                    contentDescription = stringResource(Res.string.logo_content_description),
                    modifier = Modifier
                        .height(dimens.guideLogoSize)
                )

                Spacer(modifier = Modifier.weight(1f))

                Column(modifier = Modifier.fillMaxWidth()) {
                    val currentPageData = onboardingPages[pagerState.currentPage]
                    Text(
                        text = stringResource(currentPageData.headlineRes),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        color = textColor,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(dimens.spacingSmall))
                    Text(
                        text = stringResource(currentPageData.subtitleRes),
                        style = MaterialTheme.typography.bodyLarge,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Start,
                        color = textColor,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(dimens.guideTextPaddingBottom))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PagerIndicator(
                        pageCount = pageCount,
                        currentPage = pagerState.currentPage
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    if (pagerState.currentPage == pageCount - 1) {
                        PrimaryActionButton(
                            text = stringResource(Res.string.guide_button_start),
                            onClick = onGetStarted,
                            modifier = Modifier.padding(start = dimens.spacingLarge),
                            // --- PERBAIKAN DI SINI ---
                            // Berikan nilai false karena tidak ada state loading
                            loading = false
                        )
                    } else {
                        PrimaryActionButton(
                            text = stringResource(Res.string.guide_button_next),
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            },
                            modifier = Modifier.padding(start = dimens.spacingLarge),
                            // --- PERBAIKAN DI SINI ---
                            // Berikan nilai false karena tidak ada state loading
                            loading = false
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun PagerIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    val dimens = MaterialTheme.dimens
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimens.guideIndicatorSpacing)
    ) {
        repeat(pageCount) { iteration ->
            val color = if (currentPage == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
            val width = if (currentPage == iteration) dimens.guideIndicatorWidth else dimens.guideIndicatorHeight

            Box(
                modifier = Modifier
                    .width(width)
                    .height(dimens.guideIndicatorHeight)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}