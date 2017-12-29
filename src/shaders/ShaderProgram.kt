package shaders

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

abstract class ShaderProgram {
    private var programID = 0
    private var vertextShaderID = 0
    private var fragmentShaderID = 0

    constructor(vertexFile : String, fragmentFile : String) {
        vertextShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER)
        fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER)
        programID = GL20.glCreateProgram()

        GL20.glAttachShader(programID, vertextShaderID)
        GL20.glAttachShader(programID, fragmentShaderID)
        GL20.glLinkProgram(programID)
        GL20.glValidateProgram(programID)
        bindAttributes()
    }

    protected abstract fun bindAttributes()
    protected fun bindAttribute(attribute : Int, variableName : String) {
        GL20.glBindAttribLocation(programID, attribute, variableName)
    }

    companion object {
        private fun loadShader(file : String, type : Int) : Int {
            val shaderSource = StringBuilder()
            try {
                val file = File(file)
                file.forEachLine {
                    shaderSource.append(it).append("//\n")
                }
            } catch (e: IOException) {
                e.printStackTrace()
                System.exit(-1)
            }

            val shaderID = GL20.glCreateShader(type)
            GL20.glShaderSource(shaderID, shaderSource)
            GL20.glCompileShader(shaderID)
            if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
                System.out.println(GL20.glGetShaderInfoLog(shaderID, 500))
                System.err.println("Could not compile shader!")
                System.exit(-1)
            }
            return shaderID
        }
    }

    fun start() {
        GL20.glUseProgram(programID)
    }

    fun stop() {
        GL20.glUseProgram(0)
    }

    fun cleanUp() {
        stop()
        GL20.glDetachShader(programID, vertextShaderID)
        GL20.glDetachShader(programID, fragmentShaderID)
        GL20.glDeleteShader(vertextShaderID)
        GL20.glDeleteShader(fragmentShaderID)
        GL20.glDeleteProgram(programID)
    }
}