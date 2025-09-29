---
applyTo: "**/*"
---

# 🏛️ Instrucciones de Arquitectura Android - Reference App

## 🌟 Stack Tecnológico

### Lenguaje Base

- **Kotlin**: Lenguaje principal para toda la aplicación
- Usar características modernas de Kotlin (coroutines, flows, extension functions)
- Seguir convenciones de nomenclatura de Kotlin

### UI Framework

- **Jetpack Compose**: Framework declarativo para UI
- Evitar el uso de Views tradicionales (XML layouts)
- Implementar temas consistentes usando Material Design 3
- **Atomic Design**: Metodología para organizar componentes UI
- Jerarquía: Atoms → Molecules → Organisms → Templates → Pages

### Navegación

- **Jetpack Compose Navigation**: Sistema de navegación oficial
- **Kotlinx Serialization**: Para type-safe navigation arguments
- Definir rutas como objetos serializables
- Usar NavController de manera centralizada

### Arquitectura

- **MVI (Model-View-Intent) + Clean Architecture**
- Separación clara en capas: Presentation, Domain, Data
- Use Cases para encapsular lógica de negocio
- Repositories como abstracción de fuentes de datos

### Inyección de Dependencias

- **Dagger Hilt**: Framework de DI oficial de Android
- Usar anotaciones @HiltAndroidApp, @AndroidEntryPoint
- Definir módulos Hilt para cada capa

---

## 🏗️ Estructura de Capas

### 📱 Presentation Layer

```
presentation/
├── ui/
│   ├── screens/           # Screens (Pantallas completas)
│   ├── components/        # Atomic Design Components
│   │   ├── templates/     # Templates (Layouts base)
│   │   ├── organisms/     # Organisms (Componentes complejos)
│   │   ├── molecules/     # Molecules (Grupos de atoms)
│   │   └── atoms/        # Atoms (Componentes básicos)
│   ├── theme/            # Temas, colores, tipografías
│   └── navigation/       # Configuración de navegación
├── viewmodel/            # ViewModels con Hilt
└── state/               # Estados UI y eventos
```

**Reglas:**

- Cada pantalla debe tener su propio ViewModel
- Estados UI deben ser inmutables (data classes)
- Usar StateFlow para exponer estados
- Manejar eventos UI mediante sealed classes/interfaces
- **Seguir jerarquía Atomic Design estrictamente**

**Atomic Design Hierarchy:**

- **Atoms**: Elementos básicos no divisibles (Button, TextField, Icon, Text)
- **Molecules**: Combinación de atoms con propósito específico (SearchBar, Card)
- **Organisms**: Secciones complejas de la interfaz (Header, ProductList, NavigationBar)
- **Templates**: Layouts que definen estructura sin contenido específico
- **Screens**: Instancias específicas de templates con datos reales

### 🧠 Domain Layer

```
domain/
├── model/               # Entidades de dominio
├── usecase/            # Casos de uso
├── repository/         # Interfaces de repositorios
└── util/              # Utilidades de dominio
```

**Reglas:**

- No dependencias de Android Framework
- Use Cases deben ser pequeños y enfocados
- Entidades de dominio puras (sin anotaciones de Room/Retrofit)
- Interfaces de repositorios definidas aquí

### 💾 Data Layer

```
data/
├── repository/         # Implementaciones de repositorios
├── datasource/
│   ├── local/         # Room, SharedPreferences, etc.
│   ├── remote/        # Retrofit, APIs
│   └── cache/         # Caché en memoria
├── mapper/            # Mappers entre DTOs y entidades
└── di/               # Módulos Hilt de datos
```

**Reglas:**

- Implementar interfaces del domain layer
- Usar DTOs para APIs y entidades Room
- Mappers para convertir entre capas
- Manejo de errores y excepciones

---

## 🎯 Patrones y Convenciones

### Atomic Design Implementation

```kotlin
// ATOMS - Elementos básicos
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
    ) {
        Text(text = text)
    }
}

// MOLECULES - Combinación de atoms
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.weight(1f)
        )
        PrimaryButton(
            text = "Buscar",
            onClick = onSearch
        )
    }
}

// ORGANISMS - Secciones complejas
@Composable
fun ProductList(
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(products) { product ->
            ProductCard(
                product = product,
                onClick = { onProductClick(product) }
            )
        }
    }
}

// TEMPLATES - Layouts base
@Composable
fun ListTemplate(
    topBar: @Composable () -> Unit,
    content: @Composable () -> Unit,
    floatingActionButton: @Composable () -> Unit = {}
) {
    Scaffold(
        topBar = topBar,
        floatingActionButton = floatingActionButton
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            content()
        }
    }
}

// SCREENS - Pantallas completas
@Composable
fun ProductListScreen(
    state: ProductListState,
    onIntent: (ProductListIntent) -> Unit
) {
    ListTemplate(
        topBar = {
            SearchBar(
                query = state.searchQuery,
                onQueryChange = { onIntent(ProductListIntent.UpdateSearch(it)) },
                onSearch = { onIntent(ProductListIntent.Search) }
            )
        },
        content = {
            ProductList(
                products = state.products,
                onProductClick = { onIntent(ProductListIntent.SelectProduct(it)) }
            )
        }
    )
}
```

