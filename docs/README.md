# Avo 🥑 User Guide

Welcome to **Avo**, your friendly task management chatbot! Avo helps you keep track of todos, deadlines, and events, all from a simple chat interface.

---

## Getting Started

1. **Launch the app** (double-click the JAR or run via terminal).
2. Type your commands in the input box and press Enter or click Send.
3. Avo will respond in the chat window.

---

## Features & Commands

### 1. Add a Todo
Add a simple task with a description.
```
todo <task description>
```
**Example:**
```
todo buy groceries
```

### 2. Add a Deadline
Add a task with a due date.
```
deadline <task description> /by <yyyy-mm-dd>
```
**Example:**
```
deadline submit report /by 2026-02-20
```

### 3. Add an Event
Add a task that happens over a period of time.
```
event <task description> /from <start> /to <end>
```
**Example:**
```
event project meeting /from 2pm /to 4pm
```

### 4. List All Tasks
Show all your tasks.
```
list
```

### 5. Mark/Unmark a Task as Done
Mark a task as done:
```
mark <task number>
```
Unmark a task:
```
unmark <task number>
```
**Example:**
```
mark 2
unmark 2
```

### 6. Delete a Task
Remove a task from your list.
```
delete <task number>
```
**Example:**
```
delete 1
```

### 7. Find Tasks by Keyword
Search for tasks containing a keyword.
```
find <keyword>
```
**Example:**
```
find book
```

### 8. Show Tasks on a Specific Date
List all deadlines due on a specific date.
```
on <yyyy-mm-dd>
```
**Example:**
```
on 2026-02-20
```

### 9. Exit
Say goodbye to Avo.
```
bye
```

---

## Tips
- Dates must be in `yyyy-mm-dd` format (e.g., 2026-02-20).
- Task numbers refer to the number shown in the `list` command.
- Avo will highlight errors and guide you if you make a mistake.

---

## Example Session
```
todo read book
Added: [T][ ] read book

deadline return book /by 2026-02-20
Added: [D][ ] return book (by: Feb 20 2026)

list
1.[T][ ] read book
2.[D][ ] return book (by: Feb 20 2026)

mark 2
Task marked as done!
  [D][X] return book (by: Feb 20 2026)

bye
Bye! Avo is going back to sleep 😴
```

---

## Need Help?
Type any unknown command and Avo will show you the list of available commands.

---

Enjoy using **Avo** to organize your tasks! 🥑