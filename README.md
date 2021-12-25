# Домашнее задание на аутсорс

### Как работает

Программе необходимо передать 1 обязательный аргумент командной строки (имя файла) и 1 опциональный (тип отображения списков)
<pre>./myapp filename.txt --verbose=none</pre>

Параметр <pre>--verbose</pre> поддерживает 3 режима:
* <pre>--verbose=default</pre> - отображаются списки учеников каждого класса (решение 1-2 пункта задачи)
* <pre>--verbose=none</pre> - отображаются оценки лишь по предмету, введенному пользователем (решение 3 пункта задачи)
* <pre>--verbose=subject</pre> - отображаются оценки для каждого класса по конкретному предмету (решение 4 пункта задачи)