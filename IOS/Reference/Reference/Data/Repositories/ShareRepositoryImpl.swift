//
//  ShareRepositoryImpl.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import Foundation
import Combine
import UIKit

/// Implementación del repositorio de sharing usando APIs nativas de iOS
class ShareRepositoryImpl: ShareRepositoryProtocol {
    
    func shareContent(_ content: ShareContent) -> AnyPublisher<ShareResult, Never> {
        Future { promise in
            DispatchQueue.main.async {
                var activityItems: [Any] = [content.text]
                
                if let url = content.url {
                    activityItems.append(url)
                }
                
                if let imageData = content.image, let image = UIImage(data: imageData) {
                    activityItems.append(image)
                }
                
                let activityViewController = UIActivityViewController(
                    activityItems: activityItems,
                    applicationActivities: nil
                )
                
                // Configurar completion handler
                activityViewController.completionWithItemsHandler = { _, completed, _, error in
                    if let error = error {
                        promise(.success(.failed(error)))
                    } else if completed {
                        promise(.success(.success))
                    } else {
                        promise(.success(.cancelled))
                    }
                }
                
                // Obtener el root view controller para presentar el share sheet
                if let windowScene = UIApplication.shared.connectedScenes.first as? UIWindowScene,
                   let window = windowScene.windows.first,
                   let rootViewController = window.rootViewController {
                    
                    // Configurar popover para iPad
                    if let popover = activityViewController.popoverPresentationController {
                        popover.sourceView = rootViewController.view
                        popover.sourceRect = CGRect(x: rootViewController.view.bounds.midX,
                                                  y: rootViewController.view.bounds.midY,
                                                  width: 0, height: 0)
                        popover.permittedArrowDirections = []
                    }
                    
                    rootViewController.present(activityViewController, animated: true)
                } else {
                    promise(.success(.failed(ShareError.noViewController)))
                }
            }
        }
        .eraseToAnyPublisher()
    }
    
    func shareContentWithApp(_ content: ShareContent, appInfo: AppInfo) -> AnyPublisher<ShareResult, Never> {
        // Para una implementación específica por app, usaríamos URL schemes
        // Por ahora, delegamos al sharing system nativo
        return shareContent(content)
    }
    
    func copyToClipboard(_ content: String) -> AnyPublisher<Bool, Never> {
        Future { promise in
            DispatchQueue.main.async {
                UIPasteboard.general.string = content
                promise(.success(true))
            }
        }
        .eraseToAnyPublisher()
    }
}

// MARK: - Custom Errors

enum ShareError: LocalizedError {
    case noViewController
    case invalidContent
    
    var errorDescription: String? {
        switch self {
        case .noViewController:
            return "No se pudo encontrar un view controller para presentar el share sheet"
        case .invalidContent:
            return "El contenido a compartir no es válido"
        }
    }
}