//
//  UseCaseProtocols.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import Foundation
import Combine

// MARK: - Get Installed Apps Use Case Protocol
protocol GetInstalledAppsUseCaseProtocol {
    func execute() -> AnyPublisher<[AppInfo], Error>
}

// MARK: - Share Content Use Case Protocol
protocol ShareContentUseCaseProtocol {
    func execute(content: ShareContent, to app: AppInfo?) -> AnyPublisher<ShareResult, Error>
}

// MARK: - Copy to Clipboard Use Case Protocol
protocol CopyToClipboardUseCaseProtocol {
    func execute(content: String) -> AnyPublisher<Bool, Error>
}

// MARK: - Get Installed Apps Use Case Implementation
class GetInstalledAppsUseCase: GetInstalledAppsUseCaseProtocol {
    private let repository: AppsRepositoryProtocol
    
    init(repository: AppsRepositoryProtocol) {
        self.repository = repository
    }
    
    func execute() -> AnyPublisher<[AppInfo], Error> {
        repository.getInstalledApps()
    }
}

// MARK: - Share Content Use Case Implementation
class ShareContentUseCase: ShareContentUseCaseProtocol {
    private let repository: ShareRepositoryProtocol
    
    init(repository: ShareRepositoryProtocol) {
        self.repository = repository
    }
    
    func execute(content: ShareContent, to app: AppInfo?) -> AnyPublisher<ShareResult, Error> {
        repository.shareContent(content, to: app)
    }
}

// MARK: - Copy to Clipboard Use Case Implementation
class CopyToClipboardUseCase: CopyToClipboardUseCaseProtocol {
    private let repository: ShareRepositoryProtocol
    
    init(repository: ShareRepositoryProtocol) {
        self.repository = repository
    }
    
    func execute(content: String) -> AnyPublisher<Bool, Error> {
        repository.copyToClipboard(content)
    }
}

// MARK: - Convenience initializer for clipboard-only use case
extension CopyToClipboardUseCase {
    convenience init() {
        self.init(repository: ShareRepositoryImpl())
    }
}