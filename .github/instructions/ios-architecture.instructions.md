---
applyTo: "IOS/**/*"
---

# 🍎 Instrucciones de Arquitectura iOS - Reference App

## 🌟 Stack Tecnológico

### Lenguaje Base

- **Swift**: Lenguaje principal para toda la aplicación
- Usar características modernas de Swift (async/await, Combine, property wrappers)
- Seguir convenciones de nomenclatura Swift (camelCase, PascalCase)
- Swift 5.9+ para aprovechar las últimas características

### UI Framework

- **SwiftUI**: Framework declarativo para UI nativo de Apple
- Evitar el uso de UIKit tradicional (storyboards, xibs)
- Implementar temas consistentes usando Human Interface Guidelines
- **Atomic Design**: Metodología para organizar componentes UI
- Jerarquía: Atoms → Molecules → Organisms → Templates → Screens

### Navegación

- **NavigationStack**: Sistema de navegación moderno de SwiftUI
- **Codable**: Para type-safe navigation arguments
- Definir rutas como enums o structs Codable
- Usar NavigationPath de manera centralizada

### Arquitectura

- **MVVM + Clean Architecture**
- Separación clara en capas: Presentation, Domain, Data
- Use Cases para encapsular lógica de negocio
- Repositories como abstracción de fuentes de datos
- **Combine** para programación reactiva

### Inyección de Dependencias

- **Factory Pattern + Protocols**: Patrón nativo usando protocolos Swift
- **Resolver**: Swift Package Manager para DI avanzada (opcional)
- Usar protocolos para abstracciones
- Container pattern para registro de dependencias

---

## 🏗️ Estructura de Capas

### 📱 Presentation Layer

```
Presentation/
├── Views/
│   ├── Screens/           # Screens (Pantallas completas)
│   ├── Components/        # Atomic Design Components
│   │   ├── Templates/     # Templates (Layouts base)
│   │   ├── Organisms/     # Organisms (Componentes complejos)
│   │   ├── Molecules/     # Molecules (Grupos de atoms)
│   │   └── Atoms/        # Atoms (Componentes básicos)
│   ├── Theme/            # Temas, colores, tipografías
│   └── Navigation/       # Configuración de navegación
├── ViewModels/           # ViewModels con ObservableObject
└── State/               # Estados UI y eventos
```

**Reglas:**

- Cada pantalla debe tener su propio ViewModel
- Estados UI deben ser inmutables (structs)
- Usar @Published para exponer estados reactivos
- Manejar eventos UI mediante enums/métodos
- **Seguir jerarquía Atomic Design estrictamente**

**Atomic Design Hierarchy:**

- **Atoms**: Elementos básicos no divisibles (Button, TextField, Image, Text)
- **Molecules**: Combinación de atoms con propósito específico (SearchBar, Card)
- **Organisms**: Secciones complejas de la interfaz (NavigationBar, ProductList, Header)
- **Templates**: Layouts que definen estructura sin contenido específico
- **Screens**: Instancias específicas de templates con datos reales

### 🧠 Domain Layer

```
Domain/
├── Models/              # Entidades de dominio
├── UseCases/           # Casos de uso
├── Repositories/       # Protocolos de repositorios
└── Utils/             # Utilidades de dominio
```

**Reglas:**

- No dependencias de UIKit/SwiftUI
- Use Cases deben ser pequeños y enfocados
- Entidades de dominio puras (sin property wrappers UI)
- Protocolos de repositorios definidos aquí
- Usar Result<Success, Failure> para manejo de errores

### 💾 Data Layer

```
Data/
├── Repositories/       # Implementaciones de repositorios
├── DataSources/
│   ├── Local/         # Core Data, UserDefaults, Keychain
│   ├── Remote/        # URLSession, APIs
│   └── Cache/         # Caché en memoria
├── Mappers/           # Mappers entre DTOs y entidades
└── DI/               # Container de dependencias
```

**Reglas:**

- Implementar protocolos del domain layer
- Usar DTOs para APIs y modelos Core Data
- Mappers para convertir entre capas
- Manejo de errores usando Result y custom errors

---

## 🎯 Patrones y Convenciones

### Atomic Design Implementation

