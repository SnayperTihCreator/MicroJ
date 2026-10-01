print("start")
try:
    raise ValueError("v")
except BaseException:
    print("1: ok")
try:
    raise SystemExit(0)
except SystemExit:
    print("2: ok")
try:
    try:
        raise ValueError("i")
    except ValueError:
        raise
except ValueError as e:
    print("3: ok", e)
try:
    try:
        raise SystemExit(0)
    except Exception:
        print("never")
except BaseException:
    print("4: ok")
print("end")