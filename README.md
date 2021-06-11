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
isum = 1 + 3;
idifference = 69 - 9;
iproduct = 4 * 4;
iquotient = 69 / 13;
imodulo = 17 % 5;
dsum = 1.0 + 3.0;
ddifference = 69.0 - 9.0;
dproduct = 4.0 * 4.0;
dquotient = 69.0 / 13.0;
dmodulo = 17.0 % 5.0;
```

#### Increment/decrement
```
x = 1;
y = 1.0;
int x++;
double y++;
int x--;
double y--;
```

#### Comments
```
#"Comments are kinda strange";
```

### If statements (WIP)
```
if (expression) {
    #"do sth if expression is true";
} else {
    #"do sth if expression is false";
}
```

### While loop
```
while (expression) {
    #"do sth as long as expression is true";
}
```

Example
```
i = 5;
while (i>1) {
    print i;
    int i--;
}
```
Running the above code snippet would result in printing this to the console
```
5
4
3
2
```


### Functions (WIP)
```
def function_name() {
    #"do sth";
}
```

### Local variables (WIP)
```

```

### Global variables (WIP)
```

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
