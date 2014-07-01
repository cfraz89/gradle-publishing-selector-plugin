package au.org.trogdor.gradlerepositoryselector

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.internal.project.ProjectInternal

class RepositoryPlugin implements Plugin<Project> {
	void apply(Project project) {
        project.extensions.create("publishingRepository", RepositoryExtension, project)
        ((ProjectInternal)project).getConfigurationActions().add(new Action<ProjectInternal>() {
            void execute(ProjectInternal projectInternal) {
                if (projectInternal.plugins.hasPlugin('publishing')) {
                    def repositoryName = projectInternal.publishingRepository.repository
                    if (projectInternal.version.endsWith("-SNAPSHOT"))
                        repositoryName += projectInternal.publishingRepository.snapshotExtension

                    def artifactRepo = projectInternal.repositories.findByName(repositoryName)
                    if (artifactRepo)
                        projectInternal.publishing.repositories.add(artifactRepo)
                }
            }
        })
    }
}

class RepositoryExtension {
    private Project mProject
    private String mSnapshotExtension
    private String mRepository

    RepositoryExtension(project) {
        mProject = project
        mSnapshotExtension = '.snapshot'
        mRepository = ''
    }

    def getRepository() {
        mRepository
    }

    def getSnapshotExtension() {
        mSnapshotExtension
    }

    def repository(String repository) {
        mRepository = repository
    }

    def snapshotExtension(String snapshotExtension) {
        mSnapshotExtension = snapshotExtension
    }
}