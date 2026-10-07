package com.example.tugaspraktikum2_pam.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tugaspraktikum2_pam.data.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun mulaiEdit() {
        _uiState.update {
            it.copy(
                isEditing = true,
                namaEdit = it.nama,
                bioEdit = it.bio,
                pesanError = null
            )
        }
    }

    fun ubahNama(nama: String) {
        _uiState.update { it.copy(namaEdit = nama, pesanError = null) }
    }

    fun ubahBio(bio: String) {
        _uiState.update { it.copy(bioEdit = bio) }
    }

    fun simpanProfil() {
        _uiState.update {
            when {
                !it.isEditing -> it
                it.namaEdit.isBlank() -> it.copy(pesanError = "Nama tidak boleh kosong.")
                else -> it.copy(
                    nama = it.namaEdit.trim(),
                    bio = it.bioEdit.trim(),
                    namaEdit = it.namaEdit.trim(),
                    bioEdit = it.bioEdit.trim(),
                    isEditing = false,
                    pesanError = null
                )
            }
        }
    }

    fun batalEdit() {
        _uiState.update {
            it.copy(
                isEditing = false,
                namaEdit = it.nama,
                bioEdit = it.bio,
                pesanError = null
            )
        }
    }

    fun ubahModeGelap(aktif: Boolean) {
        _uiState.update { it.copy(isDarkMode = aktif) }
    }

    fun ubahTampilInformasi() {
        _uiState.update { it.copy(tampilInformasi = !it.tampilInformasi) }
    }
}
