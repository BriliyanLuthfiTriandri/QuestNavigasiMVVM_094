package com.example.navigationwithmvvm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.navigationwithmvvm.ui.viewmodel.MahasiswaViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.navigationwithmvvm.model.DataKelamin
import com.example.navigationwithmvvm.model.DataMahasiswa
import com.example.navigationwithmvvm.ui.view.DetailMahasiswaView
import com.example.navigationwithmvvm.ui.view.FormMahasiswaView

enum class Halaman{
    Formulir,
    Detail,
}

@Composable
fun PengelolaHalaman (
    modifier: Modifier = Modifier,
    viewModel: MahasiswaViewModel = viewModel(),
    navHost: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navHost, startDestination = Halaman.Formulir.name) {
        composable(route = Halaman.Formulir.name) {
            val konteks = LocalContext.current
            FormMahasiswaView(
                listJK = DataKelamin.listJK.map { id ->
                    konteks.resources.getString(id)
                },
                onSubmitClicked = {
                    viewModel.saveDataMahasiswa(it)
                    navHost.navigate(Halaman.Detail.name)
                }
            )
        }
        composable(route = Halaman.Detail.name) {
            DetailMahasiswaView (
                uiStateMahasiswa = uiState,
                onBackButton = {
                    navHost.popBackStack()
                }
            )
        }
    }
}