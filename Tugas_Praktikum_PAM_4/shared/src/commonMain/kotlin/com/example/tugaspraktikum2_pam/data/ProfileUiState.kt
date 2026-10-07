package com.example.tugaspraktikum2_pam.data

data class ProfileUiState(
    val nama: String = "Syehabudin",
    val nim: String = "124140102",
    val bio: String = "Mahasiswa Teknik Informatika ITERA",
    val email: String = "syehbaudin.124140102@student.itera.ac.id",
    val telepon: String = "082384696868",
    val lokasi: String = "Sukarame",
    val isDarkMode: Boolean = false,
    val isEditing: Boolean = false,
    val namaEdit: String = nama,
    val bioEdit: String = bio,
    val tampilInformasi: Boolean = true,
    val pesanError: String? = null
)
