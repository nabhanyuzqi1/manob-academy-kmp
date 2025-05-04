package com.mnb.manobacademy.features.auth.store // Atau package onboarding

import manobacademykmp.composeapp.generated.resources.Res
import manobacademykmp.composeapp.generated.resources.bg_guide_1
import manobacademykmp.composeapp.generated.resources.bg_guide_2
import manobacademykmp.composeapp.generated.resources.bg_guide_3
import manobacademykmp.composeapp.generated.resources.guide_background_desc
import manobacademykmp.composeapp.generated.resources.guide_headline_1
import manobacademykmp.composeapp.generated.resources.guide_headline_2
import manobacademykmp.composeapp.generated.resources.guide_headline_3
import manobacademykmp.composeapp.generated.resources.guide_subtitle_1
import manobacademykmp.composeapp.generated.resources.guide_subtitle_2
import manobacademykmp.composeapp.generated.resources.guide_subtitle_3
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

// Data class dan list onboardingPages diasumsikan ada di file yang sama atau diimpor
data class OnboardingPage(
    val imageRes: DrawableResource,
    val headlineRes: StringResource,
    val subtitleRes: StringResource,
    val imageContentDescRes: StringResource = Res.string.guide_background_desc
)

// Ganti dengan resource Anda yang sebenarnya
val onboardingPages = listOf(
    OnboardingPage(
        imageRes = Res.drawable.bg_guide_1,
        headlineRes = Res.string.guide_headline_1,
        subtitleRes = Res.string.guide_subtitle_1
    ),
    OnboardingPage(
        imageRes = Res.drawable.bg_guide_2, // Ganti gambar
        headlineRes = Res.string.guide_headline_2, // Ganti teks
        subtitleRes = Res.string.guide_subtitle_2
    ),
    OnboardingPage(
        imageRes = Res.drawable.bg_guide_3, // Ganti gambar
        headlineRes = Res.string.guide_headline_3, // Ganti teks
        subtitleRes = Res.string.guide_subtitle_3
    )
)