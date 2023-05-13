# Flight-search-app

Flight search - это приложение для поиска авиарейсов, в котором пользователи вводят информацию об аэропорте и могут просматривать список пунктов назначения, используя этот аэропорт в качестве пункта отправления. 

В приложение демонстирует использование:
* Библиотеки Room для работы с БД
* OutlinedTextField с фильтром результата поиска
* ViewModel
* Navigation
* Flow

# Реализация
Когда пользователь впервые открывает приложение, он видит пустой экран с текстовым полем, запрашивающим аэропорт.

Чтобы сгенерировать рейсы, необходимо нажать на FloatingActionButton в нижней части экрана, с помощью которой сгенерируется 100 авиарейсов и удаляться Избранные рейсы (если такие были).

Когда пользователь начинает вводить текст, приложение отображает список предложений автозаполнения, которые соответствуют либо названию, либо идентификатору аэропорта.

Когда пользователь выбирает рейс, приложение отображает список всех возможных рейсов из этого аэропорта. Каждый элемент содержит идентификатор и названия обоих аэропортов, а также кнопку для сохранения пункта назначения в качестве избранного. 

После на главном экране, если поисковый запрос не введен, будут отображаться все выбранные пользователем избранные маршруты в виде списка.

# Данные
Flight search имеет три локальные базы данных:

1. Airport - БД содержащая описание аэропортов. Это БД данная сразу и не изменяется

| id | iataCode | name | passengers |
|-----|:---------:|:---------:|:---------:|
| Int | String | String | Int |

2. flights - БД содержащая информация о рейсе по iata коду двух аэропортов. Первоначально она пуста, пока её не сгенерируют.

| id | departure_code | destination_code |
|-----|:---------:|:---------:|
| Int | String | String |

3. favorite - БД содержащая информация о избранных рейсах. Первоночально пуста.

| id | departure_code | destination_code |
|-----|:---------:|:---------:|
| Int | String | String |

# Скриншоты
