# 1. deep chain
def inner():
    raise ValueError("deep")

def middle():
    inner()
    print("never1")

try:
    middle()
except ValueError as e:
    print("caught:", e)
print("done")

# 2. forward ref
def a():
    return b() + 1

def b():
    return 1
print(a())                          # 2

# 3. настоящая ячейка
def outer():
    x = 10
    def get():
        return x
    return get()
print(outer())                      # 10

# 4. цепочка ячеек
def l1():
    x = 5
    def l2():
        def l3():
            return x
        return l3()
    return l2()
print(l1())                         # 5