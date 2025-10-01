//
//  AppNavigationView.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import SwiftUI

/// Vista principal de navegación de la aplicación
struct AppNavigationView: View {
    @State private var navigationPath = NavigationPath()
    @EnvironmentObject private var container: AppDependencyContainer
    
    var body: some View {
        NavigationStack(path: $navigationPath) {
            HomeScreen()
                .navigationDestination(for: AppRoute.self) { route in
                    switch route {
                    case .home:
                        HomeScreen()
                    case .shareDialog:
                        ShareDialogScreen()
                    case .settings:
                        SettingsScreen()
                    }
                }
        }
    }
}

#Preview {
    AppNavigationView()
        .environmentObject(AppDependencyContainer())
}