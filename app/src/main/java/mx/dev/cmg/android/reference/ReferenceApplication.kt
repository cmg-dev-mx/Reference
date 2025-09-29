package mx.dev.cmg.android.reference

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class configurada con Hilt para inyección de dependencias.
 * Esta clase es el punto de entrada principal para la configuración de Hilt
 * en toda la aplicación Reference.
 */
@HiltAndroidApp
class ReferenceApplication : Application()