//
//  ShareContent.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import Foundation

/// Modelo de dominio para contenido a compartir
struct ShareContent: Codable, Equatable {
    let text: String
    let url: URL?
    let image: Data?
    let title: String?
    
    init(
        text: String,
        url: URL? = nil,
        image: Data? = nil,
        title: String? = nil
    ) {
        self.text = text
        self.url = url
        self.image = image
        self.title = title
    }
}

/// Resultado del proceso de compartir
enum ShareResult: Equatable {
    case success
    case cancelled
    case failed(Error)
    
    static func == (lhs: ShareResult, rhs: ShareResult) -> Bool {
        switch (lhs, rhs) {
        case (.success, .success):
            return true
        case (.cancelled, .cancelled):
            return true
        case (.failed(let lhsError), .failed(let rhsError)):
            return lhsError.localizedDescription == rhsError.localizedDescription
        default:
            return false
        }
    }
}