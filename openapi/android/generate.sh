java -jar ../openapi-generator-cli-5.1.1.jar generate -i ../food.yaml -g kotlin -o api -c config.yaml

cd api

chmod +x gradlew

./gradlew build

mv build/libs/*.jar ../

rm -rf ../api
