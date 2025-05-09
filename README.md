# PPJ 2025 - Chmelař

## přidání dat:

### stránka pro přidání:
http://localhost:8080/

### REST API:
http://localhost:8080/weather?lat=50.4564&lon=15.56465

## průměrná teplota:

### zobrazení na stránce:
http://localhost:8080/prumer

### REST API:
http://localhost:8080/prumer/den
http://localhost:8080/prumer/tyden
http://localhost:8080/prumer/14dni

## editace:

### editace stránka + možnost smazání:
http://localhost:8080/edit

### REST API:
(záznam s id = 5 nastavení humidity na 91)
http://localhost:8080/edit/5/humidity=91

(country s id = 4 upravit název na Yemen)
http://localhost:8080/country/edit/4/name=Yemen

(city s id = 4 upravit na ArRaydah)
http://localhost:8080/city/edit/4/name=ArRaydah

## Vypsání dat z databaze:
http://localhost:8080/weatherSQL
