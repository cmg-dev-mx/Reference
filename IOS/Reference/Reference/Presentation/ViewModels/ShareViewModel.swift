//
//  ShareViewModel.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import Foundation
import Combine

// MARK: - Share Dialog ViewModel
@MainActor
class ShareViewModel: ObservableObject {
    @Published var state = ShareState()
    
    private let getInstalledAppsUseCase: GetInstalledAppsUseCaseProtocol
    private let shareContentUseCase: ShareContentUseCaseProtocol
    private let copyToClipboardUseCase: CopyToClipboardUseCaseProtocol
    
    private var cancellables = Set<AnyCancellable>()
    
    init(
        getInstalledAppsUseCase: GetInstalledAppsUseCaseProtocol,
        shareContentUseCase: ShareContentUseCaseProtocol,
        copyToClipboardUseCase: CopyToClipboardUseCaseProtocol
    ) {
        self.getInstalledAppsUseCase = getInstalledAppsUseCase
        self.shareContentUseCase = shareContentUseCase
        self.copyToClipboardUseCase = copyToClipboardUseCase
        
        setupContent()
    }
    
    func handleAction(_ action: ShareAction) {
        switch action {
        case .loadApps:
            loadAvailableApps()
        case .searchApps(let query):
            state.updateSearch(query)
        case .selectApp(let app):
            selectApp(app)
        case .shareToApp(let app):
            shareToApp(app)
        case .copyToClipboard:
            copyContentToClipboard()
        case .dismissShareSheet:
            state.isShareSheetPresented = false
        case .clearError:
            state.error = nil
        }
    }
    
    // MARK: - Private Methods
    
    private func setupContent() {
        // Set up default content for demonstration
        state.selectedContent = ShareContent(
            text: "Check out this awesome app!",
            contentType: .text
        )
    }
    
    private func loadAvailableApps() {
        state.setLoading(true)
        
        getInstalledAppsUseCase.execute()
            .receive(on: DispatchQueue.main)
            .sink(
                receiveCompletion: { [weak self] completion in
                    if case .failure(let error) = completion {
                        self?.state.setError(error.localizedDescription)
                    }
                },
                receiveValue: { [weak self] apps in
                    self?.state.setApps(apps)
                }
            )
            .store(in: &cancellables)
    }
    
    private func selectApp(_ app: AppInfo) {
        // Handle app selection if needed
        print("Selected app: \(app.name)")
    }
    
    private func shareToApp(_ app: AppInfo) {
        guard let content = state.selectedContent else {
            state.setError("No content selected")
            return
        }
        
        shareContentUseCase.execute(content: content, to: app)
            .receive(on: DispatchQueue.main)
            .sink(
                receiveCompletion: { [weak self] completion in
                    if case .failure(let error) = completion {
                        self?.state.setError(error.localizedDescription)
                    }
                },
                receiveValue: { [weak self] result in
                    self?.handleShareResult(result)
                }
            )
            .store(in: &cancellables)
    }
    
    private func copyContentToClipboard() {
        guard let content = state.selectedContent else {
            state.setError("No content to copy")
            return
        }
        
        copyToClipboardUseCase.execute(content: content.text)
            .receive(on: DispatchQueue.main)
            .sink(
                receiveCompletion: { [weak self] completion in
                    if case .failure(let error) = completion {
                        self?.state.setError(error.localizedDescription)
                    }
                },
                receiveValue: { success in
                    if success {
                        print("Content copied to clipboard")
                    }
                }
            )
            .store(in: &cancellables)
    }
    
    private func handleShareResult(_ result: ShareResult) {
        state.shareResult = result
        
        switch result {
        case .success:
            print("Content shared successfully")
        case .cancelled:
            print("Share cancelled by user")
        case .failed(let error):
            state.setError(error.localizedDescription)
        }
    }
}