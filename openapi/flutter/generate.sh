rm -rf api

java -jar ../openapi-generator-cli-5.1.1.jar generate -i ../food.yaml -g dart-dio-next -o api -c config.yaml

cd api

dart pub get

dart run build_runner build