### ViewModels (MVI)

```kotlin
@HiltViewModel
class FeatureViewModel @Inject constructor(
    private val useCase: FeatureUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FeatureState())
    val state = _state.asStateFlow()

    fun handleIntent(intent: FeatureIntent) {
        when (intent) {
            is FeatureIntent.LoadData -> loadData()
            is FeatureIntent.Refresh -> refresh()
        }
    }

    private fun loadData() {
        // Implementación
    }
}
```

### Estados UI

```kotlin
data class FeatureState(
    val isLoading: Boolean = false,
    val data: List<Item> = emptyList(),
    val error: String? = null
)

sealed interface FeatureIntent {
    object LoadData : FeatureIntent
    object Refresh : FeatureIntent
    data class SelectItem(val item: Item) : FeatureIntent
}
```

### Navegación Type-Safe

```kotlin
@Serializable
object HomeRoute

@Serializable
data class DetailRoute(val itemId: String)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {
        composable<HomeRoute> {
            HomeScreen(
                onNavigateToDetail = { id ->
                    navController.navigate(DetailRoute(id))
                }
            )
        }
        composable<DetailRoute> { backStackEntry ->
            val args = backStackEntry.toRoute<DetailRoute>()
            DetailScreen(itemId = args.itemId)
        }
    }
}
```

### Use Cases

```kotlin
class GetUserDataUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String): Flow<Result<User>> {
        return userRepository.getUserData(userId)
    }
}
```

---

## 🔧 Configuración Hilt

### Application Class

```kotlin
@HiltAndroidApp
class ReferenceApplication : Application()
```

### Activity/Fragment

```kotlin
@AndroidEntryPoint
class MainActivity : ComponentActivity()
```

### Módulos

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService = // implementación

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}
```

---

## 📋 Reglas de Desarrollo

### ✅ Hacer (DO)

- Usar Flow para streams de datos reactivos
- Implementar Resource/Result classes para manejo de estados
- Aplicar principios SOLID en cada capa
- Escribir tests unitarios para ViewModels y Use Cases
- Usar Compose previews para componentes UI
- Seguir Material Design 3 guidelines
- Implementar estados de loading, success, error
- **Respetar estrictamente la jerarquía Atomic Design**
- **Componer molecules usando solo atoms**
- **Componer organisms usando molecules y atoms**
- **Crear templates reutilizables para estructuras comunes**

### ❌ No Hacer (DON'T)

- No usar findViewById o View Binding
- No acceder directamente a repositorios desde ViewModels
- No mezclar lógica de negocio en Composables
- No usar Context en domain layer
- No hacer llamadas suspend en composables directamente
- No usar LiveData (preferir StateFlow/Flow)
- No violar las dependencias entre capas
- **No saltarse niveles en Atomic Design** (ej: atoms directamente en organisms)
- **No duplicar atoms** (reutilizar componentes básicos existentes)
- **No crear screens sin templates** cuando la estructura se repite

### 🧪 Testing

- Unit tests para ViewModels usando Turbine
- Unit tests para Use Cases
- Integration tests para Repositories
- UI tests con Compose Test Framework
- Usar TestDispatchers para coroutines en tests

---

## 📁 Nomenclatura de Archivos

### Atomic Design Components

- **Atoms**: `PrimaryButton.kt`, `AppTextField.kt`, `AppIcon.kt`
- **Molecules**: `SearchBar.kt`, `ProductCard.kt`, `LoginForm.kt`
- **Organisms**: `NavigationBar.kt`, `ProductList.kt`, `UserProfile.kt`
- **Templates**: `ListTemplate.kt`, `DetailTemplate.kt`, `FormTemplate.kt`
- **Screens**: `ProductListScreen.kt`, `ProductDetailScreen.kt`, `LoginScreen.kt`

### Architecture Components

- **ViewModels**: `FeatureViewModel.kt`
- **Use Cases**: `GetFeatureDataUseCase.kt`
- **Repositories**: `FeatureRepository.kt`, `FeatureRepositoryImpl.kt`
- **Entities**: `Feature.kt`
- **DTOs**: `FeatureDto.kt`
- **Mappers**: `FeatureMapper.kt`
- **States**: `FeatureState.kt`
- **Navigation**: `FeatureRoute.kt`

---

## 🚀 Consideraciones de Performance

- Usar `remember` y `LaunchedEffect` apropiadamente
- Implementar `derivedStateOf` para cálculos costosos
- Aplicar `@Stable` y `@Immutable` cuando sea necesario
- Usar `collectAsStateWithLifecycle` para observar flows
- Implementar paginación para listas grandes
- Caché inteligente en repositories

---

_"La arquitectura es el esqueleto sobre el cual se construye la carne del código"_ 💀⚡
