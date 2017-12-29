package engineTester

import org.lwjgl.opengl.Display
import renderEngine.DisplayManager
import renderEngine.Loader
import renderEngine.Renderer
import shaders.StaticShader

fun main(args : Array<String>) {
    DisplayManager.createDisplay()

    //Setup the scene
    val loader = Loader()
    val renderer = Renderer()
    val shader = StaticShader()

    //Setup data
    val verticesList = listOf(
        -0.5f, 0.5f, 0f,
        -0.5f, -0.5f, 0f,
        0.5f, -0.5f, 0f,
        0.5f, 0.5f, 0f
    )

    val indicesList = listOf(
        0, 1, 3,
        3, 1, 2
    )

    val vertices = verticesList.toFloatArray()
    val indices = indicesList.toIntArray()
    val model = loader.loadToVAO(vertices, indices)

    while (!Display.isCloseRequested()) {
        renderer.prepare()
        shader.start()
        renderer.render(model)
        shader.stop()

        DisplayManager.updateDisplay()
    }

    shader.cleanUp()
    loader.cleanUp()
    DisplayManager.closeDisplay()
}