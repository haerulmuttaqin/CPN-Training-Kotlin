package id.haerulmuttaqin.data.di

import id.haerulmuttaqin.data.MainRepositoryImpl
import id.haerulmuttaqin.domain.MainRepository
import org.koin.dsl.module

val repositoryModule = module { 
    single<MainRepository> { MainRepositoryImpl(get(), get()) }
}