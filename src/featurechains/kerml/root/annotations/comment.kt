@file:Suppress("unused")

package sandbox.featurechains.kerml.root.annotations

import sandbox.kerml.root.annotations.Comment

/** @see Comment.body */
val Iterable<Comment>.body: List<String>
    get() = map(Comment::body)

/** @see Comment.locale */
val Iterable<Comment>.locale: List<String?>
    get() = map(Comment::locale)
