# Docs

This is a pure ~~java~~ kotlin application that generates the data required for keyboard layout documentation

## Running
* `./gradlew run --args="path/to/layout/xml/file path/to/output/json"`
* Alternatively, run `./generate.sh` which will build the application, and convert all the layouts in `../../java/res/xml` to the folder `../../docs/generated`