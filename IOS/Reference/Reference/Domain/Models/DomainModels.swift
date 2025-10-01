//
//  DomainModels.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import Foundation

// MARK: - App Info Model
struct AppInfo: Identifiable, Equatable, Hashable {
    let id: String
    let name: String
    let bundleIdentifier: String
    let iconData: Data?
    let isSystemApp: Bool
    
    init(
        id: String = UUID().uuidString,
        name: String,
        bundleIdentifier: String,
        iconData: Data? = nil,
        isSystemApp: Bool = false
    ) {
        self.id = id
        self.name = name
        self.bundleIdentifier = bundleIdentifier
        self.iconData = iconData
        self.isSystemApp = isSystemApp
    }
}

// MARK: - Share Content Model
struct ShareContent: Equatable {
    let text: String
    let url: URL?
    let data: Data?
    let contentType: ShareContentType
    
    init(text: String, url: URL? = nil, data: Data? = nil, contentType: ShareContentType = .text) {
        self.text = text
        self.url = url
        self.data = data
        self.contentType = contentType
    }
}

// MARK: - Share Content Type
enum ShareContentType: String, CaseIterable {
    case text = "text"
    case url = "url"
    case image = "image"
    case file = "file"
}

// MARK: - Share Result
enum ShareResult {
    case success
    case cancelled
    case failed(Error)
}