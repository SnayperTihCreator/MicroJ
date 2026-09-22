def divide(a, b):
    return a / b

try:
    divide(10, 0)
except ZeroDivisionError as e:
    print("Caught:", e)