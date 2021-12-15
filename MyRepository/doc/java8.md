# 循环
## 下标
```
// list
IntStream.range(0, result.length).forEach(i -> result[i] = list.get(i));

// set
Iterator<Integer> iterator = set.iterator();
IntStream.range(0, result.length).forEach(i -> result[i] = iterator.next());
```