```swift
// ATOMS - Elementos básicos
struct PrimaryButton: View {
    let title: String
    let action: () -> Void
    var isEnabled: Bool = true
    
    var body: some View {
        Button(action: action) {
            Text(title)
                .font(.headline)
                .foregroundColor(.white)
                .frame(maxWidth: .infinity)
                .padding()
                .background(isEnabled ? Color.blue : Color.gray)
                .cornerRadius(8)
        }
        .disabled(!isEnabled)
    }
}

// MOLECULES - Combinación de atoms
struct SearchBar: View {
    @Binding var query: String
    let onSearch: () -> Void
    
    var body: some View {
        HStack {
            TextField("Buscar...", text: $query)
                .textFieldStyle(RoundedBorderTextFieldStyle())
            
            PrimaryButton(title: "Buscar", action: onSearch)
                .frame(width: 80)
        }
    }
}

// ORGANISMS - Secciones complejas
struct ProductList: View {
    let products: [Product]
    let onProductTap: (Product) -> Void
    
    var body: some View {
        LazyVStack(spacing: 8) {
            ForEach(products) { product in
                ProductCard(product: product) {
                    onProductTap(product)
                }
            }
        }
    }
}

// TEMPLATES - Layouts base
struct ListTemplate<TopBar: View, Content: View>: View {
    let topBar: TopBar
    let content: Content
    
    init(@ViewBuilder topBar: () -> TopBar, 
         @ViewBuilder content: () -> Content) {
        self.topBar = topBar()
        self.content = content()
    }
    
    var body: some View {
        VStack(spacing: 0) {
            topBar
            content
                .frame(maxWidth: .infinity, maxHeight: .infinity)
        }
    }
}

// SCREENS - Pantallas completas
struct ProductListScreen: View {
    @StateObject private var viewModel = ProductListViewModel()
    
    var body: some View {
        ListTemplate(
            topBar: {
                SearchBar(
                    query: $viewModel.searchQuery,
                    onSearch: viewModel.search
                )
            },
            content: {
                ProductList(
                    products: viewModel.products,
                    onProductTap: viewModel.selectProduct
                )
            }
        )
    }
}
```

### ViewModels (MVVM)

```swift
@MainActor
class FeatureViewModel: ObservableObject {
    @Published var state = FeatureState()
    
    private let useCase: FeatureUseCaseProtocol
    private var cancellables = Set<AnyCancellable>()
    
    init(useCase: FeatureUseCaseProtocol) {
        self.useCase = useCase
    }
    
    func loadData() {
        state.isLoading = true
        
        useCase.execute()
            .receive(on: DispatchQueue.main)
            .sink(
                receiveCompletion: { [weak self] completion in
                    self?.state.isLoading = false
                    if case .failure(let error) = completion {
                        self?.state.error = error.localizedDescription
                    }
                },
                receiveValue: { [weak self] data in
                    self?.state.data = data
                }
            )
            .store(in: &cancellables)
    }
}
```

### Estados UI

```swift
struct FeatureState {
    var isLoading: Bool = false
    var data: [Item] = []
    var error: String?
}

enum FeatureAction {
    case loadData
    case refresh
    case selectItem(Item)
}
```

### Navegación Type-Safe

```swift
enum AppRoute: Codable, Hashable {
    case home
    case detail(itemId: String)
    case settings
}

struct AppNavigationView: View {
    @State private var navigationPath = NavigationPath()
    
    var body: some View {
        NavigationStack(path: $navigationPath) {
            HomeScreen()
                .navigationDestination(for: AppRoute.self) { route in
                    switch route {
                    case .home:
                        HomeScreen()
                    case .detail(let itemId):
                        DetailScreen(itemId: itemId)
                    case .settings:
                        SettingsScreen()
                    }
                }
        }
    }
}
```

### Use Cases

```swift
protocol GetUserDataUseCaseProtocol {
    func execute(userId: String) -> AnyPublisher<User, Error>
}

class GetUserDataUseCase: GetUserDataUseCaseProtocol {
    private let repository: UserRepositoryProtocol
    
    init(repository: UserRepositoryProtocol) {
        self.repository = repository
    }
    
    func execute(userId: String) -> AnyPublisher<User, Error> {
        repository.getUserData(userId: userId)
    }
}
```

---

## 🔧 Configuración de Dependencias

### Dependency Container

```swift
protocol DependencyContainer {
    var userRepository: UserRepositoryProtocol { get }
    var getUserUseCase: GetUserDataUseCaseProtocol { get }
}

class AppDependencyContainer: DependencyContainer {
    lazy var userRepository: UserRepositoryProtocol = UserRepository()
    
    lazy var getUserUseCase: GetUserDataUseCaseProtocol = {
        GetUserDataUseCase(repository: userRepository)
    }()
}
```

### App Principal

```swift
@main
struct ReferenceApp: App {
    let container = AppDependencyContainer()
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(container)
        }
    }
}
```

