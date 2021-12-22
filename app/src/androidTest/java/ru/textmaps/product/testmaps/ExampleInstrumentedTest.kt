package ru.textmaps.product.testmaps



import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*



/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContextPositive() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("ru.textmaps.product.testmaps", appContext.packageName)
    }

    @Test
    fun useAppContextNegative() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertNotEquals("", appContext.packageName)
    }
}