#№ AиСД Лабораторная работа №2
____
Даны прямоугольники на плоскости с углами в целочисленных координатах ([1..10^9],[1..10^9]).
Требуется как можно быстрее выдавать ответ на вопрос «Скольким прямоугольникам принадлежит точка (x,y)?», подготовка данных должна также занимать мало времени.
____
##№ Алгоритм полного перебора

```public int search(Point point) {
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
____
Сложность: подготовка данных `O(1)`, поиск `O(N * M)`, где N - количество прямоугольников, M - количество точек.

