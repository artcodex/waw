package shaders
class StaticShader : ShaderProgram {
    constructor() : super(VERTEX_FILE, FRAGMENT_FILE)

    companion object {
        const val VERTEX_FILE = "src/shaders/vertexShader.vert"
        const val FRAGMENT_FILE = "src/shaders/fragmentShader.frag"
    }

    override fun bindAttributes() {
        super.bindAttribute(0, "position")
    }
}