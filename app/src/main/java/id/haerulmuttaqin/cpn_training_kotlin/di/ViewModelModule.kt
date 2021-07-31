package id.haerulmuttaqin.cpn_training_kotlin.di

import id.haerulmuttaqin.cpn_training_kotlin.present.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}