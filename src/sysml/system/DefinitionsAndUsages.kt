@file:Suppress("unused")

package sandbox.sysml.system.definitionsandusages

import sandbox.kerml.core.classifiers.Classifier

interface Definition : Classifier {
    
}

interface ReferenceUsage : Usage {
    
}

interface Usage : Feature {
    
}

interface VariantMembership : OwningMembership {
    
}
