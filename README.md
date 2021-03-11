# compiler
Project for Formal Languages and Compilers course

### compose-validator
Simplified syntax validator for a subset of docker-compose yaml files.

Supported keywords: version, services, build, ports, image, volumes, environment, networks, deploy.

Task description (in Polish):
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
