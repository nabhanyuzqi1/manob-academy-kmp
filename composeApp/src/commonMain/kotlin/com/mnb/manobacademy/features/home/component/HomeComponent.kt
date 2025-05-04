package com.mnb.manobacademy.features.home.component

import com.arkivanov.decompose.ComponentContext

// Interface untuk komponen Home
interface HomeComponent {
    // Tambahkan fungsi atau state lain yang diperlukan untuk Home

    /** Dipanggil saat pengguna ingin logout. */
    fun onLogoutClicked()
}

// Implementasi default untuk komponen Home
class DefaultHomeComponent(
    componentContext: ComponentContext,
    private val onLogout: () -> Unit // Callback untuk aksi logout (navigasi)
) : HomeComponent, ComponentContext by componentContext {

    override fun onLogoutClicked() {
        println("Logout initiated from HomeComponent.")
        // Panggil callback yang akan menangani navigasi (disediakan oleh RootComponent)
        onLogout()
    }

    // Tambahkan lifecycle handling jika diperlukan
    // init {
    //     lifecycle.onDestroy {
    //         // Cleanup logic
    //     }
    // }
}
