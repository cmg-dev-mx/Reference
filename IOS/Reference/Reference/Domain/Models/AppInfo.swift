//
//  AppInfo.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import Foundation

/// Modelo de dominio para información de aplicaciones instaladas
struct AppInfo: Identifiable, Codable, Equatable {
    let id: String
    let name: String
    let bundleIdentifier: String
    let iconData: Data?
    let canShare: Bool
    
    init(
        id: String = UUID().uuidString,
        name: String,
        bundleIdentifier: String,
        iconData: Data? = nil,
        canShare: Bool = true
    ) {
        self.id = id
        self.name = name
        self.bundleIdentifier = bundleIdentifier
        self.iconData = iconData
        self.canShare = canShare
    }
}