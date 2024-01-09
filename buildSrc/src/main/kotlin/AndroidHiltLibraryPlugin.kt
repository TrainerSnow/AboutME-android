import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidHiltLibraryPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        project.androidConfig()

        project.hiltAndroidPlugins()
        project.hiltDependencies()
    }

}