// --- File: composeApp/src/commonMain/kotlin/features/home/component/HomeComponent.kt ---
// ... (Kode HomeComponent bisa tetap sebagai placeholder) ...
package com.mnb.manobacademy.features.home.component

import com.arkivanov.decompose.ComponentContext

interface HomeComponent { }

class DefaultHomeComponent(
    componentContext: ComponentContext
) : HomeComponent, ComponentContext by componentContext {
    init { }
}
