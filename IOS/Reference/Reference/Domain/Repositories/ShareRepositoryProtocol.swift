//
//  ShareRepositoryProtocol.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import Foundation
import Combine

/// Protocolo para el repositorio de funcionalidad de compartir
protocol ShareRepositoryProtocol {
    /// Comparte contenido usando el sistema nativo de iOS
    func shareContent(_ content: ShareContent) -> AnyPublisher<ShareResult, Never>
    
    /// Comparte contenido con una aplicación específica
    func shareContentWithApp(_ content: ShareContent, appInfo: AppInfo) -> AnyPublisher<ShareResult, Never>
    
    /// Copia contenido al portapapeles
    func copyToClipboard(_ content: String) -> AnyPublisher<Bool, Never>
}