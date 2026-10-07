package com.example.tugaspraktikum2_pam.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tugaspraktikum2_pam.data.ProfileUiState
import com.example.tugaspraktikum2_pam.resources.Res
import com.example.tugaspraktikum2_pam.resources.foto_profil
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onEdit: () -> Unit,
    onNamaChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    onDarkModeChange: (Boolean) -> Unit,
    onToggleInformasi: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "My Profile App",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mode Gelap",
                    style = MaterialTheme.typography.titleMedium
                )
                Switch(
                    checked = uiState.isDarkMode,
                    onCheckedChange = onDarkModeChange
                )
            }

            ProfileHeader(nama = uiState.nama, nim = uiState.nim)
            ProfileCard(judul = "Tentang Saya", isi = uiState.bio)

            if (uiState.isEditing) {
                EditProfileForm(
                    nama = uiState.namaEdit,
                    bio = uiState.bioEdit,
                    pesanError = uiState.pesanError,
                    onNamaChange = onNamaChange,
                    onBioChange = onBioChange,
                    onSave = onSave,
                    onCancel = onCancel
                )
            } else {
                Button(onClick = onEdit, modifier = Modifier.fillMaxWidth()) {
                    Text("Edit Profil")
                }
            }

            Button(
                onClick = onToggleInformasi,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (uiState.tampilInformasi) {
                        "Sembunyikan Kontak"
                    } else {
                        "Tampilkan Kontak"
                    }
                )
            }

            if (uiState.tampilInformasi) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Informasi Kontak",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        InfoItem(label = "Email", nilai = uiState.email)
                        InfoItem(label = "Phone", nilai = uiState.telepon)
                        InfoItem(label = "Location", nilai = uiState.lokasi)
                    }
                }
            }
        }
    }
}

@Composable
fun EditProfileForm(
    nama: String,
    bio: String,
    pesanError: String?,
    onNamaChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Edit Profil",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            LabeledTextField(
                label = "Nama",
                value = nama,
                onValueChange = onNamaChange,
                pesanError = pesanError
            )
            LabeledTextField(
                label = "Bio",
                value = bio,
                onValueChange = onBioChange,
                singleLine = false
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(onClick = onCancel, modifier = Modifier.weight(1f)) {
                    Text("Batal")
                }
                Button(onClick = onSave, modifier = Modifier.weight(1f)) {
                    Text("Simpan")
                }
            }
        }
    }
}

@Composable
fun LabeledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    pesanError: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = singleLine,
        minLines = if (singleLine) 1 else 3,
        isError = pesanError != null,
        supportingText = if (pesanError != null) {
            { Text(pesanError) }
        } else {
            null
        }
    )
}

@Composable
fun ProfileHeader(nama: String, nim: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.foto_profil),
                contentDescription = "Foto profil $nama",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(128.dp).clip(CircleShape)
            )
        }
        Text(
            text = nama,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "NIM $nim",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ProfileCard(judul: String, isi: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = judul,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(text = isi, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun InfoItem(label: String, nilai: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(text = label.take(1), fontWeight = FontWeight.Bold)
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(text = nilai, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
