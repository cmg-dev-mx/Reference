//
//  DependencyContainer.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import Foundation

/// Protocol que define el contenedor de dependencias principal
protocol DependencyContainer {
    // Use Cases
    var getInstalledAppsUseCase: GetInstalledAppsUseCaseProtocol { get }
    var shareContentUseCase: ShareContentUseCaseProtocol { get }
    var copyToClipboardUseCase: CopyToClipboardUseCaseProtocol { get }
    
    // Repositories
    var appsRepository: AppsRepositoryProtocol { get }
    var shareRepository: ShareRepositoryProtocol { get }
}

/// Implementación principal del contenedor de dependencias
class AppDependencyContainer: DependencyContainer {
    
    // MARK: - Repositories
    lazy var appsRepository: AppsRepositoryProtocol = AppsRepositoryImpl()
    lazy var shareRepository: ShareRepositoryProtocol = ShareRepositoryImpl()
    
    // MARK: - Use Cases
    lazy var getInstalledAppsUseCase: GetInstalledAppsUseCaseProtocol = {
        GetInstalledAppsUseCase(repository: appsRepository)
    }()
    
    lazy var shareContentUseCase: ShareContentUseCaseProtocol = {
        ShareContentUseCase(repository: shareRepository)
    }()
    
    lazy var copyToClipboardUseCase: CopyToClipboardUseCaseProtocol = {
        CopyToClipboardUseCase()
    }()
}

/// Factory para crear ViewModels con dependencias inyectadas
class ViewModelFactory {
    private let container: DependencyContainer
    
    init(container: DependencyContainer) {
        self.container = container
    }
    
    func makeShareDialogViewModel() -> ShareDialogViewModel {
        ShareDialogViewModel(
            getInstalledAppsUseCase: container.getInstalledAppsUseCase,
            shareContentUseCase: container.shareContentUseCase,
            copyToClipboardUseCase: container.copyToClipboardUseCase
        )
    }
}