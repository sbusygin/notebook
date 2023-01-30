# Проект "NOTEPAD"

Проект написан с использованием javax.swin

Использует одну внешную зависимость - 
org.apache.commons
commons-text
version 1.9


### Для создания исполняемого файла использовать команды:
#### 1. Сборка проекта
```
mvn clean package
```
#### 2. Переместим созданный jar-файл в новый каталог target/, где он будет отделен от других файлов
Если нет папки build - то создаем ее
```
mkdir build
```
перемещаем jar файл в target
```
mv target/notepad-1.0-SNAPSHOT.jar build
```
#### 3. С помощью jlink создаем “настраиваемый исполняемый образ”
```
jlink --add-modules ALL-MODULE-PATH --output build/runtime
```
#### 4. Заархивируйте каталог, содержащий JRE и jar notepad приложения.

#### 5. Прикрепите сценарий-заглушку (stub script) к верхней части этого zip-файла, который извлечет данные во временный каталог и запустит код.

#### 6. Cоздаем исполняемый файл с именем “application”. Если вы создаете его для Windows, то нужно указать “application.exe”. Когда исполняемый файл запускается, {{caxa}} будет заменен на временный каталог, в котором был развернут zip-файл.
```
npx caxa \
    --input build \
    --output application \
    --no-include-node \
    -- "{{caxa}}/runtime/bin/java" "-jar" "{{caxa}}/notepad-1.0-SNAPSHOT.jar"
```