import microj
print(microj.backend)

class Odd:
    def __init__(self, n): self.n = n
    def __bool__(self): return self.n > 0

o = Odd(0)
if o: print("never")
print("ok1")
o2 = Odd(3)
if o2: print("truthy")

class Box:
    def __len__(self): return 0
b = Box()
if b: print("never2")
print("ok2")

print(1 and 2 and 3)      # 3
print(0 or "d" or 3)      # d
print(not 0, not 5)       # True False
assert Odd(0) or "fallback"
print(2 ** 100)           # bigint-путь
print("done")