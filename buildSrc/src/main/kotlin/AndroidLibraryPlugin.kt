import org.gradle.api.Plugin
import org.gradle.api.Project


class AndroidLibraryPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        project.androidPlugins()
        project.androidConfig()

        project.testDependencies()
    }

}