def gen():
    yield 1
    yield 2

for x in gen():
    print(x)