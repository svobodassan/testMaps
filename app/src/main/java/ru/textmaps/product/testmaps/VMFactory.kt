package ru.textmaps.product.testmaps
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.textmaps.product.testmaps.interactors.InteractorRussia
import ru.textmaps.product.testmaps.presentation.MapsActivityViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class VMFactory @Inject constructor(
    val interactorRussia: InteractorRussia
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {

            modelClass.isAssignableFrom(MapsActivityViewModel::class.java) -> MapsActivityViewModel(
                interactorRussia
            )

            else -> throw IllegalStateException("Cannot create instance of view model")
        } as T
}