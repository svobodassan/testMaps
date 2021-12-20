package air.ru.obi.mobile.core.dagger


import dagger.Component
import ru.textmaps.product.testmaps.MapsActivity
import javax.inject.Singleton

@Singleton
@Component()
interface MapsDaggerComponent {
    fun inject(activity: MapsActivity)
}