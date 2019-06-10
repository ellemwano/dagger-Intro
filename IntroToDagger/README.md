# IntroToDagger

This project demonstrates the basics of Dagger2, covering the following topics: components, modules, scopes, @Inject annotation, subcomponents and dependency layering. 
There are also two flavors of the app, one which takes advantage of Dagger and one that does not. This is evident through the flavor specific classes, notably the Injector (Kotlin) file.

The project itself is a simple app which fetches and parses JSON data containing tv show episodes via a public api (https://www.tvmaze.com/api). 
The user can save/delete episodes to a favourites list, as well as view specific episode details.

