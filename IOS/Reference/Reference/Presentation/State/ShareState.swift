//
//  ShareState.swift
//  Reference
//
//  Created by iOS Architecture Setup on 10/1/25.
//

import Foundation

// MARK: - Share Dialog State
struct ShareState {
    var isLoading: Bool = false
    var searchQuery: String = ""
    var availableApps: [AppInfo] = []
    var filteredApps: [AppInfo] = []
    var selectedContent: ShareContent?
    var error: String?
    var isShareSheetPresented: Bool = false
    var shareResult: ShareResult?
    
    mutating func setLoading(_ loading: Bool) {
        isLoading = loading
        if loading {
            error = nil
        }
    }
    
    mutating func setError(_ errorMessage: String) {
        error = errorMessage
        isLoading = false
    }
    
    mutating func setApps(_ apps: [AppInfo]) {
        availableApps = apps
        filteredApps = apps
        isLoading = false
        error = nil
    }
    
    mutating func updateSearch(_ query: String) {
        searchQuery = query
        if query.isEmpty {
            filteredApps = availableApps
        } else {
            filteredApps = availableApps.filter { app in
                app.name.lowercased().contains(query.lowercased())
            }
        }
    }
}

// MARK: - Share Dialog Actions
enum ShareAction {
    case loadApps
    case searchApps(query: String)
    case selectApp(AppInfo)
    case shareToApp(AppInfo)
    case copyToClipboard
    case dismissShareSheet
    case clearError
}