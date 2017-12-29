package renderEngine

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import java.nio.FloatBuffer
import java.nio.IntBuffer

class Loader {
    val vaos = mutableListOf<Int>()
    val vbos = mutableListOf<Int>()

    fun loadToVAO(positions : FloatArray, indices: IntArray) : RawModel {
        val vaoID = createVAO()
        bindIndicesBuffer(indices)
        storeDataInAttributeLost(0, positions)
        unbindVAO()
        return RawModel(vaoID, indices.size)
    }

    fun cleanUp() {
        for (vao in vaos) {
            GL30.glDeleteVertexArrays(vao)
        }

        for (vbo in vbos) {
            GL15.glDeleteBuffers(vbo)
        }
    }

    private fun createVAO() : Int {
        val vaoID = GL30.glGenVertexArrays()
        vaos.add(vaoID)
        GL30.glBindVertexArray(vaoID)
        return vaoID
    }

    private fun storeDataInAttributeLost(attributeNumber : Int, data : FloatArray) {
        val vboID = GL15.glGenBuffers()
        vbos.add(vboID)
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID)

        val buffer = storeDataInFloatBuffer(data)
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW)
        GL20.glVertexAttribPointer(attributeNumber, 3, GL11.GL_FLOAT, false, 0, 0)

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0)
    }

    private fun bindIndicesBuffer(indices : IntArray) {
        val vboID = GL15.glGenBuffers()
        vbos.add(vboID)
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID)

        val buffer = storeDataInIntBuffer(indices)
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW)
    }

    private fun storeDataInIntBuffer(data : IntArray) : IntBuffer {
        val buffer = BufferUtils.createIntBuffer(data.size)
        buffer.put(data)
        buffer.flip()
        return buffer
    }

    private fun storeDataInFloatBuffer(data : FloatArray) : FloatBuffer {
        val buffer = BufferUtils.createFloatBuffer(data.size)
        buffer.put(data)
        buffer.flip()
        return buffer
    }

    private fun unbindVAO() {
        //Bind to 0, so unbind the VAO
        GL30.glBindVertexArray(0)
    }
}