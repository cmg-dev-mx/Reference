//
//  GetInstalledAppsUseCase.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import Foundation
import Combine

/// Protocolo para el use case de obtener aplicaciones instaladas
protocol GetInstalledAppsUseCaseProtocol {
    func execute() -> AnyPublisher<[AppInfo], Error>
}

/// Use case para obtener aplicaciones instaladas que pueden recibir contenido compartido
class GetInstalledAppsUseCase: GetInstalledAppsUseCaseProtocol {
    private let repository: AppsRepositoryProtocol
    
    init(repository: AppsRepositoryProtocol) {
        self.repository = repository
    }
    
    func execute() -> AnyPublisher<[AppInfo], Error> {
        repository.getInstalledApps()
            .map { apps in
                // Filtrar solo apps que pueden compartir y ordenar alfabéticamente
                apps.filter { $0.canShare }
                    .sorted { $0.name < $1.name }
            }
            .eraseToAnyPublisher()
    }
}