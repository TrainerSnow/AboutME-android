import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidFeaturePlugin: Plugin<Project> {

    override fun apply(project: Project) {
        project.androidConfig()
        project.composeConfig()

        project.hiltAndroidPlugins()

        project.hiltDependencies()
        project.composeDependencies()
        project.featureDependencies()
        project.testDependencies()
    }

}