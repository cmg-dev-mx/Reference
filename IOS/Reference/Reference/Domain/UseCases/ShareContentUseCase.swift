//
//  ShareContentUseCase.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import Foundation
import Combine

/// Protocolo para el use case de compartir contenido
protocol ShareContentUseCaseProtocol {
    func execute(content: ShareContent) -> AnyPublisher<ShareResult, Never>
    func executeWithApp(content: ShareContent, appInfo: AppInfo) -> AnyPublisher<ShareResult, Never>
}

/// Use case para compartir contenido
class ShareContentUseCase: ShareContentUseCaseProtocol {
    private let repository: ShareRepositoryProtocol
    
    init(repository: ShareRepositoryProtocol) {
        self.repository = repository
    }
    
    func execute(content: ShareContent) -> AnyPublisher<ShareResult, Never> {
        repository.shareContent(content)
    }
    
    func executeWithApp(content: ShareContent, appInfo: AppInfo) -> AnyPublisher<ShareResult, Never> {
        repository.shareContentWithApp(content, appInfo: appInfo)
    }
}