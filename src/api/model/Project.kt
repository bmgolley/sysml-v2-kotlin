package sandbox.api.model

import api.model.Record
import sandbox.api.model.DataIdentity
import javax.management.Query
import kotlin.time.Instant

class Project : Record() {
    val queries: Set<Query>
    override var name: String
    val created: Instant

    val identifiedData: Set<DataIdentity>
        get()

    val commits: Set<Commit>
    val commitReferences: Set<CommitReference>
    val defaultBranch: Branch
    val branches: Set<Branch> = mutableSetOf(defaultBranch)
}