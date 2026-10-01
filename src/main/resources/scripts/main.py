def f():
    raise ValueError("x")

try:
    f()                 # ← исключение из ВЫЗВАННОЙ функции
except ValueError:
    print("caught")
print("done")