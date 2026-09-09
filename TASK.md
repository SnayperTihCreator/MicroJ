### 🔥 Группа 1 — Quick Wins
**Срочность: максимальная | Сложность: низкая**

Фичи, которые закрывают 80% типичных скриптов, но делаются за 1-2 вечера.

| Фича | Что нужно сделать | Реализовано |
|---|---|---|
| `in` / `not in` | Протокол `__contains__` + инструкция `Contains` | ✅ |
| `enumerate`, `zip`, `map`, `filter` | Встроенные функции на Java (~20 строк каждая) |
| `sum`, `min`, `max`, `abs`, `round`, `divmod` | Встроенные функции |
| `isinstance`, `hasattr`, `getattr`, `setattr`, `callable` | Встроенные + `PyClass` доработка |
| `chr`, `ord`, `bin`, `oct`, `hex` | Встроенные |
| `dict.items()`, `dict.pop()`, `dict.update()` | Методы `PyDict` |
| `str.join()`, `str.format()` | Методы `PyString` |
| `assert` | Инструкция `Assert` + парсер |
| `%`, `//` | Операторы + `SmartInt`/`SmartFloat` методы |
| `tuple` unpacking в `for` | `for a, b in lst:` — уже есть `UnpackSequence`, нужен парсер |

---

### 🔥 Группа 2 — Must-Have
**Срочность: максимальная | Сложность: средняя**

Без этого скриптовый движок буквально ломается на половине реальных скриптов.

| Фича | Что нужно сделать |
|---|---|
| **Slices** `a[1:3]`, `a[::-1]` | Тип `PySlice`, доработка `BinarySubscript`/`StoreSubscript`, парсер |
| **Default args**, `*args`, `**kwargs` | Парсер + `PyFunction` + `CallFunction` + `MakeFunction` |
| **`lambda`** | Безымянная функция в компиляторе (почти как `def`, но auto-return) |
| **`global` / `nonlocal`** | Новая инструкция + разрешение имён в `LoadName`/`StoreName` |
| **`with`** + `__enter__` / `__exit__` | Инструкции `SetupWith`/`PopWith` + протокол |
| **`open()`** + файловый I/O | Класс `PyFile` + модуль `io` |
| **`json`** модуль | Обёртка над `com.fasterxml.jackson` или `org.json` |

---

### 🔥 Группа 3 — Core Architecture
**Срочность: высокая | Сложность: высокая**

Меняют архитектуру, но без них ООП и функциональщина не работают.

| Фича | Что нужно сделать |
|---|---|
| **Наследование классов** | MRO (C3 linearization), `super()`, `isinstance`, `issubclass` |
| **List / dict / set comprehensions** | Компилятор: `listcomp` функция + `yield`-подобный механизм |
| **`yield` + generators** | Persistent frame state в VM (frame не удаляется при `yield`) |
| **`f-strings`** `f"hello {x+1}"` | Парсер должен лексировать выражения внутри `{}` |
| **`@property`, `@staticmethod`, `@classmethod`** | Дескрипторы — новый протокол + `PyProperty` |
| **Slicing assignment** `a[1:3] = [4,5]` | `StoreSubscript` + `PySlice` |

---

### 💧 Группа 4 — Quality of Life
**Срочность: низкая | Сложность: низкая**

Делаются легко, но скрипт и так запустится без них.

| Фича | Что нужно сделать | Реализовано |
|---|---|-------------|
| `list + list`, `list * n` | `__add__` / `__mul__` в `PyList` | ✅           |
| `reversed()`, `sorted()`, `any()`, `all()` | Встроенные функции |
| `str` методы: `lstrip`, `rstrip`, `partition`, `encode` | Методы `PyString` |
| `tuple.count()`, `tuple.index()` | Методы `PyTuple` |
| Модуль `random` | `java.util.Random` обёртка |
| Модуль `datetime` | `java.time` обёртка |
| `dict \| dict` (Python 3.9) | `__or__` в `PyDict` |
| `str.startswith(tuple)` | Перегрузка существующего метода |

---

### 💧 Группа 5 — Major Features
**Срочность: низкая | Сложность: высокая**

Крупные куски функциональности. Добавлять, когда основа стабильна.

| Фича | Что нужно сделать |
|---|---|
| **Multiple inheritance** | Полноценный C3 MRO + conflict resolution |
| **`match/case`** (Python 3.10) | Новый парсер + pattern matching VM |
| **`:=` walrus operator** | Новая инструкция + парсер |
| **`async` / `await`** | Корутины — task-based VM уже готов, но нужен event loop |
| **Полный `re`** | Regex engine (можно обернуть `java.util.regex`) |
| **`os`, `pathlib`, `subprocess`** | OS-обёртки |
| **`collections`** (Counter, defaultdict, deque) | Модули |

---

### 💧 Группа 6 — Sugar
**Срочность: низкая | Сложность: средняя**

Синтаксический сахар. Приятно, но не критично.

| Фича | Что нужно сделать |
|---|---|
| Ternary `x if c else y` | Парсер + компилятор (if-expr) |
| `raise exc from cause` | Расширение `PyBaseException` + парсер |
| Multiple `with`: `with a as x, b as y` | Парсер + цепочка `SetupWith` |
| `...` (Ellipsis) | Синглтон `PyEllipsis` |
| `try/except* ExceptionGroup` | Python 3.11+ фича |

---

## Рекомендуемый порядок

```
Группа 1 (2-3 дня) → Группа 2 (1-2 недели) → Группа 3 (2-3 недели)
         ↓
    После этого твой движок запускает 90% реальных скриптов
         ↓
Группа 4 (по настроению) → Группа 5 (когда нужно) → Группа 6 (если скучно)
```