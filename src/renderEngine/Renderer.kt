package renderEngine

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

class Renderer {
    fun prepare() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT)
        GL11.glClearColor(1.0f, 0.0f, 0.0f, 1.0f)
    }

    fun render(model : RawModel) {
        GL30.glBindVertexArray(model.VaoID)
        GL20.glEnableVertexAttribArray(0)
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.VertexCount, GL11.GL_UNSIGNED_INT, 0)
        GL20.glDisableVertexAttribArray(0)
        GL30.glBindVertexArray(0)
    }
}