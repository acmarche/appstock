package be.marche.appstock.di

import be.marche.appstock.database.AppDatabase
import be.marche.appstock.BuildConfig
import be.marche.appstock.SyncViewModel
import be.marche.appstock.api.StockService
import be.marche.appstock.categorie.CategorieRepository
import be.marche.appstock.categorie.CategorieViewModel
import be.marche.appstock.produit.ProduitRepository
import be.marche.appstock.produit.ProduitViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    single { AppDatabase.buildDatabase(androidApplication()) }

    single { get<AppDatabase>().bottinDao() }

    single { ProduitRepository(get()) }
    single { CategorieRepository(get()) }

    viewModel { ProduitViewModel(get(), get()) }
    viewModel { CategorieViewModel(get(), get()) }
    viewModel { SyncViewModel(get(), get(), get()) }

    single { createOkHttpClient<OkHttpClient>() }
    single {
        createWebService<StockService>(
            get(),
            "https://apptravaux.marche.be/stock/api/"
        )
    }
}

inline fun <reified T> createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })
      //  .addInterceptor(BasicAuthInterceptor("**", "**"))
        .build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}