package com.example.navigationwithmvvm.ui.viewmodel

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.min
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigationwithmvvm.model.DataKelamin
import com.example.navigationwithmvvm.ui.view.DetailMahasiswaView
import com.example.navigationwithmvvm.ui.view.FormMahasiswaView

enum class Halaman{
    Form,
    Data
}

@Composable
fun Navigasi(
    modifier : Modifier = Modifier,
    viewModel: MahasiswaViewModel = viewModel(),
    navHost: NavHostController = rememberNavController()
){
    Scaffold { isipadding ->
        val uiState by viewModel.uiState.collectAsState()
        NavHost (
            modifier = modifier.padding(isipadding),
            navController = navHost, starDestination = Halaman.Form.name
        ){
            composable(route = Halaman.Form.name) {
                val konteks = LocalContext.current
                FormMahasiswaView(

                    //Di bawah ini merupakan dari parameter halaman FormulirView
                    listJK = DataKelamin.listJK.map { // Data Jk dapat dari object
                            isi ->
                        konteks.resources.getString(isi)
                    },
                    onSubmitClicked = {
                        viewModel.saveDataMahasiswa(it)
                        navHost.navigate(Halaman.Data.name)
                    }
                )
            }
            composable(route = Halaman.Data.name){
                DetailMahasiswaView(
                    mhs = uiState,
                    onClickButton = {
                        navHost.popBackStack()
                    }
                )
            }
        }
    }
}