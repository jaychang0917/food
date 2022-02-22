# todo: openapi kotlin-spring generator cannot build a jar.
java -jar ../openapi-generator-cli-5.1.1.jar generate -i ../food.yaml -g kotlin-spring -o api -c config.yaml

#cd api

#gradle build

#mv build/libs/*.jar ../

#rm -rf ../api
