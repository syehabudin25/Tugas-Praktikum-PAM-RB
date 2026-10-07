package com.example.tugaspraktikum2_pam

import androidx.lifecycle.ViewModelStore
import com.example.tugaspraktikum2_pam.viewmodel.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {
    @Test
    fun perubahanProfilBaruDiterapkanSetelahSimpan() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        val penyimpanan = ViewModelStore()
        try {
            val viewModel = ProfileViewModel()
            penyimpanan.put("profil", viewModel)
            viewModel.mulaiEdit()
            viewModel.ubahNama("  Syehabudin Baru  ")
            viewModel.ubahBio("  Belajar MVVM  ")
            assertEquals("Syehabudin", viewModel.uiState.value.nama)
            assertEquals("Mahasiswa Teknik Informatika ITERA", viewModel.uiState.value.bio)

            viewModel.simpanProfil()
            assertEquals("Syehabudin Baru", viewModel.uiState.value.nama)
            assertEquals("Belajar MVVM", viewModel.uiState.value.bio)
            assertEquals("124140102", viewModel.uiState.value.nim)
            assertFalse(viewModel.uiState.value.isEditing)
        } finally {
            penyimpanan.clear()
            Dispatchers.resetMain()
        }
    }

    @Test
    fun batalEditMengembalikanDataTerakhirYangDisimpan() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        val penyimpanan = ViewModelStore()
        try {
            val viewModel = ProfileViewModel()
            penyimpanan.put("profil", viewModel)
            viewModel.mulaiEdit()
            viewModel.ubahNama("Nama tersimpan")
            viewModel.ubahBio("Bio tersimpan")
            viewModel.simpanProfil()
            viewModel.mulaiEdit()
            viewModel.ubahNama("Perubahan dibatalkan")
            viewModel.ubahBio("Bio dibatalkan")
            viewModel.batalEdit()

            assertEquals("Nama tersimpan", viewModel.uiState.value.nama)
            assertEquals("Bio tersimpan", viewModel.uiState.value.bio)
            assertEquals("Nama tersimpan", viewModel.uiState.value.namaEdit)
            assertEquals("Bio tersimpan", viewModel.uiState.value.bioEdit)
            assertFalse(viewModel.uiState.value.isEditing)
            assertNull(viewModel.uiState.value.pesanError)
        } finally {
            penyimpanan.clear()
            Dispatchers.resetMain()
        }
    }

    @Test
    fun namaKosongDitolakDanDapatDiperbaiki() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        val penyimpanan = ViewModelStore()
        try {
            val viewModel = ProfileViewModel()
            penyimpanan.put("profil", viewModel)
            viewModel.mulaiEdit()
            viewModel.ubahNama("   ")
            viewModel.simpanProfil()
            assertEquals("Syehabudin", viewModel.uiState.value.nama)
            assertTrue(viewModel.uiState.value.isEditing)
            assertNotNull(viewModel.uiState.value.pesanError)

            viewModel.ubahNama("Syehabudin")
            assertNull(viewModel.uiState.value.pesanError)
            viewModel.simpanProfil()
            assertFalse(viewModel.uiState.value.isEditing)
        } finally {
            penyimpanan.clear()
            Dispatchers.resetMain()
        }
    }

    @Test
    fun gantiTemaDanKontakTidakMenghapusDraftProfil() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        val penyimpanan = ViewModelStore()
        try {
            val viewModel = ProfileViewModel()
            penyimpanan.put("profil", viewModel)
            viewModel.mulaiEdit()
            viewModel.ubahNama("Nama dalam form")
            viewModel.ubahBio("Bio dalam form")
            viewModel.ubahModeGelap(true)
            viewModel.ubahTampilInformasi()
            assertTrue(viewModel.uiState.value.isDarkMode)
            assertFalse(viewModel.uiState.value.tampilInformasi)
            assertEquals("Nama dalam form", viewModel.uiState.value.namaEdit)
            assertEquals("Bio dalam form", viewModel.uiState.value.bioEdit)
            assertEquals("Syehabudin", viewModel.uiState.value.nama)

            viewModel.simpanProfil()
            assertEquals("Nama dalam form", viewModel.uiState.value.nama)
            assertTrue(viewModel.uiState.value.isDarkMode)
            viewModel.ubahModeGelap(false)
            assertFalse(viewModel.uiState.value.isDarkMode)
        } finally {
            penyimpanan.clear()
            Dispatchers.resetMain()
        }
    }
}
