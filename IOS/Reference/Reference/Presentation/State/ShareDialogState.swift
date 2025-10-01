//
//  ShareDialogState.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import Foundation

/// Estado para el diálogo de compartir
struct ShareDialogState {
    var isLoading: Bool = false
    var installedApps: [AppInfo] = []
    var selectedContent: ShareContent?
    var error: String?
    var isShareSheetPresented: Bool = false
    var shareResult: ShareResult?
    
    /// Computed property para verificar si hay contenido válido para compartir
    var hasValidContent: Bool {
        guard let content = selectedContent else { return false }
        return !content.text.isEmpty || content.url != nil || content.image != nil
    }
}

/// Acciones que puede realizar el usuario en el diálogo de compartir
enum ShareDialogAction {
    case loadApps
    case selectContent(ShareContent)
    case shareWithSystemSheet
    case shareWithApp(AppInfo)
    case copyToClipboard(String)
    case dismissError
    case dismissShareSheet
}