mkdir bin/;

javac -d bin/ --release 11 src/com/moura/Application.java;
javac -d bin/ --release 11 src/com/moura/MetadataEditor.java;
javac -d bin/ -cp bin/ --release 11 src/MetadataEditor.java;

# Build Jar process
cd bin/;
jar -cmf ../MANIFEST.MF ../app.jar *.class com/moura/*.class;
cd ..;
rm -r bin/;
echo "\033[;32mApp build successufully";
