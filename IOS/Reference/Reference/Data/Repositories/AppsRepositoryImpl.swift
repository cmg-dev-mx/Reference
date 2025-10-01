//
//  AppsRepositoryImpl.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import Foundation
import Combine
import UIKit

/// Implementación del repositorio de aplicaciones usando APIs nativas de iOS
class AppsRepositoryImpl: AppsRepositoryProtocol {
    
    func getInstalledApps() -> AnyPublisher<[AppInfo], Error> {
        Future { promise in
            // En iOS, no hay API pública para obtener todas las apps instaladas
            // Por ahora, retornamos una lista mock de apps comunes que soportan sharing
            let mockApps = self.getMockSharingApps()
            promise(.success(mockApps))
        }
        .eraseToAnyPublisher()
    }
    
    func canAppHandleContent(_ appInfo: AppInfo, content: ShareContent) -> Bool {
        // En una implementación real, verificaríamos los URL schemes y activity types
        // Por ahora, retornamos true para todas las apps mock
        return appInfo.canShare
    }
    
    // MARK: - Private Methods
    
    private func getMockSharingApps() -> [AppInfo] {
        return [
            AppInfo(
                name: "Messages",
                bundleIdentifier: "com.apple.MobileSMS",
                canShare: true
            ),
            AppInfo(
                name: "Mail",
                bundleIdentifier: "com.apple.mobilemail",
                canShare: true
            ),
            AppInfo(
                name: "Notes",
                bundleIdentifier: "com.apple.mobilenotes",
                canShare: true
            ),
            AppInfo(
                name: "WhatsApp",
                bundleIdentifier: "net.whatsapp.WhatsApp",
                canShare: true
            ),
            AppInfo(
                name: "Telegram",
                bundleIdentifier: "ph.telegra.Telegraph",
                canShare: true
            ),
            AppInfo(
                name: "Twitter",
                bundleIdentifier: "com.atebits.Tweetie2",
                canShare: true
            )
        ]
    }
}