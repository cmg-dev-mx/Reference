//
//  HomeScreen.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import SwiftUI

/// Pantalla principal de la aplicación
struct HomeScreen: View {
    @EnvironmentObject private var container: AppDependencyContainer
    
    var body: some View {
        VStack(spacing: 24) {
            Spacer()
            
            // Logo o imagen principal
            Image(systemName: "square.and.arrow.up")
                .font(.system(size: 80))
                .foregroundColor(.blue)
            
            // Título principal
            Text("Reference App")
                .font(.largeTitle)
                .fontWeight(.bold)
            
            // Subtítulo
            Text("Demostración de arquitectura iOS")
                .font(.headline)
                .foregroundColor(.secondary)
                .multilineTextAlignment(.center)
            
            Spacer()
            
            // Botón principal para abrir share dialog
            NavigationLink(value: AppRoute.shareDialog) {
                Label("Abrir Share Dialog", systemImage: "square.and.arrow.up")
                    .font(.headline)
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .frame(height: 50)
                    .background(Color.blue)
                    .cornerRadius(12)
            }
            .padding(.horizontal, 32)
            
            // Botón secundario para configuración
            NavigationLink(value: AppRoute.settings) {
                Label("Configuración", systemImage: "gear")
                    .font(.subheadline)
                    .foregroundColor(.blue)
                    .frame(maxWidth: .infinity)
                    .frame(height: 44)
                    .background(Color.blue.opacity(0.1))
                    .cornerRadius(8)
            }
            .padding(.horizontal, 32)
            
            Spacer()
        }
        .padding()
        .navigationTitle("Home")
        .navigationBarTitleDisplayMode(.inline)
    }
}

#Preview {
    NavigationStack {
        HomeScreen()
            .environmentObject(AppDependencyContainer())
    }
}