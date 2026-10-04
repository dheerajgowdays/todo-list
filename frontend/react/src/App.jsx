import { useState } from 'react'
import './App.css'

function App() {
  const [todos, setTodos] = useState([
    {
      id: 1,
      title: 'Learn Java OOPs',
      description: 'Learn depth java',
      completed: false
    },
    {
      id: 2,
      title: 'learn Spring boot',
      description: 'Learn spring',
      completed: false
    }
  ])

  const [title, setTitle] = useState('')
  const [editingId, setEditingId] = useState(null)

  function addTodo() {
    if (title.trim() === '') return

    const newTodo = {
      id: Date.now(),
      title: title,
      description: '',
      completed: false
    }
    setTodos([...todos, newTodo])
    setTitle('')
  }

  function toggleTodo(id) {
    setTodos(
      todos.map((todo) =>
        todo.id === id ? { ...todo, completed: !todo.completed } : todo
      )
    )
  }

  function deleteTodo(id) {
    setTodos(todos.filter((todo) => todo.id !== id))
  }

  function editTodo(id) {
    setEditingId(id)
  }

  function saveTodo(id, newTitle) {
    if (newTitle.trim() === '') return
    setTodos(
      todos.map((todo) =>
        todo.id === id ? { ...todo, title: newTitle } : todo
      )
    )
    setEditingId(null)
  }

  return (
    <div>
      <h1>My Todo List</h1>

      <input
        type="text"
        placeholder="What do you need to do ?"
        value={title}
        onChange={(event) => setTitle(event.target.value)}
      />
      <button onClick={addTodo}>Add</button>

      <div>
        {todos.map((todo) => (
          <div key={todo.id}>
            {editingId === todo.id ? (
              <input
                defaultValue={todo.title}
                autoFocus
                onKeyDown={(event) => {
                  if (event.key === 'Enter') {
                    saveTodo(todo.id, event.target.value)
                  } else if (event.key === 'Escape') {
                    setEditingId(null)
                  }
                }}
                onBlur={(event) => saveTodo(todo.id, event.target.value)}
              />
            ) : (
              <>
                <h3>
                  {todo.completed ? '✅' : '⬜'} {todo.title}
                </h3>
                <p>{todo.description}</p>

                <button onClick={() => toggleTodo(todo.id)}>
                  {todo.completed ? 'Undo' : 'Complete'}
                </button>
                <button onClick={() => editTodo(todo.id)}>Edit</button>
                <button onClick={() => deleteTodo(todo.id)}>Delete</button>
              </>
            )}
          </div>
        ))}
      </div>
    </div>
  )
}

export default App