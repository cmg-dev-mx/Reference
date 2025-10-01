//
//  ShareDialogViewModel.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import Foundation
import Combine

/// ViewModel para el diálogo de compartir siguiendo patrón MVVM
@MainActor
class ShareDialogViewModel: ObservableObject {
    @Published var state = ShareDialogState()
    
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
    }
    
    func handleAction(_ action: ShareDialogAction) {
        switch action {
        case .loadApps:
            loadInstalledApps()
        case .selectContent(let content):
            selectContent(content)
        case .shareWithSystemSheet:
            shareWithSystemSheet()
        case .shareWithApp(let appInfo):
            shareWithSpecificApp(appInfo)
        case .copyToClipboard(let text):
            copyTextToClipboard(text)
        case .dismissError:
            dismissError()
        case .dismissShareSheet:
            dismissShareSheet()
        }
    }
    
    // MARK: - Private Methods
    
    private func loadInstalledApps() {
        state.isLoading = true
        state.error = nil
        
        getInstalledAppsUseCase.execute()
            .receive(on: DispatchQueue.main)
            .sink(
                receiveCompletion: { [weak self] completion in
                    self?.state.isLoading = false
                    if case .failure(let error) = completion {
                        self?.state.error = error.localizedDescription
                    }
                },
                receiveValue: { [weak self] apps in
                    self?.state.installedApps = apps
                }
            )
            .store(in: &cancellables)
    }
    
    private func selectContent(_ content: ShareContent) {
        state.selectedContent = content
        state.error = nil
    }
    
    private func shareWithSystemSheet() {
        guard let content = state.selectedContent else {
            state.error = "No hay contenido seleccionado para compartir"
            return
        }
        
        shareContentUseCase.execute(content: content)
            .receive(on: DispatchQueue.main)
            .sink { [weak self] result in
                self?.handleShareResult(result)
            }
            .store(in: &cancellables)
    }
    
    private func shareWithSpecificApp(_ appInfo: AppInfo) {
        guard let content = state.selectedContent else {
            state.error = "No hay contenido seleccionado para compartir"
            return
        }
        
        shareContentUseCase.executeWithApp(content: content, appInfo: appInfo)
            .receive(on: DispatchQueue.main)
            .sink { [weak self] result in
                self?.handleShareResult(result)
            }
            .store(in: &cancellables)
    }
    
    private func copyTextToClipboard(_ text: String) {
        copyToClipboardUseCase.execute(text: text)
            .receive(on: DispatchQueue.main)
            .sink { [weak self] success in
                if success {
                    // Aquí podrías mostrar un feedback visual (toast, etc.)
                    print("Texto copiado al portapapeles")
                } else {
                    self?.state.error = "Error al copiar al portapapeles"
                }
            }
            .store(in: &cancellables)
    }
    
    private func handleShareResult(_ result: ShareResult) {
        state.shareResult = result
        
        switch result {
        case .success:
            // Compartido exitosamente
            break
        case .cancelled:
            // Usuario canceló
            break
        case .failed(let error):
            state.error = error.localizedDescription
        }
    }
    
    private func dismissError() {
        state.error = nil
    }
    
    private func dismissShareSheet() {
        state.isShareSheetPresented = false
        state.shareResult = nil
    }
}