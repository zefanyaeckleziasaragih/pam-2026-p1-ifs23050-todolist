package services

interface ITodoService {
    fun showTodos()
    fun addTodo(title: String)
    fun removeTodo(id: Int)
    fun updateTodo(id: Int, newTitle: String, isFinished: Boolean)
    fun searchTodo(keyword: String)
    fun sortTodo(criteria: String, isAscending: Boolean)
}