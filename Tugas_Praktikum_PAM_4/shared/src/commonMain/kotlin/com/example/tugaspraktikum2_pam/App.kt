package com.example.tugaspraktikum2_pam

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugaspraktikum2_pam.ui.ProfileScreen
import com.example.tugaspraktikum2_pam.viewmodel.ProfileViewModel

@Composable
fun App(profileViewModel: ProfileViewModel = viewModel { ProfileViewModel() }) {
    val uiState by profileViewModel.uiState.collectAsState()

    val warna = if (uiState.isDarkMode) {
        darkColorScheme(
            primary = Color(0xFFD0BCFF),
            background = Color(0xFF141218),
            surface = Color(0xFF211F26)
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF6750A4),
            background = Color(0xFFF5F3FA),
            surface = Color.White
        )
    }

    MaterialTheme(colorScheme = warna) {
        ProfileScreen(
            uiState = uiState,
            onEdit = profileViewModel::mulaiEdit,
            onNamaChange = profileViewModel::ubahNama,
            onBioChange = profileViewModel::ubahBio,
            onSave = profileViewModel::simpanProfil,
            onCancel = profileViewModel::batalEdit,
            onDarkModeChange = profileViewModel::ubahModeGelap,
            onToggleInformasi = profileViewModel::ubahTampilInformasi
        )
    }
}
