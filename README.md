# Projekte in der Vorlesung Verteilte Verarbeitung (kurz VV) - SS2020

## Beschreibung der Verzeichnisse

* ReaktivesSystem: Dateien zum ersten Projekt in VV

## Beschreibung der einzelnen Projekten
* ReaktivesSystem: Bei diesem Projekt handelt es sich um eine Art von Dropbox. Es sollen Dateien überwacht werden. Wenn die Datei geändert wird, so soll dies synchronisiert werden. Dabei werden wir es einfach halten und nur bis zu dem Punkt, das er weiß, das er sychnronisieren muss. 

## Vorgehensweise der einzelnen Projekte
Reaktives System: 
* Schritt 1 - WatchedFile erstellen
* Schritt 2 - DirectoryWatcher erstellen
* Schritt 3 - WatchedDirectory erstellen
* Schritt 4 - Änderung der implementierten Klassen
* Schritt 5 - SyncServer erstellen
* Schritt 6 - Überprüfung des Codes 

## Übersicht des Automaten
* Insync --- sync : insync
* Insync --- create : modified
* Insync --- modify : modified
* Insync --- delete: deleted

* Created --- sync : insync
* created --- create: created
* created --- modify: created
* created --- delete: gone


* modified --- sync : insync
* modified --- create: modified
* modified --- modify: modified
* modified --- delete: deleted

* deleted --- sync: gone
* deleted --- create: modified
* deleted --- modify: modified
* deleted --- delete: deleted

* gone --- sync: gone
* gone --- create: created
* gone --- modify: gone
* gone --- delete: gone

## Hinweise zur Installation 
* Clonen von Git
