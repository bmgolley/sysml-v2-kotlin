@file:Suppress("unused")

package sandbox.sysml.system

import sandbox.kerml.core.Classifier
import sandbox.kerml.core.Feature
import sandbox.kerml.root.OwningMembership

interface Definition : Classifier {
    
}

interface ReferenceUsage : Usage {
    
}

interface Usage : Feature {
    
}

interface VariantMembership : OwningMembership {
    
}
