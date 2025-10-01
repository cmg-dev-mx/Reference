//
//  RepositoryImplementations.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import Foundation
import Combine
import UIKit

// MARK: - Apps Repository Implementation
class AppsRepositoryImpl: AppsRepositoryProtocol {
    
    func getInstalledApps() -> AnyPublisher<[AppInfo], Error> {
        // TODO: Implement actual iOS app discovery
        // Note: iOS has restrictions on app enumeration for privacy
        // This will return mock data for now
        
        let mockApps = [
            AppInfo(
                name: "Messages",
                bundleIdentifier: "com.apple.MobileSMS",
                isSystemApp: true
            ),
            AppInfo(
                name: "Mail",
                bundleIdentifier: "com.apple.mobilemail",
                isSystemApp: true
            ),
            AppInfo(
                name: "Safari",
                bundleIdentifier: "com.apple.mobilesafari",
                isSystemApp: true
            )
        ]
        
        return Just(mockApps)
            .setFailureType(to: Error.self)
            .eraseToAnyPublisher()
    }
    
    func searchApps(query: String) -> AnyPublisher<[AppInfo], Error> {
        return getInstalledApps()
            .map { apps in
                apps.filter { app in
                    app.name.lowercased().contains(query.lowercased())
                }
            }
            .eraseToAnyPublisher()
    }
}

// MARK: - Share Repository Implementation
class ShareRepositoryImpl: ShareRepositoryProtocol {
    
    func shareContent(_ content: ShareContent, to app: AppInfo?) -> AnyPublisher<ShareResult, Error> {
        return Future<ShareResult, Error> { promise in
            DispatchQueue.main.async {
                let activityItems: [Any] = [content.text]
                
                let activityViewController = UIActivityViewController(
                    activityItems: activityItems,
                    applicationActivities: nil
                )
                
                // Present the share sheet
                if let windowScene = UIApplication.shared.connectedScenes.first as? UIWindowScene,
                   let window = windowScene.windows.first,
                   let rootViewController = window.rootViewController {
                    
                    activityViewController.completionWithItemsHandler = { _, completed, _, error in
                        if let error = error {
                            promise(.success(.failed(error)))
                        } else if completed {
                            promise(.success(.success))
                        } else {
                            promise(.success(.cancelled))
                        }
                    }
                    
                    rootViewController.present(activityViewController, animated: true)
                } else {
                    promise(.success(.failed(ShareError.noViewController)))
                }
            }
        }
        .eraseToAnyPublisher()
    }
    
    func copyToClipboard(_ content: String) -> AnyPublisher<Bool, Error> {
        return Future<Bool, Error> { promise in
            DispatchQueue.main.async {
                UIPasteboard.general.string = content
                promise(.success(true))
            }
        }
        .eraseToAnyPublisher()
    }
    
    func canShare(to app: AppInfo) -> Bool {
        // TODO: Implement app-specific sharing capability check
        // For now, assume all apps can receive shared content
        return true
    }
}

// MARK: - Share Errors
enum ShareError: LocalizedError {
    case noViewController
    case unsupportedContentType
    case shareNotAvailable
    
    var errorDescription: String? {
        switch self {
        case .noViewController:
            return "No view controller available to present share sheet"
        case .unsupportedContentType:
            return "Content type not supported for sharing"
        case .shareNotAvailable:
            return "Sharing not available"
        }
    }
}