package sandbox.api.model

import sandbox.model.Project

class DataVersion {
    val identity: DataIdentity
    val project: Project
        get()
    val commit: Commit
    val payload: Data?
}