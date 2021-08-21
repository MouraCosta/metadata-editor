mkdir bin/;

javac -d bin/ src/com/moura/Application.java;
javac -d bin/ -cp bin/ src/MetadataEditor.java;

# Build Jar process
cd bin/;
jar -cmf ../MANIFEST.MF ../metadataEditorApp.jar *.class com/moura/*.class;
cd ..;
rm -r bin/;
echo "\033[;32mApp build successufully";