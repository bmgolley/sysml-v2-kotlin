@file:Suppress("unused")

package sandbox.featurechains.kerml.root.annotations

import sandbox.kerml.root.annotations.Comment

val Iterable<Comment>.body: List<String>
    get() = map(Comment::body)

val Iterable<Comment>.locale: List<String?>
    get() = map(Comment::locale)
