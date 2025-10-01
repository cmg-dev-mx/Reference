//
//  AppRoute.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import Foundation
import SwiftUI

// MARK: - App Routes
enum AppRoute: Codable, Hashable {
    case home
    case shareDialog(content: String)
    case settings
}

// MARK: - Navigation Manager
@MainActor
class NavigationManager: ObservableObject {
    @Published var path = NavigationPath()
    
    func navigate(to route: AppRoute) {
        path.append(route)
    }
    
    func navigateBack() {
        path.removeLast()
    }
    
    func navigateToRoot() {
        path.removeLast(path.count)
    }
    
    func popToRoot() {
        path = NavigationPath()
    }
}