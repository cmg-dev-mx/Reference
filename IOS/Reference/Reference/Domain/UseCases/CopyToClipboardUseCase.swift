//
//  CopyToClipboardUseCase.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import Foundation
import Combine

/// Protocolo para el use case de copiar al portapapeles
protocol CopyToClipboardUseCaseProtocol {
    func execute(text: String) -> AnyPublisher<Bool, Never>
}

/// Use case para copiar contenido al portapapeles
class CopyToClipboardUseCase: CopyToClipboardUseCaseProtocol {
    private let repository: ShareRepositoryProtocol
    
    init(repository: ShareRepositoryProtocol = ShareRepositoryImpl()) {
        self.repository = repository
    }
    
    func execute(text: String) -> AnyPublisher<Bool, Never> {
        repository.copyToClipboard(text)
    }
}