package id.haerulmuttaqin.domain.di

import id.haerulmuttaqin.domain.MainUseCase
import id.haerulmuttaqin.domain.MainUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module { 
    single<MainUseCase> { MainUseCaseImpl(get()) }
}