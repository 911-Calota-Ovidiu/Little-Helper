package org.example.a.MockApi

import org.example.MockApiServer
import org.example.MockRoute
import java.awt.*
import javax.swing.*

class
MockApiPanel : JPanel() {
    private val apiServer = MockApiServer()
    private val existingRoutes = mutableListOf<MockRoute>()

    init {
        layout = BorderLayout()

        val pathField = JTextField("/api/test")
        val methodCombo = JComboBox(arrayOf("GET", "POST", "PUT", "DELETE"))
        val responseArea = JTextArea("""{"message": "Hello from mock API"}""", 5, 30)
        val startButton = JButton("Start Server")
        val addRouteButton = JButton("Add Route")
        val logArea = JTextArea(5, 30).apply { isEditable = false }

        val controlPanel = JPanel(GridLayout(0, 1))
        controlPanel.add(JLabel("Endpoint path:"))
        controlPanel.add(pathField)
        controlPanel.add(JLabel("Method:"))
        controlPanel.add(methodCombo)
        controlPanel.add(JLabel("Mock response:"))
        controlPanel.add(JScrollPane(responseArea))
        controlPanel.add(addRouteButton)
        controlPanel.add(startButton)

        add(controlPanel, BorderLayout.NORTH)
        add(JScrollPane(logArea), BorderLayout.CENTER)

        startButton.addActionListener {
            apiServer.start(8080)
            logArea.append("Server started at http://localhost:8080\n")
            existingRoutes.forEach {
                apiServer.addRoute(it)
                logArea.append("Re-registered: ${it.method} ${it.path}\n")
            }
        }

        addRouteButton.addActionListener {
            val rawPath = pathField.text.trim()
            val path = if (!rawPath.startsWith("/")) "/$rawPath" else rawPath
            val method = methodCombo.selectedItem.toString()
            val response = responseArea.text.trim()

            val route = MockRoute(method, path, response)
            existingRoutes.add(route)
            apiServer.addRoute(route)

            logArea.append("Route added: $method $path\n")
        }
    }
}
