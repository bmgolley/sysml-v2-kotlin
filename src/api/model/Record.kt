package sandbox.api.model

import org.apache.jena.irix.IRIx
import kotlin.uuid.Uuid

abstract class Record {
    val id: Uuid
    var resourceIdentifier: IRIx?
    val alias: Set<String>
    var name: String?
    var description: String
}
