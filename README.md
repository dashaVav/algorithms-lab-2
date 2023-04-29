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

Подготовка данных происходит с помощью сжатия координат  всех угловых точек прямоугольников по осям х и y, создания и заполнения карты. Создание карты - это создание матрицы размерности i х j, где i - количество сжатых точек по оси х, j - количество сжатых точек по оси у. Заполнение карты - это обход всех прямоугольников и увеличение определенной проекции каждого прямоугольника на сжатых координатах на 1 в данной матрице.  
  
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
Подготовка данных происходит с помощью построения персистентного дерева отрезков на сжатых координатах (создается PersistentSegmentTreeNodes, в котором хранятся корни деревьев).  
  
Поиск - получения нужно корня дерева по сжатым координатам точки и обход данного дерева.  
```java
    public int search(int x, int y){
        return searchInPersistentSegmentTree(PersistentSegmentTreeNodes.get(x), y);
    }

    private int searchInPersistentSegmentTree(Node root, int num) {
        if (root == null) return 0;

        int middle = (root.leftRange + root.rightRange) / 2;
        if (num < middle) {
            return root.sum + searchInPersistentSegmentTree(root.left, num);
        }
        else return root.sum + searchInPersistentSegmentTree(root.right, num);
    }
```
##### Сложность:
подготовка `O(NlogN)`, поиск `O(logN)`, где N - количество прямоугольников.
____
### Тестирование и выводы 
#### Тестирование
##### Генерация прямоугольников
Создается по формуле `{(10*i, 10*i), (10*(2N-i), 10*(2N-i))}` для сордания набора вложенных дрруг в друга прмяугольников 
```java
private static List<Rectangle> generateTestRectangles(int n){
        List<Rectangle> rectangles= new ArrayList<>();
        for (int i = 0; i < n; i++) {
            rectangles.add(new Rectangle(10 * i, 10 * i, 10 * (2 * n - 1), 10 * (2 * n - 1)));
        }
        return rectangles;
    }
```
##### Генерация точек:
Создается с неслучайный набор распределенных равномерно по ненулевому пересечению прямоугольников, с помощью хэш функции `(p*i)^31%(20*N)` 
от i с разным базисом для x и y, p-большое простое, разное для x и y.
```java
    private static List<Point> generateTestPoints(int n){
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int x = (int) Math.pow(1009 * i, 31) % (20 * n);
            int y = (int) Math.pow(1013 * i, 31) % (20 * n);
            points.add(new Point(x, y));
        }
        return points;
    }
```
При запуске [main]([http://sabaka.net](https://github.com/dashaVav/algorithms_lab2/blob/master/src/main/java/tests/TestGenerator.java)) используютс тестовые данные. Результаты тестирования записываются в csv-файлы, для каждого алгоритма свой файл.
Для посторения графиков используется [draw_graphs.py](https://github.com/dashaVav/algorithms_lab2/blob/master/artefacts/draw_graphs.py)
#### Выводы

![BuildTime.jpg](https://github.com/dashaVav/algorithms_lab2/blob/master/artefacts/BuildTime.jpg)





