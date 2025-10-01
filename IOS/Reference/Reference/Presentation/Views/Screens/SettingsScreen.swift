//
//  SettingsScreen.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import SwiftUI

/// Pantalla de configuración
struct SettingsScreen: View {
    var body: some View {
        List {
            Section("Aplicación") {
                HStack {
                    Label("Versión", systemImage: "info.circle")
                    Spacer()
                    Text("1.0.0")
                        .foregroundColor(.secondary)
                }
                
                HStack {
                    Label("Build", systemImage: "hammer")
                    Spacer()
                    Text("1")
                        .foregroundColor(.secondary)
                }
            }
            
            Section("Arquitectura") {
                Label("Clean Architecture", systemImage: "building.columns")
                Label("MVVM Pattern", systemImage: "arrow.triangle.branch")
                Label("Combine Framework", systemImage: "link")
                Label("SwiftUI", systemImage: "swift")
            }
            
            Section("Funcionalidades") {
                Label("Share Dialog", systemImage: "square.and.arrow.up")
                Label("Navigation Stack", systemImage: "map")
                Label("Dependency Injection", systemImage: "syringe")
                Label("Atomic Design", systemImage: "atom")
            }
        }
        .navigationTitle("Settings")
        .navigationBarTitleDisplayMode(.inline)
    }
}

#Preview {
    NavigationStack {
        SettingsScreen()
    }
}