# compiler
Project for Formal Languages and Compilers course

### Language manual
#### Basic printing
```
print 10;
print 10.11;
print "string works as well";
```
#### Variable assignment
```
stringVar = "matlab";
intVar = 10;
doubleVar = 10.10;
```

#### Variable printing
```
stringVar = "matlab";
intVar = 10;
doubleVar = 10.10;
print stringVar;
print intVar;
print doubleVar;
```

#### Variable input
```
input int i;
input double d;
```

#### INT<->DOUBLE casting
```
a = 69;
b = 69 (double);
c = 6.9;
d = 6.9 (int);
```

#### Arithmetics
```
sum = 1 + 3;
difference = 69 - 9;
product = 4 * 4;
quotient = 69 / 13;
```

#### Increment/decrement
```
x = 1;
y = 1.0;
x++;
y++;
x--;
y--;
```

#### Comments
```
#"Comments are kinda strange";
```

### compose-validator
Simplified syntax validator for a subset of docker-compose yaml files.

Supported keywords: version, services, build, ports, image, volumes, environment, networks, deploy.

Task description (in Polish, will be translated one day):
```
Projekt 1: Uproszczony walidator dla podzbioru składni plików docker compose w języku YAML. 
Minimalny zakres sprawdzanej składni powinien obejmować konstrukcje wykorzystujące słowa kluczowe: 
version, services, build, ports, image, volumes, environment, networks i deploy.

Uwaga! Obrona projektu na pierwszym terminie zajęć. Projekt omawiamy będzie na wykładzie.

Projekt można pisać w dowolnym języku programowania, ale koncepcyjnie musi to być zgodne z załączonym przykładem. 
Zwrócić uwagę na:
- analiza  składniowa poprzez rekurencyjne wywołanie funkcji 
  (symbole nieterminalne to nazwy funkcji/metod),
- analiza leksykalna wyraźnie oddzielona od analizy składniowej,
- analiza leksykalna zrealizowana w dowolny sposób 
  (while-if-switch, prosty automat skończony, wyrażenia regularne),
- możliwie precyzyjne komunikaty o błędach

Na obronę projektu proszę przynieść wersję zaprojektowanej gramatyki w notacji BNF.
```
