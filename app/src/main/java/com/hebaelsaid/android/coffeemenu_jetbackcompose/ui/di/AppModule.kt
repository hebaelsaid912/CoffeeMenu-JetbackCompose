package com.hebaelsaid.android.coffeemenu_jetbackcompose.ui.di


import android.content.Context
import androidx.room.Room
import com.hebaelsaid.android.coffeemenu_jetbackcompose.data.local.database.CoffeeMenuDatabase
import com.hebaelsaid.android.coffeemenu_jetbackcompose.data.remote.CoffeeApiInterface
import com.hebaelsaid.android.coffeemenu_jetbackcompose.data.repository.CoffeeDetailsRepositoryImpl
import com.hebaelsaid.android.coffeemenu_jetbackcompose.data.repository.CoffeeRepositoryImpl
import com.hebaelsaid.android.coffeemenu_jetbackcompose.domain.repository.CoffeeDetailsRemoImpl
import com.hebaelsaid.android.coffeemenu_jetbackcompose.domain.repository.CoffeeRepoImpl
import com.hebaelsaid.android.coffeemenu_jetbackcompose.utils.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule  {

    @Provides
    @Singleton
    fun provideCoffeeApiInterface(): CoffeeApiInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoffeeApiInterface::class.java)
    }
    @Provides
    @Singleton
    fun provideCoffeeDatabase(@ApplicationContext appContext: Context): CoffeeMenuDatabase {
        return Room.databaseBuilder(appContext, CoffeeMenuDatabase::class.java,
            "coffee_menu.db").build()
    }

    @Provides
    @Singleton
    fun provideCoffeeRepository(api: CoffeeApiInterface): CoffeeRepoImpl {
        return CoffeeRepositoryImpl(api = api)
    }
    @Provides
    @Singleton
    fun provideCoffeeDetailsRepository(): CoffeeDetailsRemoImpl {
        return CoffeeDetailsRepositoryImpl()
    }
}