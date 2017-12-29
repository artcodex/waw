package renderEngine

import org.lwjgl.LWJGLException
import org.lwjgl.opengl.*
import org.lwjgl.opengl.GL11



class DisplayManager {
    companion object {
        private const val WIDTH = 1280
        private const val HEIGHT = 720
        const val FPS_CAP = 60

        fun createDisplay() {
            var attribs = ContextAttribs(3,2)
                    .withForwardCompatible(true)
                    .withProfileCore(true)

            try {
                Display.setDisplayMode(DisplayMode(WIDTH, HEIGHT))
                Display.create(PixelFormat(), attribs)
                Display.setTitle("Game Playground")
            } catch (e : LWJGLException) {
                e.printStackTrace()
            }

            GL11.glViewport(0,0, WIDTH, HEIGHT)
        }

        fun updateDisplay() {
            Display.sync(FPS_CAP)
            Display.update()
        }

        fun closeDisplay() {
            Display.destroy()
        }
    }
}