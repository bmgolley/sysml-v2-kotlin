package sandbox.api.model

import api.model.Record
import sandbox.model.DataVersion

class DataIdentity : Record() {
    val version: Set<DataVersion>
    
    val createdAt: Commit
        get()

    val deletedAt: Commit?
        get()
}
