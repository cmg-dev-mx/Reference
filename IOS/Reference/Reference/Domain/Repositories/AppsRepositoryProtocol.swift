//
//  AppsRepositoryProtocol.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import Foundation
import Combine

/// Protocolo para el repositorio de aplicaciones
protocol AppsRepositoryProtocol {
    /// Obtiene la lista de aplicaciones instaladas que pueden recibir contenido compartido
    func getInstalledApps() -> AnyPublisher<[AppInfo], Error>
    
    /// Verifica si una aplicación específica puede manejar un tipo de contenido
    func canAppHandleContent(_ appInfo: AppInfo, content: ShareContent) -> Bool
}