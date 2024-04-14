package ru.ermolnik.filmapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.ermolnik.filmapp.MainActivity

@Component(modules = [DataModule::class, DomainModule::class, ViewModelModule::class])
internal interface AppComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            context: Context
        ): AppComponent
    }
}