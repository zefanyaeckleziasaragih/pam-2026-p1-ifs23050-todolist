package services

import entities.Todo
import repositories.ITodoRepository

class TodoService(private val todoRepository: ITodoRepository) : ITodoService {
    override fun showTodos() {
        val todos = todoRepository.getAllTodos()

        println("Daftar Todo:")
        var counter = 0
        for (todo in todos) {
            counter++
            println(todo)
        }

        if (counter <= 0) {
            println("- Data todo belum tersedia!")
        }
    }

    override fun addTodo(title: String) {
        val newTodo = Todo(title = title)
        todoRepository.addTodo(newTodo)
    }

    override fun removeTodo(id: Int) {
        val success = todoRepository.removeTodo(id)
        if (!success) {
            println("[!] Gagal menghapus todo dengan ID: $id.")
            return
        }
    }

    override fun updateTodo(id: Int, newTitle: String, isFinished: Boolean) {
        val success = todoRepository.updateTodo(id, newTitle, isFinished)
        if (!success) {
            println("[!] Gagal memperbarui todo dengan ID: $id.")
            return
        }
    }

    override fun searchTodo(keyword: String) {
        val todos = todoRepository.searchTodo(keyword)

        var counter = 0
        for (todo in todos) {
            counter++
            println(todo)
        }

        if (counter <= 0) {
            println("- Data todo tidak ditemukan!")
        }
    }

    override fun sortTodo(criteria: String, isAscending: Boolean) {
        val todos = todoRepository.sortTodo(criteria, isAscending)

        var counter = 0
        for (todo in todos) {
            counter++
            println(todo)
        }

        if (counter <= 0) {
            println("- Data todo belum tersedia!")
        }
    }
}