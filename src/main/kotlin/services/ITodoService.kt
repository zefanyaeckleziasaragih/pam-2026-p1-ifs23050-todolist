package services

interface ITodoService {
    fun showTodos()
    fun addTodo(title: String)
    fun removeTodo(id: Int)
    fun updateTodo(id: Int, title: String, isFinished: Boolean)
    fun searchTodo(keyword: String)
    fun sortTodo(criteria: String, isAscending: Boolean)
}


