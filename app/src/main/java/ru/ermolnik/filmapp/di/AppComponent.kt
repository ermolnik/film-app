package ru.ermolnik.filmapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.ermolnik.filmapp.MainActivity

@Component(modules = [DataModule::class, DomainModule::class, PresentationModule::class])
internal interface AppComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}