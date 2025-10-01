//
//  RepositoryProtocols.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import Foundation
import Combine

// MARK: - Apps Repository Protocol
protocol AppsRepositoryProtocol {
    func getInstalledApps() -> AnyPublisher<[AppInfo], Error>
    func searchApps(query: String) -> AnyPublisher<[AppInfo], Error>
}

// MARK: - Share Repository Protocol
protocol ShareRepositoryProtocol {
    func shareContent(_ content: ShareContent, to app: AppInfo?) -> AnyPublisher<ShareResult, Error>
    func copyToClipboard(_ content: String) -> AnyPublisher<Bool, Error>
    func canShare(to app: AppInfo) -> Bool
}