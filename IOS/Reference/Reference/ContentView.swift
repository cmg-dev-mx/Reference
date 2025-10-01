//
//  ContentView.swift
//  Reference
//
//  Created by Cesar Garduno on 10/1/25.
//

import SwiftUI

/// Vista de contenido principal - Deprecated, usar AppNavigationView
struct ContentView: View {
    var body: some View {
        VStack {
            Image(systemName: "exclamationmark.triangle")
                .imageScale(.large)
                .foregroundStyle(.orange)
            Text("ContentView is deprecated")
                .font(.headline)
            Text("Use AppNavigationView instead")
                .font(.caption)
                .foregroundColor(.secondary)
        }
        .padding()
    }
}

#Preview {
    ContentView()
}
