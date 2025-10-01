//
//  DependencyContainer.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import Foundation

// MARK: - Dependency Container Protocol
protocol DependencyContainer {
    // Repositories
    var shareRepository: ShareRepositoryProtocol { get }
    var appsRepository: AppsRepositoryProtocol { get }
    
    // Use Cases
    var getInstalledAppsUseCase: GetInstalledAppsUseCaseProtocol { get }
    var shareContentUseCase: ShareContentUseCaseProtocol { get }
    var copyToClipboardUseCase: CopyToClipboardUseCaseProtocol { get }
}

// MARK: - App Dependency Container
class AppDependencyContainer: DependencyContainer {
    
    // MARK: - Repositories
    lazy var shareRepository: ShareRepositoryProtocol = {
        ShareRepositoryImpl()
    }()
    
    lazy var appsRepository: AppsRepositoryProtocol = {
        AppsRepositoryImpl()
    }()
    
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

// MARK: - ViewModelFactory
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