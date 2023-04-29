## AиСД Лабораторная работа №2
Даны прямоугольники на плоскости с углами в целочисленных координатах.
Требуется как можно быстрее определять количество прямоугольников, покрывающих точку на плоскости, подготовка данных также должна занимать мало времени.
____
### Реализация трех алгоритмов 
___
#### Алгоритм полного перебора
Не подразумевает специальную подготовку данных. Для каждой точки перебираются все прямоугольники.

```java
    public int search(Point point) {
        int count = 0;

        for (Rectangle rectangle : rectangles) {
            if (point.x >= rectangle.x1 && point.x <= rectangle.x2
                    && point.y >= rectangle.y1 && point.y <= rectangle.y2) {
                count++;
            }
        }

        return count;
    }
```

##### Сложность:
подготовка `O(1)`, поиск `O(N * M)`, где N - количество прямоугольников, M - количество точек.
____
#### Алгоритм сжатия координат и построение карты

Подготовка данных просиходит с помощью сжатия координат  всех угловых точек прямоугольников по осям х и y, создания и заполнения карты.
Создание карты - это создание матрицы размерности i х j, где i - количество сжатых точек по оси х, j - количество сжатых точек по оси у.
Заполнение карты - это обход всех прямоугольников и увеличение определенной проекции каждого прямоугольника на сжатых координатах на 1 в данной матрице.

```java
    public SecondAlgorithm(List<Rectangle> rectangles) {
        Set<Integer> setX = new HashSet<>();
        Set<Integer> setY = new HashSet<>();
        for (Rectangle r : rectangles) {
            setX.add(r.x1);
            setX.add(r.x2);
            setY.add(r.y1);
            setY.add(r.y2);
            setX.add(r.x2 + 1);
            setY.add(r.y2 + 1);
        }

        zipX = new ArrayList<>(setX);
        zipY = new ArrayList<>(setY);
        Collections.sort(zipX);
        Collections.sort(zipY);

        Map<Integer, Integer> coordsX = new HashMap<>();
        Map<Integer, Integer> coordsY = new HashMap<>();
        for (int i = 0; i < zipX.size(); i++) {
            coordsX.put(zipX.get(i), i);
        }
        for (int i = 0; i < zipY.size(); i++) {
            coordsY.put(zipY.get(i), i);
        }

        map = new int[zipX.size()][zipY.size()];
        for (Rectangle r: rectangles) {
            for (int i = coordsX.get(r.x1); i < coordsX.get(r.x2) + 1; i++) {
                for (int j = coordsY.get(r.y1); j < coordsY.get(r.y2) + 1; j++) {
                    map[i][j] ++;
                }
            }
        }
    }
```
Поиск - запрос к матрице map[i][j], где i - сжатая координата точки по х, j - сжатая координата точки по y. Сжатые координаты находятся с помощью бинарного поиска.
```java
 public int search(Point point) {
        int i = binSearch(point.x, zipX);
        int j = binSearch(point.y, zipY);
        if (i == -1 || j == -1) return 0;
        return map[i][j];
    }
```
##### Сложность:
подготовка `O(N^3)`, поиск `O(logN)`, где N - количество прямоугольников.
____
#### Алгоритм сжатия координат и построения персистентного дерева отрезков 

##### Сложность:
подготовка `O(NlogN)`, поиск `O(logN)`, где N - количество прямоугольников.
____


### Тестирование 
#### Генерация тестовых данных 

#### Результаты тестирования 
____
### Вывод




