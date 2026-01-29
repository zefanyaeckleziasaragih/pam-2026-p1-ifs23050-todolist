package repositories

import entities.Todo

class TodoRepository : ITodoRepository {
    val data = ArrayList<Todo>()

    override fun getAllTodos(): ArrayList<Todo> {
        return data
    }

    override fun addTodo(newTodo: Todo) {
        data.add(newTodo)
    }

    override fun removeTodo(id: Int): Boolean {
        val targetTodo = data
            .find { it.id == id }

        if (targetTodo == null) {
            return false
        }

        data.remove(targetTodo)
        return true
    }

    override fun updateTodo(id: Int, newTitle: String, isFinished: Boolean): Boolean {
        val targetTodo = data.find { it.id == id }

        if (targetTodo == null) {
            return false
        }

        targetTodo.title = newTitle
        targetTodo.isFinished = isFinished
        return true
    }

    override fun searchTodo(keyword: String): List<Todo> {
        return data.filter { it.title.contains(keyword, ignoreCase = true) }
    }

    override fun sortTodo(criteria: String): List<Todo> {
        return when (criteria.lowercase()) {
            "id" -> data.sortedBy { it.id }
            "title" -> data.sortedBy { it.title }
            "finished" -> data.sortedBy { it.isFinished }
            else -> data
        }
    }
}