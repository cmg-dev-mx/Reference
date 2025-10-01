//
//  AppRoute.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import Foundation

/// Definición de rutas type-safe para navegación
enum AppRoute: Codable, Hashable {
    case home
    case shareDialog
    case settings
}

/// Extensión para obtener títulos de navegación
extension AppRoute {
    var title: String {
        switch self {
        case .home:
            return "Home"
        case .shareDialog:
            return "Share"
        case .settings:
            return "Settings"
        }
    }
}