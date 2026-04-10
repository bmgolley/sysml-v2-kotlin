package sandbox.util

@Suppress("JavaDefaultMethodsNotOverriddenByDelegation")
class ObservableList<E>(
    private val wrapped: MutableList<E>,
    val listeners: List<Listener<E>>
) : MutableList<E> by wrapped {
    override fun add(element: E): Boolean {
        add(size, element)
        return true
    }

    override fun add(index: Int, element: E) {
        wrapped.add(index, element)
        listeners.onAdd(element)
    }

    override fun clear() {
        val removed = toList()
        wrapped.clear()

        for (element in removed) {
            listeners.onRemove(element)
        }

    }

    override fun addAll(elements: Collection<E>): Boolean = addAll(size, elements)
    override fun addAll(index: Int, elements: Collection<E>): Boolean = if (wrapped.addAll(index, elements)) {
        for (element in elements) {
            listeners.onAdd(element)
        }
        true
    } else {
        false
    }

    override fun remove(element: E): Boolean = if (wrapped.remove(element)) {
        listeners.onRemove(element)
        true
    } else {
        false
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        val removed = toMutableList()
        removed.retainAll(removed)
        return if (wrapped.removeAll(elements)) {
            for (element in removed) {
                listeners.onRemove(element)
            }
            true
        } else {
            false
        }
    }

    override fun removeAt(index: Int): E = wrapped.removeAt(index).also { listeners.onRemove(it) }

    override operator fun set(index: Int, element: E): E =
        wrapped.set(index, element).also { listeners.onChange(it, element) }

    private fun Iterable<Listener<E>>.onAdd(element: E) {
        for (listener in this) {
            listener.onAdd(element)
        }
    }

    private fun Iterable<Listener<E>>.onRemove(element: E) {
        for (listener in this) {
            listener.onRemove(element)
        }
    }

    private fun Iterable<Listener<E>>.onChange(oldValue: E, newValue: E) {
        for (listener in this) {
            listener.onChange(oldValue, newValue)
        }
    }

    interface Listener<E> {
        fun onAdd(element: E) {}
        fun onRemove(element: E) {}
        fun onChange(oldValue: E, newValue: E) {
            onRemove(oldValue)
            onAdd(newValue)
        }
    }
}
