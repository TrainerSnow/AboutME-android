import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidComposeLibraryPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        project.androidConfig()
        project.composeConfig()

        project.composeDependencies()
    }

}