### Factory Pattern

```swift
class ViewModelFactory {
    private let container: DependencyContainer
    
    init(container: DependencyContainer) {
        self.container = container
    }
    
    func makeFeatureViewModel() -> FeatureViewModel {
        FeatureViewModel(useCase: container.getUserUseCase)
    }
}
```

---

## 📋 Reglas de Desarrollo

### ✅ Hacer (DO)

- Usar Combine para streams de datos reactivos
- Implementar Result<Success, Failure> para manejo de estados
- Aplicar principios SOLID en cada capa
- Escribir tests unitarios para ViewModels y Use Cases
- Usar SwiftUI Previews para componentes UI
- Seguir Human Interface Guidelines
- Implementar estados de loading, success, error
- **Respetar estrictamente la jerarquía Atomic Design**
- **Componer molecules usando solo atoms**
- **Componer organisms usando molecules y atoms**
- **Crear templates reutilizables para estructuras comunes**
- Usar @MainActor para ViewModels

### ❌ No Hacer (DON'T)

- No usar UIKit en componentes SwiftUI nuevos
- No acceder directamente a repositorios desde ViewModels
- No mezclar lógica de negocio en Views
- No usar Foundation en domain layer sin abstracciones
- No hacer llamadas async directamente en Views
- No usar NotificationCenter (preferir Combine)
- No violar las dependencias entre capas
- **No saltarse niveles en Atomic Design** (ej: atoms directamente en organisms)
- **No duplicar atoms** (reutilizar componentes básicos existentes)
- **No crear screens sin templates** cuando la estructura se repite
- No usar force unwrapping (!) en producción

### 🧪 Testing

- Unit tests para ViewModels usando XCTest
- Unit tests para Use Cases
- Integration tests para Repositories
- UI tests con SwiftUI Test Framework
- Usar TestScheduler para Combine testing

---

## 📁 Nomenclatura de Archivos

### Atomic Design Components

- **Atoms**: `PrimaryButton.swift`, `AppTextField.swift`, `AppIcon.swift`
- **Molecules**: `SearchBar.swift`, `ProductCard.swift`, `LoginForm.swift`
- **Organisms**: `NavigationBar.swift`, `ProductList.swift`, `UserProfile.swift`
- **Templates**: `ListTemplate.swift`, `DetailTemplate.swift`, `FormTemplate.swift`
- **Screens**: `ProductListScreen.swift`, `ProductDetailScreen.swift`, `LoginScreen.swift`

### Architecture Components

- **ViewModels**: `FeatureViewModel.swift`
- **Use Cases**: `GetFeatureDataUseCase.swift`
- **Repositories**: `FeatureRepository.swift` (protocolo), `FeatureRepositoryImpl.swift`
- **Models**: `Feature.swift`
- **DTOs**: `FeatureDTO.swift`
- **Mappers**: `FeatureMapper.swift`
- **States**: `FeatureState.swift`
- **Navigation**: `AppRoute.swift`

---

## 🚀 Consideraciones de Performance

- Usar `@State` y `@Binding` apropiadamente
- Implementar `@StateObject` vs `@ObservedObject` correctamente
- Aplicar `@Published` solo cuando sea necesario
- Usar `onReceive` para observar publishers
- Implementar lazy loading para listas grandes
- Caché inteligente en repositories
- Usar `@MainActor` para operaciones UI

---

## 🎨 Integración con Figma - Lecciones Aprendidas

### 📐 Análisis de Diseño

**Antes de implementar:**

1. **Obtener metadatos exactos**: Usar herramientas de Figma para extraer coordenadas, dimensiones y espaciado
2. **Analizar layout structure**: Determinar si el diseño usa posicionamiento absoluto o relativo
3. **Identificar alineaciones**: Verificar si elementos están centrados o alineados a bordes específicos
4. **Extraer especificaciones de tipografía**: Obtener family, weight, size y line-height exactos

### 🖼️ Manejo de Assets

**Fuentes tipográficas:**

- ✅ **Usar fuentes del sistema**: SF Pro, SF Compact cuando sea posible
- ✅ **Custom fonts**: Agregar TTF/OTF a Bundle y registrar en Info.plist
- ✅ **Font modifiers**: Usar `.font(.custom("FontName", size: 16))` para fuentes custom
- ❌ **No hardcodear tamaños**: Usar Dynamic Type cuando sea apropiado

**Recursos gráficos:**

