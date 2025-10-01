//
//  ShareDialogScreen.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import SwiftUI

/// Pantalla del diálogo de compartir
struct ShareDialogScreen: View {
    @EnvironmentObject private var container: AppDependencyContainer
    @StateObject private var viewModel: ShareDialogViewModel
    
    init() {
        // Creamos el ViewModel usando un placeholder
        // En la inicialización real usaríamos el factory del container
        self._viewModel = StateObject(wrappedValue: ShareDialogViewModel(
            getInstalledAppsUseCase: GetInstalledAppsUseCase(repository: AppsRepositoryImpl()),
            shareContentUseCase: ShareContentUseCase(repository: ShareRepositoryImpl()),
            copyToClipboardUseCase: CopyToClipboardUseCase()
        ))
    }
    
    var body: some View {
        VStack(spacing: 16) {
            // Header
            Text("Compartir Contenido")
                .font(.title2)
                .fontWeight(.semibold)
                .padding(.top)
            
            // Contenido de ejemplo
            VStack(alignment: .leading, spacing: 8) {
                Text("Contenido a compartir:")
                    .font(.headline)
                
                Text("¡Hola! Este es un contenido de ejemplo para demostrar la funcionalidad de compartir en iOS.")
                    .padding()
                    .background(Color.gray.opacity(0.1))
                    .cornerRadius(8)
            }
            .padding(.horizontal)
            
            // Botón para seleccionar contenido de ejemplo
            Button("Seleccionar este contenido") {
                let sampleContent = ShareContent(
                    text: "¡Hola! Este es un contenido de ejemplo para demostrar la funcionalidad de compartir en iOS.",
                    url: URL(string: "https://developer.apple.com"),
                    title: "Ejemplo de contenido"
                )
                viewModel.handleAction(.selectContent(sampleContent))
            }
            .buttonStyle(.borderedProminent)
            
            if viewModel.state.hasValidContent {
                // Botón principal de compartir
                Button("Compartir con Sistema") {
                    viewModel.handleAction(.shareWithSystemSheet)
                }
                .buttonStyle(.borderedProminent)
                .controlSize(.large)
                
                // Botón para copiar al portapapeles
                Button("Copiar al Portapapeles") {
                    if let content = viewModel.state.selectedContent {
                        viewModel.handleAction(.copyToClipboard(content.text))
                    }
                }
                .buttonStyle(.bordered)
            }
            
            // Lista de aplicaciones disponibles
            if !viewModel.state.installedApps.isEmpty {
                Text("Aplicaciones disponibles:")
                    .font(.headline)
                    .padding(.top)
                
                LazyVStack(spacing: 8) {
                    ForEach(viewModel.state.installedApps) { app in
                        Button(action: {
                            viewModel.handleAction(.shareWithApp(app))
                        }) {
                            HStack {
                                Image(systemName: "app")
                                    .frame(width: 24, height: 24)
                                
                                Text(app.name)
                                    .foregroundColor(.primary)
                                
                                Spacer()
                                
                                Image(systemName: "chevron.right")
                                    .foregroundColor(.secondary)
                                    .font(.caption)
                            }
                            .padding()
                            .background(Color.gray.opacity(0.1))
                            .cornerRadius(8)
                        }
                    }
                }
                .padding(.horizontal)
            }
            
            Spacer()
        }
        .padding()
        .navigationTitle("Share")
        .navigationBarTitleDisplayMode(.inline)
        .alert("Error", isPresented: .constant(viewModel.state.error != nil)) {
            Button("OK") {
                viewModel.handleAction(.dismissError)
            }
        } message: {
            if let error = viewModel.state.error {
                Text(error)
            }
        }
        .onAppear {
            viewModel.handleAction(.loadApps)
        }
    }
}

#Preview {
    NavigationStack {
        ShareDialogScreen()
            .environmentObject(AppDependencyContainer())
    }
}