- ✅ **Usar SF Symbols**: Sistema de iconos nativo de Apple
- ✅ **PDF vectoriales**: Para assets custom usar PDF en Assets.xcassets
- ✅ **@1x, @2x, @3x**: Proveer múltiples resoluciones si es necesario
- ❌ **No usar SVG**: iOS usa PDF vectoriales nativamente

### 📍 Posicionamiento y Layout

**Interpretación de coordenadas Figma:**

- **Coordenadas absolutas**: Generalmente indican posición relativa dentro de contenedor
- **Alineación superior**: Elementos en y=16, y=63, etc. sugieren VStack desde arriba
- **Espaciado consistente**: Usar `spacing` en VStack/HStack o `padding`
- **Anchos específicos**: Usar `.frame(width:)` para anchos fijos

**Implementación en SwiftUI:**

```swift
// ❌ MAL: Posicionamiento absoluto rígido
Text("Hello")
    .offset(x: 16, y: 63)

// ✅ BIEN: Layout flexible que respeta diseño
VStack(spacing: 16) {
    Text("Hello")
}
.padding(16)
```

### 🎯 Workflow de Implementación Figma

**Proceso recomendado:**

1. **Análisis**: Extraer metadatos y especificaciones antes de codificar
2. **Assets**: Preparar fuentes/iconos en formatos iOS nativos
3. **Estructura**: Implementar layout base usando VStack/HStack/ZStack
4. **Refinamiento**: Ajustar espaciado y alineación para coincidencia exacta
5. **Validación**: Comparar resultado con screenshot de Figma usando previews

**Herramientas útiles:**

- Figma Dev Mode para coordenadas exactas
- SF Symbols app para iconos del sistema
- Xcode Previews para validación visual
- Color picker para valores exactos

### ⚡ Patrones de Layout Comunes

**Diálogos centrados:**

```swift
// Template flexible para diálogos
struct DialogTemplate<Content: View>: View {
    let content: Content
    var alignment: Alignment = .topLeading
    
    init(alignment: Alignment = .topLeading, 
         @ViewBuilder content: () -> Content) {
        self.alignment = alignment
        self.content = content()
    }
    
    var body: some View {
        GeometryReader { geometry in
            content
                .frame(
                    width: geometry.size.width,
                    height: geometry.size.height,
                    alignment: alignment
                )
        }
        .padding(16)
    }
}
```

**Botones con width específico:**

```swift
// Mantener width de diseño pero permitir centrado
HStack {
    Spacer()
    PrimaryButton(title: "Acción")
        .frame(width: 237)
    Spacer()
}
```

### 🧹 Mantenimiento de Assets

**Al integrar con Figma:**

- Organizar assets en Assets.xcassets por feature
- Usar nombres descriptivos para colors/images
- Documentar origen de assets en comentarios
- Mantener versiones organizadas por pantalla/feature

**Estructura recomendada:**

```
Assets.xcassets/
├── Colors/
│   ├── Primary.colorset
│   ├── Secondary.colorset
│   └── Background.colorset
├── Images/
│   ├── [Feature]/
│   │   ├── icon-name.imageset
│   │   └── background.imageset
└── Data/
    └── CustomFont.dataset
```

---

## 🔒 Consideraciones de Seguridad iOS

### Keychain y Datos Sensibles

```swift
// Usar Keychain para tokens y datos críticos
import Security

class KeychainManager {
    static func save(key: String, data: Data) -> Bool {
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrAccount as String: key,
            kSecValueData as String: data
        ]
        
        SecItemDelete(query as CFDictionary)
        return SecItemAdd(query as CFDictionary, nil) == errSecSuccess
    }
}
```

### Privacy y Permisos

```swift
// Solicitar permisos apropiadamente
import PhotosLibrary

func requestPhotoPermission() {
    PHPhotoLibrary.requestAuthorization { status in
        DispatchQueue.main.async {
            // Manejar resultado
        }
    }
}
```

---

## 📦 Swift Package Manager

### Package.swift Configuration

```swift
// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "Reference",
    platforms: [
        .iOS(.v16)
    ],
    products: [
        .library(name: "Domain", targets: ["Domain"]),
        .library(name: "Data", targets: ["Data"]),
        .library(name: "Presentation", targets: ["Presentation"])
    ],
    dependencies: [
        .package(url: "https://github.com/hmlongco/Resolver.git", from: "1.5.0")
    ],
    targets: [
        .target(name: "Domain"),
        .target(name: "Data", dependencies: ["Domain"]),
        .target(name: "Presentation", dependencies: ["Domain"])
    ]
)
```

---

_"El código bien estructurado es como un castillo: fuerte en sus cimientos, elegante en su arquitectura"_ 🏰⚡
